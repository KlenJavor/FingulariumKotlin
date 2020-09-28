package com.example.a6_1


import org.junit.Assert.assertEquals
import org.junit.Test

class WordUnitTests {
    @Test
    fun create() {
        val w = Word("Finnish", "kaivo")
        assertEquals(w.text, "kaivo")
        assertEquals(w.lang, "Finnish")
        assertEquals(0, w.translationCount("English"))
    }

    @Test
    fun addTranslationIdentical() {
        val w = Word("Finnish", "kaivo")
        w.addTranslation(Word("English", "well"))
        assertEquals(true, w.isTranslation(Word("English", "well")))
    }

    @Test
    fun addTranslationDifferentLanguage() {
        val w = Word("kaivo", "Finnish")
        w.addTranslation(Word("well", "English"))
        assertEquals(false, w.isTranslation(Word("well", "Swedish")))
    }

    @Test
    fun addTranslationsNonEmpty() {
        val w = Word("kaivo", "Finnish")
        w.addTranslations(setOf(Word("well", "English")))
        assertEquals(true, w.isTranslation(Word("well", "English")))
    }

    @Test
    fun addTranslationsEmpty() {
        val w = Word("kaivo", "Finnish")
        w.addTranslations(setOf())
        assertEquals(false, w.isTranslation(Word("well", "English")))
    }

    @Test
    fun addTranslationDifferent() {
        val w = Word("kaivo", "Finnish")
        w.addTranslation(Word("well", "English"))
        assertEquals(false, w.isTranslation(Word("sell", "English")))
    }

    @Test
    fun translationCountEmpty() {
        val w = Word("kaivo", "Finnish")
        assertEquals(0, w.translationCount("English"))
    }

    @Test
    fun translationCountOne() {
        val w = Word("Finnish", "kaivo")
        w.addTranslation(Word("English", "well"))
        assertEquals(1, w.translationCount("English"))
    }

    @Test
    fun translationCountMany() {
        val w = Word("Finnish", "kaivo")
        w.addTranslation(Word("English", "well"))
        w.addTranslation(Word("English", "spring"))
        assertEquals(2, w.translationCount("English"))
    }

    @Test
    fun distanceIdentical() {
        val w = Word("Finnish", "kaivo")
        assertEquals(0, w.editDistance(Word("Finnish", "kaivo")))
    }

    @Test
    fun distanceEmpty1() {
        val w = Word("Finnish", "kaivo")
        assertEquals(5, w.editDistance(Word("Finnish", "")))
    }

    @Test
    fun distanceEmpty2() {
        val w = Word("Finnish", "")
        assertEquals(5, w.editDistance(Word("Finnish", "kaivo")))
    }

    @Test
    fun distance1change() {
        val w = Word("Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("Finnish", "raivo")))
    }

    @Test
    fun distance1delete() {
        val w = Word("Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("Finnish", "aivo")))
    }

    @Test
    fun distance1add() {
        val w = Word("Finnish", "kaivo")
        assertEquals(1, w.editDistance(Word("Finnish", "kaivot")))
    }

    @Test
    fun distance1add1delete() {
        val w = Word("Finnish", "kaivo")
        assertEquals(2, w.editDistance(Word("Finnish", "aivot")))
    }

    @Test
    fun distanceManyAdd() {
        val w = Word("Finnish", "kaivo")
        assertEquals(12, w.editDistance(Word("Finnish", "vesikaivotkinkaan")))
    }

    @Test
    fun translationsContent() {
        val w = Word("abcde", "Finnish")
        w.addTranslation(Word("aaaa", "Finnish"))
        w.addTranslation(Word("abcd", "Finnish"))
        w.addTranslation(Word("abab", "Finnish"))
        w.addTranslation(Word("aaaa", "Swedish"))
        w.addTranslation(Word("aaaae", "Finnish"))
        assertEquals(true,
                w.isTranslation(Word("aaaa", "Finnish"))
                        && w.isTranslation(Word("abcd", "Finnish"))
                        && w.isTranslation(Word("abab", "Finnish"))
                        && w.isTranslation(Word("aaaa", "Swedish"))
                        && w.isTranslation(Word("aaaae", "Finnish"))
        )
    }

    @Test
    fun closestTest() {
        val aC = Word("Czech", "důkladně")
        val bC = Word("Czech", "pěkně")
        val cC = Word("Czech", "dobře")
        val dC = Word("Czech", "studna")
        val aF = Word("Finnish", "hyvinkin")
        val bF = Word("Finnish", "selvästi")
        val cF = Word("Finnish", "hyvin")
        val dF = Word("Finnish", "kaivo")
        val aE = Word("English", "well")

        dF.addTranslation(aC)
        dF.addTranslation(bC)
        dF.addTranslation(cC)
        dF.addTranslation(dC)
        dF.addTranslation(aF)
        dF.addTranslation(bF)
        dF.addTranslation(cF)
        dF.addTranslation(dF)
        dF.addTranslation(aE)

        assertEquals( listOf("pěkně", "dobře"), dF.closestTranslations(2, "Czech"))
    }
}