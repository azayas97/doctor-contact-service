package io.zayasanton.app.actions.mutations

import graphql.GraphQLContext
import graphql.schema.DataFetchingEnvironment
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.zayasanton.app.api.DoctorContactApi
import io.zayasanton.app.fixtures.getCreateDoctorMutationRequest
import io.zayasanton.app.fixtures.getCreateDoctorResponse
import io.zayasanton.app.fixtures.getUpdateDoctorMutationRequest
import io.zayasanton.app.fixtures.getUpdateDoctorResponse
import io.zayasanton.app.fixtures.getRemoveDoctorMutationRequest
import io.zayasanton.app.fixtures.getRemoveDoctorResponse
import io.zayasanton.app.types.Constants.GENERIC_ERROR
import io.zayasanton.app.types.DCSResultCardType
import io.zayasanton.app.types.DoctorContactAPIException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Flux

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorMutationTest {

    @RelaxedMockK
    lateinit var doctorContactApi: DoctorContactApi

    @RelaxedMockK
    lateinit var dataFetchingEnvironment: DataFetchingEnvironment

    @InjectMockKs
    lateinit var doctorMutation: DoctorMutation

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
    fun `createDoctorMutation - returns successful response`() {
        coEvery {
            doctorContactApi.createDoctor(any())
        } returns Flux.just(getCreateDoctorResponse())

        runBlocking {
            val response = doctorMutation.createDoctorMutation(
                getCreateDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.SUCCESS, response.resultCard.type)
            Assertions.assertEquals("Created doctor successfully", response.resultCard.text)
        }
    }

    @Test
    fun `createDoctorMutation - catches error`() {
        coEvery {
            doctorContactApi.createDoctor(any())
        } throws DoctorContactAPIException("Error!")

        runBlocking {
            val response = doctorMutation.createDoctorMutation(
                getCreateDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.ERROR, response.resultCard.type)
            Assertions.assertEquals("Error!", response.resultCard.text)
        }
    }

    @Test
    fun `createDoctorMutation - catches error without message`() {
        coEvery {
            doctorContactApi.createDoctor(any())
        } throws Exception()

        runBlocking {
            val response = doctorMutation.createDoctorMutation(
                getCreateDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.ERROR, response.resultCard.type)
            Assertions.assertEquals(GENERIC_ERROR, response.resultCard.text)
        }
    }

    @Test
    fun `updateDoctorMutation - returns successful response`() {
        coEvery {
            doctorContactApi.updateDoctor(any())
        } returns Flux.just(getUpdateDoctorResponse())

        runBlocking {
            val response = doctorMutation.updateDoctorMutation(
                getUpdateDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.SUCCESS, response.resultCard.type)
            Assertions.assertEquals("Updated doctor successfully", response.resultCard.text)
        }
    }

    @Test
    fun `updateDoctorMutation - catches error`() {
        coEvery {
            doctorContactApi.updateDoctor(any())
        } throws DoctorContactAPIException("Error!")

        runBlocking {
            val response = doctorMutation.updateDoctorMutation(
                getUpdateDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.ERROR, response.resultCard.type)
            Assertions.assertEquals("Error!", response.resultCard.text)
        }
    }

    @Test
    fun `updateDoctorMutation - catches error without message`() {
        coEvery {
            doctorContactApi.updateDoctor(any())
        } throws Exception()

        runBlocking {
            val response = doctorMutation.updateDoctorMutation(
                getUpdateDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.ERROR, response.resultCard.type)
            Assertions.assertEquals(GENERIC_ERROR, response.resultCard.text)
        }
    }

    @Test
    fun `removeDoctorMutation - returns successful response`() {
        coEvery {
            doctorContactApi.removeDoctor(any())
        } returns Flux.just(getRemoveDoctorResponse())

        runBlocking {
            val response = doctorMutation.removeDoctorMutation(
                getRemoveDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.SUCCESS, response.resultCard.type)
            Assertions.assertEquals("Removed doctor successfully", response.resultCard.text)
        }
    }

    @Test
    fun `removeDoctorMutation - catches error`() {
        coEvery {
            doctorContactApi.removeDoctor(any())
        } throws DoctorContactAPIException("Error!")

        runBlocking {
            val response = doctorMutation.removeDoctorMutation(
                getRemoveDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.ERROR, response.resultCard.type)
            Assertions.assertEquals("Error!", response.resultCard.text)
        }
    }

    @Test
    fun `removeDoctorMutation - catches error without message`() {
        coEvery {
            doctorContactApi.removeDoctor(any())
        } throws Exception()

        runBlocking {
            val response = doctorMutation.removeDoctorMutation(
                getRemoveDoctorMutationRequest(),
                dataFetchingEnvironment
            )

            Assertions.assertEquals(DCSResultCardType.ERROR, response.resultCard.type)
            Assertions.assertEquals(GENERIC_ERROR, response.resultCard.text)
        }
    }
}