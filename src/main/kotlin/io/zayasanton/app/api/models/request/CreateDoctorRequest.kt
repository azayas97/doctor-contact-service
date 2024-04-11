package io.zayasanton.app.api.models.request

import graphql.GraphQLContext

data class CreateDoctorRequest(
    val graphQLContext: GraphQLContext,
    val data: CreateDoctorInput
)

data class CreateDoctorInput(
    val name: String,
    val dpt: String,
    val clinic: String,
    val phone: String
)

fun CreateDoctorRequest.getAuthTokenHeader() = this.graphQLContext["token"] ?: ""
