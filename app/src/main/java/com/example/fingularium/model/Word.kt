package com.example.fingularium.model

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import android.util.Log

/**
 * @Word holds the basic structure for the vocabulary and questions.
 * Important is collection translations which can hold several translations for the same word, e.g. in multiple languages
 */

data class Word(val lang:String, val text:String) {
    private var translations = mutableSetOf<Word>()
    var n:Int = 0

    fun addTranslation(t: Word) {
        translations.add(t)
    }

    fun addTranslations(ts: Set<Word>) {
        translations = (translations + ts) as MutableSet<Word>
    }
    fun isTranslation(word: Word): Boolean {
        return translations.contains(word)
    }

    fun translationCount(lang: String): Int {
        return translations.size
    }

    fun closestTranslations(n:Int, lang:String): MutableList<String> {
        var distances: MutableMap<String, Int> = mutableMapOf()

        println("\nCalculating distances...")
        for (translation in translations) {
            if (translation.lang == lang){
                distances.put(translation.text, this.editDistance(translation))
            }
        }
        var sorted = distances.toList().sortedBy { (_, value) -> value}.toMap()
        var cropped  = mutableListOf<String>()

        println("\nOrdering by distance...")

        println ("\n\nPrinting out the $n closest translation in $lang language for $this")
        for (i in 0..n-1){
            cropped.add(sorted.keys.elementAt(i))
        }
        return cropped
    }

    fun editDistance(another: Word): Int {
        val string1 = this.text
        val string2 = another.text

        val d = Array(string1.length + 1) { i ->
            IntArray(string2.length + 1) { j ->
                when {
                    i == 0 -> j
                    j == 0 -> i
                    else -> Int.MAX_VALUE
                }
            }
        }

        for (i in 1..string1.length) {
            for (j in 1..string2.length) {
                d[i][j] = if (string1[i - 1] == string2[j - 1]) {
                    d[i - 1][j - 1]

                } else {
                    minOf(
                            d[i - 1][j] + 1,    // deletion
                            d[i][j - 1] + 1,    // insertion
                            d[i - 1][j - 1] + 1 // substitution
                    )
                }
            }
        }
        return d[string1.length][string2.length]
    }
}