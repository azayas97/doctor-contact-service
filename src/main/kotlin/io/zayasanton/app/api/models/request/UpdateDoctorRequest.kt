package io.zayasanton.app.api.models.request

import graphql.GraphQLContext
import io.zayasanton.app.types.Constants.SESSION_ID_KEY

data class UpdateDoctorRequest(
    val graphQLContext: GraphQLContext,
    val data: UpdateDoctorInput,
    val userId: String,
)

data class UpdateDoctorInput(
    val id: String,
    val name: String?,
    val dpt: String?,
    val clinic: String?,
    val phone: String?
)

fun UpdateDoctorRequest.retrieveSessionId() = this.graphQLContext[SESSION_ID_KEY] ?: ""
