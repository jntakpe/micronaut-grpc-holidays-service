package com.github.jntakpe.holidays.config

import com.github.jntakpe.holidays.shared.GrpcMockServer
import com.github.jntakpe.holidays.shared.GrpcMockService
import io.grpc.ManagedChannel
import io.grpc.inprocess.InProcessChannelBuilder
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Replaces
import io.micronaut.grpc.channels.GrpcManagedChannelFactory

@Factory
@Replaces(factory = GrpcManagedChannelFactory::class)
class GrpcTestConfig(services: List<GrpcMockService>) {

    private val channel = GrpcMockServer.start(services).run { registerChannel() }

    @Bean
    @Primary
    fun managedChannel(@Parameter target: String) = channel

    private fun String.registerChannel(): ManagedChannel = InProcessChannelBuilder.forName(this).directExecutor().build()
}
