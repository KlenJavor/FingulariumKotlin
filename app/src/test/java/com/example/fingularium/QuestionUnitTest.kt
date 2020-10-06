package com.example.fingularium

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import com.example.fingularium.model.Question
import org.junit.Assert.assertEquals
import org.junit.Test

class QuestionUnitTests {

      @Test
    fun createResult() {
        val r = Question("koira", listOf("dog", "cat", "mouse", "bird"))
        assertEquals(r.text, "koira")
        assertEquals(r.answers, listOf("dog", "cat", "mouse", "bird"))
    }
}