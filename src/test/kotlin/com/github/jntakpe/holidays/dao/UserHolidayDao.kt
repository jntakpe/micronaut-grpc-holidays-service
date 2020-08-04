package com.github.jntakpe.holidays.dao

import com.github.jntakpe.holidays.model.entity.UserHoliday
import com.github.jntakpe.holidays.shared.MongoDao
import com.github.jntakpe.holidays.shared.TestDataProvider
import com.mongodb.reactivestreams.client.MongoDatabase
import org.bson.types.ObjectId
import org.litote.kmongo.reactivestreams.getCollection
import java.util.*
import javax.inject.Singleton

@Singleton
class UserHolidayDao(database: MongoDatabase) : MongoDao<UserHoliday>(database.getCollection(), PersistedData) {

    object PersistedData : TestDataProvider<UserHoliday> {
        val JDOE_ID = ObjectId().toString()
        val MMOE_ID = ObjectId().toString()
        val jdoe = UserHoliday(JDOE_ID, Locale.FRANCE.country)
        val mmoe = UserHoliday(MMOE_ID, Locale.UK.country)

        override fun data() = listOf(jdoe, mmoe)
    }

    object TransientData : TestDataProvider<UserHoliday> {
        val RROE_ID = ObjectId().toString()
        val JSMITH_ID = ObjectId().toString()
        val rroe = UserHoliday(RROE_ID, Locale.FRANCE.country)
        val jsmith = UserHoliday(JSMITH_ID, Locale.UK.country)

        override fun data() = listOf(rroe, jsmith)
    }
}
