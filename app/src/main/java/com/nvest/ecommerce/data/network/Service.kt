package com.nvest.ecommerce.data.network


import com.nvest.ecommerce.data.network.model.*
import retrofit2.Response
import retrofit2.http.*


interface Service {


    @GET("products/")
   suspend fun getDashboardDetails(): Response<List<DashboardResponseItem>>


}
