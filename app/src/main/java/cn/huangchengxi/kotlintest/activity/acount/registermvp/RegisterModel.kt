package cn.huangchengxi.kotlintest.activity.acount.registermvp

import cn.huangchengxi.kotlintest.databean.RegisterConfigBean

class RegisterModel {
    interface CommitCallback{
        fun onRegisterSuccess()
        fun onAccountAlreadyExist()
        fun onNetworkUnavailable()
    }
    fun commit(configBean: RegisterConfigBean,commitCallback: CommitCallback){
        //do register process
        //success
        commitCallback.onRegisterSuccess()
    }
}