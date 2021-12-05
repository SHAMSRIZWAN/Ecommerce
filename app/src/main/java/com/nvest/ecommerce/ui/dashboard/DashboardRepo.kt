package com.nvest.ecommerce.ui.dashboard

import com.nvest.ecommerce.data.network.ApiHelper
import com.nvest.ecommerce.data.network.Resource
import com.nvest.ecommerce.data.network.model.DashboardResponseItem
import com.nvest.ecommerce.data.prefs.PreferencesHelper
import com.nvest.ecommerce.ui.base.BaseRepository
import javax.inject.Inject

class DashboardRepo @Inject constructor(
    apiHelper: ApiHelper,
    preferencesHelper: PreferencesHelper,
) : BaseRepository(apiHelper, preferencesHelper) {


    suspend fun getDashboardDetails(): Resource<List<DashboardResponseItem>> {

        return getApiHelper().getDashboardDetails()
    }

}
