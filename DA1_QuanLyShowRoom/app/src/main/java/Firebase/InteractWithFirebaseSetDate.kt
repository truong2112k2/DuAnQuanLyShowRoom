package Firebase

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import data.dataCar
import data.dataDate

class InteractWithFirebaseSetDate {
    private lateinit  var db : DatabaseReference

    fun addDataDate( Uname:String, Uaddress:String, Uphone:String, meet_address: String, meet_date:String, meet_time:String, dataCar: dataCar, userId: String, callback: (Boolean)-> Unit){
        db = FirebaseDatabase.getInstance().getReference("DATA_DATE")
       val id = db.push().key // tạo id
        val dataDate = dataDate(id,Uname, Uaddress,Uphone,meet_address,meet_date,meet_time,dataCar,userId)
        db.child(id.toString()).setValue(dataDate)
            .addOnCompleteListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
    fun get_a_DataDate(idDate: String, callback: (dataDate) -> Unit){
        db = FirebaseDatabase.getInstance().getReference("DATA_DATE").child(idDate.toString())

        db.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if( snapshot.exists()){
                    val data = snapshot.getValue(dataDate :: class.java)
                    callback(data!!)
                }else{
                     val dataNull = dataDate()
                    callback(dataNull)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getDataDate(callback: (List<dataDate>) -> Unit){
        val list = ArrayList<dataDate>()
        db = FirebaseDatabase.getInstance().getReference("DATA_DATE")

        // thao tác và tự động cập nhật lên recycleview
        db.addChildEventListener( object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val date = snapshot.getValue( dataDate :: class.java)
                if( date != null ){
                  list.add(date)
                    callback(list)
                }

            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val date = snapshot.getValue( dataDate :: class.java)
                if( date != null ){
                    val index = list.indexOfFirst { it.idDate == date.idDate }
                    /*
                     Đoạn code này tìm vị trí của đối tượng User trong danh sách list dựa trên trường id của User.
                     Nếu không tìm thấy, index sẽ có giá trị -1.
                     */
                    if( index != -1){ //kiểm tra xem đối tượng User có tồn tại trong danh sách list hay không.
                        list[index] = date //cập nhật đối tượng User trong danh sách list bằng đối tượng user mới.
                        callback(list)
                    }
                }
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                val date = snapshot.getValue( dataDate :: class.java)
                if( date != null ){
                    val index = list.indexOfFirst { it.idDate == date.idDate }
                    /*
                     tìm vị trí của đối tượng trong danh sách list dựa trên trường idDate của dataDate.
                     Nếu không tìm thấy, index sẽ có giá trị -1.
                     */
                    if( index != -1){ // kiểm tra xem đối tượng có tồn tại trong danh sách list hay không.
                        list.removeAt(index) // xóa đối tượng khỏi danh sách list tại vị trí index.
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