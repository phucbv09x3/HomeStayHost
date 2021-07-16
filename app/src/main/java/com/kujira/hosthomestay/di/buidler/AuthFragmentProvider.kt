package com.kujira.hosthomestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.kujira.hosthomestay.ui.login.LoginFragment

@Module
abstract class AuthFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment
}