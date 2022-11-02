package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val list = mutableListOf<String>()
    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        adapter = RecyclerAdapter(list) {
            // адаптеру передали обработчик удаления элемента
            list.removeAt(it)
            adapter.notifyItemRemoved(it)
        }

        val list = mutableListOf<String>()
        adapter = RecyclerAdapter(list){
            list.removeAt(it)
            adapter.notifyItemRemoved(it)
        }

        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val buttonAdd = findViewById<Button>(R.id.button)
            // реакция на нажатие
        buttonAdd.setOnClickListener {
            // добавляем элемент в список
            list.add(Math.random().toString())

            // извещаем адаптер об изменениях
            adapter.notifyItemInserted(list.lastIndex)
        }
        // заполняем список

        for (i in 0..100) {
            list.add(Math.random().toString())
        }

// создаём инстанс адаптера, отдаём ему список

// у нас будет линейный список
        recyclerView.layoutManager = LinearLayoutManager(this)
// прикручиваем адаптер к RecyclerView
        recyclerView.adapter = adapter


    }
}
class RecyclerAdapter(
    private val list: List<String>,
    // передаём коллбек нажатия на кнопку
    private val onItemClick: (id: Int) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = list[position]
        // обработчик нажатия кнопки
        holder.button.setOnClickListener {
            onItemClick(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)
        // находим кнопку
        val button = itemView.findViewById<Button>(R.id.button)
    }
}