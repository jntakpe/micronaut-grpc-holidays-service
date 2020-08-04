package com.github.jntakpe.holidays.shared

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Hooks
import reactor.core.publisher.Operators

typealias ErrorLoggingFunction = (String, Throwable) -> Unit

@JvmSynthetic
inline fun <reified T> T.logger(): Logger {
    return if (T::class.isCompanion) {
        LoggerFactory.getLogger(T::class.java.enclosingClass)
    } else {
        LoggerFactory.getLogger(T::class.java)
    }
}

object ScriptLogger {
    val log = logger()
}

object ReactorExceptionLogger {
    init {
        Hooks.onLastOperator(Operators.lift { _, u -> LoggingSubscriber(u) })
    }
}
