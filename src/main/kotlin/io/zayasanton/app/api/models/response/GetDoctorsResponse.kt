package io.zayasanton.app.api.models.response

import io.zayasanton.app.api.models.Response
import io.zayasanton.app.api.models.commons.Doctor

data class GetDoctorsResponse(
    override val success: Boolean,
    override val code: Int,
    override val message: String?,
    val data: List<Doctor>
) : Response

