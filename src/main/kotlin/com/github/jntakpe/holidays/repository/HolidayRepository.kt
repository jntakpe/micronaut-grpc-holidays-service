package com.github.jntakpe.holidays.repository

import com.github.jntakpe.holidays.model.entity.Holiday
import com.github.jntakpe.holidays.model.entity.Holiday_.Companion.UserId
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import com.mongodb.reactivestreams.client.MongoDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.findOne
import org.litote.kmongo.reactivestreams.getCollection
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.inject.Singleton

@Singleton
class HolidayRepository(database: MongoDatabase) {

    private val collection = database.getCollection<Holiday>()

    init {
        collection.createIndex(Indexes.ascending(UserId.name), IndexOptions().unique(true)).toMono().subscribe()
    }

    fun findByUserId(userId: String): Mono<Holiday> = collection.findOne(UserId eq userId).toMono()

    fun create(holiday: Holiday): Mono<Holiday> = collection.insertOne(holiday).toMono().thenReturn(holiday)
}
