package open.your.eyes.miichibaseapp.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import open.your.eyes.miichibaseapp.ui.main.MainActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

}