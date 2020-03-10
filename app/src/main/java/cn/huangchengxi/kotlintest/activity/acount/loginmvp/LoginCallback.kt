package cn.huangchengxi.kotlintest.activity.acount.loginmvp

import cn.huangchengxi.kotlintest.databean.LocalUserDBBean

interface LoginCallback {
    fun getUser():LocalUserDBBean?
    fun onLoginSuccess()
    fun onLoginFailed()
    fun onFormatError()
}