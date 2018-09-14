package com.teamtreehouse.pizzakeeper

import android.app.Application
import android.arch.persistence.room.Room
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.teamtreehouse.pizzakeeper.data.PizzaDatabase
import com.teamtreehouse.pizzakeeper.data.Topping
import kotlin.concurrent.thread

val toppingBitmaps = mutableMapOf<Topping, Bitmap>()
val toppings = mutableListOf(
        Topping(1, "Pepperoni", "topping_pepperoni"),
        Topping(2, "Sausage", "topping_sausage"),
        Topping(3, "Green Peppers", "topping_green_pepper"),
        Topping(4, "Onions", "topping_onion"),
        Topping(5, "Mushrooms", "topping_mushroom"),
        Topping(6, "Jalapeños", "topping_jalepeno"),
        Topping(7, "Pineapple", "topping_pineapple"),
        Topping(8, "Spinach", "topping_spinach")
)
lateinit var db: PizzaDatabase


class App : Application() {

    override fun onCreate() {
        db = Room.databaseBuilder(applicationContext, PizzaDatabase::class.java, "PizzaDatabase")
                .build()
        thread {
            toppings.forEach {
                toppingBitmaps[it] = getBitmap(it.drawableName)
                db.toppingDao().insert(it)
            }
        }
        super.onCreate()
    }

    private fun getBitmap(drawableName: String): Bitmap {
        val resId = resources.getIdentifier(drawableName, "drawable", packageName)
        return BitmapFactory.decodeResource(resources, resId)
    }
}