package cn.soybean.system.infrastructure.web

import cn.soybean.system.infrastructure.common.generateOperationId
import cn.soybean.system.infrastructure.dto.ApiEndpoint
import io.quarkus.security.PermissionsAllowed
import jakarta.ws.rs.HttpMethod
import jakarta.ws.rs.Path
import jakarta.ws.rs.container.DynamicFeature
import jakarta.ws.rs.container.ResourceInfo
import jakarta.ws.rs.core.FeatureContext
import jakarta.ws.rs.ext.Provider
import org.eclipse.microprofile.openapi.annotations.Operation
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.kotlinFunction

@Provider
class ApiEndpointDynamicFeature : DynamicFeature {
    companion object {
        val apiEndpoints = mutableSetOf<ApiEndpoint>()
    }

    override fun configure(resourceInfo: ResourceInfo, context: FeatureContext) {
        val resourceClass = resourceInfo.resourceClass.kotlin
        val resourceMethod = resourceInfo.resourceMethod.kotlinFunction

        val classPath = resourceClass.findAnnotation<Path>()?.value ?: ""
        val methodPath = resourceMethod?.findAnnotation<Path>()?.value ?: ""
        val fullPath =
            if (classPath.isNotEmpty() && methodPath.isNotEmpty()) "$classPath$methodPath" else classPath + methodPath

        val httpMethod = resourceMethod?.javaMethod?.declaredAnnotations?.find { annotation ->
            annotation.annotationClass.annotations.any { it is HttpMethod }
        }?.annotationClass?.simpleName

        val operationAnnotation = resourceMethod?.findAnnotation<Operation>()
        val summary = operationAnnotation?.summary
        val description = operationAnnotation?.description

        val permissionsAllowedAnnotation = resourceMethod?.findAnnotation<PermissionsAllowed>()
        val permissionsAllowedValue = permissionsAllowedAnnotation?.value?.joinToString(separator = ", ")
        val permissionsAllowedInclusive = permissionsAllowedAnnotation?.inclusive

        if (fullPath.isNotEmpty() && httpMethod != null) {
            val apiEndpoint = ApiEndpoint(
                path = fullPath,
                httpMethod = httpMethod,
                summary = summary,
                description = description,
                permissions = permissionsAllowedValue,
                inclusive = permissionsAllowedInclusive,
                operationId = generateOperationId(httpMethod, fullPath)
            )
            apiEndpoints.add(apiEndpoint)
        }
    }
}