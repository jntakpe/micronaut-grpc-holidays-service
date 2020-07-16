package com.github.jntakpe.holidays.shared

import io.grpc.Status
import org.assertj.core.api.Assertions.assertThat
import reactor.test.StepVerifier

fun <T> StepVerifier.Step<T>.expectCommonException(status: Status, message: String? = null): StepVerifier {
    return consumeErrorWith { it.assertCommonException(status, message) }
}

fun Throwable.assertCommonException(expectedStatus: Status, expectedMessage: String? = null) {
    assertThat(this).isInstanceOf(CommonException::class.java)
    this as CommonException
    assertThat(status.code).isEqualTo(expectedStatus.code)
    expectedMessage?.also { assertThat(message).isEqualTo(it) }
}
