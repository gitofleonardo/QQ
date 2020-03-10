package cn.huangchengxi.kotlintest.activity.acount.registermvp.registerfragment


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText

import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.activity.acount.registermvp.RegisterPresenter
import cn.huangchengxi.kotlintest.localutils.TextValidator
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class RegisterPageThreeFragment(interaction: Interaction,presenter: RegisterPresenter) : Fragment() {
    private val interaction=interaction
    private val presenter=presenter

    private var loginButton: Button?=null
    private var nickname:EditText?=null
    private var password:EditText?=null

    private val imm by lazy { context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_register_page_three, container, false)
        loginButton=view.findViewById(R.id.reg_and_login)
        nickname=view.findViewById(R.id.nickname)
        password=view.findViewById(R.id.password)
        val watcher=object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                val nicknameStr=nickname?.text.toString()
                val passwordStr=password?.text.toString()
                if (TextValidator.checkNickname(nicknameStr) && TextValidator.checkPassword(passwordStr)){
                    //interaction.success(passwordStr)
                    loginButton?.isEnabled=true
                    loginButton?.isClickable=true
                }else{
                    loginButton?.isEnabled=false
                    loginButton?.isClickable=false
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        }
        loginButton?.setOnClickListener {
            interaction.success(password?.text.toString())
        }
        nickname?.addTextChangedListener(watcher)
        password?.addTextChangedListener(watcher)
        nickname?.requestFocus()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imm.showSoftInput(nickname,0)
    }
    interface Interaction{
        fun success(password:String)
        fun networkError()
    }

}
