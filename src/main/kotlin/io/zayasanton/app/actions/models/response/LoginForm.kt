package io.zayasanton.app.actions.models.response

import io.zayasanton.app.types.DCSButton
import io.zayasanton.app.types.DCSField

data class LoginForm(
    val header: String,
    val subHeader: String,
    val fields: LoginFormFields
)

data class LoginFormFields(
    val email: DCSField,
    val password: DCSField,
    val button: DCSButton
)
