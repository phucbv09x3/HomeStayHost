package com.kujira.hosthomestay.ui.manager

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.data.model.response.AccUser
import com.kujira.hosthomestay.databinding.FragmentManagerAccBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_host_main.*
import kotlinx.android.synthetic.main.fragment_manager_acc.*


class ManagerAccFragment : BaseFragment<ManagerAccViewModel, FragmentManagerAccBinding>(), IClick {
    override fun createViewModel(): Class<ManagerAccViewModel> {
        return ManagerAccViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_manager_acc

    override fun initView() {
        activity.btn_managerRoom_on_main.setTextColor(context.getColor(R.color.color_show_choose))
        activity.btn_add_room_on_main.setTextColor(context.getColor(R.color.colorBlack))
        activity.btn_my_account_on_main.setTextColor(context.getColor(R.color.colorBlack))
        activity.linear_on_main.visibility = View.VISIBLE
        rcy_manager.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ManagerRoomAdapter(mutableListOf(), this@ManagerAccFragment)
        }

    }

    override fun bindViewModel() {
        viewModel.managerListAcc()
        viewModel.listRoomLiveData.observe(this, {
            (rcy_manager.adapter as ManagerRoomAdapter).setList(it)
        })

    }

    override fun clickShowReport(acc: AccUser) {
        val bundle = Bundle()
        bundle.putParcelable("bundle",acc)
        navigators.navigate(R.id.detailReportFragment,bundle)
    }

    override fun clickBlockAcc(accUser: AccUser) {
        val alertDialog = android.app.AlertDialog.Builder(context).create()
        alertDialog.setTitle("Block User")
        alertDialog.setMessage("Khóa tạm thời tài khoản này !")
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ ->
            if (accUser.permission == "1"){
                viewModel.blockAcc(accUser.uid)
                dialog.dismiss()
            }else{
                viewModel.blockAccClient(accUser.uid)
                dialog.dismiss()
            }

        }
        alertDialog.show()
    }

    //    override fun click(addRoomModel: AddRoomModel) {
//        val alertDialog = android.app.AlertDialog.Builder(context).create()
//        alertDialog.setTitle("Xóa Phòng")
//        alertDialog.setMessage("Bạn xác nhận xóa phòng này!")
//        alertDialog.setButton(
//            AlertDialog.BUTTON_NEUTRAL, "OK"
//        ) { dialog, _ ->
//            viewModel.removeRoom(addRoomModel)
//            dialog.dismiss()
//        }
//        alertDialog.show()
//
//    }
//
//    override fun longClick(addRoomModel: AddRoomModel) {
//        val alertDialog = AlertDialog.Builder(context).create()
//        val dialogView = layoutInflater.inflate(R.layout.custom_dilog_edit, null)
//        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        alertDialog.setView(dialogView)
//
//        val roomNameDialog = dialogView.findViewById<EditText>(R.id.edt_room_name_dialog).text
//        val sRoom = dialogView.findViewById<EditText>(R.id.edt_s_room_dialog).text
//        val numberSleep = dialogView.findViewById<EditText>(R.id.edt_sleep_room_dialog).text
//        val convenient = dialogView.findViewById<EditText>(R.id.edt_detail_dialog).text
//        val convenientAll = dialogView.findViewById<EditText>(R.id.edt_detail_all_dialog).text
//        val price = dialogView.findViewById<EditText>(R.id.edt_price_dialog).text
//        dialogView.findViewById<Button>(R.id.btn_edit_dialog).setOnClickListener {
//            if (roomNameDialog.isNotEmpty() && sRoom.isNotEmpty() && numberSleep.isNotEmpty()
//                && convenient.isNotEmpty() && convenientAll.isNotEmpty() && price.isNotEmpty()
//            ) {
//                val model = AddRoomModel(
//                    addRoomModel.id,
//                    "",
//                    "",
//                    roomNameDialog.toString(),
//                    sRoom.toString(),
//                    numberSleep
//                        .toString(),
//                    convenient.toString(),
//                    convenientAll.toString(),
//                    "",
//                    "",
//                    "",
//                    price.toString(),
//                    ""
//                )
//                viewModel.editRoom(model)
//                viewModel.listener.observe(this,{
//                    if (it==1){
//                        Toast.makeText(context, "Thành Công !", Toast.LENGTH_LONG).show()
//                    }
//                })
//            } else {
//                Toast.makeText(context, "Vui long nhập đủ thông tin !", Toast.LENGTH_LONG).show()
//            }
//        }
//        dialogView.findViewById<Button>(R.id.btn_cancel_edit).setOnClickListener {
//            alertDialog.dismiss()
//        }
//        alertDialog.show()
//    }
//
//    override fun clickExitRoom(addRoomModel: AddRoomModel) {
//        val alertDialog = android.app.AlertDialog.Builder(context).create()
//        alertDialog.setTitle("Khách hàng trả phòng")
//        alertDialog.setMessage("Đã trả !")
//        alertDialog.setButton(
//            AlertDialog.BUTTON_NEUTRAL, "OK"
//        ) { dialog, _ ->
//            viewModel.cancelRoom(addRoomModel.id)
//            dialog.dismiss()
//        }
//        alertDialog.show()
//    }
}