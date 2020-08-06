package com.github.jntakpe.holidays.config

import com.github.jntakpe.holidays.HolidaysServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import javax.inject.Singleton

@Factory
class ClientConfig {

    @Singleton
    fun serverStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): HolidaysServiceGrpc.HolidaysServiceBlockingStub {
        return HolidaysServiceGrpc.newBlockingStub(channel)
    }
}
