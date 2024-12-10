/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.projection

import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Instance
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Message

@ApplicationScoped
class ProjectionInvoker(private val handlers: Instance<Projection>) {
    private fun distributionProcess(eventEntity: AggregateEventEntity): Uni<Unit> {
        val supportedHandlers = handlers.filter { it.supports(eventEntity.eventType) }

        return when {
            supportedHandlers.isEmpty() -> {
                Log.warnf("[ProjectionInvoker] No Projection found for event type: %s", eventEntity.eventType)
                Uni.createFrom().item(Unit)
            }

            else -> {
                val processingUnis =
                    supportedHandlers.map { handler ->
                        handler.process(eventEntity)
                            .onItem().invoke { _ ->
                                Log.debugf(
                                    "[ProjectionInvoker] Processed event %s by %s",
                                    eventEntity.eventType,
                                    handler::class.java.simpleName,
                                )
                            }.onFailure().invoke { ex ->
                                Log.errorf(
                                    ex,
                                    "[ProjectionInvoker] Error processing event %s by %s",
                                    eventEntity.eventType,
                                    handler::class.java.simpleName,
                                )
                            }
                    }
                Uni.combine().all().unis<Unit>(processingUnis).discardItems().replaceWithUnit()
            }
        }
    }

    @Incoming(value = "eventstore-in")
    @WithSpan
    fun process(message: Message<ByteArray>): Uni<Unit> {
        Log.debugf("[ProjectionInvoker] (consumer) process events: >>>>> %s", String(message.payload))
        val events = SerializerUtils.deserializeEventsFromJsonBytes(message.payload)

        return when {
            events.isEmpty() ->
                Uni.createFrom().item(Unit)
                    .onItem().invoke { _ -> Log.warn("[ProjectionInvoker] empty events list") }
                    .onItem().invoke { _ -> message.ack() }
                    .onFailure().invoke { ex -> Log.errorf(ex, "[ProjectionInvoker] (process) msg ack exception") }

            else ->
                Multi.createFrom().iterable(events.toList())
                    .onItem().call { event -> distributionProcess(event) }.toUni().replaceWithUnit()
                    .onItem().invoke { _ -> message.ack() }
                    .onFailure()
                    .invoke { ex ->
                        Log.errorf(
                            ex,
                            "[ProjectionInvoker] consumer process events aggregateId: %s",
                            events[0].aggregateId,
                        )
                    }
        }
    }
}
