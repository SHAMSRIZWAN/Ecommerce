package com.nvest.ecommerce.ui.login

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.nvest.ecommerce.ui.BaseInstrument
import com.nvest.ecommerce.R
import com.nvest.ecommerce.util.ENTER_EMAIL_ID
import com.nvest.ecommerce.util.ENTER_NAME
import com.nvest.ecommerce.util.ENTER_PASSWORD
import com.nvest.ecommerce.util.NO_INTERNET_CONNECTION
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
class LoginActivityTest : BaseInstrument(){

    @get : Rule
    val composeTestRule = createAndroidComposeRule(LoginActivity::class.java)
    lateinit var activity: ComponentActivity

    @Before
   override fun setUp() {
        activity = composeTestRule.activity
        composeTestRule
            .onNodeWithTag(activity.getString(R.string.email_address))
            .performTextClearance()
        composeTestRule
            .onNodeWithTag(activity.getString(R.string.password))
            .performTextClearance()
    }

    @Test
    fun login_emptyEmail_showMessage() {
        composeTestRule
            .onNodeWithTag(activity.getString(R.string.password))
            .performTextInput("12323")

        composeTestRule.onNodeWithTag(activity.getString(R.string.sign_in))
            .performClick()

        composeTestRule.onNodeWithText(ENTER_EMAIL_ID)
            .assertIsDisplayed()


    }


    @Test
    fun login_emptyPass_showMessage() {
        composeTestRule
            .onNodeWithTag(activity.getString(R.string.email_address))
            .performTextInput("12323")

        composeTestRule.onNodeWithTag(activity.getString(R.string.sign_in))
            .performClick()

        composeTestRule.onNodeWithText(ENTER_PASSWORD)
            .assertIsDisplayed()


    }

    @Test
    fun login_filledEmailAndPass_openDashboard() {
        composeTestRule
            .onNodeWithTag(activity.getString(R.string.email_address))
            .performTextInput("Nvest@Gmail.com")

        composeTestRule
            .onNodeWithTag(activity.getString(R.string.password))
            .performTextInput("Nvest@Gmail.com")

        composeTestRule.onNodeWithTag(activity.getString(R.string.sign_in))
            .performClick()

        composeTestRule.
                onNodeWithTag(activity.getString(R.string.dashboard_content_tag))
            .assertIsDisplayed()

    }





}