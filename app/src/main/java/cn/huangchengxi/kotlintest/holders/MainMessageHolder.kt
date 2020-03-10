package cn.huangchengxi.kotlintest.holders

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cn.huangchengxi.kotlintest.R
import org.w3c.dom.Text

class MainMessageHolder(view:View):RecyclerView.ViewHolder(view) {
    val parent=view.findViewById<LinearLayout>(R.id.parent)
    val portrait=view.findViewById<ImageView>(R.id.portrait)
    val name=view.findViewById<TextView>(R.id.name)
    val content=view.findViewById<TextView>(R.id.content)
    val time=view.findViewById<TextView>(R.id.time)
}