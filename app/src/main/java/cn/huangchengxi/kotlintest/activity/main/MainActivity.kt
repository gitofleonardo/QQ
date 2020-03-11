package cn.huangchengxi.kotlintest.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.localutils.DisplayUtils
import com.google.android.material.navigation.NavigationView
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private val drawer by lazy { findViewById<DrawerLayout>(R.id.main_drawer) }
    private val drawerNavigation by lazy { findViewById<NavigationView>(R.id.home_navigation) }
    private val mainView by lazy { findViewById<LinearLayout>(R.id.home_main_view) }
    private val leftTopPortrait by lazy { findViewById<ImageView>(R.id.my_portrait) }
    @Volatile
    private var opening=false
    private val simple=object : GestureDetector.SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            //Log.e("action","$distanceX $distanceY")
            if (!opening && !drawer.isDrawerOpen(GravityCompat.START)){
                opening=true
                Log.e("open drawer","open1")
                drawer.openDrawer(GravityCompat.START)
            }
            return true
        }
    }
    private val gestureDetector by lazy { GestureDetector(this,simple) }

    private var lastX=0f
    private var lastY=0f
    private var gestureConsume=false
    private var activityConsume=false

    private val drawerListener=object : DrawerLayout.DrawerListener{
        override fun onDrawerStateChanged(newState: Int) {
            if (DrawerLayout.STATE_IDLE==newState)
                opening=false
        }
        override fun onDrawerOpened(drawerView: View) {
            opening=false
        }
        override fun onDrawerClosed(drawerView: View) {
            opening=false
        }
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
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_DOWN->{
                lastX=ev.x
                lastY=ev.y
            }
            MotionEvent.ACTION_MOVE->{
                val stz= lastX-ev.x<0
                val dx= abs(lastX-ev.x)
                val dy=abs(lastY-ev.y)
                lastX=ev.x
                lastY=ev.y
                if (!activityConsume && !gestureConsume){
                    if (dx==dy){
                        return super.dispatchTouchEvent(ev)
                    }
                    if (stz && dx>dy && !drawer.isDrawerOpen(GravityCompat.START)){
                        gestureConsume=true
                        gestureDetector.onTouchEvent(ev)
                    }else{
                        activityConsume=true
                        return super.dispatchTouchEvent(ev)
                    }
                }else if (activityConsume){
                    return super.dispatchTouchEvent(ev)
                }else{
                    if (!opening)
                        return gestureDetector.onTouchEvent(ev)
                }
            }
            MotionEvent.ACTION_UP->{
                gestureConsume=false
                activityConsume=false
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
            return
        }
        super.onBackPressed()
    }
}
