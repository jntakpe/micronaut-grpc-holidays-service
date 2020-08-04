package com.github.jntakpe.holidays.shared

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import reactor.core.CoreSubscriber

class LoggingSubscriber<T>(private val subscriber: Subscriber<T>) : CoreSubscriber<T> {

    override fun onComplete() {
        subscriber.onComplete()
    }

    override fun onSubscribe(s: Subscription) {
        subscriber.onSubscribe(s)
    }

    override fun onNext(t: T) {
        subscriber.onNext(t)
    }

    override fun onError(t: Throwable) {
        t.asCommonException()?.apply { logging(message, this) }
        subscriber.onError(t)
    }

    private fun Throwable.asCommonException() = this as? CommonException
}
