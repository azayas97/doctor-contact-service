package io.zayasanton.app.api.models.request

import graphql.GraphQLContext

data class RemoveDoctorRequest(
    val graphQLContext: GraphQLContext,
    val data: RemoveDoctorInput
)

data class RemoveDoctorInput(
    val doctorId: String
)

fun RemoveDoctorRequest.getAuthTokenHeader() = this.graphQLContext["token"] ?: ""
