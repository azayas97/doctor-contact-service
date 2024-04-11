package io.zayasanton.app.actions.models.response

import io.zayasanton.app.types.DCSLink

data class NavigationBar(
    val mainLink: DCSLink,
    val logout: DCSLink
)
