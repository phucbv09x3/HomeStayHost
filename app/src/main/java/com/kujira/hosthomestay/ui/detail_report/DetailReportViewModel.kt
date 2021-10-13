package com.kujira.hosthomestay.ui.detail_report

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.ui.base.BaseViewModel
import com.kujira.hosthomestay.utils.Constants
import com.kujira.hosthomestay.utils.printLog

class DetailReportViewModel : BaseViewModel() {
    private val dataRef = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
    private var listAcc = mutableListOf<ReportModel>()
    var listAccLiveData = MutableLiveData<MutableList<ReportModel>>()
    var uidClient =""
    fun getListReport() {
        dataRef.child("Report").child(uidClient)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listAcc.clear()
                    for (snap in snapshot.children) {
                        val idHost = snap.child("idHost")
                            .value.toString()
                        val content = snap.child("contentReport")
                            .value.toString()
                        listAcc.add(ReportModel(idHost, content))
                    }
                    listAccLiveData.value = listAcc
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    fun resetReportAccClient(uid: String) {
        showLoading.onNext(true)
        val dataRefer = FirebaseDatabase.getInstance().getReference(Constants.CLIENT)
        printLog(" resetReportAccClient: ${uid}")
        dataRefer.child("Report").child("ReportClient").child(uid).removeValue()
            .addOnSuccessListener {
                showLoading.onNext(false)
                navigation.navigateUp()
            }
    }
}