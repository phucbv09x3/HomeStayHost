package open.hosthomestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import open.hosthomestay.ui.login.LoginFragment

@Module
abstract class AuthFragmentProvider {

    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment
}