package camel.scheduler

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicInteger

val count = AtomicInteger()

from("timer://foo?fixedRate=true&period=60000")
    .routeId("timer-to-disruptor")
    .process { exchange ->
        val currentCount = count.incrementAndGet()
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        println("[camel.scheduler]: Trigger Disruptor Event: Execution #$currentCount at $currentTime")

        exchange.context.createProducerTemplate()
            .asyncRequestBody("disruptor:schedulerDisruptor", "Execution #$currentCount at $currentTime")

        if (currentCount >= 3) {
            count.set(0)
        }
    }

from("disruptor:schedulerDisruptor")
    .log("[camel.scheduler]: Processed by Disruptor: \${body}")