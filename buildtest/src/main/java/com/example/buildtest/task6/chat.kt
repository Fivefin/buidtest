package com.example.buildtest.task6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildtest.R
import kotlinx.android.synthetic.main.fragment_chatsoftware.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class chat : Fragment(),View.OnClickListener {

    companion object {
        lateinit var msgList: ArrayList<Msg>
        fun newInstance() = chat()
    }
    lateinit var adapter: MsgAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chatsoftware, container, false)
    }

    //被创建完成后
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //获取本地数据
        val msgs = loadData()
        if(msgs != null) {
            msgList = msgs
        } else{
            msgList = ArrayList()
            InitMsg()
        }
        val layoutManager = LinearLayoutManager(activity)
        recycleView.layoutManager = layoutManager
        adapter =
            MsgAdapter(msgList)
        recycleView.adapter = adapter
        sendButton.setOnClickListener(this)
    }
    override fun onClick(v:View?) {
        when(v) {
            sendButton -> {
                val content = inputText.text.toString()
                if (content.isNotEmpty()) {
                    if (msgList[msgList.size - 1].type == Msg.TYPE_RECEIVED) {
                        val msg = Msg(
                            content,
                            Msg.TYPE_SEND
                        )
                        msgList.add(msg)
                        adapter?.notifyItemInserted(msgList.size - 1)
                        recycleView.scrollToPosition(msgList.size - 1)
                        inputText.setText("")
                    }else {
                        val msg = Msg(
                            content,
                            Msg.TYPE_RECEIVED
                        )
                        msgList.add(msg)
                        adapter?.notifyItemInserted(msgList.size - 1)
                        recycleView.scrollToPosition(msgList.size - 1)
                        inputText.setText("")
                    }
                }
            }
        }
    }
    class MsgAdapter( val msgList:List<Msg>): RecyclerView.Adapter<MsgViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder = if (viewType == Msg.TYPE_SEND) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.right,parent,false)
            RightViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.left,parent,false)
            LeftViewHolder(view)
        }

        //提供viewtype类型参数
        override fun getItemViewType(position: Int): Int {
            val msg = msgList[position]
            return msg.type
        }
        override fun getItemCount(): Int {
            return msgList.size
        }
        override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
            val msg = msgList[position]
            when(holder) {
                is LeftViewHolder -> holder.leftMsg.text = msg.content
                is RightViewHolder -> holder.rightMsg.text = msg.content
            }
        }
    }
    //消息类
    data class Msg(val content:String,val type:Int) {
        companion object {
            const val  TYPE_RECEIVED = 0
            const val  TYPE_SEND = 1
        }
    }
    //初始化消息
    fun InitMsg() {
        val msg1 = Msg(
            "hello",
            Msg.TYPE_RECEIVED
        )
        msgList.add(msg1)
        val msg2 = Msg(
            "world！",
            Msg.TYPE_SEND
        )
        msgList.add(msg2)
        val msg3 = Msg(
            "good！",
            Msg.TYPE_RECEIVED
        )
        msgList.add(msg3)
    }
    override fun onStop() {
        super.onStop()
        //在停止的时候保存数据
        saveData()
    }
    //保存数据
    fun saveData() {
        try {
            //对象序列化
            val output = activity?.openFileOutput("chatdata", AppCompatActivity.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(output)
            objectOutputStream.writeObject(msgList)
            objectOutputStream.close()
            output?.close()
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //获取数据
    fun loadData(): ArrayList<Msg>? {
        try {
            val input = activity?.openFileInput("chatdata")
            val objectInputStream = ObjectInputStream(input)
            val msgList = objectInputStream.readObject() as ArrayList<Msg>
            objectInputStream.close()
            input?.close()
            return msgList
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}