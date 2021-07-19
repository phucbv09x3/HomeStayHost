package com.kujira.hosthomestay.ui.add

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.response.AddRoomModel
import com.kujira.hosthomestay.data.model.response.DistrictFB
import com.kujira.hosthomestay.data.model.response.ProvinceFB
import com.kujira.hosthomestay.ui.base.BaseViewModel

class AddRoomViewModel : BaseViewModel() {

    private var listProvinceFB = mutableListOf<ProvinceFB>()
    private var listDistrictsFB = mutableListOf<DistrictFB>()

    var listDistrictFBLiveData = MutableLiveData<MutableList<DistrictFB>>()
    var listProvincesFB = MutableLiveData<MutableList<ProvinceFB>>()
    private var dataReference =
        FirebaseDatabase.getInstance().getReference("Host")
    private var auth = FirebaseAuth.getInstance()
    private var dataStoreRef = FirebaseStorage.getInstance().getReference("HostStorage")
    var textWard = ObservableField<String>()
    var nameRoom = ObservableField<String>()
    var sRoom = ObservableField<String>()
    var numberSleepRoom = ObservableField<String>()
    var textDetailGT = ObservableField<String>()
    var introduce = ObservableField<String>()
    var price = ObservableField<String>()
    var listenerBtnAddHome = MutableLiveData<Int>()


    var notifyPut = MutableLiveData<Int>()

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

    fun putHomeStay(addRoom: AddRoomModel) {
        var linkImg1 = ""
        var linkImg2 = ""
        val img = dataStoreRef.child(auth.currentUser!!.uid)
            .child("image")
        val imgName1 = img.child("" + addRoom.imageRoom1)
        val imgName2 = img.child("" + addRoom.imageRoom2)
        imgName1.putFile(addRoom.imageRoom1)
            .addOnSuccessListener {
                imgName1.downloadUrl.addOnCompleteListener { result ->
                    linkImg1 = result.toString()
                }
            }
        imgName2.putFile(addRoom.imageRoom2)
            .addOnSuccessListener {
                imgName2.downloadUrl.addOnCompleteListener { result ->
                    linkImg2 = result.toString()
                }
            }

        dataReference.child("ListRoom").child(auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hashMap = HashMap<String, String>()
                    hashMap["address"] = addRoom.address
                    hashMap["typeRoom"] = addRoom.typeRoom
                    hashMap["nameRoom"] = addRoom.nameRoom
                    hashMap["s_room"] = addRoom.s_room
                    hashMap["numberSleepRoom"] = addRoom.numberSleepRoom
                    hashMap["convenient"] = addRoom.convenient
                    hashMap["introduce"] = addRoom.introduce
                    hashMap["imageRoom1"] = linkImg1
                    hashMap["imageRoom2"] = linkImg2
                    hashMap["status"] = "Còn Phòng"
                    hashMap["price"] = addRoom.price
                    dataReference.child(auth.currentUser!!.uid).setValue(hashMap)
                        .addOnSuccessListener {
                            notifyPut.value = 1
                        }
                        .addOnFailureListener {
                            notifyPut.value = 0
                        }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}

