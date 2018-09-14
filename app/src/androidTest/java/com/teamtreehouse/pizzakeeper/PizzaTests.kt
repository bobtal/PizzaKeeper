package com.teamtreehouse.pizzakeeper

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.teamtreehouse.pizzakeeper.data.*

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

    val appContext = InstrumentationRegistry.getTargetContext()
    val db = Room.databaseBuilder(appContext, PizzaDatabase::class.java, "PizzaDatabase")
            .build()

    @Test
    fun pizzaTest() {
        db.clearAllTables()
        db.pizzaDao().insert(testPizza)
        val returnedPizza = db.pizzaDao().getPizzaById(testPizza.id)
        assertEquals(testPizza, returnedPizza)
    }

    @Test
    fun pizzaToppingTest() {
        // arrange
        db.clearAllTables()
        db.pizzaDao().insert(testPizza)
        toppings.forEach(db.toppingDao()::insert)

        // act
        testToppingIds.forEach {
            val pizzaTopping = PizzaTopping(testPizza.id, it)
            db.pizzaToppingDao().insert(pizzaTopping)
        }
        val returnedToppingIds = db.pizzaToppingDao().getToppingIdsForPizzaId(testPizza.id)

        // assert
        assertEquals(testToppingIds, returnedToppingIds)
    }
}
