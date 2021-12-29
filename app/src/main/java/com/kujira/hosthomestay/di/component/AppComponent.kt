package com.kujira.hosthomestay.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.kujira.hosthomestay.App
import com.kujira.hosthomestay.di.buidler.ActivityBuilder
import com.kujira.hosthomestay.di.module.AppModule
import javax.inject.Singleton

/**
 * Created by PhucBv on 5/2021
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilder::class, AppModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


}