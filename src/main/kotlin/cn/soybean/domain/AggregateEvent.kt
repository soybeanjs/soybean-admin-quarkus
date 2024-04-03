package cn.soybean.domain

import cn.soybean.domain.SerializerUtils.serializeToJsonBytes
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import io.smallrye.reactive.messaging.kafka.KafkaClientService
import jakarta.enterprise.context.ApplicationScoped
import org.apache.kafka.clients.producer.ProducerRecord
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.time.Duration

fun interface EventBus {
    fun publish(eventEntities: List<EventEntity>): Uni<Unit>
}

@ApplicationScoped
class KafkaEventBus(
    private val kafkaClientService: KafkaClientService,
    @ConfigProperty(
        name = "mp.messaging.incoming.eventstore-in.topic",
        defaultValue = "eventstore"
    ) private val eventStoreTopic: String
) : EventBus {

    @WithSpan
    override fun publish(eventEntities: List<EventEntity>): Uni<Unit> {
        if (eventEntities.isEmpty()) {
            Log.debug("No events to publish.")
            return Uni.createFrom().item(Unit)
        }

        val firstEventType = eventEntities.first().eventType
        val eventsBytes = serializeToJsonBytes(eventEntities.toTypedArray())
        val record = ProducerRecord(eventStoreTopic, firstEventType, eventsBytes)

        return kafkaClientService.getProducer<String, ByteArray>("eventstore-out")
            .send(record)
            .ifNoItem().after(Duration.ofMillis(PUBLISH_TIMEOUT)).fail()
            .onFailure().invoke { throwable: Throwable ->
                Log.errorf(
                    throwable,
                    "Error publishing events to Kafka topic $eventStoreTopic. Payload: %s",
                    String(record.value())
                )
            }
            .onFailure().retry().withBackOff(Duration.ofMillis(BACKOFF_TIMEOUT)).atMost(RETRY_COUNT)
            .onItem().invoke { _ ->
                Log.debugf(
                    "Successfully published events to Kafka topic key %s. Payload: %s",
                    record.key(),
                    String(record.value())
                )
            }
            .replaceWithUnit()
    }

    companion object {
        private const val PUBLISH_TIMEOUT = 1000L
        private const val BACKOFF_TIMEOUT = 300L
        private const val RETRY_COUNT = 3L
    }
}