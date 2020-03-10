package cn.huangchengxi.kotlintest.localutils

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import cn.huangchengxi.kotlintest.beans.LoginAccountBean
import cn.huangchengxi.kotlintest.databean.LocalUserDBBean
import java.lang.Exception

class SqliteHelper :SQLiteOpenHelper{
    private val sql="create table users(uid text unique,password text,imgSrc text)"

    constructor(context: Context,name:String,factory:SQLiteDatabase.CursorFactory?,version:Int):super(context,name,factory,version)

    override fun onCreate(p0: SQLiteDatabase?) {
        initDatabase(p0)
    }
    private fun initDatabase(db:SQLiteDatabase?){
        db?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    companion object{
        fun deleteUser(context: Context,uid:String){
            val helper=SqliteHelper(context,"users",null,1)
            val db=helper.writableDatabase
            db.execSQL("delete from users where uid=$uid")
        }
        fun queryUser(context: Context):LocalUserDBBean?{
            val helper=SqliteHelper(context,"users",null,1)
            val db=helper.writableDatabase
            val cursor=db.query(false,"users",null,null,null,null,null,null,null)
            return when (cursor.moveToFirst()){
                true-> buildLocalUserBean(cursor)
                false->null
            }
        }
        fun queryUser(context: Context,uid: String):LocalUserDBBean?{
            val helper=SqliteHelper(context,"users",null,1)
            val db=helper.writableDatabase
            val cursor=db.query(false,"users",null, "uid=?", arrayOf(uid),null,null,null,null)
            return when (cursor.moveToFirst()){
                true-> buildLocalUserBean(cursor)
                false->null
            }
        }
        fun queryUsers(context: Context):ArrayList<LoginAccountBean>{
            val helper=SqliteHelper(context,"users",null,1)
            val db=helper.writableDatabase
            val cursor=db.query(false,"users",null,null,null,null,null,null,null)
            return if (cursor.moveToFirst()) buildLocalUserBeanList(cursor) else ArrayList()
        }
        fun updateUser(context: Context,uid:String,password:String){
            try {
                val helper=SqliteHelper(context,"users",null,1)
                val db=helper.writableDatabase
                val cursor=db.query("users",null,null,null,null,null,null,null)
                if (cursor.moveToFirst()){
                    db.execSQL("update users set password=\"$password\" where uid=\"$uid\"")
                }else{
                    db.execSQL("insert into users values(\"$uid\",\"$password\",null)")
                }
            }catch (e:Exception){
                Log.e("update",e.toString())
            }
        }
        private fun buildLocalUserBeanList(cursor: Cursor):ArrayList<LoginAccountBean>{
            val list=ArrayList<LoginAccountBean>()
            do{
                val uid=cursor.getString(cursor.getColumnIndex("uid"))
                val password=cursor.getString(cursor.getColumnIndex("password"))
                val imgSrc:String?=cursor.getString(cursor.getColumnIndex("imgSrc"))
                list.add(LoginAccountBean(imgSrc?:"null",uid,password))
                Log.e("get one","1")
            }while (cursor.moveToNext())
            return list
        }
        private fun buildLocalUserBean(cursor: Cursor):LocalUserDBBean?{
            val uid=cursor.getString(cursor.getColumnIndex("uid"))
            val password=cursor.getString(cursor.getColumnIndex("password"))
            return LocalUserDBBean(uid,password)
        }
    }
}