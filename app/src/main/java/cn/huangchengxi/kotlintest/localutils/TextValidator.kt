package cn.huangchengxi.kotlintest.localutils

import java.util.regex.Pattern

class TextValidator {
    companion object{
        fun checkUid(uid:String?):Boolean=when{
            uid==null->false
            else->{
                val pattern1=Pattern.compile("^[0-9]+$")
                val matcher1=pattern1.matcher(uid)
                matcher1.find()
            }
        }
        fun checkPhoneNumber(number:String?):Boolean=when{
            number==null->false
            else->{
                val pattern=Pattern.compile("^1[3|4|5|7|8][0-9]\\d{4,8}$")
                val matcher=pattern.matcher(number)
                matcher.find()
            }
        }
        fun checkPassword(password:String?)=when{
            password==null->false
            else->{
                val pattern=Pattern.compile("^[0-9a-zA-Z]{10,20}$")
                val matcher=pattern.matcher(password)
                matcher.find()
            }
        }
        fun checkNickname(nickname:String):Boolean=nickname.isNotEmpty()
    }
}