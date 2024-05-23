package io.zayasanton.app.types.ext

import io.zayasanton.app.types.Constants.SESSION_ID_KEY
import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import reactor.core.publisher.Mono

class RequestHeaderInterceptor : WebGraphQlInterceptor {

    override fun intercept(
        request: WebGraphQlRequest,
        chain: WebGraphQlInterceptor.Chain
    ): Mono<WebGraphQlResponse> {
        val sessionId = request.headers.getFirst("Cookie")
            ?.split("session_id=")?.get(1) ?: ""
        request.configureExecutionInput { _, builder ->
            builder.graphQLContext(mapOf(Pair(SESSION_ID_KEY, sessionId))).build()
        }
        return chain.next(request)
    }
}