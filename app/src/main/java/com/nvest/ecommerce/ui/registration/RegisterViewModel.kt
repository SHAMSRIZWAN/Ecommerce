package com.nvest.ecommerce.ui.registration

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nvest.ecommerce.data.network.DataError
import com.nvest.ecommerce.data.network.Resource
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.data.network.model.RegistrationResponse
import com.nvest.ecommerce.ui.base.BaseViewModel
import com.nvest.ecommerce.ui.login.RegistrationRepo
import com.nvest.ecommerce.util.ENTER_EMAIL_ID
import com.nvest.ecommerce.util.ENTER_NAME
import com.nvest.ecommerce.util.ENTER_PASSWORD
import com.nvest.ecommerce.util.SingleEvent
import com.nvest.ecommerce.util.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    registrationRepo: RegistrationRepo,
    appDispatcher: DispatcherProvider,
) : BaseViewModel<RegistrationRepo>(registrationRepo, appDispatcher) {

    @VisibleForTesting(otherwise =VisibleForTesting.PRIVATE)
    val registrationDataPrivate=MutableLiveData<SingleEvent<Resource<RegistrationResponse>>>()
    val registrationData:LiveData<SingleEvent<Resource<RegistrationResponse>>> get() = registrationDataPrivate

    fun onSignUpBtnClick(name: String, email: String, pass: String) {

        viewModelScope.launch(exceptionHandler) {
            when {
                name.isEmpty() -> {
                    showMessageDialog(DataError(ENTER_NAME))
                }
                email.isEmpty() -> {
                    showMessageDialog(DataError(ENTER_EMAIL_ID))
                }
                pass.isEmpty() -> {
                    showMessageDialog(DataError(ENTER_PASSWORD))
                }else ->{
                val result=getRepo().SignUp(name,email,pass)
                registrationDataPrivate.value=SingleEvent(result)
            }

            }

        }

    }


}
