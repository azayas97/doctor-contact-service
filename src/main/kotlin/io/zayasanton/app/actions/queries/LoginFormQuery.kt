package io.zayasanton.app.actions.queries

import io.zayasanton.app.actions.models.response.LoginForm
import io.zayasanton.app.actions.models.response.LoginFormFields
import io.zayasanton.app.types.DCSButton
import io.zayasanton.app.types.DCSButtonType
import io.zayasanton.app.types.DCSField
import io.zayasanton.app.types.DCSFieldType
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin(origins = ["http://localhost:3000"])
@Controller
class LoginFormQuery {

    @QueryMapping
    fun loginFormQuery(): LoginForm {
        return LoginForm(
            header = "Welcome to Doctor Contact Playground!",
            subHeader = "This is just a demo of the doctor contact repos from my Github profile",
            fields = LoginFormFields(
                email = DCSField(
                    id = "email",
                    label = "Email",
                    placeholder = "example@email.com",
                    type = DCSFieldType.TEXT,
                ),
                password = DCSField(
                    id = "password",
                    label = "Password",
                    type = DCSFieldType.PASSWORD
                ),
                button = DCSButton(
                    type = DCSButtonType.PRIMARY,
                    message = "Login"
                )
            )
        )
    }
}
