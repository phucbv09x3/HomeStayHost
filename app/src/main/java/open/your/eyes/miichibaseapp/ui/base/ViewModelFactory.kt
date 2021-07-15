package open.your.eyes.miichibaseapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import open.your.eyes.miichibaseapp.data.DataManager
import open.your.eyes.miichibaseapp.data.api.ApiService
import open.your.eyes.miichibaseapp.data.api.IApiService
import open.your.eyes.miichibaseapp.data.scheduler.ISchedulerProvider
import open.your.eyes.miichibaseapp.ui.about.AboutViewModel
import open.your.eyes.miichibaseapp.ui.list.ListViewModel
import open.your.eyes.miichibaseapp.ui.login.LoginViewModel
import open.your.eyes.miichibaseapp.ui.main.MainViewModel
import open.your.eyes.miichibaseapp.ui.register.RegisterViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val dataManager: DataManager,
    private val apiService: IApiService,
    private val scheduler: ISchedulerProvider
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel() as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel() as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel() as T
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel() as T
            modelClass.isAssignableFrom(ListViewModel::class.java) -> ListViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
        if (viewModel is BaseViewModel) {
            viewModel.initData(dataManager, apiService, scheduler)
        }
        return viewModel

    }
}