package open.your.eyes.miichibaseapp.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import open.your.eyes.miichibaseapp.ui.about.AboutFragment
import open.your.eyes.miichibaseapp.ui.list.ListFragment
import open.your.eyes.miichibaseapp.ui.login.LoginFragment
import open.your.eyes.miichibaseapp.ui.register.RegisterFragment

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