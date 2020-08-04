package com.github.jntakpe.holidays.shared

import io.grpc.Server
import io.grpc.inprocess.InProcessServerBuilder
import io.grpc.util.MutableHandlerRegistry
import java.util.*

object GrpcMockServer {

    private var instance: Server? = null
    private var name: String? = null

    fun start(services: List<GrpcMockService>): String {
        if (instance == null) {
            name = UUID.randomUUID().toString()
            instance = instance(services).start()
        }
        return name!!
    }

    private fun instance(services: List<GrpcMockService>): Server {
        val registry = MutableHandlerRegistry().apply { services.forEach { addService(it.get()) } }
        return InProcessServerBuilder
            .forName(name)
            .directExecutor()
            .fallbackHandlerRegistry(registry)
            .build()
    }
}
