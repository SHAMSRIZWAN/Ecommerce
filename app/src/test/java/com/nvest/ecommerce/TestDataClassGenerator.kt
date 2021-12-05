package com.nvest.ecommerce

import com.nvest.ecommerce.data.network.DataError
import com.nvest.ecommerce.data.network.Resource
import com.nvest.ecommerce.data.network.Success
import com.nvest.ecommerce.data.network.model.DashboardResponseItem
import com.nvest.ecommerce.data.network.model.LoginResponse
import com.nvest.ecommerce.util.NO_INTERNET_CONNECTION
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.File
import java.lang.reflect.Type


class TestDataClassGenerator {

    val moshi = Moshi.Builder().build()

    //generic function to  generate data classes from json file path
    private inline fun <reified T> buildDataClassFromJson(json: String): T {
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return jsonAdapter.fromJson(json)!!
    }



    fun getSuccessLoginResponse(): Resource<LoginResponse> {
        val jsonString = getJson("LoginApiResponse.json")

        return Success(buildDataClassFromJson(jsonString))
    }



    fun getFailedLoginResponse():Resource<LoginResponse> {
        val jsonString = getJson("LoginFailedApiResponse.json")

        return Success(buildDataClassFromJson(jsonString))
    }

    fun getDashboardDetails(): Resource<List<DashboardResponseItem>> {
        val moshi = Moshi.Builder().build()
        val type: Type = Types.newParameterizedType(
            MutableList::class.java,
            DashboardResponseItem::class.java
        )
        val adapter = moshi.adapter<List<*>>(type)

        val dashboardList: List<DashboardResponseItem> = adapter.fromJson(getJson("DashboardApiResponse.json"))
                as List<DashboardResponseItem>

        return Success(dashboardList)
    }



    fun getDashboardEmptyDetails(): Resource<List<DashboardResponseItem>> {
        val moshi = Moshi.Builder().build()
        val type: Type = Types.newParameterizedType(
            MutableList::class.java,
            DashboardResponseItem::class.java
        )
        val adapter = moshi.adapter<List<*>>(type)

        val dashboardList: List<DashboardResponseItem> = adapter.fromJson(getJson("DashboardFailedApiResponse.json"))
                as List<DashboardResponseItem>

        return Success(dashboardList)
    }


    fun getNoNetworkError():Resource<Any> {

        return DataError(NO_INTERNET_CONNECTION)
    }



    private fun getJson(resourceName: String): String {
        val file = File("src/test/resources/$resourceName")

        return String(file.readBytes())
    }


}
