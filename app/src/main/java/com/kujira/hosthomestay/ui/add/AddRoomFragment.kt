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
    private var listProvinces = arrayListOf<String>("Tinh")
    private var listDistrict = arrayListOf<String>("Quan Huyen")
    private var nameProvince = ""
    private var nameDistricts = ""
    private var listProvincesFB = mutableListOf<ProvinceFB>()
    override fun createViewModel(): Class<AddRoomViewModel> {
        return AddRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_add_room

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {


        viewModel.getListProvincesFB()
        viewModel.listProvincesFB.observe(this, {
            listProvincesFB = it
            for (element in it) {
                listProvinces.add(element.name)
            }
        })

        val spinnerProvinces = view?.findViewById(R.id.spinner_province) as Spinner
        val arrayAdapterProvinces = ArrayAdapter(
            activity,
            android.R.layout.simple_dropdown_item_1line,
            listProvinces
        ) as SpinnerAdapter
        spinnerProvinces.adapter = arrayAdapterProvinces


        val spinnerDistrict = view?.findViewById(R.id.spinner_districts_on_add) as Spinner

        val arrayAdapterDistrict = ArrayAdapter(
            activity,
            android.R.layout.simple_dropdown_item_1line,
            listDistrict
        )
        spinnerDistrict.adapter = arrayAdapterDistrict
        spinnerProvinces.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                    Log.d("listNew","${it}")

                    spinnerDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            nameDistricts = if (listDistrict.size == 0) {
                                "Quuận Huyện"
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


    }

    override fun bindViewModel() {

    }

}