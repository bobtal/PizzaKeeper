package com.teamtreehouse.pizzakeeper

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.teamtreehouse.pizzakeeper.data.PizzaDatabase

const val PIZZA_ID = "PIZZA_ID"

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(this, PizzaDatabase::class.java, "PizzaDatabase").build()

        recyclerView = findViewById(R.id.pizzaRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val launchCreatorActivity = { id: Int? ->
            val intent = Intent(this, CreatorActivity::class.java)
            intent.putExtra(PIZZA_ID, id)
            startActivity(intent)
        }
        val myAdapter = MainAdapter(launchCreatorActivity)
        recyclerView.adapter = myAdapter
    }
}

