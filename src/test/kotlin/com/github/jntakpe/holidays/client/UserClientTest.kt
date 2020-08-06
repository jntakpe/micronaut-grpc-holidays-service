package com.github.jntakpe.holidays.client

import com.github.jntakpe.holidays.dao.HolidayDao.PersistedData.JDOE_ID
import com.github.jntakpe.holidays.dao.HolidayDao.PersistedData.MMOE_ID
import com.github.jntakpe.holidays.shared.expectStatusException
import io.grpc.Status
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import reactor.kotlin.test.test
import java.util.*

@MicronautTest
internal class UserClientTest(private val client: UserClient) {

    @Test
    fun `find user country should find france`() {
        client.findUserCountry(JDOE_ID).test()
            .expectSubscription()
            .consumeNextWith { assertThat(it).isEqualTo(Locale.FRANCE.country) }
            .verifyComplete()
    }

    @Test
    fun `find user country should find uk`() {
        client.findUserCountry(MMOE_ID).test()
            .expectSubscription()
            .consumeNextWith { assertThat(it).isEqualTo(Locale.UK.country) }
            .verifyComplete()
    }

    @Test
    fun `find user country should fail when id does not exists`() {
        client.findUserCountry(ObjectId().toString()).test()
            .expectSubscription()
            .expectStatusException(Status.NOT_FOUND)
            .verify()
    }
}
