package com.archive.shared.problem

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import java.net.URI

class InputVerificationProblem(detail: String = "error.api.verification-invalid") : AbstractThrowableProblem(
    URI.create(""),
    "Verification problem!",
    Status.BAD_REQUEST,
    detail
) {
    override fun getCause(): Exceptional? = super.cause
}
