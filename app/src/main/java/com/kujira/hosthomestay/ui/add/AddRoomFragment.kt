package com.kujira.hosthomestay.ui.add

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.FragmentAddRoomBinding
import com.kujira.hosthomestay.ui.base.BaseFragment


class AddRoomFragment : BaseFragment<AddRoomViewModel, FragmentAddRoomBinding>() {
    private var listProvinces = arrayListOf<String>()
    override fun createViewModel(): Class<AddRoomViewModel> {
        return AddRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_add_room

    override fun initView() {
        val spin = view?.findViewById(R.id.spinner_province) as Spinner

        viewModel.getListProvincesFB()
        viewModel.listProvincesFB.observe(this, {
            Log.d("listPro","${it}")
            for (element in it) {
                listProvinces.add(element.name)
            }
        })
        val arrayAdapter = ArrayAdapter(
            activity.baseContext,
           android.R.layout.simple_dropdown_item_1line,
            listProvinces
        )
//        spin.prompt="helllo "
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spin.adapter = arrayAdapter

        spin.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                Toast.makeText(context,

                             listProvinces[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

    }

    override fun bindViewModel() {

    }
}