package io.zayasanton.app.actions.queries

import io.zayasanton.app.actions.models.response.NavigationBar
import io.zayasanton.app.api.DoctorContactApi
import io.zayasanton.app.types.DCSLink
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class NavigationQuery {

    @QueryMapping
    fun navigationQuery(@Argument userId: String): NavigationBar {
        return NavigationBar(
            mainLink = DCSLink(
                text = "Doctor Contacts",
                url = "/dashboard"
            ),
            logout = DCSLink(
                text = "Logout",
                url = "/login"
            )
        )
    }
}