package com.kujira.hosthomestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.kujira.hosthomestay.ui.main.MainActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

}