package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Pizza (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val creationDate: Date
)
