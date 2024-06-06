package io.zayasanton.app.actions.queries

import graphql.schema.DataFetchingEnvironment
import io.zayasanton.app.actions.models.response.Dashboard
import io.zayasanton.app.api.DoctorContactApi
import io.zayasanton.app.api.models.request.GetDoctorsRequest
import io.zayasanton.app.types.DCSButton
import io.zayasanton.app.types.DCSButtonType
import io.zayasanton.app.types.DCSCard
import io.zayasanton.app.types.DCSPopup
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class DashboardQuery {

    @Autowired
    private lateinit var doctorContactApi: DoctorContactApi

    @QueryMapping
    suspend fun dashboardQuery(
        @Argument userId: String,
        dataFetchingEnvironment: DataFetchingEnvironment
    ): Dashboard {
        val graphQLContext = dataFetchingEnvironment.graphQlContext

        return try {
            val response = doctorContactApi.getDoctors(
                GetDoctorsRequest(
                    graphQLContext = graphQLContext,
                    userId = userId
                )
            )
                .awaitFirst()
                .takeIf {
                    it.success
                } ?: return Dashboard(
                    header = "Couldn't fetch the doctors list",
                    doctors = listOf(),
                    actionButton = DCSButton(
                        type = DCSButtonType.PRIMARY,
                        message = "Add new doctor",
                        disabled = true
                    ),
                    removePopup = DCSPopup(
                        header = "",
                        body = "",
                        primary = DCSButton(
                            type = DCSButtonType.PRIMARY,
                            message = "",
                            disabled = false
                        ),
                        secondary = DCSButton(
                            type = DCSButtonType.SECONDARY,
                            message = "",
                            disabled = false
                        )
                    )
                )

            val doctorsList: List<DCSCard> = response.data.map {
                DCSCard(
                    id = it.id,
                    name = it.name,
                    workplace = "${it.clinic} - ${it.department}",
                    phoneNumber = it.phone,
                    phoneNumberLabel = "Phone number: "
                )
            }

            Dashboard(
                header = "Doctors list",
                doctors = doctorsList,
                actionButton = DCSButton(
                    type = DCSButtonType.PRIMARY,
                    message = "Add new doctor"
                ),
                removePopup = DCSPopup(
                    header = "Delete doctor",
                    body = "Are you sure you want to delete this doctor?",
                    primary = DCSButton(
                        type = DCSButtonType.PRIMARY,
                        message = "Delete",
                        disabled = false
                    ),
                    secondary = DCSButton(
                        type = DCSButtonType.SECONDARY,
                        message = "Cancel",
                        disabled = false
                    )
                )
            )
        } catch (err: Exception) {
            println(err)
            Dashboard(
                header = "Something went wrong!",
                doctors = listOf(),
                actionButton = DCSButton(
                    type = DCSButtonType.PRIMARY,
                    message = "Add new doctor",
                    disabled = true
                ),
                removePopup = DCSPopup(
                    header = "",
                    body = "",
                    primary = DCSButton(
                        type = DCSButtonType.PRIMARY,
                        message = "",
                        disabled = false
                    ),
                    secondary = DCSButton(
                        type = DCSButtonType.SECONDARY,
                        message = "",
                        disabled = false
                    )
                )
            )
        }
    }
}