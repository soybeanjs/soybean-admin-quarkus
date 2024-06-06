package cn.soybean.system.infrastructure.security

import cn.soybean.infrastructure.config.consts.AppConstants
import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.SecurityIdentityAugmentor
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.jwt.JsonWebToken
import org.redisson.api.RedissonClient
import java.security.Permission

@ApplicationScoped
class PermissionsIdentityAugmentor(private val redissonClient: RedissonClient) : SecurityIdentityAugmentor {

    override fun augment(identity: SecurityIdentity, context: AuthenticationRequestContext): Uni<SecurityIdentity> =
        when {
            isAnonymous(identity) -> Uni.createFrom().item(identity)

            isNotSystemUser(identity) -> Uni.createFrom().item(identity)

            else -> Uni.createFrom().item(build(identity))
        }

    private fun isAnonymous(identity: SecurityIdentity): Boolean = identity.isAnonymous

    /**
     * 目前强行在登录时候塞入一个角色标识位，暂时性的解决方案
     * 对端【小程序，移动端，pc。。。】等业务逻辑判断不够充分
     * todo 待重构
     * see [AppConstants.APP_COMMON_ROLE]
     */
    private fun isNotSystemUser(identity: SecurityIdentity): Boolean =
        !identity.roles.contains(AppConstants.APP_COMMON_ROLE)

    private fun build(identity: SecurityIdentity): SecurityIdentity = when (val principal = identity.principal) {
        is JsonWebToken -> {
            val userId = principal.subject.toLong()
            val permissionsKey = "${AppConstants.APP_PERM_ACTION_CACHE_PREFIX}:$userId"

            val permissions = redissonClient.getSet<String>(permissionsKey)
            when {
                permissions.isNullOrEmpty() -> identity
                else ->
                    // 创建一个新的SecurityIdentity，增加权限检查器
                    QuarkusSecurityIdentity.builder(identity)
                        .addPermissionChecker { requiredPermission: Permission ->
                            // 检查所需权限是否在用户权限列表中
                            val requiredPermName = requiredPermission.name
                            val actionsList = requiredPermission.actions?.split(",")
                                ?.filterNot { it.isBlank() }
                                ?: emptyList()
                            val accessGranted = if (actionsList.isEmpty()) {
                                // 如果没有指定任何动作，只需检查权限名即可
                                permissions.contains(requiredPermName)
                            } else {
                                // 检查是否至少有一个指定的动作存在于userPerms中
                                actionsList.any { action ->
                                    permissions.contains("$requiredPermName:$action")
                                }
                            }
                            Uni.createFrom().item(accessGranted)
                        }.build()
            }
        }

        else -> identity
    }
}
