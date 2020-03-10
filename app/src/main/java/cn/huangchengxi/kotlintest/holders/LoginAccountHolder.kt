package cn.huangchengxi.kotlintest.holders

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.huangchengxi.kotlintest.R

class LoginAccountHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
    val portraitImg=itemView.findViewById<ImageView>(R.id.portrait)
    val account=itemView.findViewById<TextView>(R.id.account)
    val deleteBtn=itemView.findViewById<ImageView>(R.id.delete_account)
    val body=itemView.findViewById<FrameLayout>(R.id.body)
}