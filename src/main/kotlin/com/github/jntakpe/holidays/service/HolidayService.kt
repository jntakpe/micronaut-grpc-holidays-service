package com.github.jntakpe.holidays.service

import com.github.jntakpe.holidays.client.UserClient
import com.github.jntakpe.holidays.model.entity.Holiday
import com.github.jntakpe.holidays.repository.HolidayRepository
import com.github.jntakpe.holidays.shared.insertError
import com.github.jntakpe.holidays.shared.logger
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class HolidayService(private val repository: HolidayRepository, private val client: UserClient) {

    private val log = logger()

    fun create(userId: String): Mono<Holiday> = findUserCountry(userId).flatMap { create(Holiday(userId, it)) }

    private fun findUserCountry(userId: String): Mono<String> {
        return client.findUserCountry(userId)
            .doOnSubscribe { log.debug("Searching user country by id {}", userId) }
            .doOnNext { log.debug("User country '{}' retrieved by id {}", it, userId) }
    }

    private fun create(holiday: Holiday): Mono<Holiday> {
        return repository.create(holiday)
            .doOnSubscribe { log.debug("Creating {}", holiday) }
            .doOnNext { log.info("{} created", it) }
            .onErrorMap { it.insertError(holiday, log) }
    }
}
