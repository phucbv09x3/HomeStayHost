package com.kujira.hosthomestay.di.buidler

import com.kujira.hosthomestay.ui.all_login.login_new.LoginActivity
import com.kujira.hosthomestay.ui.all_login.register_new.RegisterActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.kujira.hosthomestay.ui.main.MainHostActivity
import com.kujira.hosthomestay.ui.splash.SplashActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainHostActivity

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindSplashActivity(): SplashActivity

}