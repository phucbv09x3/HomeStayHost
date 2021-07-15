package open.your.eyes.miichibaseapp.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import open.your.eyes.miichibaseapp.App
import open.your.eyes.miichibaseapp.di.buidler.ActivityBuilder
import open.your.eyes.miichibaseapp.di.module.AppModule
import javax.inject.Singleton

/**
 * Created by OpenYourEyes on 3/2/2020
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