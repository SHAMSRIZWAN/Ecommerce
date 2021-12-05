package com.nvest.ecommerce.data.network

import com.nvest.ecommerce.data.network.model.DashboardResponseItem
import com.nvest.ecommerce.data.network.model.LoginResponse


interface ApiHelper {

    fun getApiHeader(): ApiHeader?
    fun updateToken(token: String)
    fun login(email: String, password: String): Resource<LoginResponse>
    suspend fun getDashboardDetails(): Resource<List<DashboardResponseItem>>

}
