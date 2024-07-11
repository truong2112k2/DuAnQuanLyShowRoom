package fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.marginTop
import com.example.da1_quanlyshowroom.R
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.collection.LLRBNode.Color
import com.google.firebase.database.collection.LLRBNode.Color.BLACK
import data.dataAccount
import kotlinx.android.synthetic.main.fragment_manager_account.table_showAccount
import kotlinx.android.synthetic.main.fragment_manager_account.txtshowNumberUser

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentManagerAccount.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentManagerAccount : Fragment(R.layout.fragment_manager_account) {

    private lateinit var view: View
    private lateinit var tablayout: TableLayout
    private lateinit var db : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       view = inflater.inflate(R.layout.fragment_manager_account, container, false)
        db = FirebaseDatabase.getInstance().getReference("USER")
       anhxaview()
        hien_du_lieu_len_bang()







        return view
    }

    @SuppressLint("SetTextI18n")
    private fun hien_du_lieu_len_bang() {

        db.addListenerForSingleValueEvent(object : ValueEventListener{
            @SuppressLint("RestrictedApi", "ResourceAsColor", "PrivateResource")
            override fun onDataChange(snapshot: DataSnapshot) {
                for( i in snapshot.children){
                    val data = i.getValue(dataAccount ::class.java)

                        val newRow = TableRow(requireContext()) // thêm hàng

                        val textviewName = TextView(requireContext()) // thêm textview và gán dữ liệu từ firebase vào
                    //mỗi textview đại diện cho 1 cột
                        textviewName.text = data!!.userName
                    textviewName.layoutParams = TableRow.LayoutParams( // xét kích thức cho cột
                        100,
                        200
                    )
                    textviewName.gravity = Gravity.CENTER // chỉnh nội dung của cột ra phía giữa

                    textviewName.setTextColor(com.google.android.material.R.color.m3_card_stroke_color)

                        val textviewPassword = TextView(requireContext())
                        textviewPassword.text = data.passWord
                    textviewPassword.layoutParams = TableRow.LayoutParams(
                        100,
                        200
                    )
                    textviewPassword.gravity = Gravity.CENTER





                    val textviewEmail = TextView(requireContext())
                        textviewEmail.text = data.email
                    textviewEmail.layoutParams = TableRow.LayoutParams(
                        100,
                        200
                    )
                    textviewEmail.gravity = Gravity.CENTER
                        // thêm textview vào hàng mới tạo
                        newRow.addView(textviewName)
                        newRow.addView(textviewPassword)
                        newRow.addView(textviewEmail)
                        // thêm hàng vào tablayout
                        table_showAccount.addView(newRow)
                    val num: Int = table_showAccount.childCount.toInt()
                    txtshowNumberUser.text =" Số người dùng hiện tại là " + (num-1)


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun anhxaview() {
        tablayout = view.findViewById(R.id.table_showAccount)
    }

}