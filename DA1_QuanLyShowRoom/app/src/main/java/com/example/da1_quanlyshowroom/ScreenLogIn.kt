package com.example.da1_quanlyshowroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_screen_log_in.btnConfirmLogin
import kotlinx.android.synthetic.main.activity_screen_log_in.btnShowPassWord
import kotlinx.android.synthetic.main.activity_screen_log_in.edtInputPasswordUserLogin
import kotlinx.android.synthetic.main.activity_screen_log_in.edtInputUserNameLogin
import kotlinx.android.synthetic.main.activity_screen_log_in.txtForgetPassword
import kotlinx.android.synthetic.main.activity_screen_log_in.txtLogInForAdmin
import kotlinx.android.synthetic.main.activity_screen_log_in.txtRegisterUser
import viewmodel.dataModel

class ScreenLogIn : AppCompatActivity() {
    private lateinit var animation: Animation
    private var passwordVisible : Boolean = false
    private val viewmodel : dataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_log_in)
        /// tạo 1 hiệu ứng khi click
        animation = AnimationUtils.loadAnimation(this@ScreenLogIn, R.anim.animation)
        xet_giao_dien_text()
        xet_hien_thi_mat_khau()
        khoi_tao_dang_nhap()



        viewmodel.resultCheckDataAccount.observe(this@ScreenLogIn, Observer {
            if (it == true) {
                chuyen_man_hinh_chinh(edtInputUserNameLogin.text.toString(), edtInputPasswordUserLogin.text.toString())
                reset_editext()
            } else {
               val snackbar = Snackbar.make(edtInputUserNameLogin,"Tài khoản không tồn tại",Toast.LENGTH_SHORT)
                snackbar.show()

            }
        })
    }

    private fun reset_editext() {
        edtInputUserNameLogin.text.clear()
        edtInputPasswordUserLogin.text.clear()
    }

    private fun chuyen_man_hinh_chinh(string: String,string2: String) {
        val i = Intent(this@ScreenLogIn, ScreenHome::class.java)
        i.putExtra("username",string)
        i.putExtra("password",string2)
        startActivity(i)
  
    }

    private fun khoi_tao_dang_nhap() {
        dang_nhap_danh_cho_addMin() // kiểm tra đăng nhập dành cho  admin
        dang_ky_danh_cho_User() // đăng ký tài khoản ng dùng
        kiem_tra_dang_nhap_User()// kiểm tra đăng nhập dành cho người dùng
        xu_ly_quen_mat_khau() // lấy lại mật khẩu khi ng dùng quên
    }

    private fun xu_ly_quen_mat_khau() {
        txtForgetPassword.setOnClickListener {
            txtForgetPassword.startAnimation(animation)
            startActivity(Intent(this@ScreenLogIn, ScreenResetPasswordUser::class.java))
        }
    }

    private fun kiem_tra_dang_nhap_User() {
        btnConfirmLogin.setOnClickListener {
            btnConfirmLogin.startAnimation(animation)
            if (edtInputUserNameLogin.text.isEmpty() || edtInputPasswordUserLogin.text.isEmpty()) {
                val snackbar = Snackbar.make(
                    edtInputUserNameLogin,
                    "Nhập đầy đủ thông tin",
                    Toast.LENGTH_SHORT
                )
                snackbar.show()
            } else {
                viewmodel.checkAccount( // sử dụng lớp viewmodel để kiểm tra đăng nhập
                    edtInputUserNameLogin.text.toString(),
                    edtInputPasswordUserLogin.text.toString()
                )
            }
        }

    }

    private fun dang_ky_danh_cho_User() {
        txtRegisterUser.setOnClickListener { // khi ng dùng click nút đăng ký
            txtRegisterUser.startAnimation(animation)
            chuyen_man_hinh_dang_ky_tai_khoan() // chuyển tới màn hình dkdi

        }
    }

    private fun chuyen_man_hinh_dang_ky_tai_khoan() {
        // chuyển màn hình đăng nhập tài khoản
        val i = Intent(this@ScreenLogIn, ScreenRegisterAccount :: class.java)
        startActivity(i)



    }

    private fun dang_nhap_danh_cho_addMin() {
        txtLogInForAdmin.setOnClickListener{
            txtLogInForAdmin.startAnimation(animation)
            chuyen_man_hinh_dang_nhap_admin()

        }
    }

    private fun chuyen_man_hinh_dang_nhap_admin() {
        val i = Intent(this@ScreenLogIn, ScreenLoginAdmin :: class.java)
        startActivity(i)

    }


    private fun xet_hien_thi_mat_khau() {
          btnShowPassWord.setOnClickListener {
              btnShowPassWord.startAnimation(animation)
              bat_tat_mat_khau(edtInputPasswordUserLogin)
          }
    }
    private var isPasswordVisible = false
    private fun bat_tat_mat_khau(editText: EditText) { // bật tắt hiện thị mật khẩu
        isPasswordVisible = !isPasswordVisible

        // Thay đổi kiểu input dựa trên trạng thái hiện tại
        if (isPasswordVisible) {
            editText.inputType = android.text.InputType.TYPE_CLASS_TEXT
        } else {
            editText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        // Di chuyển con trỏ đến cuối chuỗi
        editText.setSelection(editText.text.length)
    }

    private fun xet_giao_dien_text() {
        val noidung = "Đăng ký"
        val a = SpannableString(noidung)
        a.setSpan(UnderlineSpan(),0, 7, 1)
        txtRegisterUser.setText(a)
        val noidung2 = "Quên mật khẩu"
        val b = SpannableString(noidung2)
        b.setSpan(UnderlineSpan(),0, 13, 1)
        txtForgetPassword.setText(b)
        val noidung3 = "Dành cho ADMIN"
        val c = SpannableString(noidung3)
        c.setSpan(UnderlineSpan(),0, 14, 1)
        txtLogInForAdmin.setText(c)
    }
}