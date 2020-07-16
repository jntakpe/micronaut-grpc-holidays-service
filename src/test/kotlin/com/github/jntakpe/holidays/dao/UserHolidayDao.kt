package com.github.jntakpe.holidays.dao

import com.github.jntakpe.holidays.model.entity.UserHoliday
import com.github.jntakpe.holidays.shared.MongoDao
import com.github.jntakpe.holidays.shared.TestDataProvider
import com.mongodb.reactivestreams.client.MongoDatabase
import org.bson.types.ObjectId
import org.litote.kmongo.reactivestreams.getCollection
import javax.inject.Singleton

@Singleton
class UserHolidayDao(database: MongoDatabase) : MongoDao<UserHoliday>(database.getCollection(), PersistedData) {

    object PersistedData : TestDataProvider<UserHoliday> {
        val JDOE_ID = ObjectId().toString()
        val MMOE_ID = ObjectId().toString()

        override fun data() = listOf(jdoe(), mmoe())

        private fun jdoe() = UserHoliday(JDOE_ID)

        private fun mmoe() = UserHoliday(MMOE_ID)
    }

    object TransientData : TestDataProvider<UserHoliday> {
        val RROE_ID = ObjectId().toString()
        val JSMITH_ID = ObjectId().toString()

        override fun data() = listOf(rroe(), jsmith())

        private fun rroe() = UserHoliday(RROE_ID)

        private fun jsmith() = UserHoliday(JSMITH_ID)
    }
}
