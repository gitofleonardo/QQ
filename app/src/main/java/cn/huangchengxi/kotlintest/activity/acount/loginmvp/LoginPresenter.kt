package cn.huangchengxi.kotlintest.activity.acount.loginmvp

class LoginPresenter (callback:LoginCallback){
    private val model=LoginModel()
    private val callback=callback
    fun login(){
        val user=callback.getUser()
        if (user==null){
            callback.onFormatError()
            return
        }

        val basicLoginResultCallback=object : BasicLoginResultCallback{
            override fun onSuccess() {
                callback.onLoginSuccess()
            }
            override fun onFailed() {
                callback.onLoginFailed()
            }
        }
        model.login(user!!.name,user!!.password,basicLoginResultCallback)
    }
}