package cn.huangchengxi.kotlintest.adapter

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.beans.LoginAccountBean
import cn.huangchengxi.kotlintest.holders.LoginAccountHolder

class LoginAccountAdapter(private val accounts:ArrayList<LoginAccountBean>):RecyclerView.Adapter<LoginAccountHolder>() {
    private var deleteListener:((Int)->Unit)?=null
    private var clickListener:((Int)->Unit)?=null

    override fun getItemCount(): Int {
        return accounts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginAccountHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_login_account,parent,false)
        return LoginAccountHolder(view)
    }

    override fun onBindViewHolder(holder: LoginAccountHolder, position: Int) {
        val (portrait,account,password)=accounts[position]
        holder.account.text=SpannableStringBuilder(account)
        holder.deleteBtn.setOnClickListener {
            deleteListener?.invoke(position)
        }
        holder.body.setOnClickListener {
            clickListener?.invoke(position)
        }
    }
    fun setOnClickListener(arg:(Int)->Unit){
        this.clickListener=arg
    }
    fun setOnDeleteListener(arg: (Int) -> Unit){
        this.deleteListener=arg
    }
}