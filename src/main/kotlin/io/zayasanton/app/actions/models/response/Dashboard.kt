package io.zayasanton.app.actions.models.response

import io.zayasanton.app.types.DCSButton
import io.zayasanton.app.types.DCSCard
import io.zayasanton.app.types.DCSPopup

data class Dashboard(
    val header: String,
    val doctors: List<DCSCard>,
    val actionButton: DCSButton,
    val removePopup: DCSPopup
)
