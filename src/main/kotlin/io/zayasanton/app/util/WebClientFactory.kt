package io.zayasanton.app.util

import io.zayasanton.app.types.ClientNotFoundException
import org.springframework.web.reactive.function.client.WebClient

class WebClientFactory(
    private val webClients: Map<String, WebClient>
) {
    fun getClient(webClientName: String): WebClient {
        return webClients[webClientName] ?: throw ClientNotFoundException("Client $webClientName was not found.")
    }
}