package com.example.da1_quanlyshowroom

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_screen_reset_password_user.btnNextStep
import kotlinx.android.synthetic.main.activity_screen_reset_password_user.edtInputNameForgetPass
import kotlinx.android.synthetic.main.activity_screen_reset_password_user.edtInputEmailForgetPass
import kotlinx.android.synthetic.main.activity_screen_reset_password_user.imgGifDontWorry
import kotlinx.android.synthetic.main.activity_screen_reset_password_user.txtShowCode
import kotlinx.android.synthetic.main.activity_screen_reset_password_user.txtTitle2
import kotlinx.android.synthetic.main.activity_screen_reset_password_user.txtTtile1
import viewmodel.dataModel
import kotlin.random.Random

class ScreenResetPasswordUser : AppCompatActivity() {
    private lateinit var ani : Animation
    val viewModel: dataModel by viewModels()
    var kq = false
    private  var randomInt: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_reset_password_user)
        ani = AnimationUtils.loadAnimation(this@ScreenResetPasswordUser, R.anim.animation)
        hien_thi_gif()
        lay_lai_mat_khau()





    }



    @SuppressLint("ShowToast", "SetTextI18n")
    private fun lay_lai_mat_khau() {

        btnNextStep.setOnClickListener {
            randomInt = Random.nextInt(100,9999)

            btnNextStep.startAnimation(ani)
            if(kiem_tra_noi_dung(edtInputNameForgetPass) || kiem_tra_noi_dung(edtInputEmailForgetPass)){
                val snackbar = Snackbar.make(edtInputEmailForgetPass,"Điền đủ thông tin để tiếp tục!", Toast.LENGTH_SHORT)
                 snackbar.show()
            }else{
                viewModel.checkAccountbyNameandEmail(edtInputNameForgetPass.text.toString(), edtInputEmailForgetPass.text.toString(),randomInt)
            }
        }
        viewModel.resultCheckDataAccount2.observe(this@ScreenResetPasswordUser, Observer {
            if( it == true){
                txtTtile1.text = "Lấy mật khẩu thành công"
                txtTitle2.text ="Mật khẩu mới của bạn"
                txtShowCode.text = randomInt.toString()
                txtShowCode.visibility = View.VISIBLE
                edtInputNameForgetPass.visibility= View.GONE
                edtInputEmailForgetPass.visibility= View.GONE
                btnNextStep.setImageResource(R.drawable.icon_success)
                btnNextStep.setOnClickListener {
                    finish()
                }
             }else{
                val alerdialog = AlertDialog.Builder(this@ScreenResetPasswordUser)
                alerdialog.apply {
                    setTitle("Lấy mật khẩu thất bại ")
                    setMessage("Hãy chắc chắn bạn nhập vào đúng tài khoản và email")
                    setNegativeButton("[Ok]"){ dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()

                    }
                }.show()
            }
        })

    }


    private fun kiem_tra_noi_dung(editText: EditText): Boolean {
        if( editText.text.isEmpty()){
            return true
        }
        return false
    }

    private fun hien_thi_gif() {
        Glide.with(this@ScreenResetPasswordUser)
            .asGif()
            .load(R.drawable.dontworry)
            .into(imgGifDontWorry)
    }
}