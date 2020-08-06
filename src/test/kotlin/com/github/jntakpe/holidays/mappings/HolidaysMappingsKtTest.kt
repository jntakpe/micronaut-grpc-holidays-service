package com.github.jntakpe.holidays.mappings

import com.github.jntakpe.holidays.HolidaysResponse
import com.github.jntakpe.holidays.dao.HolidayDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class HolidaysMappingsKtTest {

    @Test
    fun `to response should map partial`() {
        val entity = HolidayDao.PersistedData.mmoe
        val expected = HolidaysResponse {
            userId = entity.userId
            id = entity.id.toString()
        }
        assertThat(entity.toResponse()).usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    @Disabled
    fun `to response should map full`() {
    }
}
