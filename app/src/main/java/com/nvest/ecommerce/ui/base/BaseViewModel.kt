package com.nvest.ecommerce.ui.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nvest.ecommerce.data.network.DataError
import com.nvest.ecommerce.data.network.Resource
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.util.SOMETHING_WENT_WRONG
import com.nvest.ecommerce.util.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel<R : BaseRepository>(
    private val repository: R,
    private val appDispatcher: DispatcherProvider,
) : ViewModel() {

    internal val showDialogLoadingPrivate = MutableLiveData(false)

    val showMessageDialog = MutableLiveData<Resource<String>>()


    protected val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        hideLoading()
        showMessageDialog(DataError(SOMETHING_WENT_WRONG))

    }


    fun isLoading(): Boolean {
        return showDialogLoadingPrivate.value!!
    }


    fun showLoading() {
        if (!showDialogLoadingPrivate.value!!) {
            showDialogLoadingPrivate.value = true
        }

    }

    fun hideLoading() {
        if (showDialogLoadingPrivate.value!!) {
            showDialogLoadingPrivate.value = false
        }
    }

    fun getRepo(): R {
        return repository
    }

    fun getAppDispatcher(): DispatcherProvider {
        return appDispatcher
    }

    fun showMessageDialog(dataError: DataError<String>) {
        showMessageDialog.value = dataError
    }

    fun showPostMessageDialog(dataError: DataError<String>) {
        showMessageDialog.value = dataError
    }

    fun hideMessageDialog(success: Success<String>) {
        showMessageDialog.value = success

    }



}

