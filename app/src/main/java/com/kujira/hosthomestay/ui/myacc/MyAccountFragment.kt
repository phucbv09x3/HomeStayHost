package com.kujira.hosthomestay.ui.myacc

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.kujira.hosthomestay.R
import com.kujira.hosthomestay.databinding.FragmentMyAccountBinding
import com.kujira.hosthomestay.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MyAccountFragment : BaseFragment<MyAccountViewModel, FragmentMyAccountBinding>() {
    override fun createViewModel(): Class<MyAccountViewModel> {
        return MyAccountViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_my_account

    override fun initView() {
        activity.btn_managerRoom_on_main.setTextColor(context.getColor(R.color.colorBlack))
        activity.btn_add_room_on_main.setTextColor(context.getColor(R.color.colorBlack))
        activity.btn_my_account_on_main.setTextColor(context.getColor(R.color.rdc))
        viewModel.updateUI()
    }

    override fun bindViewModel() {
        dataBinding.tvEdit.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context).create()
            val dialogView = layoutInflater.inflate(R.layout.custom_change_acc, null)
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog.setView(dialogView)

            val nameNew = dialogView.findViewById<EditText>(R.id.edt_new_name).text
            val phoneNew = dialogView.findViewById<EditText>(R.id.edt_new_phone).text
            dialogView.findViewById<Button>(R.id.btn_change_acc).setOnClickListener {
                viewModel.changeAcc(nameNew.toString(), phoneNew.toString())
            }
            alertDialog.show()
        }
    }
}