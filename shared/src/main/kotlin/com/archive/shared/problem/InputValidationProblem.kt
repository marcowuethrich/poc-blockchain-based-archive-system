package com.archive.shared.problem

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import java.net.URI

/**
 * Input validation problem
 */
class InputValidationProblem(detail: String = "error.api.validation-invalid") : AbstractThrowableProblem(
    URI.create(""),
    "Validation problem!",
    Status.BAD_REQUEST,
    detail
) {
    override fun getCause(): Exceptional? = super.cause
}
