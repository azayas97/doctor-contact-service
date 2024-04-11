package io.zayasanton.app.api.models

interface Response {
    val success: Boolean
    val code: Int
    val message: String?
}