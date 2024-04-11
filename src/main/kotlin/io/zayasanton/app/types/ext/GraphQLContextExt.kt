package io.zayasanton.app.types.ext

import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import reactor.core.publisher.Mono

class RequestHeaderInterceptor : WebGraphQlInterceptor {

    override fun intercept(
        request: WebGraphQlRequest,
        chain: WebGraphQlInterceptor.Chain
    ): Mono<WebGraphQlResponse> {
        val token = request.headers.getFirst("auth-token") ?: ""
        request.configureExecutionInput { _, builder ->
            builder.graphQLContext(mapOf(Pair("token", token))).build()
        }
        return chain.next(request)
    }
}