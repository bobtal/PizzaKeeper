package com.teamtreehouse.pizzakeeper

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.teamtreehouse.pizzakeeper.data.Pizza
import com.teamtreehouse.pizzakeeper.data.PizzaDatabase

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PizzaTests {

    val testPizza = Pizza(0, "Hawaiian", Date())
    val testToppingIds = listOf(1, 7)

    @Test
    fun pizzaTest() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val db = Room.databaseBuilder(appContext, PizzaDatabase::class.java, "PizzaDatabase")
                .build()
        db.clearAllTables()
        db.pizzaDao().insert(testPizza)
        val returnedPizza = db.pizzaDao().getPizzaById(testPizza.id)
        assertEquals(testPizza, returnedPizza)
    }
}
