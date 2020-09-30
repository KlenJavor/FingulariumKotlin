package com.example.a6_1

import com.squareup.moshi.Json

/**
 * This data class defines a Mars property which includes an ID, the image URL, the type (sale
 * or rental) and the price (monthly if it's a rental).
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
data class VocabularyProperty(
        val id: String,
        // used to map img_src from the JSON to imgSrcUrl in our class
        @Json(name = "Finnish_words.json") val finnish: String,
        val type: String,
        val meaning: Double)