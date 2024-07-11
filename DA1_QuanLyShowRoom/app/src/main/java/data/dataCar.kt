package data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class dataCar(var carID: String? = null, // 0
              var imageCar: String?= null, // pick
              var nameCar: String? = null, // edit_text
              var birdYear: String? = null, // datepicker
              var carBrand: String? = null, // edit_text
              var carColor: String? = null, //
              var totalNumberOfSeats: String? = null, // edit_text
              var capacity: String? = null, // edittext
              var carPrice: String? = null, // editext
              var carSale: Boolean = false,
              var percentSale: Float? = 0.0f): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Float::class.java.classLoader) as? Float
    ) {
    }

    override fun describeContents(): Int {
        return  0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(p0: Parcel, p1: Int) {
      p0.writeString(carID)
      p0.writeString(imageCar)
      p0.writeString(nameCar)
      p0.writeString(birdYear)
      p0.writeString(carBrand)
      p0.writeString(carColor)
      p0.writeString(totalNumberOfSeats)
      p0.writeString(capacity)
      p0.writeString(carPrice)
     p0.writeByte(if(carSale) 1 else 0 )
      p0.writeValue(percentSale)
    }

    companion object CREATOR : Parcelable.Creator<dataCar> {
        override fun createFromParcel(parcel: Parcel): dataCar {
            return dataCar(parcel)
        }

        override fun newArray(size: Int): Array<dataCar?> {
            return arrayOfNulls(size)
        }
    }
}