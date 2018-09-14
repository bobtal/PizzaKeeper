package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [Pizza::class, PizzaTopping::class, Topping::class], version = 1)
abstract class PizzaDatabase: RoomDatabase() {
    abstract fun pizzaDao(): PizzaDao
    abstract fun pizzaToppingDao(): PizzaToppingDao
    abstract fun toppingDao(): ToppingDao
}