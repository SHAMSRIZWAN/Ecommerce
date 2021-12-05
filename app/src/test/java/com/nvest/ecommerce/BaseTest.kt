package com.nvest.ecommerce

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nvest.ecommerce.ui.base.BaseRepository
import com.nvest.ecommerce.ui.base.BaseViewModel
import com.nvest.ecommerce.coroutines.CoroutineTestRule
import com.nvest.ecommerce.coroutines.TestDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

abstract class BaseTest<Vm : BaseViewModel<Repo>, Repo : BaseRepository> {
    // Subject under test
    protected lateinit var viewModelUnderTest: Vm

    // Use a mock Repo to be injected into the viewModel
    protected lateinit var repository: Repo

    protected val testDataClassGenerator: TestDataClassGenerator = TestDataClassGenerator()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    open val mainCoroutineRule = CoroutineTestRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    val appDispatcher = TestDispatcherProvider()

    @ExperimentalCoroutinesApi
    @Before
    abstract fun setUp()
}
