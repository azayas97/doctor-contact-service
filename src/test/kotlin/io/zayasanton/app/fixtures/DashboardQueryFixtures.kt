package io.zayasanton.app.fixtures

import io.zayasanton.app.api.models.response.GetDoctorsResponse
import io.zayasanton.app.api.models.commons.Doctor

fun getDoctorsResponse() = GetDoctorsResponse(
    success = true,
    code = 200,
    message = null,
    data = listOf(
        Doctor(
            id = "0",
            name = "Antonio",
            department = "Oncology",
            clinic = "Antonio's Clinic",
            phone = "+526625555555"
        )
    )
)

fun getDoctorsFailedResponse() = GetDoctorsResponse(
    success = false,
    code = 400,
    message = null,
    data = listOf()
)
