package cn.huangchengxi.kotlintest.customedviews

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.huangchengxi.kotlintest.R

class DrawerItemView(context: Context,attrs:AttributeSet?,defStyle:Int,defRes:Int):LinearLayout(context, attrs,defStyle,defRes) {
    constructor(context: Context,attrs: AttributeSet?,defStyle: Int):this(context,attrs,defStyle,0)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null)

    init {
        View.inflate(context, R.layout.view_drawer_item,this)
        val array=context.obtainStyledAttributes(attrs,R.styleable.DrawerItemView)
        val res=array.getResourceId(R.styleable.DrawerItemView_icon,R.drawable.close)
        val text=array.getString(R.styleable.DrawerItemView_text)
        array.recycle()
        val textview=findViewById<TextView>(R.id.text)
        val icon=findViewById<ImageView>(R.id.icon)
        val body=findViewById<FrameLayout>(R.id.body)
        textview.text=SpannableStringBuilder(text)
        icon.setImageResource(res)
    }
}