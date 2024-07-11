package com.example.da1_quanlyshowroom

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_screen_register_account.btnConfirmRegister
import kotlinx.android.synthetic.main.activity_screen_register_account.btnshowReUserPassWordRegister
import kotlinx.android.synthetic.main.activity_screen_register_account.btnshowUserPassWordRegister
import kotlinx.android.synthetic.main.activity_screen_register_account.edtInputEmailUser
import kotlinx.android.synthetic.main.activity_screen_register_account.edtInputPasswordUserRegister
import kotlinx.android.synthetic.main.activity_screen_register_account.edtInputUserNameRegister
import kotlinx.android.synthetic.main.activity_screen_register_account.edtReInputPassWordUser

import viewmodel.dataModel
import java.util.Calendar
import java.util.regex.Pattern

@Suppress("KotlinConstantConditions")
class ScreenRegisterAccount : AppCompatActivity() {
    private val viewModel: dataModel by viewModels()
    private var isPasswordVisible = false
    private var isPasswordVisible2 = false

    private lateinit var animation : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_register_account)
        animation = AnimationUtils.loadAnimation(this@ScreenRegisterAccount, R.anim.animation)
        dang_ky_tai_khoan()
        viewModel.resultCheckDataAccount.observe(this@ScreenRegisterAccount, Observer {
             if( it == true ){
                 tao_snackbar(edtInputUserNameRegister,"Tài khoản đã tồn tại")
             }else{
                 val date = lay_thoi_gian_dang_ky()
                 viewModel.inputDataAccount(edtInputUserNameRegister.text.toString(), edtInputPasswordUserRegister.text.toString(), edtInputEmailUser.text.toString(), date.toString())
                 viewModel.resultRegister.observe(this@ScreenRegisterAccount, Observer { result ->
                     if (result == true) {
                         val alertDialog = AlertDialog.Builder(this@ScreenRegisterAccount)
                         alertDialog.apply {
                             alertDialog.setTitle("Đăng ký thành công")
                             setNegativeButton("[Ok]"){ dialogInterface: DialogInterface, i: Int ->
                                 xoa_thong_tin()
                                 dialogInterface.dismiss()

                             }
                         }
                             .show()
                     }else {
                         val alertDialog2 = AlertDialog.Builder(this@ScreenRegisterAccount)
                         alertDialog2.apply {
                             alertDialog2.setTitle("Đăng ký thất bại")
                             setNegativeButton("[Ok]"){ dialogInterface: DialogInterface, i: Int ->
                                 xoa_thong_tin()
                                 dialogInterface.dismiss()
                             }
                         }
                             .show()

                     }
                 })

             }
        })

        bat_tat_mat_khau()


        }
    private fun bat_tat_mat_khau() {
        btnshowUserPassWordRegister.setOnClickListener {
             isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // Hiển thị mật khẩu
                edtInputPasswordUserRegister.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                // Ẩn mật khẩu
                edtInputPasswordUserRegister.transformationMethod = PasswordTransformationMethod.getInstance()
            }

            // Đặt con trỏ về cuối văn bản
            edtInputPasswordUserRegister.setSelection(edtInputPasswordUserRegister.text.length)
        }

        btnshowReUserPassWordRegister.setOnClickListener {
            isPasswordVisible2 = !isPasswordVisible2
            if (isPasswordVisible2) {
                // Hiển thị mật khẩu
                edtReInputPassWordUser.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                // Ẩn mật khẩu
                edtReInputPassWordUser.transformationMethod = PasswordTransformationMethod.getInstance()
            }

            // Đặt con trỏ về cuối văn bản
            edtReInputPassWordUser.setSelection(edtReInputPassWordUser.text.length)
        }
    }

    @SuppressLint("NewApi")
    private fun dang_ky_tai_khoan() {
        btnConfirmRegister.setOnClickListener {
            btnConfirmRegister.startAnimation(animation)
            if (kiem_tra_noi_dung_nhap(edtInputUserNameRegister)) {
                tao_snackbar(edtInputUserNameRegister, "Điền tên đăng nhập !")
            } else if (kiem_tra_noi_dung_nhap(edtInputPasswordUserRegister)) {
                tao_snackbar(edtInputPasswordUserRegister, "Điền mật khẩu !")
            } else if (kiem_tra_noi_dung_nhap(edtReInputPassWordUser)) {
                tao_snackbar(edtReInputPassWordUser, "Xác nhận lại mật khẩu !")
            } else if (kiem_tra_noi_dung_nhap(edtInputEmailUser)) {
                tao_snackbar(edtInputEmailUser, "Điền email !")
            } else if (kiem_tra_mat_khau(edtInputPasswordUserRegister)) {
                tao_snackbar(edtInputPasswordUserRegister, "Mật khẩu phải nhiều hơn 8 kí tự !")
            } else if(kiem_tra_2_mat_khau(edtInputPasswordUserRegister, edtReInputPassWordUser)){
                tao_snackbar(edtInputPasswordUserRegister, "Mật khẩu của bạn không trùng khớp !")
            }else if(kiem_tra_Email(edtInputEmailUser)){
                tao_snackbar(edtInputEmailUser, "Email sai định dạng !")
            } else {
                // sử dụng viewModel đưa data lên firebase
               viewModel.checkAccount(edtInputUserNameRegister.text.toString(), edtInputPasswordUserRegister.text.toString())
            }
        }
    }

    private fun xoa_thong_tin() {
        edtInputUserNameRegister.text.clear()
        edtInputPasswordUserRegister.text.clear()
        edtReInputPassWordUser.text.clear()
        edtInputEmailUser.text.clear()
    }


}

 private fun kiem_tra_2_mat_khau(editText: EditText, editText2: EditText): Boolean{
     return if( editText.text.toString() == editText2.text.toString() ){
         false
     }else{
         true
     }

 }
    private fun kiem_tra_noi_dung_nhap(editText: EditText): Boolean{
        return if( editText.text.isEmpty() ){
            true
        }else{
            false
        }
    }
private  fun lay_thoi_gian_dang_ky():String{
    val getTime = Calendar.getInstance() // lấy ra ngày tại thời điểm người dùng đăng kí
    val day = getTime.get(Calendar.DAY_OF_MONTH)
    val month =getTime.get(Calendar.MONTH)+ 1
    val year = getTime.get(Calendar.YEAR)
    val date = "${day}/${month}/${year}"
    return date
}

    private fun kiem_tra_mat_khau(editText: EditText): Boolean{
        val pass = editText.text.toString()
        return if( pass.length < 8){ // kiểm tra nếu mật khẩu có ít hơn 8 kí tự
            true
        }else{
            false
        }
    }
   private fun tao_snackbar(editText: EditText, string: String) {
       val snackbar = Snackbar.make(editText, string, Snackbar.LENGTH_SHORT)
       snackbar.show()

   }
// hàm kiểm tra định dạng email
private fun kiem_tra_Email(editText: EditText): Boolean {
    val email = editText.text.toString()
    // Biểu thức chính quy kiểm tra định dạng email
    val form = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    // tạo 1 biến để biên dịch một biểu thức chính quy trong biến form thành một đối tượng có thể sử dụng để kiểm tra chuỗi.
    val testEmail = Pattern.compile(form)
    // tạo 1 biến sử dụng biểu thức chính quy để kiểm tra xem một chuỗi có khớp với biểu thức đó hay không.
    // so sánh email xem có giống kiểu nhập với testEmail không bằng biểu thức Matcher
    val doTest = testEmail.matcher(email)
    if(doTest.matches()){ // nếu hàm này trả về true -> nghĩa là định dạng của email giống với định dạng mà t quy định tại testEmail
        return false

    }else{
        return true
    }
}

