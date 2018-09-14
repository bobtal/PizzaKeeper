package com.teamtreehouse.pizzakeeper.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(primaryKeys = ["pizzaId", "toppingId"],
        foreignKeys = [
            ForeignKey(entity = Pizza::class, parentColumns = ["id"], childColumns = ["pizzaId"]),
            ForeignKey(entity = Topping::class, parentColumns = ["id"], childColumns = ["toppingId"])
        ]
)
data class PizzaTopping (
        val pizzaId: Int,
        val toppingId: Int
)