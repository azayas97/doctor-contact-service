package io.zayasanton.app.fixtures

import io.zayasanton.app.api.models.request.CreateDoctorInput
import io.zayasanton.app.api.models.request.RemoveDoctorInput
import io.zayasanton.app.api.models.request.UpdateDoctorInput

fun getCreateDoctorInput() = CreateDoctorInput(
    name = "Antonio",
    dpt = "Oncology",
    clinic = "Antonio's Clinic",
    phone = "+526625555555"
)

fun getUpdateDoctorInput() = UpdateDoctorInput(
    id = "1",
    name = "Antonio",
    dpt = "Oncology",
    clinic = "Antonio's Clinic",
    phone = "+526625555555"
)

fun getRemoveDoctorInput() = RemoveDoctorInput(
    doctorId = "1"
)
