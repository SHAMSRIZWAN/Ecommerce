package com.nvest.ecommerce.data.network

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.nvest.ecommerce.data.network.model.DashboardResponseItem
import com.nvest.ecommerce.data.network.model.Data
import com.nvest.ecommerce.data.network.model.LoginResponse
import com.nvest.ecommerce.util.*
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class AppApiHelper @Inject constructor(
    @ApplicationContext val context: Context,
    private val apiHeader: ApiHeader,
) : ApiHelper {

    @Inject
    lateinit var serviceGenerator: ServiceGenerator

    override fun getApiHeader(): ApiHeader {

        return apiHeader
    }

    override fun updateToken(token: String) {
        serviceGenerator.protectedApiHeader = getApiHeader().protectedApiHeader.apply {
            accessToken = token
        }

    }

    override fun login(email: String, password: String): Resource<LoginResponse> {

        val data = Data(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6InN1b3JpendhbnN" +
                    "heXllZDc4NkBnbWFpbC5jb20iLCJuYmYiOjE2Mzc2NTE1MjMsImV4cCI6MTY0NTQyNzUyMywiaWF0" +
                    "IjoxNjM3NjUxNTIzfQ.qKA55avNfOJMU3lHE-e88jfAVwE_T7E12cbCwXAfYAU",
            "Nvest@Gmail.com", "User"
        )
        return Success(LoginResponse(data, "User login successful", true))

    }

    override suspend fun getDashboardDetails(): Resource<List<DashboardResponseItem>> {
        val service = serviceGenerator.getService()

        return when (val responseBodyPojo = processCall { service.getDashboardDetails() }
        ) {
            is List<*> -> Success(data = responseBodyPojo as List<DashboardResponseItem>)

            else -> DataError(responseBodyPojo as String)
        }

    }


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    inline fun processCall(responseCall: () -> Response<*>): Any? {
        if (!NetworkUtils.isNetworkAvailable(context)) {

            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                getResponseCodeString(responseCode)
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }

    fun getResponseCodeString(responseCode: Int): String {
        if (responseCode in 400..499) {
            return CLIENT_SIDE_ERROR
        } else if (responseCode in 500..599) {
            return SERVER_SIDE_ERROR
        }
        return SOMETHING_WENT_WRONG
    }

}