package com.example.da1_quanlyshowroom

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import kotlinx.android.synthetic.main.activity_screen_login_admin.btnLogInAddMin
import kotlinx.android.synthetic.main.activity_screen_login_admin.edtInputAdminName
import kotlinx.android.synthetic.main.activity_screen_login_admin.edtInputAdminPassWord
import viewmodel.dataModel

class ScreenLoginAdmin : AppCompatActivity() {
    val viewModel : dataModel by viewModels()

    private lateinit var animation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_login_admin)
         animation = AnimationUtils.loadAnimation(this@ScreenLoginAdmin, R.anim.animation)
        btnLogInAddMin.setOnClickListener {
            btnLogInAddMin.startAnimation(animation)
            if( viewModel.compareAddminWithEditextContent(edtInputAdminName.text.toString(), edtInputAdminPassWord.text.toString())){
                 chuyen_man_hinh_adminManager()
                xoa_bieu_mau()
            }else{
                val alertDialog = AlertDialog.Builder(this@ScreenLoginAdmin)
                alertDialog.apply {
                    alertDialog.setTitle("Tên đăng nhập hoặc mật khẩu sai !")
                    setPositiveButton("[Ok]") { dialogInterface: DialogInterface, i: Int ->
                        xoa_bieu_mau()
                         dialogInterface.dismiss()

                    }
                }.show()
              }
        }

    }

    private fun xoa_bieu_mau() {
        edtInputAdminName.text.clear()
        edtInputAdminPassWord.text.clear()
        edtInputAdminName.requestFocus()
    }

    private fun chuyen_man_hinh_adminManager() {
        val i = Intent(this@ScreenLoginAdmin, ScreenAdMinManager :: class.java)
        startActivity(i)
        finish()
    }
}