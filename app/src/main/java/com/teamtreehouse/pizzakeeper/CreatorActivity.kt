package com.teamtreehouse.pizzakeeper

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast

class CreatorActivity : AppCompatActivity() {
    private var pizzaId = -1
    lateinit var pizzaView: PizzaView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)

        pizzaId = intent.getIntExtra(PIZZA_ID, -1)
        pizzaView = PizzaView(this, mutableMapOf())

        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        frameLayout.addView(pizzaView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val adapter = CreatorAdapter(pizzaView)
        val recyclerView = findViewById<RecyclerView>(R.id.toppingsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        adapter.list.addAll(toppings)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.creator_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.edit_name -> {
                val editText = EditText(this)
                editText.setSingleLine()
                AlertDialog.Builder(this).setView(editText).setPositiveButton("Ok", { dialogInterface, i ->
                    if (editText.text.isNotEmpty()) {
                        val text = editText.text.toString()
                        title = text
                    }
                }).show()
            }
            R.id.save -> {
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        return true
    }
}