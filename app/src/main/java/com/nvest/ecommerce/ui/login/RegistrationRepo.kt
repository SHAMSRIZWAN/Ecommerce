package com.nvest.ecommerce.ui.login

import com.nvest.ecommerce.data.network.model.LoginResponse
import com.nvest.ecommerce.data.network.ApiHelper
import com.nvest.ecommerce.data.network.DataError
import com.nvest.ecommerce.data.network.Resource
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.data.network.model.RegistrationResponse
import com.nvest.ecommerce.data.prefs.PreferencesHelper
import com.nvest.ecommerce.ui.base.BaseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegistrationRepo @Inject constructor(
    apiHelper: ApiHelper,
    preferencesHelper: PreferencesHelper,
) : BaseRepository(apiHelper, preferencesHelper) {


    fun login(email: String, password: String): Flow<Resource<LoginResponse>> {
        val loginResult = getApiHelper().login(email, password)

        loginResult.data?.let {
            if (it.status) {
                val loginResponse = loginResult.data

                if (loginResponse.status) {
                    val userId = loginResponse.data.userId
                    val token = loginResponse.data.token
                    val userType = loginResponse.data.userType

                    getPreferencesHelper().setUserLoggedIn(
                        userId = userId,
                        userName = userType,
                        email = userId, accessToken = token
                    )
                    getApiHelper().updateToken(token)

                }
            } else {
                return flow {
                    emit(DataError(loginResult.data.message))
                }
            }
        }

        return flow {
            emit(loginResult)
        }
    }

    suspend fun isUserLoggedIn(): Flow<Int?> {

        return flow {
            delay(3000)

            emit(getPreferencesHelper().getCurrentUserLoggedInMode())
        }
    }

    fun SignUp(name: String, email: String, pass: String) :Resource<RegistrationResponse>{

    return Success(RegistrationResponse("true"))
    }


}