package cn.huangchengxi.kotlintest.customedviews

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cn.huangchengxi.kotlintest.R

class DrawerBottomItemView(context: Context,attrs:AttributeSet?,defStyle:Int,defRes:Int):LinearLayout(context, attrs,defStyle,defRes) {
    private var onClickListener:(()->Unit)?=null
    private var imgView:ImageView?=null
    private var textView:TextView?=null
    private var nameView:TextView?=null

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int):this(context,attrs,defStyle,0)
    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null)

    init {
        View.inflate(context, R.layout.view_drawer_bottom_item,this)
        val array=context.obtainStyledAttributes(attrs,R.styleable.DrawerBottomItemView)
        val isImg=array.getBoolean(R.styleable.DrawerBottomItemView_img,true)
        val reference=array.getResourceId(R.styleable.DrawerBottomItemView_iicon,R.drawable.close)
        val text=array.getString(R.styleable.DrawerBottomItemView_text_description)
        val name=array.getString(R.styleable.DrawerBottomItemView_name)

        imgView=findViewById<ImageView>(R.id.img_item)
        textView=findViewById<TextView>(R.id.text_item)
        nameView=findViewById<TextView>(R.id.name)

        if (isImg){
            imgView?.visibility=View.VISIBLE
            imgView?.setImageResource(reference)
            textView?.visibility=View.GONE
        }else{
            imgView?.visibility=View.GONE
            textView?.visibility=View.VISIBLE
            textView?.text=SpannableStringBuilder(text)
        }
        nameView?.text=SpannableStringBuilder(name)
    }
    fun setText(text:String){
        textView?.text=SpannableStringBuilder(text)
    }
    fun setName(name:String){
        nameView?.text=SpannableStringBuilder(name)
    }
    fun setOnClickListener(listener:()->Unit){
        this.onClickListener=listener
    }
}