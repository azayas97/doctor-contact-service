package io.zayasanton.app.api.models.request

import graphql.GraphQLContext

data class GetDoctorByIDRequest(
    val graphQLContext: GraphQLContext,
    val doctorId: String
)

fun GetDoctorByIDRequest.getAuthTokenHeader() = this.graphQLContext["token"] ?: ""