package io.zayasanton.app.actions.models.request

data class UpdateDoctorMutationRequest(
    val id: String,
    val name: String?,
    val department: String?,
    val clinic: String?,
    val phone: String?
)