package com.example.da1_quanlyshowroom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_test.btnPushAnh
import kotlinx.android.synthetic.main.activity_test.imgShowANh
import kotlinx.android.synthetic.main.activity_test.txtStatus

class testActivity : AppCompatActivity() {
    var name = "null"

    val PICK_IMAGE_REQUEST = 100 // đây là mã của hành động yêu cầu chọn nahr

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        chonhinhanh()
    }

    private fun chonhinhanh() {
        btnPushAnh.setOnClickListener {
            // tạo 1 intent để mở màn hình chọn ảnh
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // action_pick chỉ hành động của intent là chọn 1 mục từ dữ liêu được truyền vào
            // MediaStore.Images.Media.EXTERNAL_CONTENT_URI là URI đại hịiện cho nội dung hình ảnh
            startActivityForResult(intent, PICK_IMAGE_REQUEST) // cho intent bắt đầu chạy
        }
    }


    @Deprecated("Deprecated in Java")
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // kiểm tra xem mã hành động có được gọi không
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null ){
            val imageURI = data.data // lấy ra link URI ảnh
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageURI) // truy xuất và trích xuất đối tượng bitmap chứa dữ liệu ảnh
            txtStatus.text ="${imageURI}"
            // Picasso.get().load(txtStatus.text.toString()).into(imgShowANh) // bạn cũng có thể sử dụng picasso để show ảnh thông qua URI
            imgShowANh.setImageBitmap(bitmap) // hiển thị ảnh bitmap lên imageview
//            name = imageURI.toString()
//            btnCheckLink.setOnClickListener {
//                Toast.makeText(this@MainActivity, "${name}", Toast.LENGTH_SHORT).show()
//            }


        }
    }
}