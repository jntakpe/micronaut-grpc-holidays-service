package com.github.jntakpe.holidays.service

import com.github.jntakpe.holidays.client.UserClient
import com.github.jntakpe.holidays.model.entity.UserHoliday
import com.github.jntakpe.holidays.repository.UserHolidayRepository
import com.github.jntakpe.holidays.shared.insertError
import com.github.jntakpe.holidays.shared.logger
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
class UserHolidayService(private val repository: UserHolidayRepository, private val client: UserClient) {

    private val log = logger()

    fun create(userId: String): Mono<UserHoliday> = findUserCountry(userId).flatMap { create(UserHoliday(userId, it)) }

    private fun findUserCountry(userId: String): Mono<String> {
        return client.findUserCountry(userId)
            .doOnSubscribe { log.debug("Searching user country by id {}", userId) }
            .doOnNext { log.debug("User country '{}' retrieved by id {}", it, userId) }
    }

    private fun create(userHoliday: UserHoliday): Mono<UserHoliday> {
        return repository.create(userHoliday)
            .doOnSubscribe { log.debug("Creating {}", userHoliday) }
            .doOnNext { log.info("{} created", it) }
            .onErrorMap { it.insertError(userHoliday, log) }
    }
}
