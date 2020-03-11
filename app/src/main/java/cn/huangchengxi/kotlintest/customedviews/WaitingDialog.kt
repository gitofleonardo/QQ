package cn.huangchengxi.kotlintest.customedviews

import android.app.AlertDialog
import android.content.Context
import android.text.SpannableStringBuilder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import cn.huangchengxi.kotlintest.R

class WaitingDialog(context: Context):AlertDialog(context){
    private val contentView:View =View.inflate(context,R.layout.view_loading_dialog,null)
    private val titleView:TextView by lazy { contentView.findViewById<TextView>(R.id.title) }
    var title="正在加载"
    set(str:String){
        titleView.text=SpannableStringBuilder(str)
        field = str
    }
    var cancelListener:(()->Unit)?=null
    set(value) {
        setOnCancelListener {
            cancelListener?.invoke()
        }
        field = value
    }

    init {
        setView(contentView)
        setCancelable(false)

        val dialogWindow=window
        val p=dialogWindow!!.attributes
        p.gravity=Gravity.TOP
        p.alpha=0.5f

        dialogWindow.attributes=p
    }

    override fun show() {
        super.show()
        window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
    }
}