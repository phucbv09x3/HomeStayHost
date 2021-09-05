package com.kujira.hosthomestay.di.buidler

import com.kujira.hosthomestay.ui.add.AddRoomFragment
import com.kujira.hosthomestay.ui.detail_report.DetailReportFragment
import com.kujira.hosthomestay.ui.manager.ManagerAccFragment
import com.kujira.hosthomestay.ui.myacc.MyAccountFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun bindAddRoomFragment(): AddRoomFragment

    @ContributesAndroidInjector
    internal abstract fun bindReportFragment(): DetailReportFragment

    @ContributesAndroidInjector
    internal abstract fun bindManagerRoomFragment(): ManagerAccFragment

    @ContributesAndroidInjector
    internal abstract fun bindMyAccountFragment(): MyAccountFragment
}