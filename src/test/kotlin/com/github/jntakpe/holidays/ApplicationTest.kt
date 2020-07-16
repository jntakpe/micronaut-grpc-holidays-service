package com.github.jntakpe.holidays

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@MicronautTest
class ApplicationTest(private val application: EmbeddedApplication<*>) {

    @Test
    fun `main should start application`() {
        assertThat(application.isRunning).isTrue()
    }
}
