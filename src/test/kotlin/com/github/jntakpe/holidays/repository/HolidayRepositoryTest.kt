package com.github.jntakpe.holidays.repository

import com.github.jntakpe.holidays.dao.HolidayDao
import com.github.jntakpe.holidays.model.entity.Holiday
import com.mongodb.MongoWriteException
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.junit.jupiter.params.provider.ValueSource
import reactor.kotlin.test.test
import java.util.*

@MicronautTest
internal class HolidayRepositoryTest(private val repository: HolidayRepository, private val dao: HolidayDao) {


    @BeforeEach
    fun setup() {
        dao.init()
    }

    @ParameterizedTest
    @ArgumentsSource(HolidayDao.PersistedData::class)
    fun `find by user id should find one`(holiday: Holiday) {
        val userId = holiday.userId
        repository.findByUserId(userId).test()
            .consumeNextWith { assertThat(it.userId).isEqualTo(userId) }
            .verifyComplete()
    }

    @ParameterizedTest
    @ValueSource(strings = ["unknown", ""])
    fun `find by user id should return empty`(userId: String) {
        repository.findByUserId(userId).test()
            .expectNextCount(0)
            .verifyComplete()
    }

    @ParameterizedTest
    @ArgumentsSource(HolidayDao.TransientData::class)
    fun `create should add document`(holiday: Holiday) {
        val initSize = dao.count()
        repository.create(holiday).test()
            .consumeNextWith {
                assertThat(it).isEqualTo(holiday)
                assertThat(dao.count()).isNotZero().isEqualTo(initSize + 1)
            }
            .verifyComplete()
    }

    @Test
    fun `create should fail when user id already exists`() {
        val initSize = dao.count()
        repository.create(Holiday(HolidayDao.PersistedData.JDOE_ID, Locale.ITALY.country)).test()
            .consumeErrorWith {
                assertThat(it).isInstanceOf(MongoWriteException::class.java)
                assertThat(dao.count()).isEqualTo(initSize)
            }
            .verify()
    }
}

