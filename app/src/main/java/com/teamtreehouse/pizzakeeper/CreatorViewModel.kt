package com.teamtreehouse.pizzakeeper

import android.arch.lifecycle.ViewModel
import com.teamtreehouse.pizzakeeper.data.Topping

class CreatorViewModel : ViewModel() {
    var pizzaName = "New Pizza"
    val switchStates = mutableMapOf<Topping, Boolean>()
}