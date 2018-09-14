package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun dateToLong(date: Date): Long = date.time

    @TypeConverter
    fun longToDate(long: Long): Date = Date(long)
}