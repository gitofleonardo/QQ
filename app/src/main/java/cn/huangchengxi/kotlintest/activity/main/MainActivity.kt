package cn.huangchengxi.kotlintest.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.localutils.DisplayUtils
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private val drawer by lazy { findViewById<DrawerLayout>(R.id.main_drawer) }
    private val drawerNavigation by lazy { findViewById<NavigationView>(R.id.home_navigation) }
    private val mainView by lazy { findViewById<LinearLayout>(R.id.home_main_view) }
    private val leftTopPortrait by lazy { findViewById<ImageView>(R.id.my_portrait) }

    private val drawerListener=object : DrawerLayout.DrawerListener{
        override fun onDrawerStateChanged(newState: Int) {}
        override fun onDrawerOpened(drawerView: View) {}
        override fun onDrawerClosed(drawerView: View) {}
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            val translationX=mainView.measuredWidth*slideOffset
            mainView.translationX=translationX
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DisplayUtils.transparentStatusBar(this)
        initWidget()
    }
    private fun initWidget(){
        drawer.addDrawerListener(drawerListener)
        val param=drawerNavigation.layoutParams
        param.width=resources.displayMetrics.widthPixels
        drawerNavigation.layoutParams=param
        leftTopPortrait.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }
    }

}
