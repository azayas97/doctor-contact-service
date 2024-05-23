package io.zayasanton.app.api.models.request

import graphql.GraphQLContext
import io.zayasanton.app.types.Constants.SESSION_ID_KEY

data class CreateDoctorRequest(
    val graphQLContext: GraphQLContext,
    val data: CreateDoctorInput,
    val userId: String,
)

data class CreateDoctorInput(
    val name: String,
    val dpt: String,
    val clinic: String,
    val phone: String
)

fun CreateDoctorRequest.retrieveSessionId() = this.graphQLContext[SESSION_ID_KEY] ?: ""
