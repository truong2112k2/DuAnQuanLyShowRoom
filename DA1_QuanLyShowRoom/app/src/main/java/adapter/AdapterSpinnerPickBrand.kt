package adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.da1_quanlyshowroom.MainActivity
import com.example.da1_quanlyshowroom.R
import data.dataItemSpinner

class AdapterSpinnerPickBrand( val activity:Activity, val list: List<dataItemSpinner>): ArrayAdapter<dataItemSpinner>(activity, R.layout.item_spinner) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return init(position, convertView, parent)
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return init(position, convertView, parent)
    }

    private fun init(position: Int, convertView: View?, parent: ViewGroup): View {
        val context = activity.layoutInflater
        val view = context.inflate(R.layout.item_spinner, parent ,false)
        val nameString = view.findViewById<TextView>(R.id.txtNameBrand)
        val fromString = view.findViewById<TextView>(R.id.txtFrom)
        nameString.text = list[position].nameBrand
        fromString.text = list[position].form


        return view

    }
}