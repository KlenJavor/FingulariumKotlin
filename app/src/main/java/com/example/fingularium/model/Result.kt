package com.example.fingularium.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.*

data class Result(
        val question: String,
        val answers: String,
        val passes: Int,
        val fails: Int) {

    var results: MutableList<Result> = mutableListOf()

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