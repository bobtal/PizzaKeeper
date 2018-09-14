package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PizzaDao {
    @Query("select * from pizza")
    fun getAll(): List<Pizza>

    @Query("select * from pizza where id = :id")
    fun getPizzaById(id: Int): Pizza

    @Insert
    fun insert(pizza: Pizza)

    @Query("delete from pizza where id = :id")
    fun deletePizzaById(id: Int)
}