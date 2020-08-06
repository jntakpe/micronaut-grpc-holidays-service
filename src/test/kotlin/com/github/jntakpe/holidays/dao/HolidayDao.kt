package com.github.jntakpe.holidays.dao

import com.github.jntakpe.holidays.model.entity.Holiday
import com.github.jntakpe.holidays.shared.MongoDao
import com.github.jntakpe.holidays.shared.TestDataProvider
import com.mongodb.reactivestreams.client.MongoDatabase
import org.bson.types.ObjectId
import org.litote.kmongo.reactivestreams.getCollection
import java.util.*
import javax.inject.Singleton

@Singleton
class HolidayDao(database: MongoDatabase) : MongoDao<Holiday>(database.getCollection(), PersistedData) {

    object PersistedData : TestDataProvider<Holiday> {
        val JDOE_ID = ObjectId().toString()
        val MMOE_ID = ObjectId().toString()
        val jdoe = Holiday(JDOE_ID, Locale.FRANCE.country)
        val mmoe = Holiday(MMOE_ID, Locale.UK.country)

        override fun data() = listOf(jdoe, mmoe)
    }

    object TransientData : TestDataProvider<Holiday> {
        val RROE_ID = ObjectId().toString()
        val JSMITH_ID = ObjectId().toString()
        val rroe = Holiday(RROE_ID, Locale.FRANCE.country)
        val jsmith = Holiday(JSMITH_ID, Locale.UK.country)

        override fun data() = listOf(rroe, jsmith)
    }
}
