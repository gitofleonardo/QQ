package cn.huangchengxi.kotlintest.activity.acount.registermvp.registerfragment


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener

import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.activity.acount.registermvp.RegisterPresenter
import cn.huangchengxi.kotlintest.localutils.TextValidator

/**
 * A simple [Fragment] subclass.
 */
class RegisterPageOneFragment(interaction: Interaction,presenter: RegisterPresenter) : Fragment() {
    private val interaction=interaction
    private val presenter=presenter
    private var nextButton:Button?=null
    private var accountNumber:EditText?=null
    private var switchArea:LinearLayout?=null
    private var areaCode:TextView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_register_page_one, container, false)
        nextButton=view.findViewById(R.id.next_button)
        nextButton?.setOnClickListener {
            interaction.success(accountNumber?.text.toString())
        }
        accountNumber=view.findViewById(R.id.register_phone_number)
        accountNumber?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val number=s?.toString()?:""
                if (TextValidator.checkPhoneNumber(number)){
                    nextButton?.isEnabled=true
                    nextButton?.isClickable=true
                }else{
                    nextButton?.isEnabled=false
                    nextButton?.isClickable=false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        switchArea=view.findViewById(R.id.switch_area)
        switchArea?.setOnClickListener {

        }
        areaCode=view.findViewById(R.id.area_code)
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
    interface Interaction{
        fun success(number:String)
        fun networkError()
    }
}
