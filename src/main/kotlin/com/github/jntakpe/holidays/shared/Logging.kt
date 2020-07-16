package com.github.jntakpe.holidays.shared

import org.slf4j.Logger
import org.slf4j.LoggerFactory

typealias ErrorLoggingFunction = (String, Throwable) -> Unit

@JvmSynthetic
inline fun <reified T> T.logger(): Logger {
    return if (T::class.isCompanion) {
        LoggerFactory.getLogger(T::class.java.enclosingClass)
    } else {
        LoggerFactory.getLogger(T::class.java)
    }
}
