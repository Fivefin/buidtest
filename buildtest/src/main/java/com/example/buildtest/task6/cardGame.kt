package com.example.buildtest.task6

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buildtest.R
import com.example.buildtest.task5.MainActivity2
import com.example.buildtest.task6.Model.CardMatchingGame
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

class cardGame : Fragment() {
    companion object {
        lateinit var game: CardMatchingGame
        fun newInstance() = cardGame()
    }
    lateinit var adapter: MainActivity2.CardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //获取本地数据
        val rgame = loadData()
        if(rgame != null) {
            game = rgame
        } else {
            game = CardMatchingGame(24)
        }
        val layoutManager: GridLayoutManager
        val configuration = resources.configuration
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = GridLayoutManager(activity,4, GridLayoutManager.VERTICAL,false)
        }else {
            layoutManager = GridLayoutManager(activity,6, GridLayoutManager.VERTICAL,false)
        }
        recyclerview.layoutManager = layoutManager
        adapter = MainActivity2.CardAdapter(MainActivity2.game)
        recyclerview.adapter = adapter
        adapter.setOnClickListener {
            MainActivity2.game.chooseCardAtIndex(it)
            updateUI()
        }
        updateUI()
        button_reset.setOnClickListener {
            MainActivity2.game.reset()
            updateUI()
        }
    }

    class CardAdapter(val game:CardMatchingGame): RecyclerView.Adapter<CardAdapter.ViewHolder>() {
        inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
            val button: Button = view.findViewById(R.id.button)
        }
        var mListener: ((Int)->Unit)? = null
        fun setOnClickListener(l:(Int)->Unit) {
            mListener = l
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_item,parent,false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return game.cards.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val card = game.cardAtIndex(position)
            holder.button.isEnabled = !card.isMatched
            if (card.isChosen) {
                holder.button.text = card.toString()
                holder.button.setBackgroundColor(Color.WHITE)
            } else {
                holder.button.text = ""
                holder.button.setBackgroundResource(R.drawable.bg3)
            }
            holder.button.setOnClickListener {
                mListener?.invoke(position)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        //在停止的时候保存数据
        saveData()
    }
    fun updateUI() {
        adapter.notifyDataSetChanged()
        score.text = String.format("%s%d","Score:", MainActivity2.game.score)
    }
    //保存数据
    fun saveData() {
        try {
            //对象序列化
            val output = activity?.openFileOutput("data",AppCompatActivity.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(output)
            objectOutputStream.writeObject(game)
            objectOutputStream.close()
            output?.close()
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }

    //获取数据
    fun loadData():CardMatchingGame? {
        try {
            val input = activity?.openFileInput("data")
            val objectInputStream = ObjectInputStream(input)
            val game = objectInputStream.readObject() as CardMatchingGame
            objectInputStream.close()
            input?.close()
            return game
        } catch (e:Exception) {
            e.printStackTrace()
            return null
        }
    }

}