package com.kujira.hosthomestay.ui.add

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.response.AddRoomModel
import com.kujira.hosthomestay.data.model.response.ProvinceFB
import com.kujira.hosthomestay.databinding.FragmentAddRoomBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_host_main.*
import kotlinx.android.synthetic.main.fragment_add_room.*
import kotlinx.android.synthetic.main.fragment_my_account.*


class AddRoomFragment : BaseFragment<AddRoomViewModel, FragmentAddRoomBinding>() {
    private var listProvinces = arrayListOf<String>()
    private var listDistrict = arrayListOf<String>()
    private var nameProvince = ""
    private var nameDistricts = ""
    private var nameTypeRoom = ""
    private var uriImg1: Uri? = null
    private var uriImg2: Uri? = null
    private var listProvincesFB = mutableListOf<ProvinceFB>()
    override fun createViewModel(): Class<AddRoomViewModel> {
        return AddRoomViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_add_room

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initView() {
        activity.btn_managerRoom_on_main.setTextColor(context.getColor(R.color.colorBlack))
        activity.btn_add_room_on_main.setTextColor(context.getColor(R.color.color_show_choose))
        activity.btn_my_account_on_main.setTextColor(context.getColor(R.color.colorBlack))

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


    }

    private fun actionListener() {
        val pr = ProgressDialog(context)
        viewModel.listenerBtnAddHome.observe(this, { it ->
            when (it) {
                AddRoomViewModel.BTN_IMG_1 -> {
                    requestImage(111)
                }
                AddRoomViewModel.BTN_IMG_ACCESS -> {
                    viewModel.putTravel(uriImg1!!)
                    pr.show()
                    viewModel.listenerSuccess.observe(this, { it2 ->
                        if (it2 == 1) {
                            dataBinding.btnAccessAll.visibility = View.VISIBLE
                        }
                        pr.dismiss()
                    })

                }
                AddRoomViewModel.BTN_IMG_ACCESS_ALL -> {
                    val introduce = viewModel.introduce.get()

                    if (
                         introduce?.isNotEmpty() == true
                        && uriImg1.toString().isNotEmpty()
                    ) {
                        val address = "$nameDistricts,$nameProvince"
                        viewModel.addTravel(address)
                        viewModel.notifyPut.observe(this, { itNew->
                            if (itNew == 1) {
                                Toast.makeText(context, "Thành công!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Thất bại !", Toast.LENGTH_LONG).show()
                            }
                        })

                    } else {
                        Toast.makeText(context, "Vui lòng nhập đủ thông tin !", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        })
    }

    private fun requestImage(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Picture"
            ), requestCode
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            img_1.setImageURI(imageUri)
            uriImg1 = imageUri
        }
    }

    override fun bindViewModel() {

    }

}