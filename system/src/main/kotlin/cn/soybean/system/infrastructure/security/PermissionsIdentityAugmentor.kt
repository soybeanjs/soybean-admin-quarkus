package cn.soybean.system.infrastructure.security

import cn.soybean.infrastructure.config.consts.AppConstants
import io.quarkus.redis.client.RedisClientName
import io.quarkus.redis.datasource.ReactiveRedisDataSource
import io.quarkus.security.identity.AuthenticationRequestContext
import io.quarkus.security.identity.SecurityIdentity
import io.quarkus.security.identity.SecurityIdentityAugmentor
import io.quarkus.security.runtime.QuarkusSecurityIdentity
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.jwt.JsonWebToken

@ApplicationScoped
class PermissionsIdentityAugmentor(@RedisClientName("sign-redis") private val reactiveRedisDataSource: ReactiveRedisDataSource) :
    SecurityIdentityAugmentor {

    override fun augment(identity: SecurityIdentity, context: AuthenticationRequestContext): Uni<SecurityIdentity> =
        when {
            isAnonymous(identity) -> Uni.createFrom().item(identity)

            isNotSystemUser(identity) -> Uni.createFrom().item(identity)

            else -> augmentIdentity(identity)
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

    private fun augmentIdentity(identity: SecurityIdentity): Uni<SecurityIdentity> =
        when (identity.principal) {
            is JsonWebToken -> {
                val principal = identity.principal as JsonWebToken
                val userId = principal.subject.toLong()
                val permissionsKey = "${AppConstants.APP_PERM_ACTION_CACHE_PREFIX}:$userId"
                val commands = reactiveRedisDataSource.set(String::class.java)

                commands.smembers(permissionsKey)
                    .flatMap { permissions ->
                        when {
                            permissions.isNullOrEmpty() -> Uni.createFrom().item(identity)
                            else -> buildSecurityIdentity(identity, permissions)
                        }
                    }
            }

            else -> Uni.createFrom().item(identity)
        }

    private fun buildSecurityIdentity(identity: SecurityIdentity, permissions: Set<String>): Uni<SecurityIdentity> {
        val identityBuilder = QuarkusSecurityIdentity.builder(identity)
        identityBuilder.addPermissionChecker { requiredPermission ->
            val requiredPermName = requiredPermission.name
            val actionsList = requiredPermission.actions?.split(",")?.filterNot(String::isBlank) ?: emptyList()
            val accessGranted =
                when {
                    actionsList.isEmpty() -> permissions.contains(requiredPermName)
                    else -> actionsList.any { action ->
                        permissions.contains("$requiredPermName:$action")
                    }
                }
            Uni.createFrom().item(accessGranted)
        }
        return Uni.createFrom().item(identityBuilder.build())
    }
}
