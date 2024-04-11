package io.zayasanton.app.actions.models.request

data class CreateDoctorMutationRequest(
    val name: String,
    val department: String,
    val clinic: String,
    val phone: String
)