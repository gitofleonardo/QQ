package cn.huangchengxi.kotlintest.customedviews

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import cn.huangchengxi.kotlintest.R

class CodeValidatorView(context: Context,attrs:AttributeSet?,defStyle:Int,defRes:Int):LinearLayout(context, attrs,defStyle,defRes) {
    private val editList=ArrayList<EditText>()
    private var onFinishTyped:((String)->Unit)?=null
    private var index=0
    private val imm by lazy { context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    constructor(context: Context,attrs: AttributeSet?,defStyle: Int):this(context,attrs,defStyle,0)
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null)

    init {
        init(context, attrs)
    }
    private fun init(context: Context,attrs: AttributeSet?){
        val view=View.inflate(context, R.layout.view_code_validator_input,this)

        val e1=view.findViewById<EditText>(R.id.e1)
        val e2=view.findViewById<EditText>(R.id.e2)
        val e3=view.findViewById<EditText>(R.id.e3)
        val e4=view.findViewById<EditText>(R.id.e4)
        val e5=view.findViewById<EditText>(R.id.e5)
        val e6=view.findViewById<EditText>(R.id.e6)

        editList.add(e1)
        editList.add(e2)
        editList.add(e3)
        editList.add(e4)
        editList.add(e5)
        editList.add(e6)

        val watcher=object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                if (index+1<editList.size){
                    editList[index+1].isEnabled=true
                    editList[index+1].requestFocus()
                    editList[index++].isEnabled=false
                }else{
                    if (onFinishTyped!=null){
                        var result=""
                        for (i in 0 until editList.size){
                            result+=editList[i].text.toString()
                        }
                        onFinishTyped?.invoke(result)
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        }
        val listener=object : OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode==KeyEvent.KEYCODE_DEL && event?.action==KeyEvent.ACTION_DOWN){
                    if (index!=0){
                        editList[index-1].isEnabled=true
                        editList[index-1].requestFocus()
                        editList[index-1].removeTextChangedListener(watcher)
                        editList[index-1].setText("")
                        editList[index-1].addTextChangedListener(watcher)
                        editList[index].isEnabled=false
                        index--
                        return true
                    }
                }
                return false
            }
        }
        e1.setOnKeyListener(listener)
        e2.setOnKeyListener(listener)
        e3.setOnKeyListener(listener)
        e4.setOnKeyListener(listener)
        e5.setOnKeyListener(listener)
        e6.setOnKeyListener(listener)

        e1.addTextChangedListener(watcher)
        e2.addTextChangedListener(watcher)
        e3.addTextChangedListener(watcher)
        e4.addTextChangedListener(watcher)
        e5.addTextChangedListener(watcher)
        e6.addTextChangedListener(watcher)
        imm.showSoftInput(e1,0)
        e1.requestFocus()
        imm.showSoftInput(e1,0)
    }
    fun setOnFinishListener(onFinishListener:(String)->Unit){
        this.onFinishTyped=onFinishListener
    }
    fun showSoftInput(){
        imm.showSoftInput(editList[index],0)
    }
}