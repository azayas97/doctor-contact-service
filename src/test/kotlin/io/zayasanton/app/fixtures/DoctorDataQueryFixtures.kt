package io.zayasanton.app.fixtures

import io.zayasanton.app.api.models.response.GetDoctorByIDResponse
import io.zayasanton.app.api.models.commons.Doctor

fun getDoctorByIDResponse() = GetDoctorByIDResponse(
    success = true,
    code = 200,
    message = null,
    data = Doctor(
        id = "0",
        name = "Antonio",
        department = "Oncology",
        clinic = "Antonio's Clinic",
        phone = "+526625555555"
    )
)

fun getDoctorByIDFailedResponse() = GetDoctorByIDResponse(
    success = false,
    code = 400,
    message = null,
    data = null
)
