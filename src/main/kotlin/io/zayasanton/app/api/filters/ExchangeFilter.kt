package io.zayasanton.app.api.filters

import io.zayasanton.app.api.filters.models.ExchangeRequest
import io.zayasanton.app.api.filters.models.ExchangeResponse
import io.zayasanton.app.types.Constants.AUTH_HEADER_VALUE
import io.zayasanton.app.types.Constants.SESSION_ID_KEY
import io.zayasanton.app.types.Constants.USER_ID_KEY
import io.zayasanton.app.types.DoctorContactAPIException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient


class ExchangeFilter(private val endpoint: String) : ExchangeFilterFunction {

    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {
        return WebClient.builder()
            .baseUrl(endpoint)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create()
                )
            )
            .build()
            .post()
            .uri("/v1/auth/exchange")
            .body(
                BodyInserters.fromValue(
                    ExchangeRequest(
                        userId = request.headers().getFirst(USER_ID_KEY) ?: "",
                        sessionId = request.headers().getFirst(SESSION_ID_KEY) ?: ""
                    )
                )
            )
            .exchangeToMono {
                when (it.statusCode()) {
                    HttpStatus.OK -> it.bodyToMono(ExchangeResponse::class.java)
                    else -> throw DoctorContactAPIException("Error in Filter API call, received ${it.statusCode()}")
                }
            }
            .flatMap { response ->
                next.exchange(
                    ClientRequest.from(request)
                        .headers {
                            it.remove(SESSION_ID_KEY)
                            it.remove(USER_ID_KEY)
                            it.set(AUTH_HEADER_VALUE, response.data)
                        }
                        .build()
                )
            }
    }
}