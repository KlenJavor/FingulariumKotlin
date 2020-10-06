package com.example.fingularium.model

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

/**
 * @Result holds the number of passes and fails for particular question.
 * It also holds the question but only the correct answer.
 * It would make sense to refactor the code and join Question and Result into one class.
 */

data class Result(
        val question: String,
        val answers: String,
        val passes: Int,
        val fails: Int) {

    var results: MutableList<Result> = mutableListOf()

    // Results are considered the same when their question parameter is the same
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other.javaClass != this.javaClass) return false
        val o = other as Result
        return (this.question == o.question)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun hashCode(): Int {
        return Objects.hash(question)
    }

    init {

    }
}