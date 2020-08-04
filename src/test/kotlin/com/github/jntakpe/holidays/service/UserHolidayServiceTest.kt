package com.github.jntakpe.holidays.service

import com.github.jntakpe.holidays.dao.UserHolidayDao
import com.github.jntakpe.holidays.model.entity.UserHoliday
import com.github.jntakpe.holidays.shared.expectStatusException
import io.grpc.Status
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import reactor.kotlin.test.test

@MicronautTest
internal class UserHolidayServiceTest(private val service: UserHolidayService, private val dao: UserHolidayDao) {

    @BeforeEach
    fun setup() {
        dao.init()
    }

    @ParameterizedTest
    @ArgumentsSource(UserHolidayDao.TransientData::class)
    fun `create should return created document`(holiday: UserHoliday) {
        service.create(holiday.userId).test()
            .consumeNextWith { assertThat(it).isEqualToIgnoringGivenFields(holiday, "id") }
            .verifyComplete()
    }

    @ParameterizedTest
    @ArgumentsSource(UserHolidayDao.PersistedData::class)
    fun `create should fail with already exists code when integrity constraint violated`(holiday: UserHoliday) {
        service.create(holiday.userId).test()
            .expectStatusException(Status.ALREADY_EXISTS)
            .verify()
    }

    @Test
    fun `create should fail when user id does not exists`() {
    }
}
