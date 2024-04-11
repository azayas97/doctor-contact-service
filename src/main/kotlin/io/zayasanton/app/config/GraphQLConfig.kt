package io.zayasanton.app.config

import io.zayasanton.app.types.ext.RequestHeaderInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.server.WebGraphQlInterceptor

@Configuration
class GraphQLConfig {
    @Bean
    fun requestHeaderInterceptor(): WebGraphQlInterceptor {
        return RequestHeaderInterceptor()
    }
}