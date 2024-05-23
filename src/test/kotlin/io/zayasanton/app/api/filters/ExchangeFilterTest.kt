package io.zayasanton.app.api.filters

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.zayasanton.app.types.Constants.SESSION_ID_KEY
import io.zayasanton.app.types.Constants.USER_ID_KEY
import io.zayasanton.app.types.DoctorContactAPIException
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.web.reactive.function.client.WebClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExchangeFilterTest {

    @RelaxedMockK
    lateinit var wireMockServer: WireMockServer

    @RelaxedMockK
    lateinit var webClient: WebClient

    @RelaxedMockK
    lateinit var wireMock: WireMock

    @RelaxedMockK
    lateinit var exchangeFilter: ExchangeFilter

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this)

        wireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort())
        wireMockServer.start()

        wireMock = WireMock.create().port(wireMockServer.port()).build()
        wireMock.loadMappingsFrom("src/test/resources")

        exchangeFilter = ExchangeFilter("http://localhost:${wireMockServer.port()}")
        webClient = WebClient
            .builder()
            .baseUrl("http://localhost:${wireMockServer.port()}")
            .filter(exchangeFilter)
            .build()
    }

    @Test
    fun `Filter makes a successful call`() {
        runBlocking {
            webClient.get()
                .uri("/test")
                .retrieve()
                .bodyToMono(String::class.java)
                .awaitFirst()
        }
    }

    @Test
    fun `Filter returns an error`() {
        assertThrows<DoctorContactAPIException> {
            runBlocking {
                webClient.get()
                    .uri("/test")
                    .headers {
                        it.set(SESSION_ID_KEY, "sessionId")
                        it.set(USER_ID_KEY, "userId")
                    }
                    .retrieve()
                    .bodyToMono(String::class.java)
                    .awaitFirst()
            }
        }
    }

}