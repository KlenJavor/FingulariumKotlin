package com.example.fingularium.data

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

/**
 * @ResultsSingleton is the single access point to the list of results
 */
object ResultsSingleton {
    var results: MutableList<com.example.fingularium.model.Result> = mutableListOf()
}