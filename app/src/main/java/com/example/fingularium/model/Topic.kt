package com.example.fingularium.model

import java.util.*

/**
 * Class has the constructor and getters to get info about a topic
 */
class Topic (private val title: String, private val lenght: Int, private val chapters: Int, val enName: String, val fiName: String, val audio: String) {

    fun getTitle(): String {
        return title.toUpperCase(Locale.ROOT)
    }

    fun getLenght(): String {
        return lenght.toString()
    }

    fun getChapters(): String {
        return chapters.toString()
    }

    override fun toString(): String {
        return title
    }

}