package com.github.jntakpe.holidays.shared

import com.mongodb.reactivestreams.client.MongoCollection
import org.bson.Document
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

abstract class MongoDao<T>(private val collection: MongoCollection<T>, private val dataProvider: TestDataProvider<T>) {

    fun init() = dataProvider.data().toList().also { clean().toMono().thenMany(collection.insertMany(it)).blockLast() }

    fun clean(): Mono<Void> = collection.deleteMany(Document()).toMono().then()

    fun count() = collection.countDocuments().toMono().block() ?: 0
}
