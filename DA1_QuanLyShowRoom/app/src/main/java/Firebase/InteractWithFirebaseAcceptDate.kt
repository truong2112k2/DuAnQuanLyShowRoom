package Firebase

import android.annotation.SuppressLint
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import data.dataDate

class InteractWithFirebaseAcceptDate {
    private lateinit var db : DatabaseReference


    fun getDataAcceptDate( list:(List<dataDate>) -> Unit){
        db = FirebaseDatabase.getInstance().getReference("ACCEPT_DATE")
        val list = ArrayList<dataDate>()
        db.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val date = snapshot.getValue(dataDate ::class.java)
                if( date != null){
                    list.add(date)
                    list(list)
                }
            }

            @SuppressLint("SuspiciousIndentation")
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val date = snapshot.getValue(dataDate ::class.java)
                  if( date != null){
                      val index = list.indexOfFirst { it.idDate == date.idDate }
                      if( index != -1){
                          list[index] = date
                          list(list)
                      }
                  }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val date = snapshot.getValue(dataDate ::class.java)
                 if(date != null){
                     val index = list.indexOfFirst { it.idDate == date.idDate }
                     if( index != -1){
                         list.removeAt(index)
                         list(list)
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