package com.github.jntakpe.holidays.shared

import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusRuntimeException

class CommonException(status: Status, val log: ErrorLoggingFunction, metadata: Metadata? = null) : StatusRuntimeException(status, metadata)
