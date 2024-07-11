package adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import fragment.FragmentManagerAccount
import fragment.FragmentManagerDate
import fragment.FragmentManagerCar
import fragment.FragmentShowSaleCar

class AdapterViewPager(val fragment: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragment, lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
      return  when(position){
           0 -> {
            FragmentManagerCar()
           }
          1 -> {
              FragmentShowSaleCar()
          }
          2 -> {
              FragmentManagerAccount()
          }
          else -> { FragmentManagerDate() }
      }
    }

    }

