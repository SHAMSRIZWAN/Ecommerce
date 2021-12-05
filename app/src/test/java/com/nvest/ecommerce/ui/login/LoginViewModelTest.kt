package com.nvest.ecommerce.ui.login

import com.nvest.ecommerce.BaseTest
import com.nvest.ecommerce.data.network.Resource
import com.nvest.ecommerce.data.network.model.LoginResponse
import com.nvest.ecommerce.util.ENTER_EMAIL_ID
import com.nvest.ecommerce.util.ENTER_PASSWORD
import com.nvest.ecommerce.util.NO_INTERNET_CONNECTION
import com.nvest.ecommerce.util.SOMETHING_WENT_WRONG
import io.mockk.*
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.RuntimeException


class LoginViewModelTest : BaseTest<LoginViewModel, RegistrationRepo>() {


    @ExperimentalCoroutinesApi
    override fun setUp() {
        repository = mockk()
        viewModelUnderTest =  spyk(LoginViewModel(repository, appDispatcher))
    }

    @Test
    fun `onSignBtnClick , empty email and empty pass, return DataError ENTER_EMAIL_ID `() {
        //Given

        //when
        viewModelUnderTest.onSignInBtnClick("", "")
        viewModelUnderTest.showMessageDialog.observeForever {}

        //then
        val loginfail = viewModelUnderTest.showMessageDialog.value?.errorDescription

        assertEquals(ENTER_EMAIL_ID, loginfail)


    }
    @Test
    fun `onSignBtnClick , empty email, return DataError ENTER_EMAIL_ID `() {
        //Given

        //when
        viewModelUnderTest.onSignInBtnClick("", "pas")
        viewModelUnderTest.showMessageDialog.observeForever {}

        //then
        val loginfail = viewModelUnderTest.showMessageDialog.value?.errorDescription

        assertEquals(ENTER_EMAIL_ID, loginfail)


    }


    @Test
    fun `onSignBtnClick , empty pass, return DataError ENTER_PASSWORD `() {
        //Given

        //when
        viewModelUnderTest.onSignInBtnClick("emal", "")
        viewModelUnderTest.showMessageDialog.observeForever {}

        //then
        val loginfail = viewModelUnderTest.showMessageDialog.value?.errorDescription

        assertEquals(ENTER_PASSWORD, loginfail)


    }


    @Test
    fun `onSignBtnClick,filled email and password,return success`() {
        //Given
       coEvery {
            repository.login(any(), any())
        } returns flow { emit(testDataClassGenerator.getSuccessLoginResponse()) }


        //when
        viewModelUnderTest.onSignInBtnClick("Nvest@Gmail.com", "Nvest@Gmail.com")
        viewModelUnderTest.loginResponsePrivate.observeForever {}

        //then
        val loginResponse = viewModelUnderTest.loginResponsePrivate.value!!.data

        val expectedResponse = testDataClassGenerator.getSuccessLoginResponse().data


        assertEquals(expectedResponse, loginResponse)

        verify {
            repository.login(any(),any())
            viewModelUnderTest.showLoading()
            viewModelUnderTest.hideLoading()
        }

    }

    @Test
    fun `onSignBtnClick, Data Error No network,assert showErrorDialog`() {
        //Given
        coEvery {
            repository.login(any(), any())
        } returns flow { emit(testDataClassGenerator.getNoNetworkError() as Resource<LoginResponse>) }


        //when
        viewModelUnderTest.onSignInBtnClick("username@gmail.com", "password@123")
        viewModelUnderTest.loginResponsePrivate.observeForever {}

        //then
        val result = viewModelUnderTest.showMessageDialog.value

        assertEquals(NO_INTERNET_CONNECTION, result!!.errorDescription)

        verify {
            repository.login(any(),any())
            viewModelUnderTest.showLoading()
            viewModelUnderTest.hideLoading()
        }

    }


    @Test
    fun `onSignBtnClick,Some Exception, return DataError SOMETHING_WENT_WRONG`() {
        //given
        coEvery {
            repository.login(any(),any())
        }.throws(RuntimeException())

        //when
        try {
            viewModelUnderTest.onSignInBtnClick("a","b")
        } catch(exception: RuntimeException) {
        }

        //then
        val resultError=viewModelUnderTest.showMessageDialog.value!!.errorDescription

        assertEquals(SOMETHING_WENT_WRONG, resultError)

    }




}