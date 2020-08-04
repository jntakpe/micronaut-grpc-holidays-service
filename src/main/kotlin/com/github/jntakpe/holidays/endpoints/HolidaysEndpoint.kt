package com.github.jntakpe.holidays.endpoints

import com.github.jntakpe.holidays.Holidays
import com.github.jntakpe.holidays.ReactorHolidaysServiceGrpc
import com.github.jntakpe.holidays.service.UserHolidayService
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class HolidaysEndpoint(private val service: UserHolidayService) : ReactorHolidaysServiceGrpc.HolidaysServiceImplBase() {

    override fun create(request: Mono<Holidays.HolidaysRequest>): Mono<Holidays.HolidaysResponse> {
        return request
            .flatMap { service.create(it.userId) }
            .map { Holidays.HolidaysResponse.newBuilder().build() }
    }
}
