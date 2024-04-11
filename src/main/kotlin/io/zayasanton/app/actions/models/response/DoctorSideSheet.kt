package io.zayasanton.app.actions.models.response

import io.zayasanton.app.actions.models.commons.SideSheet
import io.zayasanton.app.types.DCSButton
import io.zayasanton.app.types.DCSField

data class DoctorSideSheet(
    override val header: String,
    override val subHeader: String? = null,
    val fields: DoctorSideSheetFields,
    val submit: DCSButton
) : SideSheet

data class DoctorSideSheetFields(
    val doctorName: DCSField,
    val department: DCSField,
    val clinic: DCSField,
    val phone: DCSField
)
