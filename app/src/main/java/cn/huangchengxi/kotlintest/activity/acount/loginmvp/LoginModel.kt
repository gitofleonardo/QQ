package cn.huangchengxi.kotlintest.activity.acount.loginmvp

import android.os.Message
import android.util.Log
import cn.huangchengxi.kotlintest.handler.CommonHandler
import java.lang.Exception

class LoginModel:CommonHandler.MessageHandler{
    private val commonHandler=cn.huangchengxi.kotlintest.handler.CommonHandler(this)
    private var t:Thread?=null
    private var basicLoginResultCallback:BasicLoginResultCallback?=null

    fun login(uid:String,password:String,basicLoginResultCallback: BasicLoginResultCallback){
        //network processing
        this.basicLoginResultCallback=basicLoginResultCallback
        t=Thread{
            try {
                Thread.sleep(1000)
                val msg=commonHandler.obtainMessage()
                msg.what=1
                commonHandler.sendMessage(msg)
            }catch (e:Exception){
                Log.e("cancel login",e.toString())
            }
        }
        t?.start()
    }
    fun cancel(){
        t?.interrupt()
    }
    override fun onHandleMessage(msg: Message) {
        basicLoginResultCallback?.onSuccess()
    }
    override fun onHandleError(e: Exception) {

    }
}