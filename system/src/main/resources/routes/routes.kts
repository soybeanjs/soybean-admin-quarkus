package routes

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine

rest {
    path("/kt-dsl") {
        get("/get") {
            produces("application/json")
            to("direct:get")
        }

        post("/post") {
            produces("application/json")
            to("direct:post")
        }

        get("/cache") {
            produces("application/json")
            to("disruptor:processCache")
        }
    }
}

from("direct:get")
    .process { e -> e.getIn().body = "{ 'message': 'Hello GET' }" }

from("direct:post")
    .process { e -> e.getIn().body = "{ 'message': 'Hello POST' }" }

val caffeineCache: Cache<String, String> = Caffeine.newBuilder()
    .maximumSize(1000L)
    .build()

from("disruptor:processCache")
    .routeId("cacheRoute")
    .log("Received key: \${header.key}, value: \${header.value}")
    .process { exchange ->
        val key = exchange.getIn().getHeader("key", String::class.java)
        val value = exchange.getIn().getHeader("value", String::class.java)

        val currentValue = caffeineCache.get(key) { value }
        if (currentValue != value) {
            caffeineCache.put(key, value)
            exchange.getIn().body = "Updated and returned new value: $value"
        } else {
            exchange.getIn().body = "Returned existing value: $currentValue"
        }
    }
    .log("Cache operation completed: \${body}")
    .process { _ ->
        val allEntries = caffeineCache.asMap().entries.joinToString(", ") { entry ->
            "${entry.key}=${entry.value}"
        }
        println("Current Cache Contents: $allEntries")
    }