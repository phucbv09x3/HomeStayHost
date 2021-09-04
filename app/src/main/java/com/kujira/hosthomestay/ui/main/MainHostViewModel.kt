package com.kujira.hosthomestay.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.ui.base.BaseViewModel

/**
 * Created by OpenYourEyes on 10/24/2020
 */
class MainHostViewModel : BaseViewModel() {
    companion object {
        const val BTN_MESSAGE = 0
        const val BTN_MANAGER_ROOM = 1
        const val BTN_ADD_ROOM = 2
        const val BTN_MY_ACC = 3
    }

    var onClickMain = MutableLiveData<Int>()
    fun click(view: View) {
        when (view.id) {
//            R.id.btn_message_on_main -> {
//                onClickMain.value = BTN_MESSAGE
//
//            }
            R.id.btn_managerRoom_on_main -> {
                onClickMain.value = BTN_MANAGER_ROOM
            }
            R.id.btn_add_room_on_main -> {
                onClickMain.value = BTN_ADD_ROOM
            }
            R.id.btn_my_account_on_main -> {
                onClickMain.value = BTN_MY_ACC
            }
        }
    }
}