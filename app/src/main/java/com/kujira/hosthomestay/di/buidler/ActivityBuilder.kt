package open.hosthomestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import open.hosthomestay.ui.main.MainActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

}