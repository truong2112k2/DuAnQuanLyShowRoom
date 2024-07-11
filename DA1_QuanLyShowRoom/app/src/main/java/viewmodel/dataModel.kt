package viewmodel

import Firebase.AddNewAccountToFireBase
import Firebase.InteractWithFirebaseAcceptDate
import Firebase.InteractWithFirebaseDataCar
import Firebase.InteractWithFirebaseSetDate
import Firebase.InteractWithFirebaseUSER
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import data.dataCar
import data.dataDate

class dataModel : ViewModel() {
//    val ModelCarID = MutableLiveData<String>()
//    val carId: LiveData<String> get() = ModelCarID
//
//    val ModelCarImage = MutableLiveData<String>()
//    val carImage: MutableLiveData<String> get() = ModelCarImage
//
//
//    var ModelNameCar = MutableLiveData<String>()
//    val nameCar: MutableLiveData<String> get() = ModelNameCar
//
//
//    var ModelBrandCar = MutableLiveData<String>()
//    val brandCar: MutableLiveData<String> get() = ModelBrandCar
//
//
//    var ModelDateCar = MutableLiveData<String>()
//    val dateCar: MutableLiveData<String> get() = ModelDateCar
//
//    var ModelColorCar = MutableLiveData<String>()
//    val colorCar: MutableLiveData<String> get() = ModelColorCar
//
//    var ModelSumOfSeat = MutableLiveData<String>()
//    val sumofSeat: MutableLiveData<String> get() = ModelSumOfSeat
//
//
//    var ModelCapacity = MutableLiveData<Float>()
//    val capacityCar: MutableLiveData<Float> get() = ModelCapacity
//
//
//    var ModelPrice = MutableLiveData<Float>()
//    val priceCar: MutableLiveData<Float> get() = ModelPrice
//
//
//    var ModelCarSale = MutableLiveData<Boolean>()
//    val saleCar: MutableLiveData<Boolean> get() = ModelCarSale
//
//    val ModelPercent = MutableLiveData<Float>()
//    val percent: MutableLiveData<Float> get() = ModelPercent
//
//    var ModelDataCar = MutableLiveData<dataCar>()
//    val data: MutableLiveData<dataCar> get() = ModelDataCar
//
//
    val ModelUserNameAddMin = MutableLiveData<String>()
    val addMinName: LiveData<String> get() = ModelUserNameAddMin

    val ModelPassWordAddMin = MutableLiveData<String>()
    val passwordAddMin: LiveData<String> get() = ModelPassWordAddMin

    val pushDataOnFirebase = AddNewAccountToFireBase() // khởi tạo 1 biến để đẩy thông tin lên firebase
    var resultRegister = MutableLiveData<Boolean>() // tạo 1 biến có kiểu boolean để nhận thông tin trả về từ biến firebase

    private val firebaseCar = InteractWithFirebaseDataCar() // khởi tạo 1 biến để lấy thông tin từ firebase
    private val listDataCar = MutableLiveData<List<dataCar>>() // list chưa thông tin car được lấy từ firebase
    val listDataCarSortSmallToBig = MutableLiveData<List<dataCar>>() // list chưa thông tin car sắp xếp từ bé đến lớn được lấy từ firebase
    val resultImageLink = MutableLiveData<Uri>()
    var resultAddDataCar = MutableLiveData<Boolean>()
    var resultUpdateDataCar = MutableLiveData<Boolean>()
    var resultDeleteDataCar = MutableLiveData<Boolean>()



    val firebaseUSER = InteractWithFirebaseUSER() // biến tương tác với class firebase
    val resultCheckDataAccount = MutableLiveData<Boolean>() // biến trả về kq kiểm tra xem tài khoản có tồn tại trong firebase không
    val resultCheckDataAccount2 = MutableLiveData<Boolean>() // biến trả về kq kiểm tra xem tài khoản có tồn tại trong firebase không
    val resultGetIdAccount = MutableLiveData<String>() // biến chứa giá trị id trả về từ class firebae
    val resultGetEmailAccount = MutableLiveData<String>() // biến chứa giá trị email trả về từ class firebae
    val restltGetDateAccount = MutableLiveData<String>() // biến chứa giá trị date trả về từ class firebae
    val resutlUpdateAccount = MutableLiveData<Boolean>() // // biến trả về kq kiểm tra xem tài khoản có được cập nhật thành công hay không
    val resutlDeleteAccount = MutableLiveData<Boolean>() // // biến trả về kq kiểm tra xem tài khoản có được xóa thành công hay không
    val listGetDataDateFromAccount = MutableLiveData<ArrayList<dataDate>>()



    val firebaseSetDate = InteractWithFirebaseSetDate()
    val resultAddDate = MutableLiveData<Boolean>()
    val listDataDate = MutableLiveData<List<dataDate>>()
    val a_date = MutableLiveData<dataDate>()


    val firebaseAcceptDate = InteractWithFirebaseAcceptDate()
    private val listDataAcceptDate = MutableLiveData<List<dataDate>>()


   fun getDateAcceptDate(): LiveData<List<dataDate>>{
       firebaseAcceptDate.getDataAcceptDate {
           listDataAcceptDate.postValue(it)
       }
       return listDataAcceptDate
   }

//    fun getDataDate(): LiveData<List<dataDate>>{
//        firebaseSetDate.getDataDate { listData->
//            listDataDate.postValue(listData)
//        }
//        return listDataDate
//    }

     fun getAdate(id: String){
         firebaseSetDate.get_a_DataDate(id){
             a_date.value = it
         }
     }
    fun addDate(Uname:String, Uaddress:String, Uphone:String, meet_address: String, meet_date:String, meet_time:String, dataCar: dataCar, userId: String){
       firebaseSetDate.addDataDate(Uname,Uaddress,Uphone,meet_address,meet_date,meet_time,dataCar,userId){
           resultAddDate.value = it
       }

    }


    fun checkAccount(uName: String, uPass: String){ // kiểm tra xem account có tồn tại không
        firebaseUSER.checkDataAccount(uName, uPass){ result->
            resultCheckDataAccount.value = result

        }
    }
    fun getEmail(uName: String, Upass: String){ // lấy email của account
        firebaseUSER.getEmail(uName, Upass){
            resultGetEmailAccount.value = it
        }
    }
    fun getDate(uName: String, Upass: String){ // lấy date của account
        firebaseUSER.getDate(uName, Upass){
            restltGetDateAccount.value = it
        }
    }
    fun getId(uName: String, Upass: String){ // lấy id của account
        firebaseUSER.getID(uName, Upass ){
            resultGetIdAccount.value = it
        }
    }
    fun updateAccount(id: String, uName: String, uPass: String,uEmail: String, uDate: String){ // cập nhật thông tin account
        firebaseUSER.updateAccount(id, uName, uPass, uEmail, uDate){
            resutlUpdateAccount.value = it
        }
    }
    fun deleteAccount(id: String){
        firebaseUSER.deleteAccount(id){
            resutlDeleteAccount.value = it
        }
    }
    fun checkAccountbyNameandEmail(userName: String, Uemail: String, randomNumber: Int){
        firebaseUSER.checkDataAccountForUserNameAndGmail(userName, Uemail, randomNumber){
            resultCheckDataAccount2.value = it
        }

    }
   fun getDataDate(): LiveData<List<dataDate>>{
       firebaseSetDate.getDataDate { listData->
           listDataDate.postValue(listData)
       }
       return listDataDate
   }
    fun getDataCar(): LiveData<List<dataCar>> { // tạo 1 hàm trả về 1 list
        firebaseCar.getDataCar { returnList -> // gọi phương thức getUser trong CarFirebaseReponsitory
            // pthuc getUser này sẽ truy cập vào firebase và trả về 1 listData
            listDataCar.postValue(returnList) // xét listData vừa được trả về cho biến listDataCar mà bạn đã tạo ra trước đó
        }
        return listDataCar
    }
    fun updateDataCar(id: String, imageCar: String, nameCar: String, dateCar: String, carBrand: String, carColor: String, sumofSeats: String, capacity: String, carPrice: String, carSale: Boolean, carPercent: Float){
        firebaseCar.upDateCar( id, imageCar, nameCar,dateCar,carBrand,carColor,sumofSeats,capacity,carPrice,carSale, carPercent ){
            resultUpdateDataCar.value = it
        }
    }
    fun deleteDataCar(id: String){
        firebaseCar.deleteCar(id){
            resultDeleteDataCar.value = it
        }
    }

    fun getDataSortSmalltoBig():  LiveData<List<dataCar>>{
        firebaseCar.sortDataFromSmallToBig { returnList->
            listDataCarSortSmallToBig.postValue(returnList)
        }
        return listDataCarSortSmallToBig

    }
    fun pushDataCar(imageCar: String, nameCar: String, dateCar: String, carBrand: String, carColor: String, sumofSeats: String, capacity: String, carPrice: String){
           firebaseCar.pushDataCar(imageCar,nameCar,dateCar,carBrand,carColor,sumofSeats,capacity,carPrice){
               resultAddDataCar.value = it
           }
    }
    fun pushImageInFireBase(imageLink: Uri){
        firebaseCar.pushImageToFireStorage(imageLink){
            resultImageLink.value = it
        }
    }
    fun getDataDatefromAccount(userId: String): LiveData<ArrayList<dataDate>>{
        firebaseUSER.getDataDateFromAccount(userId){
            listGetDataDateFromAccount.postValue(it)
        }
        return listGetDataDateFromAccount

    }
    fun inputDataAccount(
        Uname: String,
        Pass: String,
        Email: String,
        Date:String
    ) { // tạo 1 hàm truyền các tham số vào
        pushDataOnFirebase.registerAccount(
            Uname,
            Pass,
            Email,
            Date
        ) { result -> // gọi hàm registerAccount
            // hàm registerAccount là hàm thực hiện đưa data lên firebase, đã được viết ở class AddNewAccountToFireBase
            // hàm registerAccount trả về 1 biến boolean, true khi thêm data thành công hoặc false khi thất bại
            resultRegister.value = result // ta sẽ gán  kết quá cho biến  resultRegister để theo dõi và quan sát việc thêm data
        }
    }

//
    fun compareAddminWithEditextContent(
        edtName: String,
        edtPass: String,
    ): Boolean { // hàm kiểm tra tài khoản admin
        val name = "admin123"
        val password = "admin123"
        ModelUserNameAddMin.value = name
        ModelPassWordAddMin.value = password
        if (addMinName.value == edtName && passwordAddMin.value == edtPass) {
            return true
        }
        return false
    }
//
//    fun setDataContentCar() { // hàm set nội dung cho ô tô
//        val id = carId.value
//        val name = nameCar.value
//        val image = carImage.value
//        val brand = brandCar.value
//        val date = dateCar.value
//        val color = colorCar.value
//        val seat = sumofSeat.value
//        val cap = capacityCar.value
//        val price = priceCar.value
//        val sale = saleCar.value
//        val percent = percent.value
//        val dataModel: dataCar = dataCar(
//            id,
//            image,
//            name,
//            date,
//            brand,
//            color,
//            seat,
//            cap.toString(),
//            price.toString(),
//            sale!!,
//            percent
//        )
//        ModelDataCar.value = dataModel
//
//    }
//
//
//    fun setContent(String1: MutableLiveData<String>, String2: String) { // set nội dung cho String
//        String1.value = String2
//    }
//
//    fun setContentBoolean(
//        String1: MutableLiveData<Boolean>,
//        String2: Boolean,
//    ) {// set nội dung cho Boolean
//        String1.value = String2
//    }
//
//    fun setContentNumber(number: MutableLiveData<Float>, number2: Float) {// set nội dung cho Float
//        number.value = number2
//    }
//
//
//    fun resolveCapacityCCtoLit(value: Float) {// hàm chuyển giá trị của Capacity từ CC sang Lit
//        val newValue = (value / 1000)
//        ModelCapacity.value = newValue
//    }
//
//    fun resolveCapacityLittoCC(value: Float) {// hàm chuyển giá trị của Capacity từ Lit sang CC
//        val newValue = value * 1000
//        ModelCapacity.value = newValue
//    }
//

}