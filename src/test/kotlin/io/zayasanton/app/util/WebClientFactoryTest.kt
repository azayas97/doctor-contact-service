package io.zayasanton.app.util

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.zayasanton.app.types.ClientNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.web.reactive.function.client.WebClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WebClientFactoryTest {

    @RelaxedMockK
    lateinit var webClientFactory: WebClientFactory

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getClient - returns a valid webClient`() {
        val mockedWebClient = WebClient.builder().build()

        webClientFactory = WebClientFactory(
            mapOf(Pair("doctorContact", mockedWebClient))
        )

        val response = webClientFactory.getClient("doctorContact")

        Assertions.assertNotNull(response)
    }

    @Test
    fun `getClient - throws ClientNotFoundException if client wasn't found`() {
        webClientFactory = WebClientFactory(
            mapOf()
        )

        assertThrows<ClientNotFoundException> {
            webClientFactory.getClient("doctorContact")
        }
    }
}