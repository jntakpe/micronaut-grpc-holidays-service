package com.github.jntakpe.holidays.mappings

import com.github.jntakpe.holidays.HolidaysResponse
import com.github.jntakpe.holidays.model.entity.Holiday

fun Holiday.toResponse() = HolidaysResponse {
    val entity = this@toResponse
    userId = entity.userId
    addAllHolidays(holidays.map { it.toString() })
    addAllBankHolidays(emptyList())
    id = entity.id.toString()
}

