package com.archive.shared.problem

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional
import org.zalando.problem.Status
import java.net.URI

class AIPUpdateProblem(detail: String = "error.api.update-aip") : AbstractThrowableProblem(
    URI.create(""),
    "Could not update AIP!",
    Status.BAD_REQUEST,
    detail
) {
    override fun getCause(): Exceptional? = super.cause
}
