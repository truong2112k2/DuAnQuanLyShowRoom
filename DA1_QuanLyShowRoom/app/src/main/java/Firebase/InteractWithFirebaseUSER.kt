package Firebase

import android.annotation.SuppressLint
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import data.dataAccount
import data.dataDate
import java.util.EventListener

class InteractWithFirebaseUSER {
   private  var  database = FirebaseDatabase.getInstance().getReference("USER")

    fun checkDataAccount(uName: String, uPass: String, callback: (Boolean)-> Unit ){
        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( i in snapshot.children){
                    val data = i.getValue(dataAccount :: class.java)
                    if( data != null && data.userName == uName && data.passWord == uPass){
                        callback(true)
                        return
                    }
                }
                callback(false)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }

    fun checkDataAccountForUserNameAndGmail(uName: String, uGmail: String, number: Int ,callback: (Boolean)-> Unit ){
        database.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( i in snapshot.children){
                    val data = i.getValue(dataAccount :: class.java)
                    if( data != null && data.userName == uName && data.email == uGmail){
//                         val number = Random.nextInt(100, 9999)
                        val id = data.id
                        val uname = data.userName
                        val upass = number.toString()
                        val email = data.email
                        val udate = data.registerDate
                        val newData = dataAccount(id, uname,upass, email,udate)

                        val dbupdate = FirebaseDatabase.getInstance().getReference("USER").child(id.toString())
                        dbupdate.setValue(newData)
                            .addOnCompleteListener {
                                callback(true)
                            }
                            .addOnFailureListener {
                                callback(false)
                            }

                        return

                    }
                }
                callback(false)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }


    @SuppressLint("SuspiciousIndentation")
  fun getID(uName: String, uPass: String, callback: (String) -> Unit){ // hàm lấy id
     var id = "id_null"
        database.addListenerForSingleValueEvent(@SuppressLint("SuspiciousIndentation")
        object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( i in snapshot.children){
                    val data = i.getValue(dataAccount :: class.java)
                    if( data != null && data.userName == uName && data.passWord == uPass){
                      id = data.id.toString()
                        callback(id)
                        return
                    }
                }
                callback(id)

            }
            override fun onCancelled(error: DatabaseError) {
                callback(id)
            }
        })
    }
    fun getDate(uName: String, uPass: String, callback: (String) -> Unit){ // hàm lấy ngày đăng ký
        var date = "date_null"
        database.addListenerForSingleValueEvent(@SuppressLint("SuspiciousIndentation")
        object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( i in snapshot.children){
                    val data = i.getValue(dataAccount :: class.java)
                    if( data != null && data.userName == uName && data.passWord == uPass){
                        date = data.registerDate.toString()
                        callback(date)
                        return
                    }
                }
                callback(date)

            }
            override fun onCancelled(error: DatabaseError) {
                callback(date)
            }
        })
    }
    fun getEmail(uName: String, uPass: String, callback: (String) -> Unit){ // hàm lấy email
        var email = "email_null"
        database.addListenerForSingleValueEvent(@SuppressLint("SuspiciousIndentation")
        object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( i in snapshot.children){
                    val data = i.getValue(dataAccount :: class.java)
                    if( data != null && data.userName == uName && data.passWord == uPass){
                        email = data.email.toString()
                        callback(email)
                        return
                    }
                }
                callback(email)

            }
            override fun onCancelled(error: DatabaseError) {
                callback(email)
            }
        })
    }
    fun updateAccount(idAccount: String, newUName: String, newUPass:String, newEmail: String, newDate:String, callback: (Boolean) -> Unit){ // hàm cập nhật thông tin
        val dbupdate = FirebaseDatabase.getInstance().getReference("USER").child(idAccount.toString())
        val newData = dataAccount(idAccount,newUName, newUPass,newEmail,newDate)
        dbupdate.setValue(newData)
            .addOnCompleteListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
    fun deleteAccount(idAccount: String, callback: (Boolean) -> Unit){
        val dbDelete = FirebaseDatabase.getInstance().getReference("USER").child(idAccount.toString())
        val delete = dbDelete.removeValue()
        delete.addOnCompleteListener{
            callback(true)
        }
            .addOnFailureListener{
                callback(false)
            }
    }

    fun getDataDateFromAccount(idAccount: String, callback: (ArrayList<dataDate>) -> Unit){
        val list  = ArrayList<dataDate>()
        val db = FirebaseDatabase.getInstance().getReference("USER").child(idAccount).child("DATE")

        db.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val date = snapshot.getValue(dataDate ::class.java)
                list.add(date!!)
                callback(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val date = snapshot.getValue(dataDate ::class.java)
                 if( date != null){
                     val index = list.indexOfFirst { it.idDate == date.idDate }
                     if(index != 1){
                         list[index] = date
                         callback(list)
                     }
                 }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val date = snapshot.getValue(dataDate ::class.java)
                if( date != null){
                    val index = list.indexOfFirst { it.idDate == date.idDate }
                    if(index != 1){
                        list.removeAt(index)
                        callback(list)
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }


}