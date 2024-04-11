package io.zayasanton.app.types

import org.springframework.web.reactive.function.client.WebClientException

class ClientNotFoundException(message: String): RuntimeException(message)
class DoctorContactAPIException(message: String): WebClientException(message)
