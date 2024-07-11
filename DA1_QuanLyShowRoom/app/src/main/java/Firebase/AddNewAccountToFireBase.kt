package Firebase

import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import data.dataAccount

class AddNewAccountToFireBase {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference // tạo firebase

    fun registerAccount(
        uName: String,
        passWord: String,
        eMail: String ,
        date: String,
        callback: (Boolean)-> Unit
        /*
        callback: (Boolean)-> Unit: Đây là tham số callback trong đó (Boolean) -> Unit chỉ định kiểu của callback.
        Tham số callback này là một hàm có một đối số kiểu Boolean và không có giá trị trả về (Unit).
         */
    ) {

     //khởi tạo một tham chiếu accountRef đến nút "USER" trong cơ sở dữ liệu Firebase.
        val accountRef = database.child("USER")



      // tạo id một cách tự động
           val id = accountRef.push().key!!
        // tạo dataAccount
            val dataAccount = dataAccount(id, uName, passWord, eMail,date)
        accountRef.child(id).setValue(dataAccount) // đưa dataAccount lên firebase
            .addOnCompleteListener {task-> // kiểm tra task
                if (task.isSuccessful) { // nếu task == true -> thêm thành công, xét giá trị của callback thành true
                    callback(true)
                } else {// nếu task == false -> thêm không thành công, xét giá trị của callback thành false
                    callback(false)
                }
            }
            .addOnFailureListener { // trong trường hợp 0 thể kết nối đến firebase-> xét callback về true
                callback(false)
            }

    }
}