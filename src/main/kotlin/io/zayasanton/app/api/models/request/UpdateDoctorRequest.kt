package io.zayasanton.app.api.models.request

import graphql.GraphQLContext

data class UpdateDoctorRequest(
    val graphQLContext: GraphQLContext,
    val data: UpdateDoctorInput,
)

data class UpdateDoctorInput(
    val id: String,
    val name: String?,
    val dpt: String?,
    val clinic: String?,
    val phone: String?
)

fun UpdateDoctorRequest.getAuthTokenHeader() = this.graphQLContext["token"] ?: ""
