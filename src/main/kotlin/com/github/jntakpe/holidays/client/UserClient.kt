package com.github.jntakpe.holidays.client

import com.github.jntakpe.users.ByIdRequest
import com.github.jntakpe.users.ReactorUsersServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.grpc.annotation.GrpcChannel
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class UserClient(@GrpcChannel("users") channel: ManagedChannel) {

    private val stub = ReactorUsersServiceGrpc.newReactorStub(channel)

    fun findUserCountry(userId: String): Mono<String> = stub.findById(ByIdRequest { id = userId }).map { it.countryCode }
}
