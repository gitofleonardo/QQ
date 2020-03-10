package cn.huangchengxi.kotlintest.activity.acount.registermvp

import android.os.Message
import cn.huangchengxi.kotlintest.handler.CommonHandler
import java.lang.Exception

class RegisterPresenter(registerView: RegisterView):CommonHandler.MessageHandler {
    private val view=registerView
    private val model=RegisterModel()
    private val handler=CommonHandler(this)

    companion object{
        val REGISTER_SUCCESS=0
        val NETWORK_NOT_AVAILABLE=1
        val ACCOUNT_ALREADY_EXIST=2
    }

    fun register(){
        val bean=view.getUserConfig()
        model.commit(bean,object :RegisterModel.CommitCallback{
            override fun onRegisterSuccess() {
                sendMessage(0,null,null,null)
            }
            override fun onAccountAlreadyExist() {
                sendMessage(2,null,null,null)
            }
            override fun onNetworkUnavailable() {
                sendMessage(1,null,null,null)
            }
        })
    }

    override fun onHandleMessage(msg: Message) {
        when(msg.what){
            0->view.onRegisterSuccess()
            1->view.onNetworkUnavailable()
            2->view.onAccountAlreadyExist()
            else->view.onErrorMessage()
        }
    }

    override fun onHandleError(e: Exception) {
        view.onErrorMessage()
    }
    fun sendMessage(what:Int,arg1:Int?,arg2:Int?,obj:Any?){
        val msg=Message()
        msg.what=what
        msg.arg1=arg1?:0
        msg.arg2=arg2?:0
        msg.obj=obj?:Any()
        handler.sendMessage(msg)
    }
}