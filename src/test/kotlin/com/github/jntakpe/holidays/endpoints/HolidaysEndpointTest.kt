package com.github.jntakpe.holidays.endpoints

import com.github.jntakpe.holidays.HolidaysRequest
import com.github.jntakpe.holidays.HolidaysServiceGrpc
import com.github.jntakpe.holidays.dao.HolidayDao
import com.github.jntakpe.holidays.model.entity.Holiday
import com.github.jntakpe.holidays.shared.assertStatusException
import io.grpc.Status
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource

@MicronautTest
internal class HolidaysEndpointTest(private val dao: HolidayDao, private val stub: HolidaysServiceGrpc.HolidaysServiceBlockingStub) {

    @BeforeEach
    fun setup() {
        dao.init()
    }

    @ParameterizedTest
    @ArgumentsSource(HolidayDao.TransientData::class)
    fun `create should return ok response`(holiday: Holiday) {
        val initSize = dao.count()
        val response = stub.create(HolidaysRequest { userId = holiday.userId })
        assertThat(response.id).isNotEmpty()
        assertThat(response.userId).isEqualTo(holiday.userId)
        assertThat(dao.count()).isEqualTo(initSize + 1)
    }

    @ParameterizedTest
    @ArgumentsSource(HolidayDao.PersistedData::class)
    fun `create should fail when user already exists`(holiday: Holiday) {
        val initSize = dao.count()
        catchThrowable { stub.create(HolidaysRequest { userId = holiday.userId }) }.assertStatusException(Status.ALREADY_EXISTS)
        assertThat(dao.count()).isEqualTo(initSize)
    }
}
