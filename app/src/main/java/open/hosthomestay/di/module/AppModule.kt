package open.hosthomestay.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import open.hosthomestay.data.AppDataManager
import open.hosthomestay.data.DataManager
import open.hosthomestay.data.api.ApiService
import open.hosthomestay.data.api.IApiService
import open.hosthomestay.data.local.DataStoreHelper
import open.hosthomestay.data.local.DataStoreManager
import open.hosthomestay.data.scheduler.AppSchedulerProvider
import open.hosthomestay.data.scheduler.ISchedulerProvider
import open.hosthomestay.ui.base.ViewModelFactory
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