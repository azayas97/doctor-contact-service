package io.zayasanton.app.api

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import graphql.GraphQLContext
import graphql.schema.DataFetchingEnvironment
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.zayasanton.app.api.models.request.CreateDoctorRequest
import io.zayasanton.app.api.models.request.GetDoctorsRequest
import io.zayasanton.app.api.models.request.GetDoctorByIDRequest
import io.zayasanton.app.api.models.request.UpdateDoctorRequest
import io.zayasanton.app.api.models.request.RemoveDoctorRequest
import io.zayasanton.app.fixtures.getCreateDoctorInput
import io.zayasanton.app.fixtures.getUpdateDoctorInput
import io.zayasanton.app.fixtures.getRemoveDoctorInput
import io.zayasanton.app.types.DoctorContactAPIException
import io.zayasanton.app.util.WebClientFactory
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.web.reactive.function.client.WebClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorContactApiTest {

    @RelaxedMockK
    lateinit var wireMockServer: WireMockServer

    @RelaxedMockK
    lateinit var webClientFactory: WebClientFactory

    @RelaxedMockK
    lateinit var webClient: WebClient

    @RelaxedMockK
    lateinit var wireMock: WireMock

    @RelaxedMockK
    lateinit var dataFetchingEnvironment: DataFetchingEnvironment

    @InjectMockKs
    lateinit var doctorContactApi: DoctorContactApi

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this)

        wireMockServer = WireMockServer(wireMockConfig().dynamicPort())
        wireMockServer.start()

        wireMock = WireMock.create().port(wireMockServer.port()).build()
        wireMock.loadMappingsFrom("src/test/resources")

        every {
            dataFetchingEnvironment.graphQlContext
        } returns GraphQLContext.of(
            mapOf(Pair("token", "token"))
        )

        webClient = WebClient.create("http://localhost:${wireMockServer.port()}")

        every {
            webClientFactory.getClient(any())
        } returns webClient

        doctorContactApi = DoctorContactApi(webClientFactory)
    }

    @AfterAll
    fun afterAll() {
        wireMock.resetScenarios()
        wireMockServer.stop()
    }

    @Test
    fun `getDoctors - returns successful response`() {
        runBlocking {
            val response = doctorContactApi.getDoctors(
                GetDoctorsRequest(
                    graphQLContext = dataFetchingEnvironment.graphQlContext,
                    userId = "1"
                )
            ).awaitFirst()

            Assertions.assertNotNull(response)
        }
    }

    @Test
    fun `getDoctorByID - returns successful response`() {
        runBlocking {
            val response = doctorContactApi.getDoctorByID(
                GetDoctorByIDRequest(
                    graphQLContext = dataFetchingEnvironment.graphQlContext,
                    doctorId = "1"
                )
            ).awaitFirst()

            Assertions.assertNotNull(response)
        }
    }

    @Test
    fun `createDoctor - returns successful response`() {

        val createDoctorRequest = CreateDoctorRequest(
            graphQLContext = dataFetchingEnvironment.graphQlContext,
            data = getCreateDoctorInput()
        )

        runBlocking {
            val response = doctorContactApi.createDoctor(createDoctorRequest).awaitFirst()

            Assertions.assertNotNull(response)
        }
    }

    @Test
    fun `createDoctor - throws DoctorContactAPIException`() {
        val createDoctorRequest = CreateDoctorRequest(
            graphQLContext = dataFetchingEnvironment.graphQlContext,
            data = getCreateDoctorInput()
        )

        runBlocking {
            assertThrows<DoctorContactAPIException> {
                doctorContactApi.createDoctor(createDoctorRequest).awaitFirst()
            }
        }
    }

    @Test
    fun `updateDoctor - returns successful response`() {
        val updateDoctorRequest = UpdateDoctorRequest(
            graphQLContext = dataFetchingEnvironment.graphQlContext,
            data = getUpdateDoctorInput()
        )

        runBlocking {
            val response = doctorContactApi.updateDoctor(updateDoctorRequest).awaitFirst()

            Assertions.assertNotNull(response)
        }
    }

    @Test
    fun `updateDoctor - throws DoctorContactAPIException`() {
        val updateDoctorRequest = UpdateDoctorRequest(
            graphQLContext = dataFetchingEnvironment.graphQlContext,
            data = getUpdateDoctorInput()
        )

        runBlocking {
            assertThrows<DoctorContactAPIException> {
                doctorContactApi.updateDoctor(updateDoctorRequest).awaitFirst()
            }
        }
    }

    @Test
    fun `removeDoctor - returns successful response`() {
        val removeDoctorRequest = RemoveDoctorRequest(
            graphQLContext = dataFetchingEnvironment.graphQlContext,
            data = getRemoveDoctorInput()
        )

        runBlocking {
            val response = doctorContactApi.removeDoctor(removeDoctorRequest).awaitFirst()

            Assertions.assertNotNull(response)
        }
    }

    @Test
    fun `removeDoctor - throws DoctorContactAPIException`() {
        val removeDoctorRequest = RemoveDoctorRequest(
            graphQLContext = dataFetchingEnvironment.graphQlContext,
            data = getRemoveDoctorInput()
        )

        runBlocking {
            assertThrows<DoctorContactAPIException> {
                doctorContactApi.removeDoctor(removeDoctorRequest).awaitFirst()
            }
        }
    }
}