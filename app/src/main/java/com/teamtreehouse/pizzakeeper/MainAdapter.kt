package com.teamtreehouse.pizzakeeper

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.teamtreehouse.pizzakeeper.data.Pizza


class MainAdapter(val launchCreatorActivity: (Int?) -> Unit) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    val list = mutableListOf<Pizza>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pizza_row, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(position)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.textView)
        val button = view.findViewById<Button>(R.id.button)
        val divider = view.findViewById<View>(R.id.divider)

        fun updateView(index: Int) {
            // If it's not the last item in the list
            if (index != list.size) {
                textView.visibility = View.VISIBLE
                button.visibility = View.GONE
                divider.visibility = View.VISIBLE

                textView.text = list[index].name
                view.setOnClickListener {
                    launchCreatorActivity(list[index].id)
                }
            } else {
                textView.visibility = View.GONE
                button.visibility = View.VISIBLE
                divider.visibility = View.GONE

                button.setOnClickListener {
                    launchCreatorActivity(null)
                }
            }
        }
    }
}