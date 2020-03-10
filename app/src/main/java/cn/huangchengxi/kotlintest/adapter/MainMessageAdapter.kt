package cn.huangchengxi.kotlintest.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.beans.MainMessageBean
import cn.huangchengxi.kotlintest.holders.MainMessageHolder

class MainMessageAdapter(private val messages:ArrayList<MainMessageBean>):RecyclerView.Adapter<MainMessageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMessageHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.view_main_message_item,parent,false)
        return MainMessageHolder(view)
    }

    override fun onBindViewHolder(holder: MainMessageHolder, position: Int) {
        val item=messages[position]
        holder.name.text=SpannableStringBuilder(item.name)
        holder.content.text=SpannableStringBuilder(item.content)
        holder.time.text=SpannableStringBuilder(item.getFormattedDay())
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}