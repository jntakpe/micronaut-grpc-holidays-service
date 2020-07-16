package com.github.jntakpe.holidays.config

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import io.micronaut.configuration.mongo.core.DefaultMongoConfiguration
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import org.testcontainers.containers.MongoDBContainer

@Factory
class ContainerConfig {

    @Bean(preDestroy = "stop")
    fun mongoContainer(): MongoDBContainer = MongoDBContainer().apply { start() }

    @Bean(preDestroy = "close")
    @Replaces(bean = MongoClient::class)
    fun mongoContainerClient(container: MongoDBContainer, initConfig: DefaultMongoConfiguration): MongoClient {
        val testConfig = initConfig.apply { uri = "mongodb://${container.containerIpAddress}:${container.firstMappedPort}" }
        return MongoClients.create(testConfig.buildSettings())
    }
}
