package com.github.jntakpe.holidays.shared

import io.grpc.BindableService
import java.util.function.Supplier

interface GrpcMockService : Supplier<BindableService>
