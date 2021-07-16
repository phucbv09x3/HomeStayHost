package com.kujira.hosthomestay.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import com.kujira.hosthomestay.data.AppDataManager
import com.kujira.hosthomestay.data.DataManager
import com.kujira.hosthomestay.data.api.ApiService
import com.kujira.hosthomestay.data.api.IApiService
import com.kujira.hosthomestay.data.local.DataStoreHelper
import com.kujira.hosthomestay.data.local.DataStoreManager
import com.kujira.hosthomestay.data.scheduler.AppSchedulerProvider
import com.kujira.hosthomestay.data.scheduler.ISchedulerProvider
import com.kujira.hosthomestay.ui.base.ViewModelFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun providerDataStore(dataStoreManager: DataStoreManager): DataStoreHelper {
        return dataStoreManager
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideScheduler(): ISchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory {
        return factory
    }


    @Provides
    @Singleton
    fun providerApiService(): IApiService {
        return ApiService()
    }

}