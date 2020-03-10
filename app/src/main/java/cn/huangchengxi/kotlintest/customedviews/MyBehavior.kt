package cn.huangchengxi.kotlintest.customedviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout

class MyBehavior(context: Context,attrs:AttributeSet?) : AppBarLayout.Behavior(context,attrs) {
    private var lastX = 0f
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        //super.onNestedPreScroll(coordinatorLayout, child, target, dx, (dy*0.5).toInt(), consumed, type)
        child.translationY=(dy*0.5).toFloat()
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (velocityY<100){
            return false
        }else{
            return true
        }
        //return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }
}