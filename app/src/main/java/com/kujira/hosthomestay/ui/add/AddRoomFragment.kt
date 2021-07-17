package com.kujira.hosthomestay.ui.add

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.response.ProvinceFB
import com.kujira.hosthomestay.databinding.FragmentAddRoomBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_room.*


class AddRoomFragment : BaseFragment<AddRoomViewModel, FragmentAddRoomBinding>() {
    private var listProvinces = arrayListOf<String>()
    private var listDistrict = arrayListOf<String>()
    private var nameProvince = ""
    private var nameDistricts = ""
    private var listProvincesFB = mutableListOf<ProvinceFB>()
    override fun createViewModel(): Class<AddRoomViewModel> {
        return AddRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_add_room

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {

        val arrayAdapterProvinces = ArrayAdapter(
            activity,
            android.R.layout.simple_dropdown_item_1line,
            listProvinces
        )
        dataBinding.spinnerProvince.adapter = arrayAdapterProvinces

        viewModel.getListProvincesFB()
        viewModel.listProvincesFB.observe(this, {
            listProvincesFB = it
            for (element in it) {
                listProvinces.add(element.name)
            }
            arrayAdapterProvinces.notifyDataSetChanged()
        })


        val arrayAdapterDistrict = ArrayAdapter(
            activity,
            android.R.layout.simple_dropdown_item_1line,
            listDistrict
        )
        dataBinding.spinnerDistrictsOnAdd.adapter = arrayAdapterDistrict

        dataBinding.spinnerProvince.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    listDistrict.clear()
                    nameProvince = parent?.getItemAtPosition(position).toString()
                    viewModel.getListDistrictFB(parent?.getItemAtPosition(position).toString())
                    viewModel.listDistrictFBLiveData.observe(this@AddRoomFragment, {

                        it?.let { list ->
                            for (element in list) {
                                listDistrict.add(element.name)
                            }
                        }
                        arrayAdapterDistrict.notifyDataSetChanged()
                        Log.d("listNew", "${it}")

                        dataBinding.spinnerDistrictsOnAdd.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    nameDistricts = if (listDistrict.size == 0) {
                                        ""
                                    } else {
                                        parent?.getItemAtPosition(position)
                                            .toString()
                                    }

                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {

                                }
                            }

                    })
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        actionListener()
        spinnerTypeRoom()

    }

    private fun actionListener() {
        viewModel.listenerBtnAddHome.observe(this, {
            when (it) {
                AddRoomViewModel.BTN_IMG_1 -> {

                }
                AddRoomViewModel.BTN_IMG_2 -> {

                }
                AddRoomViewModel.BTN_IMG_ACCESS -> {

                }
            }
        })
    }

    private fun spinnerTypeRoom() {
        val listType = arrayListOf("Nhà Nghỉ", "Biệt Thự", "Nhà Riêng")
        val arrayAdapterTypeRoom = ArrayAdapter(
            activity,
            android.R.layout.simple_dropdown_item_1line,
            listType
        )
        dataBinding.spinnerTypeRoom.adapter = arrayAdapterTypeRoom
        // arrayAdapterTypeRoom.notifyDataSetChanged()
    }

    override fun bindViewModel() {

    }

}