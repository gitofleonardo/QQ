package cn.huangchengxi.kotlintest.activity.acount.registermvp

import cn.huangchengxi.kotlintest.databean.RegisterConfigBean

interface RegisterView {
    fun onRegisterSuccess()
    fun onAccountAlreadyExist()
    fun onNetworkUnavailable()
    fun onErrorMessage()
    fun getUserConfig():RegisterConfigBean
}