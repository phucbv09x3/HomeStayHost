package open.hosthomestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import open.hosthomestay.ui.about.AboutFragment
import open.hosthomestay.ui.list.ListFragment
import open.hosthomestay.ui.login.LoginFragment
import open.hosthomestay.ui.register.RegisterFragment

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
}