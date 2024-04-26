package cn.soybean

import io.opentelemetry.instrumentation.annotations.WithSpan
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import org.apache.camel.ProducerTemplate
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.rest.RestBindingMode

@ApplicationScoped
class CaffeineCacheRoute : RouteBuilder() {

    override fun configure() {
        restConfiguration()
            .component("platform-http")
            .host("{{quarkus.http.host}}")
            .port("{{quarkus.http.port}}")
            .contextPath("camel")
            .bindingMode(RestBindingMode.auto)

        from("rest:get:rest/hello")
            .transform().constant("Hello World from Camel")
            .log("Current thread name: \${threadName}")

        from("rest:get:rest/echo")
            .transform().simple("\${header.name}")
            .log("Received query parameter: \${header.name}")

        from("rest:get:rest/echo/{param}")
            .transform().simple("\${header.param}")
            .log("Received path parameter: \${header.param}")

        from("direct:cachePut")
            .routeId("caffeineCacheRoute")
            .setHeader("CamelCaffeineAction", constant("PUT"))
            .setHeader("CamelCaffeineKey", simple("\${header.CamelCaffeineKey}"))
            .setBody(simple("\${body}"))
            .to("caffeine-cache://testCache")
            .log("Cache put: \${body}")

        from("direct:cacheGet")
            .routeId("caffeineGetCacheRoute")
            .setHeader("CamelCaffeineAction", constant("GET"))
            .setHeader("CamelCaffeineKey", simple("\${header.CamelCaffeineKey}"))
            .to("caffeine-cache://testCache")
            .log("Cache retrieved: \${body}")
    }
}

@Path("/camel")
@ApplicationScoped
class CacheResource(private val producerTemplate: ProducerTemplate) {

    @WithSpan
    @POST
    @Path("/cache/put/{key}/{value}")
    fun putCache(@PathParam("key") key: String, @PathParam("value") value: String): Uni<String> =
        Uni.createFrom().future {
            producerTemplate.asyncRequestBodyAndHeaders(
                "direct:start",
                value,
                mapOf("CamelCaffeineKey" to key)
            ).thenApply {
                "Cache Updated for key: $key with value: $value"
            }
        }

    @WithSpan
    @GET
    @Path("/cache/get/{key}")
    fun getCache(@PathParam("key") key: String): Uni<String> {
        return Uni.createFrom().future(
            producerTemplate.asyncRequestBodyAndHeader(
                "direct:cacheGet",
                null,
                "CamelCaffeineKey",
                key,
                String::class.java
            )
        )
    }
}