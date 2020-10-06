package com.example.fingularium.model

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import java.util.*

/**
 * @Topic holds all metadata about the topic as title, reference to text and audio files
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