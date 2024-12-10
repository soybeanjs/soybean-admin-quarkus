/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.eventsourcing

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.eventsourcing.EventBus
import cn.soybean.shared.util.SerializerUtils.serializeToJsonBytes
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import io.smallrye.reactive.messaging.kafka.KafkaClientService
import io.vertx.core.Vertx
import jakarta.enterprise.context.ApplicationScoped
import org.apache.kafka.clients.producer.ProducerRecord
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.time.Duration

@ApplicationScoped
class AggregateEventBusWithKafka(
    private val kafkaClientService: KafkaClientService,
    @ConfigProperty(
        name = "mp.messaging.incoming.eventstore-in.topic",
        defaultValue = "eventstore",
    ) private val eventStoreTopic: String,
) : EventBus {
    @WithSpan
    override fun publish(eventEntities: MutableList<AggregateEventEntity>): Uni<Unit> {
        val context = Vertx.currentContext()
        if (context == null) {
            Log.error("[AggregateEventBusWithKafka] Not in a Vert.x context!")
            return Uni.createFrom().failure(IllegalStateException("Not in a Vert.x context"))
        }

        if (eventEntities.isEmpty()) {
            Log.debug("[AggregateEventBusWithKafka] No events to publish.")
            return Uni.createFrom().item(Unit)
        }

        return Uni
            .createFrom()
            .item {
                context.runOnContext {
                    val firstEventType = eventEntities.first().eventType
                    val eventsBytes = serializeToJsonBytes(eventEntities.toTypedArray())
                    val record = ProducerRecord(eventStoreTopic, firstEventType, eventsBytes)
                    kafkaClientService
                        .getProducer<String, ByteArray>("eventstore-out")
                        .send(record)
                        .ifNoItem()
                        .after(Duration.ofMillis(PUBLISH_TIMEOUT))
                        .fail()
                        .onFailure()
                        .invoke { ex ->
                            Log.errorf(
                                ex,
                                "[AggregateEventBusWithKafka] Error publishing events to Kafka topic $eventStoreTopic. Payload: %s",
                                String(record.value()),
                            )
                        }.onFailure()
                        .retry()
                        .withBackOff(Duration.ofMillis(BACKOFF_TIMEOUT))
                        .atMost(RETRY_COUNT)
                        .onItem()
                        .invoke { _ ->
                            Log.debugf(
                                "[AggregateEventBusWithKafka] Successfully published events to Kafka topic key %s. Payload: %s",
                                record.key(),
                                String(record.value()),
                            )
                        }.subscribeAsCompletionStage()
                }
            }.replaceWithUnit()
    }

    companion object {
        private const val PUBLISH_TIMEOUT = 1000L
        private const val BACKOFF_TIMEOUT = 300L
        private const val RETRY_COUNT = 3L
    }
}
