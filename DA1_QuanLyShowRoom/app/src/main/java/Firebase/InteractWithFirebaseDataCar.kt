package Firebase

import android.annotation.SuppressLint
import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import data.dataCar

/// class repository  để truy xuất dữ liệu từ Firebase
class InteractWithFirebaseDataCar {
    private val database : DatabaseReference = FirebaseDatabase.getInstance().reference // tạo firebase
    private lateinit var dbImage : StorageReference



    fun pushImageToFireStorage( imageLinK: Uri, resultLink: (Uri) -> Unit){ // đưa ảnh lên firebaseStorage
        dbImage = FirebaseStorage.getInstance().reference.child("avt")
        val name = imageLinK.lastPathSegment
        val dbrev = dbImage.child(name.toString())

        dbrev.putFile(imageLinK)
            .addOnSuccessListener {task->
              dbrev.downloadUrl.addOnSuccessListener {
                  resultLink(it)
              }

            }
            .addOnFailureListener {
                resultLink("null".toUri())
            }

    }


    fun pushDataCar(imageCar: String, nameCar: String, dateCar: String, carBrand: String, carColor: String, sumofSeats: String, capacity: String, carPrice: String,
                    callback: (Boolean) -> Unit){
        val usersRef = database.child("DATA_CAR")
        val id = usersRef.push().key!!
        val newDataDate = dataCar(id,imageCar,nameCar,dateCar,carBrand,carColor,sumofSeats,capacity,carPrice)
        usersRef.child(id).setValue(newDataDate)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }


    fun getDataCar(callback: (List<dataCar>) -> Unit){ // hàm getUser nhận vào 1 callbank kiểu Unit

               val usersRef = database.child("DATA_CAR")
        val list = ArrayList<dataCar>()
        usersRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val dataCar = snapshot.getValue(dataCar :: class.java)
                list.add(dataCar!!)
                callback(list)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val dataCar = snapshot.getValue(dataCar :: class.java)
                if( dataCar != null ){
                    val index = list.indexOfFirst { it.carID == dataCar.carID }
                    if( index != -1 ){
                        list[index] = dataCar
                        callback(list)
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val dataCar = snapshot.getValue(dataCar :: class.java)
                if( dataCar != null ){
                    val index = list.indexOfFirst { it.carID == dataCar.carID }
                    if( index != -1 ){
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

    @SuppressLint("SuspiciousIndentation")
    fun upDateCar(id: String, imageCar: String, nameCar: String, dateCar: String, carBrand: String, carColor: String, sumofSeats: String, capacity: String, carPrice: String, carSale: Boolean, carPercent: Float, result: (Boolean) -> Unit) { // cập nhật thông tin

        val newData = dataCar(id,imageCar,nameCar,dateCar,carBrand,carColor,sumofSeats,capacity,carPrice,carSale, carPercent )
        val dbUpdate = FirebaseDatabase.getInstance().getReference("DATA_CAR").child(id.toString())
           dbUpdate.setValue(newData)
               .addOnSuccessListener {
                   result(true)
               }
               .addOnFailureListener {
                   result(false)
               }
    }

   fun deleteCar(id: String, result: (Boolean) -> Unit){ // xóa thông tin
       val dbDelete = FirebaseDatabase.getInstance().getReference("DATA_CAR").child(id.toString())
                    val remove = dbDelete.removeValue()
                        .addOnSuccessListener {
                            result(true)
                        }
                        .addOnFailureListener {
                            result(false)
                        }
   }
    fun sortDataFromSmallToBig(returnList: (List<dataCar>) -> Unit){ // sắp xếp dữ liệu từ bé đến lớn
        val dataref = database.child("DATA_CAR") // firebase
        val list = ArrayList<dataCar>() // danh sách chứa dữ liệu
        dataref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for( i in snapshot.children){
                    val data = i.getValue( dataCar ::class.java)
                    if (data != null) {
                        list.add(data)
                    }
                }
                returnList(list) // gán cho biết returnList giá trị của list
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}