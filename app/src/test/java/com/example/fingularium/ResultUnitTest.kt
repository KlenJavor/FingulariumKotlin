package com.example.fingularium


import com.example.fingularium.data.ResultsSingleton.results
import com.example.fingularium.model.Result
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultUnitTests {

    @Test
    fun createResult() {
        val r = Result("questionHeading", "rightAnswer", 0, 0)
        assertEquals(r.question, "questionHeading")
        assertEquals(r.answers, "rightAnswer")
        assertEquals(r.passes, 0)
        assertEquals(r.fails, 0)
    }

    @Test
    fun resultsIdentical() {
        val r = Result("questionHeading", "rightAnswer", 0, 0)
        val s = Result("questionHeading", "wrongAnswer", 5, 8)
        assertEquals(true, r.equals(s))
    }

    @Test
    fun resultsIdentical2() {
        val r = Result("questionHeading", "rightAnswer", 0, 0)
        val s = Result("questionHeading", "rightAnswer", 0, 0)
        assertEquals(true, r.equals(s))
    }

    @Test
    fun resultsDifferent() {
        val r = Result("questionHeading", "rightAnswer", 0, 0)
        val s = Result("answerHeading", "rightAnswer", 0, 0)
        assertEquals(false, r.equals(s))
    }

    @Test
    fun hashSame() {
        val r = Result("questionHeading", "rightAnswer", 0, 0)
        val s = Result("answerHeading", "rightAnswer", 0, 0)
        assertEquals(false, r.hashCode() == s.hashCode())
    }

    @Test
    fun collectionItemIdentical() {
        val r = Result("questionHeading", "rightAnswer", 0, 0)
        val s = Result("questionHeading", "wrongAnswer", 5, 8)
        results.add(r)
        results.add(s)
        assertEquals(true, results[0].equals(results[1]))
    }

    @Test
    fun collectionNotFind1() {
        val r = Result("questionHeading", "rightAnswer", 0, 0)
        val s = Result("questionHeading", "wrongAnswer", 5, 8)
        results.add(r)
        results.add(s)
        assertEquals(true, results.find { it == Result("Heading", "rightAnswer", 0, 0) } == null)
        assertEquals(false, results.find { it == Result("questionHeading", "rightAnswer", 0, 0) } == null)
        assertEquals(false, results.find { it == Result("questionHeading", "rightAnswer", 5, 0) } == null)
    }

}