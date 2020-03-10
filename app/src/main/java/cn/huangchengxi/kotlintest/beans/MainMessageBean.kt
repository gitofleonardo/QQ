package cn.huangchengxi.kotlintest.beans

import java.text.SimpleDateFormat
import java.util.*

data class MainMessageBean(var portraitUrl:String,var name:String,var uid:String,var content:String,var time:Long,var isTop:Boolean){
    fun getFormattedDay():String{
        val simpleDateTime=SimpleDateFormat("yyyy-MM-dd")
        return simpleDateTime.format(Date(time))
    }
}