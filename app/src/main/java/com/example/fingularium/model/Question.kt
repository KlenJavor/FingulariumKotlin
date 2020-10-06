package com.example.fingularium.model

// The first answer is the correct one.  We randomize the answers before showing the text.
// All questions must have four answers.

data class Question(
        val text: String,
        val answers: List<String>)