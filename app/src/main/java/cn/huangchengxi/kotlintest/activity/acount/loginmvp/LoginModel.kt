package cn.huangchengxi.kotlintest.activity.acount.loginmvp

class LoginModel {
    fun login(uid:String,password:String,basicLoginResultCallback: BasicLoginResultCallback){
        //network processing
        basicLoginResultCallback.onSuccess()
    }
}