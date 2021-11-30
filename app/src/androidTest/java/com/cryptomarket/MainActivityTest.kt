package com.cryptomarket

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    lateinit var activityRule: ActivityScenario<MainActivity>

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun testSuccessfulApiCallShowRecyclerView() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(getCryptoMarketDataJson)
        mockWebServer.enqueue(response)

        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        activityRule = ActivityScenario.launch(intent)

        activityRule.onActivity {
            onView(withId(R.id.cryptoAssets)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
