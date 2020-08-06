package com.github.jntakpe.holidays.config

import org.testcontainers.containers.MongoDBContainer

object MongoContainer {

    val instance: MongoDBContainer by lazy {
        MongoDBContainer().apply { start() }
    }
}
