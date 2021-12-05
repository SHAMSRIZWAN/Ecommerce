package com.nvest.ecommerce.ui.registration

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.nvest.ecommerce.R
import com.nvest.ecommerce.ui.BaseInstrument
import com.nvest.ecommerce.util.ENTER_EMAIL_ID
import com.nvest.ecommerce.util.ENTER_NAME
import com.nvest.ecommerce.util.ENTER_PASSWORD
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
class RegisterActivityTest : BaseInstrument() {

    @get : Rule
    val composeRule = createAndroidComposeRule(RegisterActivity::class.java)
    lateinit var activity: ComponentActivity

    override fun setUp() {
        activity=composeRule.activity

    }

    @Test
    fun register_all_element_isDisplayed() {
        composeRule.onNodeWithTag(activity.getString(R.string.name)).assertIsDisplayed()
        composeRule.onNodeWithTag(activity.getString(R.string.email_address)).assertIsDisplayed()
        composeRule.onNodeWithTag(activity.getString(R.string.password)).assertIsDisplayed()
        composeRule.onNodeWithTag(activity.getString(R.string.sign_up)).assertIsDisplayed()

    }

    @Test
    fun onSignUpBtnClick_emptyName_EnterNameError_isDisplayed() {

        composeRule.onNodeWithTag(activity.getString(R.string.email_address))
            .performTextInput("email")

        composeRule.onNodeWithTag(activity.getString(R.string.password))
            .performTextInput("pass")

        composeRule.onNodeWithTag(activity.getString(R.string.sign_up))
            .performClick()

        composeRule.onNodeWithText(ENTER_NAME).assertIsDisplayed()

    }

    @Test
    fun onSignUpBtnClick_emptyEmail_ENTER_EMAIL_ID_isDisplayed() {
        composeRule.onNodeWithTag(activity.getString(R.string.name))
            .performTextInput("name")

        composeRule.onNodeWithTag(activity.getString(R.string.password))
            .performTextInput("pass")

        composeRule.onNodeWithTag(activity.getString(R.string.sign_up))
            .performClick()

        composeRule.onNodeWithText(ENTER_EMAIL_ID).assertIsDisplayed()

    }

    @Test
    fun onSignUpBtnClick_emptyPass_ENTER_Pass_isDisplayed() {
        composeRule.onNodeWithTag(activity.getString(R.string.name))
            .performTextInput("name")

        composeRule.onNodeWithTag(activity.getString(R.string.email_address))
            .performTextInput("email")

        composeRule.onNodeWithTag(activity.getString(R.string.sign_up))
            .performClick()

        composeRule.onNodeWithText(ENTER_PASSWORD).assertIsDisplayed()

    }


    @Test
    fun onSignUpBtnClick_FilledAllField_nVestEcommerce_isDisplayed() {
        composeRule.onNodeWithTag(activity.getString(R.string.name))
            .performTextInput("name")

        composeRule.onNodeWithTag(activity.getString(R.string.email_address))
            .performTextInput("email")


        composeRule.onNodeWithTag(activity.getString(R.string.password))
            .performTextInput("pass")

        composeRule.onNodeWithTag(activity.getString(R.string.sign_up))
            .performClick()

        composeRule.onNodeWithText(activity.getString(R.string.ecommerce))
            .assertIsDisplayed()

    }


}