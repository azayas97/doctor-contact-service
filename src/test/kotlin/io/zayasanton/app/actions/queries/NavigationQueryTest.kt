package io.zayasanton.app.actions.queries

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NavigationQueryTest {

    @InjectMockKs
    lateinit var navigationQuery: NavigationQuery

    @BeforeAll
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `navigationQuery - returns query successfully`() {
        val response = navigationQuery.navigationQuery("123")

        Assertions.assertEquals("/dashboard", response.mainLink.url)
        Assertions.assertEquals("/login", response.logout.url)
    }
}