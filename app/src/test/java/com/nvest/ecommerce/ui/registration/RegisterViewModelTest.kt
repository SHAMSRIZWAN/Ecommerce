package com.nvest.ecommerce.ui.registration

import com.nvest.ecommerce.BaseTest
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.data.network.model.RegistrationResponse
import com.nvest.ecommerce.ui.login.RegistrationRepo
import com.nvest.ecommerce.util.ENTER_EMAIL_ID
import com.nvest.ecommerce.util.ENTER_NAME
import com.nvest.ecommerce.util.ENTER_PASSWORD
import com.nvest.ecommerce.util.SOMETHING_WENT_WRONG
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class RegisterViewModelTest : BaseTest<RegisterViewModel, RegistrationRepo>() {


    @ExperimentalCoroutinesApi
    override fun setUp() {
        repository = mockk()
        viewModelUnderTest = spyk(RegisterViewModel(repository, appDispatcher))
    }


    @Test
    fun `register,empty Name,return Data Error ENTER_NAME`() {
        //Given

        //when
        viewModelUnderTest.onSignUpBtnClick("", "email", "pass")

        //then
        assertEquals(ENTER_NAME, viewModelUnderTest.showMessageDialog.value!!.errorDescription)

    }

    @Test
    fun `register,empty Email,return Data Error ENTER_EMAIL_ID`() {
        //Given

        //when
        viewModelUnderTest.onSignUpBtnClick("name", "", "sample")

        //then
        assertEquals(ENTER_EMAIL_ID, viewModelUnderTest.showMessageDialog.value!!.errorDescription)

    }

    @Test
    fun `register,empty Pass,return Data Error ENTER_PASSWORD`() {
        //Given

        //when
        viewModelUnderTest.onSignUpBtnClick("name", "email", "")

        //then
        assertEquals(ENTER_PASSWORD, viewModelUnderTest.showMessageDialog.value!!.errorDescription)

    }

    @Test
    fun `register,filled name-email-pass ,return Success`() {
        //Given
        coEvery {
            repository.SignUp(any() ,any(), any())
        } returns Success(RegistrationResponse("true"))

        //when

        viewModelUnderTest.onSignUpBtnClick("a", "b", "d")
        viewModelUnderTest.registrationDataPrivate.observeForever {  }

        //then
        assertEquals(
            "true",
            viewModelUnderTest.registrationDataPrivate.value!!.getContentIfNotHandled()!!.data!!.string
        )

    }

    @Test
    fun `register,Some Exception, return DataError SOMETHING_WENT_WRONG`() {
        //given
        coEvery {
            repository.SignUp("", "", "")
        }.throws(RuntimeException())

        //when
        try {
            viewModelUnderTest.onSignUpBtnClick("a", "b", "d")
        } catch (exception: RuntimeException) {
        }

        //then
        val resultError = viewModelUnderTest.showMessageDialog.value!!.errorDescription

        Assert.assertEquals(SOMETHING_WENT_WRONG, resultError)

    }


}