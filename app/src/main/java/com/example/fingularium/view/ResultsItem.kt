package com.example.fingularium.view

import java.util.*

/**
 * Class has the constructor and getters to get info about a result
 */
class ResultsItem (private val question: String, private val answer: String, private val passes: Int, val fails: Int) {

    fun getQuestion(): String {
        return question
    }

    fun getAnswer(): String {
        return answer
    }

    fun getPasses(): String {
        return passes.toString()
    }

    fun getFails(): String {
        return fails.toString()
    }

    override fun toString(): String {
        return question
    }

}