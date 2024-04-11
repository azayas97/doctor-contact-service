package io.zayasanton.app.actions.models.response

import io.zayasanton.app.actions.models.commons.Modal
import io.zayasanton.app.types.DCSButton

data class DeleteModal(
    override val header: String,
    override val subHeader: String? = null,
    override val closeButton: Boolean = false,
    val primaryButton: DCSButton,
    val secondaryButton: DCSButton
) : Modal
