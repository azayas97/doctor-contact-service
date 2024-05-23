package io.zayasanton.app.api.models.request

import graphql.GraphQLContext
import io.zayasanton.app.types.Constants.SESSION_ID_KEY

data class RemoveDoctorRequest(
    val graphQLContext: GraphQLContext,
    val data: RemoveDoctorInput,
    val userId: String,
)

data class RemoveDoctorInput(
    val doctorId: String
)

fun RemoveDoctorRequest.retrieveSessionId() = this.graphQLContext[SESSION_ID_KEY] ?: ""
