package cn.huangchengxi.kotlintest.activity.acount.registermvp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.activity.acount.registermvp.registerfragment.RegisterPageOneFragment
import cn.huangchengxi.kotlintest.activity.acount.registermvp.registerfragment.RegisterPageThreeFragment
import cn.huangchengxi.kotlintest.activity.acount.registermvp.registerfragment.RegisterPageTwoFragment
import cn.huangchengxi.kotlintest.databean.RegisterConfigBean

class RegActivity : AppCompatActivity(),RegisterView{
    private val presenter=RegisterPresenter(this)
    private val close by lazy{findViewById<FrameLayout>(R.id.back)}
    private val progressBar by lazy{findViewById<ProgressBar>(R.id.progress)}

    private var account:String?=null
    private var password:String?=null

    private val interaction1=object : RegisterPageOneFragment.Interaction{
        override fun success(number:String) {
            account=number
            supportFragmentManager.beginTransaction().replace(R.id.register_fragment,RegisterPageTwoFragment(interaction2,presenter)).commitAllowingStateLoss()
            progressBar.progress=66
        }
        override fun networkError() {
            Toast.makeText(this@RegActivity,"网络错误",Toast.LENGTH_SHORT).show()
        }
    }
    private val interaction2=object : RegisterPageTwoFragment.Interaction{
        override fun success() {
            supportFragmentManager.beginTransaction().replace(R.id.register_fragment,RegisterPageThreeFragment(interaction3,presenter)).commitAllowingStateLoss()
            progressBar.progress=100
        }
        override fun networkError() {
            Toast.makeText(this@RegActivity,"网络错误",Toast.LENGTH_SHORT).show()
        }
    }
    private val interaction3=object : RegisterPageThreeFragment.Interaction{
        override fun success(password:String) {
            this@RegActivity.password=password
            Toast.makeText(this@RegActivity,"注册成功",Toast.LENGTH_SHORT).show()
            val data= Intent()
            data.putExtra("account",account)
            data.putExtra("password",password)
            setResult(1,data)
            finish()
        }
        override fun networkError() {
            Toast.makeText(this@RegActivity,"网络错误",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_reg)
        init()
    }
    private fun init(){
        close.setOnClickListener { finish() }
        supportFragmentManager.beginTransaction().add(R.id.register_fragment,RegisterPageOneFragment(interaction1,presenter)).commitAllowingStateLoss()
    }
    override fun onRegisterSuccess() {
        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show()
        finish()
    }
    override fun onAccountAlreadyExist() {
        Toast.makeText(this,"账户已存在",Toast.LENGTH_SHORT).show()
    }

    override fun onNetworkUnavailable() {
        Toast.makeText(this,"网络不可用",Toast.LENGTH_SHORT).show()
    }

    override fun onErrorMessage() {
        Toast.makeText(this,"未知错误",Toast.LENGTH_SHORT).show()
    }

    override fun getUserConfig(): RegisterConfigBean {
        val number="971840889"
        val password="971840889"
        return RegisterConfigBean(number, password)
    }
}
