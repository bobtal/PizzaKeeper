package com.teamtreehouse.pizzakeeper

import android.arch.lifecycle.ViewModelProviders
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
import com.teamtreehouse.pizzakeeper.data.Pizza
import com.teamtreehouse.pizzakeeper.data.PizzaTopping
import java.util.*
import kotlin.concurrent.thread

class CreatorActivity : AppCompatActivity() {
    private var pizzaId = -1
    lateinit var pizzaView: PizzaView
    lateinit var viewModel: CreatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creator)

        pizzaId = intent.getIntExtra(PIZZA_ID, -1)
        viewModel = ViewModelProviders.of(this).get(CreatorViewModel::class.java)
        thread {
            if (pizzaId != -1) {
                viewModel.pizzaName = db.pizzaDao().getPizzaById(pizzaId).name
                val toppingIds = db.pizzaToppingDao().getToppingIdsForPizzaId(pizzaId)
                toppingIds.forEach {
                    val topping = db.toppingDao().getToppingById(it)
                    viewModel.switchStates[topping] = true
                }
            }
            title = viewModel.pizzaName
        }
        pizzaView = PizzaView(this, viewModel.switchStates)

        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        frameLayout.addView(pizzaView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        val adapter = CreatorAdapter(pizzaView, viewModel)
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
                thread {
                    if (pizzaId != -1) {
                        db.pizzaToppingDao().deletePizzaById(pizzaId)
                        db.pizzaDao().deletePizzaById(pizzaId)
                    }
                }
                finish()
            }
            R.id.edit_name -> {
                val editText = EditText(this)
                editText.setSingleLine()
                AlertDialog.Builder(this).setView(editText).setPositiveButton("Ok", { dialogInterface, i ->
                    if (editText.text.isNotEmpty()) {
                        val text = editText.text.toString()
                        title = text
                        viewModel.pizzaName = text
                    }
                }).show()
            }
            R.id.save -> {
                thread {
                    if (pizzaId != -1) { // we are editing an existing pizza,
                        // so need to delete the one in the database before inserting the new one
                        db.pizzaToppingDao().deletePizzaById(pizzaId)
                        db.pizzaDao().deletePizzaById(pizzaId)
                    }
                    val pizza = Pizza(null, viewModel.pizzaName, Date())
                    val newPizzaId = db.pizzaDao().insert(pizza).toInt()

                    viewModel.switchStates.forEach{
                        if (it.value) {
                            val pizzaTopping = PizzaTopping(newPizzaId, it.key.id)
                            db.pizzaToppingDao().insert(pizzaTopping)
                        }
                    }
                }
                finish()
            }
        }
        return true
    }
}