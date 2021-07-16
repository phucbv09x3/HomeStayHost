package com.kujira.hosthomestay.ui.mess

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.FragmentMessageBinding
import com.kujira.hosthomestay.ui.base.BaseFragment


class MessageFragment : BaseFragment<MessageViewModel, FragmentMessageBinding>() {
    override fun createViewModel(): Class<MessageViewModel> {
        return MessageViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_message

    override fun initView() {

//        val databaseReference =
//            FirebaseDatabase.getInstance().getReference("Host")
//        viewModel.getPost()
//        viewModel.listProvince.observe(this, {
//            Log.d("list", "${it}")
//            if (it != null) {
//                for (element in it) {
//                    val hashMap = HashMap<String, Any>()
//                    hashMap["name"] = element.name
//                    hashMap["code"] = element.code
//                    hashMap["phone_code"] = element.phone_code
//                    databaseReference.child("ListProvinces").child(element.name).setValue(hashMap)
//
//                    val listDistin = element.districts
//                    for (item in listDistin) {
//                        val hashMap = HashMap<String, Any>()
//                        hashMap["name"] = item.name
//                        hashMap["code"] = element.code
//                        databaseReference.child("DetailProvince").child(element.name)
//                            .child("Districts").push().setValue(hashMap)
//                    }
//                }
//            }
//
//        })

    }

    override fun bindViewModel() {

    }
}