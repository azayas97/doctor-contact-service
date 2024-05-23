package io.zayasanton.app.api.models.request

import graphql.GraphQLContext
import io.zayasanton.app.types.Constants.SESSION_ID_KEY

data class GetDoctorByIDRequest(
    val graphQLContext: GraphQLContext,
    val doctorId: String,
    val userId: String,
)

fun GetDoctorByIDRequest.retrieveSessionId() = this.graphQLContext[SESSION_ID_KEY] ?: ""