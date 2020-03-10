package cn.huangchengxi.kotlintest.customedviews

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.localutils.DisplayUtils

class QQLevelView(context: Context,attrs:AttributeSet?,defStyle:Int,defRes:Int):LinearLayout(context, attrs,defStyle,defRes) {
    private var defaultLevel=1
    private var sunCount=0
    private var moonCount=0
    private var starCount=0

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int):this(context,attrs,defStyle,0)
    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null)

    init {
        View.inflate(context,R.layout.view_qq_level,this)
        orientation=HORIZONTAL
        val array=context.obtainStyledAttributes(attrs,R.styleable.QQLevelView)
        defaultLevel=array.getInt(R.styleable.QQLevelView_qq_level,1)
        countLevelAndSet()
    }
    private fun countLevelAndSet(){
        sunCount=defaultLevel/16
        moonCount=(defaultLevel%16)/4
        starCount=(defaultLevel%16)%4

        for (i in 1..sunCount){
            val image=ImageView(context)
            val param=ViewGroup.LayoutParams(DisplayUtils.fromDp2Px(context,20),ViewGroup.LayoutParams.MATCH_PARENT)
            image.setImageResource(R.drawable.level_sun)
            image.layoutParams=param
            addView(image)
        }
        for (i in 1..moonCount){
            val image=ImageView(context)
            val param=ViewGroup.LayoutParams(DisplayUtils.fromDp2Px(context,20),ViewGroup.LayoutParams.MATCH_PARENT)
            image.setImageResource(R.drawable.level_moon)
            image.layoutParams=param
            addView(image)
        }
        for (i in 1..starCount){
            val image=ImageView(context)
            val param=ViewGroup.LayoutParams(DisplayUtils.fromDp2Px(context,20),ViewGroup.LayoutParams.MATCH_PARENT)
            image.setImageResource(R.drawable.level_star)
            image.layoutParams=param
            addView(image)
        }
    }
    fun setLevel(level:Int){
        this.defaultLevel=level
        countLevelAndSet()
    }
}