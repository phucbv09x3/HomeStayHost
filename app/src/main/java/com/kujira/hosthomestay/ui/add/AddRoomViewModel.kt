package com.kujira.hosthomestay.ui.add

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.response.DistrictFB
import com.kujira.hosthomestay.data.model.response.ProvinceFB
import com.kujira.hosthomestay.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_add_room.view.*

class AddRoomViewModel : BaseViewModel() {

    private var listProvinceFB = mutableListOf<ProvinceFB>()
    private var listDistrictsFB = mutableListOf<DistrictFB>()

    var listDistrictFBLiveData = MutableLiveData<MutableList<DistrictFB>>()
    var listProvincesFB = MutableLiveData<MutableList<ProvinceFB>>()
    var dataReference =
        FirebaseDatabase.getInstance().getReference("Host")

    var textWard = ObservableField<String>()
    var nameRoom = ObservableField<String>()
    var sRoom = ObservableField<String>()
    var numberSleepRoom = ObservableField<String>()
    var textDetail = ObservableField<String>()
    var introduce = ObservableField<String>()
    var listenerBtnAddHome = MutableLiveData<Int>()

    companion object {
        const val BTN_IMG_1 = 0
        const val BTN_IMG_2 = 1
        const val BTN_IMG_ACCESS = 2
    }

    fun getListProvincesFB() {
        dataReference.child("ListProvinces")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    listProvinceFB.clear()
                    for (pos in snapshot.children) {
                        val obProvinceFB = ProvinceFB(
                            pos.child("code").value.toString().toInt(),
                            pos.child("name").value.toString(),
                            pos.child("phone_code").value.toString().toInt(),
                        )
                        listProvinceFB.add(obProvinceFB)
                    }
                    listProvincesFB.value = listProvinceFB
                }

            })
    }

    fun getListDistrictFB(name: String) {
        listDistrictsFB.clear()
        dataReference.child("DetailProvince").child(name).child("Districts")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (pos in snapshot.children) {
                        val obDistrictFB = DistrictFB(
                            pos.child("code").value.toString(),
                            pos.child("name").value.toString(),

                            )
                        listDistrictsFB.add(obDistrictFB)
                    }
                    listDistrictFBLiveData.value = listDistrictsFB
                }

            })
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_add_img_1 -> {
                listenerBtnAddHome.value = BTN_IMG_1
            }
            R.id.btn_add_img_2 -> {
                listenerBtnAddHome.value = BTN_IMG_2
            }
            R.id.btn_access_add -> {
                listenerBtnAddHome.value = BTN_IMG_ACCESS
            }
        }
    }

    fun putHomeStay(addRoomViewModel: AddRoomViewModel) {

    }

}

