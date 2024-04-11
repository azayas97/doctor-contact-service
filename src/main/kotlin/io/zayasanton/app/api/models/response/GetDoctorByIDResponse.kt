package io.zayasanton.app.api.models.response

import io.zayasanton.app.api.models.Response
import io.zayasanton.app.api.models.commons.Doctor

data class GetDoctorByIDResponse(
    override val success: Boolean,
    override val code: Int,
    override val message: String?,
    val data: Doctor?
) : Response
