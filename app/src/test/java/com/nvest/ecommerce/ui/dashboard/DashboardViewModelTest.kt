package com.nvest.ecommerce.ui.dashboard

import com.nvest.ecommerce.BaseTest
import com.nvest.ecommerce.data.network.Resource
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.data.network.model.DashboardResponseItem
import com.nvest.ecommerce.util.NO_INTERNET_CONNECTION
import com.nvest.ecommerce.util.NO_PRODUCT_FOUND
import com.nvest.ecommerce.util.SOMETHING_WENT_WRONG
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class DashboardViewModelTest : BaseTest<DashboardViewModel, DashboardRepo>() {

    override fun setUp() {
        repository = mockk(relaxUnitFun = true)
        viewModelUnderTest = spyk(DashboardViewModel(repository, appDispatcher))
    }

    @Test
    fun `dashboard,responseSuccess, assert List Size 3`() {
        //given
        coEvery {
            repository.getDashboardDetails()
        } returns testDataClassGenerator.getDashboardDetails()
        //when

        viewModelUnderTest.getDashboardDetails()

        //then
        val result=viewModelUnderTest.dashboardDetailsDataPrivate.value!!.data
        assertEquals(3,result!!.size)
        verify {
            viewModelUnderTest.showLoading()
            viewModelUnderTest.hideLoading()
        }


    }

    @Test
    fun `dashboard,sortListByName, assert top 3 List items`() {
        //given
        viewModelUnderTest.dashboardDetailsDataPrivate.value=Success(
            listOf(
                DashboardResponseItem(
                    title = "Acer SB220Q bi 21.5 inches Full HD (1920 x 1080) IPS Ultra-Thin",
                    price = 599.0
                ),
                DashboardResponseItem(
                    title = "BIYLACLESEN Women's 3-in-1 Snowboard Jacket Winter Coats",
                    price =  56.99
                ),
                DashboardResponseItem(
                    title = "DANVOUY Womens T Shirt Casual Cotton Short",
                    price =  12.99
                ),

            )
        )
        //when

        viewModelUnderTest.filterByName()

        //then
        val result=viewModelUnderTest.dashboardDetailsDataPrivate.value!!.data
        assertEquals("Acer SB220Q bi 21.5 inches Full HD (1920 x 1080) IPS Ultra-Thin",result!!.get(0).title)
        assertEquals("BIYLACLESEN Women's 3-in-1 Snowboard Jacket Winter Coats",result!!.get(1).title)
        assertEquals("DANVOUY Womens T Shirt Casual Cotton Short",result!!.get(2).title)

        verify {
            viewModelUnderTest.showLoading()
            viewModelUnderTest.hideLoading()
        }


    }

    @Test
    fun `dashboard,sortListByPrice, assert top 3 List items`() {
        //given
        viewModelUnderTest.dashboardDetailsDataPrivate.value=Success(
            listOf(
                DashboardResponseItem(
                    title = "Acer SB220Q bi 21.5 inches Full HD (1920 x 1080) IPS Ultra-Thin",
                    price = 599.0
                ),
                DashboardResponseItem(
                    title = "BIYLACLESEN Women's 3-in-1 Snowboard Jacket Winter Coats",
                    price =  56.99
                ),
                DashboardResponseItem(
                    title = "DANVOUY Womens T Shirt Casual Cotton Short",
                    price =  12.99
                ),

            )
        )
        //when

        viewModelUnderTest.filterByPrice()

        //then
        val result=viewModelUnderTest.dashboardDetailsDataPrivate.value!!.data
        assertEquals(12.99,result!!.get(0).price)
        assertEquals(56.99,result!!.get(1).price)
        assertEquals(599.0,result!!.get(2).price)

        verify {
            viewModelUnderTest.showLoading()
            viewModelUnderTest.hideLoading()
        }


    }

    @Test
    fun `dashboard,responseEmpty, return DataError NO_PRODUCT_FOUND`() {
        //given
        coEvery {
            repository.getDashboardDetails()
        } returns testDataClassGenerator.getDashboardEmptyDetails()
        //when

        viewModelUnderTest.getDashboardDetails()

        //then
        val resultError=viewModelUnderTest.dashboardDetailsDataPrivate.value!!.errorDescription
        assertEquals(NO_PRODUCT_FOUND,resultError)
        verify {
            viewModelUnderTest.showLoading()
            viewModelUnderTest.hideLoading()
        }


    }

    @Test
    fun `dashboard,No network Exception, return DataError NO_PRODUCT_FOUND`() {
        //given
        coEvery {
            repository.getDashboardDetails()
        } returns testDataClassGenerator.getNoNetworkError() as Resource<List<DashboardResponseItem>>
        //when

        viewModelUnderTest.getDashboardDetails()

        //then
        val resultError=viewModelUnderTest.showMessageDialog.value!!.errorDescription
        assertEquals(NO_INTERNET_CONNECTION,resultError)
        verify {
            viewModelUnderTest.showLoading()
            viewModelUnderTest.hideLoading()
        }


    }

    @Test
    fun `dashboard,Some Exception, return DataError SOMETHING_WENT_WRONG`() {
        //given
        coEvery {
            repository.getDashboardDetails()
        }.throws(RuntimeException())

        //when
        try {
            viewModelUnderTest.getDashboardDetails()
        } catch(exception: RuntimeException) {
        }

        //then
        val resultError=viewModelUnderTest.showMessageDialog.value!!.errorDescription

        assertEquals(SOMETHING_WENT_WRONG,resultError)


    }




}