package io.zayasanton.app.actions.models.response

import io.zayasanton.app.types.DCSButton
import io.zayasanton.app.types.DCSCard

data class Dashboard(
    val header: String,
    val doctors: List<DCSCard>,
    val actionButton: DCSButton
)
