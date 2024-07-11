package fragment

import Interface.setOnClickItem
import adapter.AdapterSaleCar
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_quanlyshowroom.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import data.dataCar
import viewmodel.dataModel

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentShowSaleCar.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentShowSaleCar : Fragment(R.layout.fragment_show_sale_car) {
    private lateinit var viewSale: View
    private lateinit var listSale: ArrayList<dataCar>
    private lateinit var adt: AdapterSaleCar
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: DatabaseReference
    private lateinit var viewModel: dataModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this@FragmentShowSaleCar).get(dataModel ::class.java)
        viewSale = inflater.inflate(R.layout.fragment_show_sale_car, container, false)
        anhxaview()
        db = FirebaseDatabase.getInstance().getReference("DATA_CAR")
        listSale = ArrayList<dataCar>()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
       hien_thi_san_pham()
        return viewSale

    }

    private fun hien_thi_san_pham() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for( i in snapshot.children){
                    val data = i.getValue( dataCar:: class.java)
                    if(data!!.carSale == true){
                        listSale.add(data)

                    }
                }
                adt = AdapterSaleCar(listSale)
                recyclerView.adapter = adt
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



    private fun anhxaview() {
        recyclerView = viewSale.findViewById(R.id.recycle_sale)

    }


}