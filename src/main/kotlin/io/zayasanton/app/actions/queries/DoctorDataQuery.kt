package io.zayasanton.app.actions.queries

import graphql.schema.DataFetchingEnvironment
import io.zayasanton.app.actions.models.response.DeleteModal
import io.zayasanton.app.actions.models.response.DoctorSideSheet
import io.zayasanton.app.actions.models.response.DoctorSideSheetFields
import io.zayasanton.app.api.DoctorContactApi
import io.zayasanton.app.api.models.request.GetDoctorByIDRequest
import io.zayasanton.app.api.models.response.GetDoctorByIDResponse
import io.zayasanton.app.types.DCSButton
import io.zayasanton.app.types.DCSButtonType
import io.zayasanton.app.types.DCSField
import io.zayasanton.app.types.DCSFieldType
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorDataQuery {

    @Autowired
    private lateinit var doctorContactApi: DoctorContactApi

    @QueryMapping
    fun deleteDoctorModalQuery(
        dataFetchingEnvironment: DataFetchingEnvironment
    ): DeleteModal {
        return DeleteModal(
            header = "Delete doctor",
            subHeader = "Are you sure to delete this doctor?",
            primaryButton = DCSButton(
                type = DCSButtonType.PRIMARY,
                message = "Delete"
            ),
            secondaryButton = DCSButton(
                type = DCSButtonType.SECONDARY,
                message = "Cancel"
            )
        )
    }

    @QueryMapping
    suspend fun doctorSideSheetQuery(
        @Argument doctorId: String?,
        @Argument userId: String,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): DoctorSideSheet {

        val graphQLContext = dataFetchingEnvironment.graphQlContext

        return try {
            var header = "Create doctor"
            var response: GetDoctorByIDResponse? = null
            var isDisabled = false
            var subHeader: String? = null

            if (doctorId != null) {
                header =  "Update doctor"
                response = doctorContactApi.getDoctorByID(
                    GetDoctorByIDRequest(
                        graphQLContext = graphQLContext,
                        doctorId = doctorId,
                        userId = userId
                    )
                )
                    .awaitFirst()
                    .takeIf {
                        it.success
                    }

                subHeader = if (response == null)
                    "An error occurred and we couldn't fetch the doctor's data"
                else null

                isDisabled = !subHeader.isNullOrBlank()
            }


            DoctorSideSheet(
                header = header,
                subHeader = subHeader,
                fields = DoctorSideSheetFields(
                    doctorName = DCSField(
                        id = "doctorName",
                        label = "Doctor's name",
                        type = DCSFieldType.TEXT,
                        disabled = isDisabled,
                        value = response?.data?.name
                    ),
                    department = DCSField(
                        id = "department",
                        label = "Department",
                        type = DCSFieldType.TEXT,
                        disabled = isDisabled,
                        value = response?.data?.department
                    ),
                    clinic = DCSField(
                        id = "clinic",
                        label = "Clinic",
                        type = DCSFieldType.TEXT,
                        disabled = isDisabled,
                        value = response?.data?.clinic
                    ),
                    phone = DCSField(
                        id = "phone",
                        label = "Phone number",
                        type = DCSFieldType.TEXT,
                        disabled = isDisabled,
                        value = response?.data?.phone
                    )
                ),
                submit = DCSButton(
                    type = DCSButtonType.PRIMARY,
                    message = "Submit",
                    disabled = isDisabled
                )
            )
        } catch (err: Exception) {
            println(err)
            DoctorSideSheet(
                header = "Update doctor",
                subHeader = "An error occurred and we couldn't fetch the doctor's data",
                fields = DoctorSideSheetFields(
                    doctorName = DCSField(
                        id = "doctorName",
                        label = "Doctor's name",
                        type = DCSFieldType.TEXT,
                        disabled = true
                    ),
                    department = DCSField(
                        id = "department",
                        label = "Department",
                        type = DCSFieldType.TEXT,
                        disabled = true
                    ),
                    clinic = DCSField(
                        id = "clinic",
                        label = "Clinic",
                        type = DCSFieldType.TEXT,
                        disabled = true
                    ),
                    phone = DCSField(
                        id = "phone",
                        label = "Phone number",
                        type = DCSFieldType.TEXT,
                        disabled = true
                    )
                ),
                submit = DCSButton(
                    type = DCSButtonType.PRIMARY,
                    message = "Submit",
                    disabled = true
                )
            )
        }
    }
}