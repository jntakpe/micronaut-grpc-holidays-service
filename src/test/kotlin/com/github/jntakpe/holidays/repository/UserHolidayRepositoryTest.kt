package com.github.jntakpe.holidays.repository

import com.github.jntakpe.holidays.dao.UserHolidayDao
import com.github.jntakpe.holidays.model.entity.UserHoliday
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import reactor.kotlin.test.test

@MicronautTest
internal class UserHolidayRepositoryTest(private val repository: UserHolidayRepository, private val dao: UserHolidayDao) {


    @BeforeEach
    fun setup() {
        dao.init()
    }

    @ParameterizedTest
    @ArgumentsSource(UserHolidayDao.PersistedData::class)
    fun `find by username should find one`(userHoliday: UserHoliday) {
        val userId = userHoliday.userId
        repository.findByUserId(userId).test()
            .consumeNextWith { assertThat(it.userId).isEqualTo(userId) }
            .verifyComplete()
    }
}

