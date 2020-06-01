package com.archive.shared.problem

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import java.net.URI

/**
 * File not found in request problem
 */
class FileNotFoundInRequestProblem(detail: String = "error.api.file-not-found") : AbstractThrowableProblem(
    URI.create(""),
    "File not found!",
    Status.BAD_REQUEST,
    detail
) {
    override fun getCause(): Exceptional? = super.cause
}
