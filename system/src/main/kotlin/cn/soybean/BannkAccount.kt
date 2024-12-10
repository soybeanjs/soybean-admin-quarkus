/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean

import cn.soybean.shared.domain.aggregate.AggregateEventBase
import cn.soybean.shared.domain.aggregate.AggregateEventEntity
import cn.soybean.shared.domain.aggregate.AggregateRoot
import cn.soybean.shared.eventsourcing.EventStoreDB
import cn.soybean.shared.projection.Projection
import cn.soybean.shared.util.SerializerUtils
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.yitter.idgen.YitIdHelper
import io.opentelemetry.instrumentation.annotations.WithSpan
import io.quarkus.logging.Log
import io.quarkus.mongodb.panache.common.MongoEntity
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoEntity
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.replaceWithUnit
import jakarta.enterprise.context.ApplicationScoped
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.faulttolerance.CircuitBreaker
import org.eclipse.microprofile.faulttolerance.Retry
import org.eclipse.microprofile.faulttolerance.Timeout
import java.math.BigDecimal

data class CreateBankAccountCommand(
    val email: String,
    val userName: String,
    val address: String,
)

data class ChangeEmailCommand(
    val aggregateID: String,
    val newEmail: String,
)

data class ChangeAddressCommand(
    val aggregateID: String,
    val newAddress: String,
)

data class DepositAmountCommand(
    val aggregateID: String,
    val amount: BigDecimal,
)

data class DeleteBankAccountCommand(
    val aggregateID: String,
)

interface BankAccountCommandService {
    fun handle(command: CreateBankAccountCommand): Uni<String>

    fun handle(command: ChangeEmailCommand): Uni<Unit>

    fun handle(command: ChangeAddressCommand): Uni<Unit>

    fun handle(command: DepositAmountCommand): Uni<Unit>

    fun handle(command: DeleteBankAccountCommand): Uni<Boolean>
}

@ApplicationScoped
class BankAccountCommandHandler(
    private val eventStoreDB: EventStoreDB,
) : BankAccountCommandService {
    override fun handle(command: CreateBankAccountCommand): Uni<String> {
        val aggregate = BankAccountAggregate(YitIdHelper.nextId().toString())
        aggregate.createBankAccount(command.email, command.address, command.userName)
        return eventStoreDB
            .save(aggregate)
            .replaceWith(aggregate.aggregateId)
            .onItem()
            .invoke { _ -> Log.debugf("created bank account: %s", aggregate) }
    }

    override fun handle(command: ChangeEmailCommand): Uni<Unit> =
        eventStoreDB
            .load(command.aggregateID, BankAccountAggregate::class.java)
            .map { aggregate ->
                aggregate.changeEmail(command.newEmail)
                aggregate
            }.flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .onItem()
            .invoke { _ ->
                Log.debugf(
                    "changed email: %s, aggregateId: %s",
                    command.newEmail,
                    command.aggregateID,
                )
            }

    override fun handle(command: ChangeAddressCommand): Uni<Unit> =
        eventStoreDB
            .load(command.aggregateID, BankAccountAggregate::class.java)
            .map { aggregate ->
                aggregate.changeAddress(command.newAddress)
                aggregate
            }.flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .onItem()
            .invoke { _ ->
                Log.debugf(
                    "changed address: %s, aggregateId: %s",
                    command.newAddress,
                    command.aggregateID,
                )
            }

    override fun handle(command: DepositAmountCommand): Uni<Unit> =
        eventStoreDB
            .load(command.aggregateID, BankAccountAggregate::class.java)
            .map { aggregate ->
                aggregate.depositBalance(command.amount)
                aggregate
            }.flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .onItem()
            .invoke { _ ->
                Log.debugf(
                    "deposited amount: %s, aggregateId: %s",
                    command.amount,
                    command.aggregateID,
                )
            }

    override fun handle(command: DeleteBankAccountCommand): Uni<Boolean> =
        eventStoreDB
            .load(command.aggregateID, BankAccountAggregate::class.java)
            .map { aggregate ->
                aggregate.deleteBankAccount(command.aggregateID)
                aggregate
            }.flatMap { aggregate -> eventStoreDB.save(aggregate) }
            .map { true }
            .onItem()
            .invoke { _ ->
                Log.debugf(
                    "deleted bank account, aggregateId: %s",
                    command.aggregateID,
                )
            }
}

data class BankAccountCreatedAggregateEventBase(
    val aggregateId: String,
    val email: String,
    val userName: String,
    val address: String,
) : AggregateEventBase(aggregateId) {
    companion object {
        const val BANK_ACCOUNT_CREATED_V1 = "BANK_ACCOUNT_CREATED_V1"
    }
}

data class EmailChangedAggregateEventBase(
    val aggregateId: String,
    val newEmail: String,
) : AggregateEventBase(aggregateId) {
    companion object {
        const val EMAIL_CHANGED_V1 = "EMAIL_CHANGED_V1"
    }
}

data class AddressUpdatedAggregateEventBase(
    val aggregateId: String,
    val newAddress: String,
) : AggregateEventBase(aggregateId) {
    companion object {
        const val ADDRESS_UPDATED_V1 = "ADDRESS_UPDATED_V1"
    }
}

data class BalanceDepositedAggregateEventBase(
    val aggregateId: String,
    val amount: BigDecimal,
) : AggregateEventBase(aggregateId) {
    companion object {
        const val BALANCE_DEPOSITED_V1 = "BALANCE_DEPOSITED_V1"
    }
}

data class BankAccountDeletedAggregateEventBase(
    val aggregateId: String,
) : AggregateEventBase(aggregateId) {
    companion object {
        const val BANK_ACCOUNT_DELETED_V1 = "BANK_ACCOUNT_DELETED_V1"
    }
}

class BankAccountAggregate
    @JsonCreator
    constructor(
        @JsonProperty("aggregateId") aggregateId: String,
    ) : AggregateRoot(aggregateId, AGGREGATE_TYPE) {
        private var email: String = ""
        private var userName: String = ""
        private var address: String = ""
        private var balance: BigDecimal = BigDecimal.ZERO

        override fun whenCondition(eventEntity: AggregateEventEntity) {
            when (eventEntity.eventType) {
                BankAccountCreatedAggregateEventBase.BANK_ACCOUNT_CREATED_V1 ->
                    handle(
                        SerializerUtils.deserializeFromJsonBytes(
                            eventEntity.data,
                            BankAccountCreatedAggregateEventBase::class.java,
                        ),
                    )

                EmailChangedAggregateEventBase.EMAIL_CHANGED_V1 ->
                    handle(
                        SerializerUtils.deserializeFromJsonBytes(
                            eventEntity.data,
                            EmailChangedAggregateEventBase::class.java,
                        ),
                    )

                AddressUpdatedAggregateEventBase.ADDRESS_UPDATED_V1 ->
                    handle(
                        SerializerUtils.deserializeFromJsonBytes(
                            eventEntity.data,
                            AddressUpdatedAggregateEventBase::class.java,
                        ),
                    )

                BalanceDepositedAggregateEventBase.BALANCE_DEPOSITED_V1 ->
                    handle(
                        SerializerUtils.deserializeFromJsonBytes(
                            eventEntity.data,
                            BalanceDepositedAggregateEventBase::class.java,
                        ),
                    )

                BankAccountDeletedAggregateEventBase.BANK_ACCOUNT_DELETED_V1 -> Unit

                else -> throw RuntimeException(eventEntity.eventType)
            }
        }

        private fun handle(event: BankAccountCreatedAggregateEventBase) {
            this.email = event.email
            this.userName = event.userName
            this.address = event.address
            this.balance = BigDecimal.valueOf(0)
        }

        private fun handle(event: EmailChangedAggregateEventBase) {
            this.email = event.newEmail
        }

        private fun handle(event: AddressUpdatedAggregateEventBase) {
            this.address = event.newAddress
        }

        private fun handle(event: BalanceDepositedAggregateEventBase) {
            this.balance = balance.add(event.amount)
        }

        fun createBankAccount(
            email: String,
            address: String,
            userName: String,
        ) {
            val data = BankAccountCreatedAggregateEventBase(aggregateId, email, address, userName)

            val dataBytes = SerializerUtils.serializeToJsonBytes(data)
            val event = this.createEvent(BankAccountCreatedAggregateEventBase.BANK_ACCOUNT_CREATED_V1, dataBytes, null)
            this.apply(event)
        }

        fun changeEmail(newEmail: String) {
            val data = EmailChangedAggregateEventBase(aggregateId, newEmail)

            val dataBytes = SerializerUtils.serializeToJsonBytes(data)
            val event = this.createEvent(EmailChangedAggregateEventBase.EMAIL_CHANGED_V1, dataBytes, null)
            this.apply(event)
        }

        fun changeAddress(newAddress: String) {
            val data = AddressUpdatedAggregateEventBase(aggregateId, newAddress)

            val dataBytes = SerializerUtils.serializeToJsonBytes(data)
            val event = this.createEvent(AddressUpdatedAggregateEventBase.ADDRESS_UPDATED_V1, dataBytes, null)
            this.apply(event)
        }

        fun depositBalance(amount: BigDecimal) {
            val data = BalanceDepositedAggregateEventBase(aggregateId, amount)

            val dataBytes = SerializerUtils.serializeToJsonBytes(data)
            val event = this.createEvent(BalanceDepositedAggregateEventBase.BALANCE_DEPOSITED_V1, dataBytes, null)
            this.apply(event)
        }

        fun deleteBankAccount(aggregateId: String) {
            val data = BankAccountDeletedAggregateEventBase(aggregateId)

            val dataBytes = SerializerUtils.serializeToJsonBytes(data)
            val event = this.createEvent(BankAccountDeletedAggregateEventBase.BANK_ACCOUNT_DELETED_V1, dataBytes, null)
            this.apply(event)
        }

        companion object {
            const val AGGREGATE_TYPE: String = "BankAccountAggregate"
        }
    }

@MongoEntity(collection = "bankAccounts")
class BankAccountDocument : ReactivePanacheMongoEntity() {
    companion object : ReactivePanacheMongoCompanion<BankAccountDocument>

    lateinit var aggregateId: String
    var email: String? = null
    var userName: String? = null
    var address: String? = null
    var balance: BigDecimal? = null
}

@ApplicationScoped
class BankAccountCreatedEventProjection : Projection {
    @WithSpan
    @Retry(maxRetries = 3, delay = 500)
    @Timeout(value = 5000)
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        Log.debugf(
            "(when) BankAccountCreatedAggregateEventBase: %s, aggregateID: %s",
            eventEntity,
            eventEntity.aggregateId,
        )

        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, BankAccountCreatedAggregateEventBase::class.java)

        val bankAccountDocument = BankAccountDocument()
        bankAccountDocument.aggregateId = event.aggregateId
        bankAccountDocument.email = event.email
        bankAccountDocument.address = event.address
        bankAccountDocument.userName = event.userName
        bankAccountDocument.balance = BigDecimal.ZERO

        return BankAccountDocument
            .persist(bankAccountDocument)
            .onItem()
            .invoke { result -> Log.debugf("persist document result: %s", result) }
            .onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "handle BankAccountCreatedEvent persist aggregateID: %s",
                    event.aggregateId,
                )
            }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == BankAccountCreatedAggregateEventBase.BANK_ACCOUNT_CREATED_V1
}

@ApplicationScoped
class EmailChangedEventProjection : Projection {
    @WithSpan
    @Retry(maxRetries = 3, delay = 500)
    @Timeout(value = 5000)
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        Log.debugf(
            "(when) EmailChangedAggregateEventBase: %s, aggregateID: %s",
            eventEntity,
            eventEntity.aggregateId,
        )

        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, EmailChangedAggregateEventBase::class.java)

        return BankAccountDocument
            .find("aggregateId", event.aggregateId)
            .firstResult()
            .onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "handle EmailChangedAggregateEventBase findByAggregateId aggregateID: %s",
                    event.aggregateId,
                )
            }.flatMap { bankAccountDocument ->
                if (bankAccountDocument != null) {
                    bankAccountDocument.email = event.newEmail
                    BankAccountDocument.update(bankAccountDocument).replaceWith { bankAccountDocument }
                } else {
                    Uni.createFrom().item(BankAccountDocument())
                }
            }.onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "handle EmailChangedAggregateEventBase update aggregateID: %s",
                    event.aggregateId,
                )
            }.onItem()
            .invoke { updatedDocument ->
                Log.debugf(
                    "(EmailChangedAggregateEventBase) updatedDocument: %s",
                    updatedDocument,
                )
            }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == EmailChangedAggregateEventBase.EMAIL_CHANGED_V1
}

@ApplicationScoped
class AddressUpdatedEventProjection : Projection {
    @WithSpan
    @Retry(maxRetries = 3, delay = 500)
    @Timeout(value = 5000)
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        Log.debugf(
            "(when) AddressUpdatedAggregateEventBase: %s, aggregateID: %s",
            eventEntity,
            eventEntity.aggregateId,
        )

        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, AddressUpdatedAggregateEventBase::class.java)

        return BankAccountDocument
            .find("aggregateId", event.aggregateId)
            .firstResult()
            .onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "handle AddressUpdatedAggregateEventBase findByAggregateId aggregateID: %s",
                    event.aggregateId,
                )
            }.flatMap { bankAccountDocument ->
                if (bankAccountDocument != null) {
                    bankAccountDocument.address = event.newAddress
                    BankAccountDocument.update(bankAccountDocument).replaceWith { bankAccountDocument }
                } else {
                    Uni.createFrom().item(BankAccountDocument())
                }
            }.onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "handle AddressUpdatedAggregateEventBase update aggregateID: %s",
                    event.aggregateId,
                )
            }.onItem()
            .invoke { updatedDocument ->
                Log.debugf(
                    "(AddressUpdatedAggregateEventBase) updatedDocument: %s",
                    updatedDocument,
                )
            }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == AddressUpdatedAggregateEventBase.ADDRESS_UPDATED_V1
}

@ApplicationScoped
class BalanceDepositedEventProjection : Projection {
    @WithSpan
    @Retry(maxRetries = 3, delay = 500)
    @Timeout(value = 5000)
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        Log.debugf(
            "(when) BalanceDepositedAggregateEventBase: %s, aggregateID: %s",
            eventEntity,
            eventEntity.aggregateId,
        )

        val event =
            SerializerUtils.deserializeFromJsonBytes(eventEntity.data, BalanceDepositedAggregateEventBase::class.java)

        return BankAccountDocument
            .find("aggregateId", event.aggregateId)
            .firstResult()
            .onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "handle BalanceDepositedAggregateEventBase findByAggregateId aggregateID: %s",
                    event.aggregateId,
                )
            }.flatMap { bankAccountDocument ->
                if (bankAccountDocument != null) {
                    bankAccountDocument.balance = bankAccountDocument.balance?.add(event.amount)
                    BankAccountDocument.update(bankAccountDocument).replaceWith { bankAccountDocument }
                } else {
                    Uni.createFrom().item(BankAccountDocument())
                }
            }.onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "handle BalanceDepositedAggregateEventBase update aggregateID: %s",
                    event.aggregateId,
                )
            }.onItem()
            .invoke { updatedDocument ->
                Log.debugf(
                    "(BalanceDepositedAggregateEventBase) updatedDocument: %s",
                    updatedDocument,
                )
            }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == BalanceDepositedAggregateEventBase.BALANCE_DEPOSITED_V1
}

@ApplicationScoped
class BankAccountDeletedEventProjection : Projection {
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        Log.debugf(
            "(when) BankAccountDeletedAggregateEventBase: %s, aggregateID: %s",
            eventEntity,
            eventEntity.aggregateId,
        )

        return BankAccountDocument
            .delete("aggregateId", eventEntity.aggregateId)
            .onItem()
            .invoke { result -> Log.debugf("delete document result: %s", result) }
            .onFailure()
            .invoke { ex ->
                Log.errorf(
                    ex,
                    "handle BankAccountDeletedEvent persist aggregateID: %s",
                    eventEntity.aggregateId,
                )
            }.replaceWithUnit()
    }

    override fun supports(eventType: String): Boolean = eventType == BankAccountDeletedAggregateEventBase.BANK_ACCOUNT_DELETED_V1
}

@ApplicationScoped
class BankCardDeletedEventProjection : Projection {
    override fun process(eventEntity: AggregateEventEntity): Uni<Unit> {
        Log.debugf(
            "(when) BankCardDeletedEventProjection: %s, aggregateID: %s",
            eventEntity,
            eventEntity.aggregateId,
        )

        return Uni.createFrom().item(Unit)
    }

    override fun supports(eventType: String): Boolean = eventType == BankAccountDeletedAggregateEventBase.BANK_ACCOUNT_DELETED_V1
}

data class CreateBankAccountRequestDTO(
    @field:Size(min = 10, max = 250) @field:NotBlank @field:Email @param:Email @param:NotBlank @param:Size(
        min = 10,
        max = 250,
    ) val email: String,
    @field:Size(min = 10, max = 250) @field:NotBlank @param:NotBlank @param:Size(
        min = 10,
        max = 250,
    ) val address: String,
    @field:Size(min = 10, max = 250) @field:NotBlank @param:NotBlank @param:Size(
        min = 10,
        max = 250,
    ) val userName: String,
)

data class ChangeEmailRequestDTO(
    @field:Size(
        min = 10,
        max = 250,
    ) @field:NotBlank @field:Email @param:Email @param:NotBlank @param:Size(min = 10, max = 250) val newEmail: String,
)

data class ChangeAddressRequestDTO(
    @field:Size(
        min = 10,
        max = 250,
    ) @field:NotBlank @param:NotBlank @param:Size(min = 10, max = 250) val newAddress: String,
)

data class DepositAmountRequestDTO(
    @field:NotNull @field:Min(
        value = 300,
        message = "minimal amount is 300",
    ) @param:Min(value = 300, message = "minimal amount is 300") @param:NotNull val amount: BigDecimal,
)

@Path(value = "/api/v1/bank")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class BankAccountResource(
    private val commandService: BankAccountCommandService,
) {
    @POST
    @WithSpan
    @Retry(maxRetries = 3, delay = 300)
    @Timeout(value = 5000)
    @CircuitBreaker(requestVolumeThreshold = 30, delay = 3000, failureRatio = 0.6)
    fun createBanAccount(
        @Valid dto: CreateBankAccountRequestDTO,
    ): Uni<Response> {
        val command = CreateBankAccountCommand(dto.email, dto.userName, dto.address)
        Log.debugf("CreateBankAccountCommand: %s", command)
        return commandService.handle(command).map { aggregateId ->
            Response.status(Response.Status.CREATED).entity(aggregateId).build()
        }
    }

    @POST
    @Path("/email/{aggregateID}")
    @WithSpan
    @Retry(maxRetries = 3, delay = 300)
    @Timeout(value = 5000)
    @CircuitBreaker(requestVolumeThreshold = 30, delay = 3000, failureRatio = 0.6)
    fun updateEmail(
        @PathParam("aggregateID") aggregateID: String,
        @Valid dto: ChangeEmailRequestDTO,
    ): Uni<Response> {
        val command = ChangeEmailCommand(aggregateID, dto.newEmail)
        Log.debugf("ChangeEmailCommand: %s", command)
        return commandService.handle(command).map { Response.status(Response.Status.NO_CONTENT).build() }
    }

    @POST
    @Path("/address/{aggregateID}")
    @WithSpan
    @Retry(maxRetries = 3, delay = 300)
    @Timeout(value = 5000)
    @CircuitBreaker(requestVolumeThreshold = 30, delay = 3000, failureRatio = 0.6)
    fun changeAddress(
        @PathParam("aggregateID") aggregateID: String,
        @Valid dto: ChangeAddressRequestDTO,
    ): Uni<Response> {
        val command = ChangeAddressCommand(aggregateID, dto.newAddress)
        Log.debugf("ChangeAddressCommand: %s", command)
        return commandService.handle(command).map { Response.status(Response.Status.NO_CONTENT).build() }
    }

    @POST
    @Path("/deposit/{aggregateID}")
    @WithSpan
    @Retry(maxRetries = 3, delay = 300)
    @Timeout(value = 5000)
    @CircuitBreaker(requestVolumeThreshold = 30, delay = 3000, failureRatio = 0.6)
    fun depositAmount(
        @PathParam("aggregateID") aggregateID: String,
        @Valid dto: DepositAmountRequestDTO,
    ): Uni<Response> {
        val command = DepositAmountCommand(aggregateID, dto.amount)
        Log.debugf("DepositAmountCommand: %s", command)
        return commandService.handle(command).map { Response.status(Response.Status.NO_CONTENT).build() }
    }

    @DELETE
    @Path("/{aggregateID}")
    @WithSpan
    @Retry(maxRetries = 3, delay = 300)
    @Timeout(value = 5000)
    @CircuitBreaker(requestVolumeThreshold = 30, delay = 3000, failureRatio = 0.6)
    fun deleteBanAccount(
        @PathParam("aggregateID") aggregateID: String,
    ): Uni<Response> {
        val command = DeleteBankAccountCommand(aggregateID)
        Log.debugf("DeleteBankAccountCommand: %s", command)
        return commandService.handle(command).map { Response.status(Response.Status.NO_CONTENT).build() }
    }
}
