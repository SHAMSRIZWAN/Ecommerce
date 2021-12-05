package com.nvest.ecommerce.di

import com.nvest.ecommerce.data.network.ApiHeader
import com.nvest.ecommerce.data.network.ApiHelper
import com.nvest.ecommerce.data.network.AppApiHelper
import com.nvest.ecommerce.data.prefs.AppPreferencesHelper
import com.nvest.ecommerce.data.prefs.PreferencesHelper
import com.nvest.ecommerce.ui.base.BaseRepository
import com.nvest.ecommerce.ui.login.RegistrationRepo
import com.nvest.ecommerce.util.PREF_NAME
import com.nvest.ecommerce.util.coroutines.AppDispatcherProvider
import com.nvest.ecommerce.util.coroutines.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 class AppModule {


    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: AppApiHelper): ApiHelper {
        return apiHelper
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return PREF_NAME
    }

    @Provides
    @ApiInfo
    fun provideApiKey(): String {
        return ""
    }


    @Provides
    @Singleton
    fun provideProtectedApiHeader(@ApiInfo apiKey:String,preferencesHelper : PreferencesHelper)
    : ApiHeader.ProtectedApiHeader{
        return ApiHeader.ProtectedApiHeader(
            preferencesHelper.getAccessToken() ?:apiKey,
            preferencesHelper.getCurrentUserId(),
            preferencesHelper.getAccessToken())
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
    return appPreferencesHelper
    }


    @Provides
    @Singleton
    fun provideDispatcher(dispatcherProvider: AppDispatcherProvider): DispatcherProvider {
    return dispatcherProvider
    }


    @Provides
    @Singleton
//    @RegistrationScope
    fun provideRegistrationRepo(registrationRepo: RegistrationRepo): BaseRepository {
    return registrationRepo
    }




}