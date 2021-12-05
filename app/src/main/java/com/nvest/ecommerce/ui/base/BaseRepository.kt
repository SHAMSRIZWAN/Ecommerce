package com.nvest.ecommerce.ui.base

import com.nvest.ecommerce.data.network.ApiHelper
import com.nvest.ecommerce.data.prefs.PreferencesHelper


open class BaseRepository(
    private val apiHelper: ApiHelper,
    private val preferencesHelper: PreferencesHelper
) {


    fun getApiHelper(): ApiHelper {

        return apiHelper
    }

    fun getPreferencesHelper(): PreferencesHelper {
        return preferencesHelper
    }

    fun getUserId(): String {
        return getPreferencesHelper().getCurrentUserId() ?: ""

    }


}