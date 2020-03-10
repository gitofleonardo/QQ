package cn.huangchengxi.kotlintest.activity.acount.registermvp.registerfragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.activity.acount.registermvp.RegisterPresenter
import cn.huangchengxi.kotlintest.customedviews.CodeValidatorView

/**
 * A simple [Fragment] subclass.
 */
class RegisterPageTwoFragment(interaction: Interaction,presenter: RegisterPresenter) : Fragment() {
    private val interaction=interaction
    private val presenter=presenter
    private var codeValidatorView:CodeValidatorView?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view=inflater.inflate(R.layout.fragment_register_page_two, container, false)
        codeValidatorView=view.findViewById(R.id.code_input)
        codeValidatorView?.setOnFinishListener {
            interaction.success()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        codeValidatorView?.showSoftInput()
    }
    interface Interaction{
        fun success()
        fun networkError()
    }
}
