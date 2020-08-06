package com.github.jntakpe.holidays.service

import com.github.jntakpe.holidays.dao.HolidayDao
import com.github.jntakpe.holidays.model.entity.Holiday
import com.github.jntakpe.holidays.shared.expectStatusException
import io.grpc.Status
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import reactor.kotlin.test.test

@MicronautTest
internal class HolidayServiceTest(private val service: HolidayService, private val dao: HolidayDao) {

    @BeforeEach
    fun setup() {
        dao.init()
    }

    @ParameterizedTest
    @ArgumentsSource(HolidayDao.TransientData::class)
    fun `create should return created document`(holiday: Holiday) {
        service.create(holiday.userId).test()
            .consumeNextWith { assertThat(it).isEqualToIgnoringGivenFields(holiday, "id") }
            .verifyComplete()
    }

    @ParameterizedTest
    @ArgumentsSource(HolidayDao.PersistedData::class)
    fun `create should fail with already exists code when integrity constraint violated`(holiday: Holiday) {
        service.create(holiday.userId).test()
            .expectStatusException(Status.ALREADY_EXISTS)
            .verify()
    }

    @Test
    fun `create should fail when user id does not exists`() {
        service.create(ObjectId().toString()).test()
            .expectStatusException(Status.NOT_FOUND)
            .verify()
    }
}
