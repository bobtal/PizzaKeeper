package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface ToppingDao {
    @Query("select * from topping")
    fun getAll(): List<Topping>

    @Query("select * from topping where id = :id")
    fun getToppingById(id: Int): Topping

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topping: Topping)
}