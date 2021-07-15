package open.your.eyes.miichibaseapp

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import okhttp3.OkHttpClient
import open.your.eyes.miichibaseapp.di.component.DaggerAppComponent
import open.your.eyes.miichibaseapp.utils.Constants
import java.util.concurrent.TimeUnit

/**
 * Created by OpenYourEyes on 10/24/2020
 */
class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        initNetworking()
    }

    private fun initNetworking() {
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
        AndroidNetworking.initialize(applicationContext, okHttpClient)
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BASIC)
    }
}