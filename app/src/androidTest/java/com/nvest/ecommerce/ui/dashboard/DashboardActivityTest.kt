package com.nvest.ecommerce.ui.dashboard

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.nvest.ecommerce.R
import com.nvest.ecommerce.ui.BaseInstrument
import com.nvest.ecommerce.ui.login.LoginActivity

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
class DashboardActivityTest : BaseInstrument() {

    @get : Rule
    val composeTestRule = createAndroidComposeRule(DashboardActivity::class.java)
    lateinit var activity: ComponentActivity
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    override fun setUp() {
        activity=composeTestRule.activity
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.waitForIdle() // Advances the clock until Compose is idle

    }

    @ExperimentalCoroutinesApi
    @Test  //this should wait to complete the api call but its not even though using runBlockingTest
    fun dashboardDetails_allElements_isDisplayed()= testScope.runBlockingTest{

        composeTestRule.onNodeWithText(activity.getString(R.string.dashboard)).assertIsDisplayed()

        composeTestRule.onNodeWithTag(activity.getString(R.string.dashboard_list_tag)).assertIsDisplayed()

    }


}