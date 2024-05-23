package io.zayasanton.app.api.filters.models

import io.zayasanton.app.api.models.Response

data class ExchangeResponse(
    override val success: Boolean,
    override val code: Int,
    override val message: String?,
    val data: String
) : Response
