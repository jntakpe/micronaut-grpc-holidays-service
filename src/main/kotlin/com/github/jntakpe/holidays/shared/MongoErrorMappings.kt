package com.github.jntakpe.holidays.shared

import com.mongodb.ErrorCategory.DUPLICATE_KEY
import com.mongodb.ErrorCategory.fromErrorCode
import com.mongodb.MongoWriteException
import io.grpc.Status
import org.slf4j.Logger

fun <T : Identifiable> Throwable.insertError(entity: T, log: Logger): CommonException {
    return if (isDuplicateKey()) {
        CommonException("$entity already exists", log::info, Status.Code.ALREADY_EXISTS, this)
    } else {
        CommonException("Unable to store $entity", log::warn, Status.Code.INTERNAL, this)
    }
}

private fun Throwable.isDuplicateKey() = this is MongoWriteException && fromErrorCode(this.error.code) === DUPLICATE_KEY
