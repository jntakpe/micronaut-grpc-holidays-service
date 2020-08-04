package com.github.jntakpe.holidays.shared

import io.grpc.Status
import io.grpc.StatusRuntimeException
import org.assertj.core.api.Assertions.assertThat
import reactor.test.StepVerifier

fun <T> StepVerifier.Step<T>.expectStatusException(status: Status, message: String? = null): StepVerifier {
    return consumeErrorWith { it.assertStatusException(status, message) }
}

fun Throwable.assertStatusException(expectedStatus: Status, expectedMessage: String? = null) {
    assertThat(this).isInstanceOf(StatusRuntimeException::class.java)
    this as StatusRuntimeException
    assertThat(status.code).isEqualTo(expectedStatus.code)
    expectedMessage?.also { assertThat(message).isEqualTo(it) }
}
