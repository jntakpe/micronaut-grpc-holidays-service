package com.github.jntakpe.holidays.endpoints

import com.github.jntakpe.holidays.Holidays
import com.github.jntakpe.holidays.ReactorHolidaysServiceGrpc
import com.github.jntakpe.holidays.mappings.toResponse
import com.github.jntakpe.holidays.service.HolidayService
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class HolidaysEndpoint(private val service: HolidayService) : ReactorHolidaysServiceGrpc.HolidaysServiceImplBase() {

    override fun create(request: Mono<Holidays.HolidaysRequest>): Mono<Holidays.HolidaysResponse> {
        return request
            .flatMap { service.create(it.userId) }
            .map { it.toResponse() }
    }
}
