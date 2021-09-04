package com.kujira.hosthomestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.kujira.hosthomestay.ui.about.AboutFragment
import com.kujira.hosthomestay.ui.add.AddRoomFragment
import com.kujira.hosthomestay.ui.list.ListFragment
import com.kujira.hosthomestay.ui.login.LoginFragment
import com.kujira.hosthomestay.ui.manager.ManagerAccFragment
import com.kujira.hosthomestay.ui.mess.MessageFragment
import com.kujira.hosthomestay.ui.myacc.MyAccountFragment
import com.kujira.hosthomestay.ui.register.RegisterFragment

@Module
abstract class MainFragmentProvider {
    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun bindRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    internal abstract fun bindAboutFragment(): AboutFragment

    @ContributesAndroidInjector
    internal abstract fun bindListFragment(): ListFragment

    @ContributesAndroidInjector
    internal abstract fun bindAddRoomFragment(): AddRoomFragment

    @ContributesAndroidInjector
    internal abstract fun bindMessageFragment(): MessageFragment

    @ContributesAndroidInjector
    internal abstract fun bindManagerRoomFragment(): ManagerAccFragment

    @ContributesAndroidInjector
    internal abstract fun bindMyAccountFragment(): MyAccountFragment
}