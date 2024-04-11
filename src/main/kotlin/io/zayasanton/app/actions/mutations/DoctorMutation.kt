package io.zayasanton.app.actions.mutations

import graphql.schema.DataFetchingEnvironment
import io.zayasanton.app.actions.models.request.CreateDoctorMutationRequest
import io.zayasanton.app.actions.models.request.RemoveDoctorMutationRequest
import io.zayasanton.app.actions.models.request.UpdateDoctorMutationRequest
import io.zayasanton.app.actions.models.response.MutationResponse
import io.zayasanton.app.api.DoctorContactApi
import io.zayasanton.app.api.models.request.CreateDoctorInput
import io.zayasanton.app.api.models.request.CreateDoctorRequest
import io.zayasanton.app.api.models.request.UpdateDoctorInput
import io.zayasanton.app.api.models.request.UpdateDoctorRequest
import io.zayasanton.app.api.models.request.RemoveDoctorInput
import io.zayasanton.app.api.models.request.RemoveDoctorRequest
import io.zayasanton.app.types.Constants.GENERIC_ERROR
import io.zayasanton.app.types.DCSResultCard
import io.zayasanton.app.types.DCSResultCardType
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class DoctorMutation {

    @Autowired
    private lateinit var doctorContactApi: DoctorContactApi

    @MutationMapping
    suspend fun createDoctorMutation(
        @Argument request: CreateDoctorMutationRequest,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): MutationResponse {

        val graphQLContext = dataFetchingEnvironment.graphQlContext

        return try {
            doctorContactApi.createDoctor(
                CreateDoctorRequest(
                    graphQLContext = graphQLContext,
                    data = CreateDoctorInput(
                        name = request.name,
                        dpt = request.department,
                        clinic = request.clinic,
                        phone = request.phone
                    )
                )
            ).awaitFirst()

            MutationResponse(
                resultCard = DCSResultCard(
                    type = DCSResultCardType.SUCCESS,
                    text = "Created doctor successfully"
                )
            )
        } catch (err: Exception) {
            println(err.message)
            MutationResponse(
                resultCard = DCSResultCard(
                    type = DCSResultCardType.ERROR,
                    text = err.message ?: GENERIC_ERROR
                )
            )
        }
    }

    @MutationMapping
    suspend fun updateDoctorMutation(
        @Argument request: UpdateDoctorMutationRequest,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): MutationResponse {

        val graphQLContext = dataFetchingEnvironment.graphQlContext

        return try {
            doctorContactApi.updateDoctor(
                UpdateDoctorRequest(
                    graphQLContext = graphQLContext,
                    data = UpdateDoctorInput(
                        id = request.id,
                        name = request.name,
                        dpt = request.department,
                        clinic = request.clinic,
                        phone = request.phone
                    )
                )
            ).awaitFirst()

            MutationResponse(
                resultCard = DCSResultCard(
                    type = DCSResultCardType.SUCCESS,
                    text = "Updated doctor successfully"
                )
            )
        } catch (err: Exception) {
            println(err.message)
            MutationResponse(
                resultCard = DCSResultCard(
                    type = DCSResultCardType.ERROR,
                    text = err.message ?: GENERIC_ERROR
                )
            )
        }
    }

    @MutationMapping
    suspend fun removeDoctorMutation(
        @Argument request: RemoveDoctorMutationRequest,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): MutationResponse {

        val graphQLContext = dataFetchingEnvironment.graphQlContext

        return try {
            doctorContactApi.removeDoctor(
                RemoveDoctorRequest(
                    graphQLContext = graphQLContext,
                    data = RemoveDoctorInput(
                        doctorId = request.doctorId
                    )
                )
            ).awaitFirst()

            MutationResponse(
                resultCard = DCSResultCard(
                    type = DCSResultCardType.SUCCESS,
                    text = "Removed doctor successfully"
                )
            )
        } catch (err: Exception) {
            println(err.message)
            MutationResponse(
                resultCard = DCSResultCard(
                    type = DCSResultCardType.ERROR,
                    text = err.message ?: GENERIC_ERROR
                )
            )
        }
    }
}