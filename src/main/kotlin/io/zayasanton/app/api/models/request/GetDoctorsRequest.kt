package io.zayasanton.app.api.models.request

import graphql.GraphQLContext
import io.zayasanton.app.types.Constants.SESSION_ID_KEY

data class GetDoctorsRequest(
    val graphQLContext: GraphQLContext,
    val userId: String
)

fun GetDoctorsRequest.retrieveSessionId() = this.graphQLContext[SESSION_ID_KEY] ?: ""
