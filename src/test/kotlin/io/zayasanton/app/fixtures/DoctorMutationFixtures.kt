package io.zayasanton.app.fixtures

import io.zayasanton.app.actions.models.request.CreateDoctorMutationRequest
import io.zayasanton.app.actions.models.request.RemoveDoctorMutationRequest
import io.zayasanton.app.actions.models.request.UpdateDoctorMutationRequest
import io.zayasanton.app.api.models.commons.Doctor
import io.zayasanton.app.api.models.response.CreateDoctorResponse
import io.zayasanton.app.api.models.response.UpdateDoctorResponse

fun getCreateDoctorMutationRequest() = CreateDoctorMutationRequest(
    name = "Antonio",
    department = "Oncology",
    clinic = "Antonio's Clinic",
    phone = "+526625555555"
)

fun getUpdateDoctorMutationRequest() = UpdateDoctorMutationRequest(
    id = "0",
    name = "Antonio",
    department = "Oncology",
    clinic = "Antonio's Clinic",
    phone = "+526625555555"
)

fun getRemoveDoctorMutationRequest() = RemoveDoctorMutationRequest(
    doctorId = "1"
)

fun getCreateDoctorResponse() = CreateDoctorResponse(
    success = true,
    code = 200,
    message = null,
    data = getDoctorData()
)

fun getUpdateDoctorResponse() = UpdateDoctorResponse(
    success = true,
    code = 200,
    message = null,
    data = getDoctorData()
)

fun getRemoveDoctorResponse() = UpdateDoctorResponse(
    success = true,
    code = 200,
    message = null,
    data = null
)

fun getDoctorData() = Doctor(
    id = "0",
    name = "Antonio",
    department = "Oncology",
    clinic = "Antonio's Clinic",
    phone = "+526625555555"
)
