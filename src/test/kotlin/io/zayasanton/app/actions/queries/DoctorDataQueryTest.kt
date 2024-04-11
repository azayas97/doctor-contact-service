package io.zayasanton.app.actions.queries

import graphql.schema.DataFetchingEnvironment
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.zayasanton.app.api.DoctorContactApi
import io.zayasanton.app.fixtures.getDoctorByIDFailedResponse
import io.zayasanton.app.fixtures.getDoctorByIDResponse
import io.zayasanton.app.types.DCSButtonType
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import reactor.core.publisher.Flux
import java.lang.Exception

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorDataQueryTest {

    @RelaxedMockK
    lateinit var doctorContactApi: DoctorContactApi

    @RelaxedMockK
    lateinit var dataFetchingEnvironment: DataFetchingEnvironment

    @InjectMockKs
    lateinit var doctorDataQuery: DoctorDataQuery

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `updateDoctorSideSheetQuery - returns sidesheet successfully`() {
        coEvery {
            doctorContactApi.getDoctorByID(any())
        } returns Flux.just(getDoctorByIDResponse())

        runBlocking {
            val response = doctorDataQuery.updateDoctorSideSheetQuery("123", dataFetchingEnvironment)

            Assertions.assertEquals("Update doctor", response.header)
            Assertions.assertNull(response.subHeader)
            Assertions.assertNotNull(response.fields.doctorName.value)
            Assertions.assertNotNull(response.fields.department.value)
            Assertions.assertNotNull(response.fields.clinic.value)
            Assertions.assertNotNull(response.fields.phone.value)
            Assertions.assertFalse(response.submit.disabled)
        }
    }

    @Test
    fun `updateDoctorSideSheetQuery - returns sidesheet with error from api call`() {
        coEvery {
            doctorContactApi.getDoctorByID(any())
        } returns Flux.just(getDoctorByIDFailedResponse())

        runBlocking {
            val response = doctorDataQuery.updateDoctorSideSheetQuery("123", dataFetchingEnvironment)

            Assertions.assertEquals("Update doctor", response.header)
            Assertions.assertNotNull(response.subHeader)
            Assertions.assertNull(response.fields.doctorName.value)
            Assertions.assertNull(response.fields.department.value)
            Assertions.assertNull(response.fields.clinic.value)
            Assertions.assertNull(response.fields.phone.value)
            Assertions.assertTrue(response.submit.disabled)
        }
    }

    @Test
    fun `updateDoctorSideSheetQuery - api call throws exception`() {
        coEvery {
            doctorContactApi.getDoctorByID(any())
        } throws Exception()

        runBlocking {
            val response = doctorDataQuery.updateDoctorSideSheetQuery("123", dataFetchingEnvironment)

            Assertions.assertEquals("Update doctor", response.header)
            Assertions.assertEquals(
                "An error occurred and we couldn't fetch the doctor's data",
                response.subHeader
            )
            Assertions.assertNull(response.fields.doctorName.value)
            Assertions.assertNull(response.fields.department.value)
            Assertions.assertNull(response.fields.clinic.value)
            Assertions.assertNull(response.fields.phone.value)
            Assertions.assertTrue(response.submit.disabled)
        }
    }

    @Test
    fun `createDoctorSideSheetQuery - returns sidesheet successfully`() {
        runBlocking {
            val response = doctorDataQuery.createDoctorSideSheetQuery(dataFetchingEnvironment)

            Assertions.assertEquals("Create doctor", response.header)
            Assertions.assertNull(response.subHeader)
            Assertions.assertNull(response.fields.doctorName.value)
            Assertions.assertNull(response.fields.department.value)
            Assertions.assertNull(response.fields.clinic.value)
            Assertions.assertNull(response.fields.phone.value)
            Assertions.assertFalse(response.submit.disabled)
        }
    }

    @Test
    fun `deleteDoctorModalQuery - returns modal successfully`() {
        runBlocking {
            val response = doctorDataQuery.deleteDoctorModalQuery(dataFetchingEnvironment)

            Assertions.assertEquals("Delete doctor", response.header)
            Assertions.assertEquals(
                "Are you sure to delete this doctor?",
                response.subHeader
            )
            Assertions.assertEquals(DCSButtonType.PRIMARY, response.primaryButton.type)
            Assertions.assertEquals("Delete", response.primaryButton.message)
            Assertions.assertEquals(DCSButtonType.SECONDARY, response.secondaryButton.type)
            Assertions.assertEquals("Cancel", response.secondaryButton.message)
        }
    }
}