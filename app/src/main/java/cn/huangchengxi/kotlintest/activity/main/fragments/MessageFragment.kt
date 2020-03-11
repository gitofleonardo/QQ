package cn.huangchengxi.kotlintest.activity.main.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import cn.huangchengxi.kotlintest.R
import cn.huangchengxi.kotlintest.adapter.MainMessageAdapter
import cn.huangchengxi.kotlintest.beans.MainMessageBean
import java.util.*
import kotlin.collections.ArrayList

class MessageFragment : Fragment() {
    private var messageRv:RecyclerView?=null
    private val messageList=ArrayList<MainMessageBean>()
    private val adapter= MainMessageAdapter(messageList)
    private val layoutManager by lazy { LinearLayoutManager(context) }

    companion object {
        fun newInstance() = MessageFragment()
    }

    private lateinit var viewModel: MessageViewModel
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        for (i in 0 until 20){
            messageList.add(MainMessageBean("null","大娱乐家","null","嘤嘤嘤",Date().time,false))
        }
        val view=inflater.inflate(R.layout.message_fragment, container, false)
        messageRv=view.findViewById(R.id.message_rv_list)
        messageRv!!.adapter=adapter
        messageRv!!.layoutManager=layoutManager
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
    }
}
