package io.zayasanton.app.actions.queries

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.zayasanton.app.types.DCSButtonType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginFormQueryTest {

    @InjectMockKs
    lateinit var loginFormQuery: LoginFormQuery

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `loginFormQuery - returns the form successfully`() {
        val response = loginFormQuery.loginFormQuery()

        Assertions.assertEquals("Welcome to Doctor Contact Playground!", response.header)
        Assertions.assertEquals("loginEmail", response.fields.email.id)
        Assertions.assertFalse(response.fields.email.disabled)
        Assertions.assertEquals("loginPass", response.fields.password.id)
        Assertions.assertFalse(response.fields.password.disabled)
        Assertions.assertEquals(DCSButtonType.PRIMARY, response.fields.button.type)
    }
}