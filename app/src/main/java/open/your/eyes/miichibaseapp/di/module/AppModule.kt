package open.your.eyes.miichibaseapp.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import open.your.eyes.miichibaseapp.data.AppDataManager
import open.your.eyes.miichibaseapp.data.DataManager
import open.your.eyes.miichibaseapp.data.api.ApiService
import open.your.eyes.miichibaseapp.data.api.IApiService
import open.your.eyes.miichibaseapp.data.local.DataStoreHelper
import open.your.eyes.miichibaseapp.data.local.DataStoreManager
import open.your.eyes.miichibaseapp.data.scheduler.AppSchedulerProvider
import open.your.eyes.miichibaseapp.data.scheduler.ISchedulerProvider
import open.your.eyes.miichibaseapp.ui.base.ViewModelFactory
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