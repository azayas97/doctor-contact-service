package io.zayasanton.app.actions.queries

import graphql.GraphQLContext
import graphql.schema.DataFetchingEnvironment
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.zayasanton.app.api.DoctorContactApi
import io.zayasanton.app.fixtures.getDoctorsFailedResponse
import io.zayasanton.app.fixtures.getDoctorsResponse
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Flux
import java.lang.Exception

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DashboardQueryTest {

    @RelaxedMockK
    lateinit var doctorContactApi: DoctorContactApi

    @RelaxedMockK
    lateinit var dataFetchingEnvironment: DataFetchingEnvironment

    @InjectMockKs
    lateinit var dashboardQuery: DashboardQuery

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this)

        every {
            dataFetchingEnvironment.graphQlContext
        } returns GraphQLContext.of(
            mapOf(Pair("token", "token"))
        )
    }

    @Test
    fun `dashboardQuery - returns successful response`() {
        coEvery {
            doctorContactApi.getDoctors(any())
        } returns Flux.just(getDoctorsResponse())

        runBlocking {
            val response = dashboardQuery.dashboardQuery("123", dataFetchingEnvironment)

            Assertions.assertTrue(response.doctors.isNotEmpty())
            Assertions.assertNotEquals("Couldn't fetch the doctors list", response.header)
        }
    }

    @Test
    fun `dashboardQuery - api call is not successful`() {
        coEvery {
            doctorContactApi.getDoctors(any())
        } returns Flux.just(getDoctorsFailedResponse())

        runBlocking {
            val response = dashboardQuery.dashboardQuery("123", dataFetchingEnvironment)

            Assertions.assertTrue(response.doctors.isEmpty())
            Assertions.assertEquals("Couldn't fetch the doctors list", response.header)
        }
    }

    @Test
    fun `dashboardQuery - api call throws error`() {
        coEvery {
            doctorContactApi.getDoctors(any())
        } throws Exception()

        runBlocking {
            val response = dashboardQuery.dashboardQuery("123", dataFetchingEnvironment)

            Assertions.assertTrue(response.doctors.isEmpty())
            Assertions.assertEquals("Something went wrong!", response.header)
        }
    }
}