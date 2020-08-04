package com.github.jntakpe.holidays.dao

import com.github.jntakpe.holidays.dao.UserHolidayDao.PersistedData.JDOE_ID
import com.github.jntakpe.holidays.dao.UserHolidayDao.PersistedData.MMOE_ID
import com.github.jntakpe.holidays.dao.UserHolidayDao.TransientData.JSMITH_ID
import com.github.jntakpe.holidays.dao.UserHolidayDao.TransientData.RROE_ID
import com.github.jntakpe.holidays.shared.GrpcMockService
import com.github.jntakpe.users.UserResponse
import com.github.jntakpe.users.Users
import com.github.jntakpe.users.UsersServiceGrpc
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.grpc.stub.StreamObserver
import java.util.*
import javax.inject.Singleton

@Singleton
class MockUserService : GrpcMockService {

    override fun get() = object : UsersServiceGrpc.UsersServiceImplBase() {
        override fun findById(request: Users.ByIdRequest, responseObserver: StreamObserver<Users.UserResponse>) {
            val response = when (request.id) {
                JDOE_ID -> UserResponse {
                    id = request.id
                    username = "jdoe"
                    countryCode = Locale.FRANCE.country
                }
                MMOE_ID -> UserResponse {
                    id = request.id
                    username = "mmoe"
                    countryCode = Locale.UK.country
                }
                RROE_ID -> UserResponse {
                    id = request.id
                    username = "rroe"
                    countryCode = Locale.FRANCE.country
                }
                JSMITH_ID -> UserResponse {
                    id = request.id
                    username = "jsmith"
                    countryCode = Locale.UK.country
                }
                else -> null
            }
            response?.also {
                responseObserver.onNext(it)
                responseObserver.onCompleted()
            } ?: responseObserver.onError(StatusRuntimeException(Status.NOT_FOUND))
        }
    }
}
