package com.nvest.ecommerce.ui.dashboard

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nvest.ecommerce.data.network.DataError
import com.nvest.ecommerce.data.network.Resource
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.data.network.model.DashboardResponseItem
import com.nvest.ecommerce.ui.base.BaseViewModel
import com.nvest.ecommerce.util.NO_PRODUCT_FOUND
import com.nvest.ecommerce.util.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    dashboard: DashboardRepo,
    appDispatcher: DispatcherProvider,
) : BaseViewModel<DashboardRepo>(dashboard, appDispatcher) {


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val dashboardDetailsDataPrivate = MutableLiveData<Resource<List<DashboardResponseItem>>>()
    val dashboardDetailsData: LiveData<Resource<List<DashboardResponseItem>>> get() = dashboardDetailsDataPrivate

    init {
        getDashboardDetails()
    }

    fun getDashboardDetails() {

        viewModelScope.launch(exceptionHandler) {
            showLoading()
            val dashboardResult = getRepo().getDashboardDetails()
            hideLoading()

            if (dashboardResult.data != null) {
                if (dashboardResult.data.isEmpty()) {
                    dashboardDetailsDataPrivate.value = DataError(NO_PRODUCT_FOUND)

                } else {
                    dashboardDetailsDataPrivate.value = dashboardResult

                }

            } else {
                showMessageDialog(dashboardResult as DataError<String>)
            }


        }


    }

    fun filterByName() {

        viewModelScope.launch(exceptionHandler) {
            showLoading()

            flow {

                val value = dashboardDetailsDataPrivate.value
                val result = value?.data?.sortedWith(compareBy { it.title })

                emit(result)
            }.flowOn(getAppDispatcher().computation())
                .collect {
                    it?.let {
                        dashboardDetailsDataPrivate.value = Success(it)

                    }

                }
            hideLoading()

        }


    }

    fun filterByPrice() {


        viewModelScope.launch(exceptionHandler) {
            showLoading()
            flow {

                val value = dashboardDetailsDataPrivate.value
                val result = value?.data?.sortedWith(compareBy { it.price })

                emit(result)
            }.flowOn(getAppDispatcher().computation())
                .collect {
                    it?.let {
                        dashboardDetailsDataPrivate.value = Success(it)

                    }

                    hideLoading()
                }


        }

    }


}