package open.your.eyes.miichibaseapp.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import open.your.eyes.miichibaseapp.ui.login.LoginFragment

@Module
abstract class AuthFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment
}