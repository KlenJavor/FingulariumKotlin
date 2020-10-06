package com.example.fingularium.model

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

/**
 * @Question holds a question and a list of four answers when the first answer is the correct one.
 *  All questions must have four answers.
 */

data class Question(
        val text: String,
        val answers: List<String>)