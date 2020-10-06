package com.example.fingularium.data

import com.example.fingularium.model.Result
import com.example.fingularium.view.TopicsItem
import java.util.ArrayList

object ResultsSingleton {

    var results: MutableList<com.example.fingularium.model.Result> = mutableListOf()

    fun getResults(i: Int): com.example.fingularium.model.Result {
        return results[i]
    }

 }