package open.hosthomestay.ui.main

import android.view.View
import open.hosthomestay.R
import open.hosthomestay.ui.base.BaseViewModel

/**
 * Created by OpenYourEyes on 10/24/2020
 */
class MainViewModel : BaseViewModel() {

    fun click(view: View) {
        when (view.id) {
            R.id.btn_message_on_main -> {
                navigation.navigate(R.id.messageFragment)
            }
            R.id.btn_managerRoom_on_main -> {
                navigation.navigate(R.id.managerRoomFragment)
            }
            R.id.addRoomFragment -> {
                navigation.navigate(R.id.addRoomFragment)
            }
            R.id.myAccountFragment -> {
                navigation.navigate(R.id.myAccountFragment)
            }
        }
    }
}