package io.zayasanton.app.types.ext

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.graphql.server.WebGraphQlInterceptor
import org.springframework.graphql.server.WebGraphQlRequest
import org.springframework.graphql.server.WebGraphQlResponse
import org.springframework.http.HttpHeaders
import org.springframework.util.CollectionUtils
import reactor.core.publisher.Mono
import wiremock.org.apache.http.client.utils.URIBuilder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GraphQLContextExtTest {

    @RelaxedMockK
    lateinit var chain: WebGraphQlInterceptor.Chain

    @RelaxedMockK
    lateinit var webGraphQlRequest: WebGraphQlRequest

    @RelaxedMockK
    lateinit var webGraphQlResponse: WebGraphQlResponse

    @InjectMockKs
    lateinit var requestHeaderInterceptor: RequestHeaderInterceptor

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this)

        webGraphQlRequest = WebGraphQlRequest(
            URIBuilder()
                .setPath("/")
                .setHost("http://0.0.0.0")
                .build(),
            HttpHeaders.readOnlyHttpHeaders(
                CollectionUtils.toMultiValueMap(
                    mapOf(
                        Pair("Cookie", listOf("session_id=session"))
                    )
                )
            ),
            mapOf(
                Pair("query", "asd")
            ),
            "id",
            null
        )
    }

    @Test
    fun `Intercepts GraphQL request successfully`() {
        coEvery {
            chain.next(any())
        } returns Mono.just(webGraphQlResponse)

        runBlocking {
            requestHeaderInterceptor.intercept(webGraphQlRequest, chain).awaitFirst()

            verify(exactly = 1) {
                chain.next(any())
            }
        }
    }

}