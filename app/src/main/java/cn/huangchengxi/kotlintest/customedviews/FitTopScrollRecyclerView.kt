package cn.huangchengxi.kotlintest.customedviews

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FitTopScrollRecyclerView(context:Context,attrs:AttributeSet?,defStyle:Int):RecyclerView(context, attrs,defStyle){
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null)

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        val manager=layoutManager as LinearLayoutManager
        val firstPos=manager.findFirstVisibleItemPosition()
        return if (firstPos==0){
            false
        }else{
            super.onTouchEvent(e)
        }
    }
}