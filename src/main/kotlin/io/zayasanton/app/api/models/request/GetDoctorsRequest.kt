package io.zayasanton.app.api.models.request

import graphql.GraphQLContext

data class GetDoctorsRequest(
    val graphQLContext: GraphQLContext,
    val userId: String
)

fun GetDoctorsRequest.getAuthTokenHeader() = this.graphQLContext["token"] ?: ""