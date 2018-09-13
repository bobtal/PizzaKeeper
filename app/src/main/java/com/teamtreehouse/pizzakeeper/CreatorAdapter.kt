package com.teamtreehouse.pizzakeeper

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.teamtreehouse.pizzakeeper.data.Topping

class CreatorAdapter(val pizzaView: PizzaView, val viewModel: CreatorViewModel):
        RecyclerView.Adapter<CreatorAdapter.ViewHolder>() {
    val list = mutableListOf<Topping>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topping_row, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.textView)
        val switch = view.findViewById<Switch>(R.id.toggle)

        fun updateView(index: Int) {
            val topping = list[index]

            switch.setOnCheckedChangeListener { button, isChecked ->
                viewModel.switchStates[topping] = isChecked
                pizzaView.invalidate()
            }

            textView.text = topping.name
            switch.isChecked = viewModel.switchStates[topping] ?: false
        }
    }
}