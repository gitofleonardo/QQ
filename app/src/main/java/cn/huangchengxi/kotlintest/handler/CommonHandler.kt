package cn.huangchengxi.kotlintest.handler

import android.os.Handler
import android.os.Message
import android.util.Log
import java.lang.Exception
import java.lang.ref.WeakReference

class CommonHandler(handler:MessageHandler): Handler() {
    private val mHandler:WeakReference<MessageHandler> by lazy { WeakReference<MessageHandler>(handler) }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        try {
            mHandler.get()?.onHandleMessage(msg)
        }catch (e:Exception){
            Log.e("error",e.toString())
            mHandler.get()?.onHandleError(e)
        }
    }
    interface MessageHandler{
        fun onHandleMessage(msg:Message)
        fun onHandleError(e:Exception)
    }
}