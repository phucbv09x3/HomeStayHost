package com.kujira.hosthomestay.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kujira.hosthomestay.data.DataManager
import com.kujira.hosthomestay.data.api.IApiService
import com.kujira.hosthomestay.data.scheduler.ISchedulerProvider
import com.kujira.hosthomestay.ui.about.AboutViewModel
import com.kujira.hosthomestay.ui.add.AddRoomViewModel
import com.kujira.hosthomestay.ui.all_login.login_new.LoginAccViewModel
import com.kujira.hosthomestay.ui.all_login.register_new.RegisterAccViewModel
import com.kujira.hosthomestay.ui.list.ListViewModel
import com.kujira.hosthomestay.ui.login.LoginViewModel
import com.kujira.hosthomestay.ui.main.MainHostViewModel
import com.kujira.hosthomestay.ui.manager.ManagerAccViewModel
import com.kujira.hosthomestay.ui.mess.MessageViewModel
import com.kujira.hosthomestay.ui.myacc.MyAccountViewModel
import com.kujira.hosthomestay.ui.register.RegisterViewModel
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
            modelClass.isAssignableFrom(MainHostViewModel::class.java) -> MainHostViewModel() as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel() as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel() as T
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel() as T
            modelClass.isAssignableFrom(ListViewModel::class.java) -> ListViewModel() as T

            modelClass.isAssignableFrom(AddRoomViewModel::class.java) -> AddRoomViewModel() as T
            modelClass.isAssignableFrom(MessageViewModel::class.java) -> MessageViewModel() as T
            modelClass.isAssignableFrom(ManagerAccViewModel::class.java) -> ManagerAccViewModel() as T
            modelClass.isAssignableFrom(MyAccountViewModel::class.java) -> MyAccountViewModel() as T

            modelClass.isAssignableFrom(LoginAccViewModel::class.java) -> LoginAccViewModel() as T
            modelClass.isAssignableFrom(RegisterAccViewModel::class.java) -> RegisterAccViewModel() as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
        if (viewModel is BaseViewModel) {
            viewModel.initData(dataManager, apiService, scheduler)
        }
        return viewModel

    }
}