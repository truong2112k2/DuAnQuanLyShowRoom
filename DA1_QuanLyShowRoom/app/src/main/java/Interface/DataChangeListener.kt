package Interface

import data.dataCar

interface DataChangeListener {
    fun onDataAdded(dataCar: dataCar)
}