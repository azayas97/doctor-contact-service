package io.zayasanton.app.config

import io.zayasanton.app.util.WebClientFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
@ConfigurationProperties(prefix = "webclient")
@ConstructorBinding
class WebClientConfig(
    val clients: Map<String, WebClientData> = mutableMapOf()
) {
    data class WebClientData(
        val url: String,
        val timeout: Long = 3000
    )

    @Bean
    fun webClientFactory(): WebClientFactory {
        val webClients = mutableMapOf<String, WebClient>()
        for ((clientName, webClientData) in clients) {
            val client: HttpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(webClientData.timeout))
            val webClient = WebClient.builder()
                .baseUrl(webClientData.url)
                .defaultHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(ReactorClientHttpConnector(client))
                .build()
            webClients[clientName] = webClient
        }
        return WebClientFactory(webClients)
    }
}
