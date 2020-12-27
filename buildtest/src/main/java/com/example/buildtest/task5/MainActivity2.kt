package com.example.buildtest.task5

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildtest.R
import com.example.buildtest.task5.Model.CardMatchingGame
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    //    法二：采用伴随对象，使数据属于该类而不是属于实例，实例会销毁，而类不会
    companion object {
        val game: CardMatchingGame = CardMatchingGame(24)
    }

    lateinit var adapter: CardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
//        获取数据
//        创建网格式布局布局
        val layoutManager: GridLayoutManager
//        获取到手机的配置文件来判断当前所处的手机状态
        val configuration = resources.configuration
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            竖屏
            layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        } else {
            layoutManager = GridLayoutManager(this, 6, GridLayoutManager.VERTICAL, false)
        }
        recyclerview.layoutManager = layoutManager
//        创建适配器
        adapter = CardAdapter(game)
        recyclerview.adapter = adapter
//        回调
        adapter.setOnClickListener {
            game.chooseCardAtIndex(it)
            updateUI()
        }
        updateUI()
        button_reset.setOnClickListener {
            game.reset()
            updateUI()
        }
    }

    class CardAdapter(val game: CardMatchingGame) :
        RecyclerView.Adapter<CardAdapter.ViewHolder>() {

        //        获取布局内的对象
        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val button: Button = view.findViewById(R.id.button)
        }

        //        回调函数
        var mListener: ((Int) -> Unit)? = null

        fun setOnClickListener(l: (Int) -> Unit) {
            mListener = l
        }

        //        创建view
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return game.cards.size
        }

        //        赋值
        override fun onBindViewHolder(holder: CardAdapter.ViewHolder, position: Int) {
            val card = game.cardAtIndex(position)
            holder.button.isEnabled = !card.isMatched
            if (card.isChosen) {
                holder.button.text = card.toString()
                holder.button.setBackgroundColor(Color.WHITE)
            } else {
                holder.button.text = ""
                holder.button.setBackgroundResource(R.drawable.bg3)
            }
//    点击回调
            holder.button.setOnClickListener {
                mListener?.invoke(position)
            }
        }

    }

    fun updateUI() {
        adapter.notifyDataSetChanged()
//        通知改变之后，就重新bindviewholder操作
        score.text = String.format("%s%d", "Score:", game.score)
    }
}

