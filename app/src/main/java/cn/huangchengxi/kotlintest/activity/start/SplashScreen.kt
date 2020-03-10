package cn.huangchengxi.kotlintest.activity.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.activity.acount.loginmvp.LoginActivity
import cn.huangchengxi.kotlintest.localutils.SqliteHelper
import com.bumptech.glide.Glide

class SplashScreen : AppCompatActivity(){
    private val splashImg by lazy { findViewById<ImageView>(R.id.splash_img) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
        init()
        jumpSwitch()
    }
    private fun init(){
        Glide.with(this).load(R.drawable.qq_splash).into(splashImg)
    }
    private fun jumpSwitch(){
        val user=SqliteHelper.queryUser(this)
        if (user==null){
            LoginActivity.startLoginActivity(this)
        }else{
            LoginActivity.startLoginActivity(this,user.name,user.password)
        }
        finish()
    }
}
