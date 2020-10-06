package com.example.fingularium.controllers

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fingularium.R
import com.example.fingularium.services.vocabularyRepository
import com.example.fingularium.data.ResultsSingleton.results
import com.example.fingularium.model.Question
import com.example.fingularium.model.Result
import com.example.fingularium.model.Word
import com.example.fingularium.viewmodel.MainViewModelFactory
import com.example.fingularium.viewmodel.VocabularyViewModel
import kotlinx.android.synthetic.main.activity_quiz.*

/**
 * @QuizActivity takes Words from Vocabulary, transforms them to Questions which are displayed to user.
 * Quiz results are saved with Result class
 */

class QuizActivity : AppCompatActivity() {

    private lateinit var viewModel: VocabularyViewModel
    private var wordIndex: Int = 0
    private var questionHeading = ""
    private lateinit var rightAnswer: String
    private lateinit var answers: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Retrieve json vocabulary into local variable jsonVocabulary and if successful, play a quiz game with it
        val repository = vocabularyRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VocabularyViewModel::class.java)
        viewModel.getVocabulary()
        viewModel.myVocabulary.observe(this, { response ->
            if (response.isSuccessful) {
                val jsonVocabulary = response.body()
                playGame(jsonVocabulary)
            } else {
                questionText.text = response.code().toString()
            }
        })

        // Display quiz results in a new activity on a button click
        statsButton.setOnClickListener {
            val intent = Intent(this@QuizActivity, ResultsActivity::class.java)
            intent.putExtra("stats", "results")
            this@QuizActivity.startActivity(intent)
            startActivity(intent)
        }
    }

    // Make quizzes and respond to user choices
    private fun playGame(jsonVocabulary: List<List<Word>>?) {

        // 1. Build a question
        var vocQuestion = buildQuestion(jsonVocabulary)

        // 2. Set first question
        displayQuestion(vocQuestion)
        results.add(Result(questionHeading, rightAnswer, 0, 0))

        // 3. Display question + set onClickListener for the submitButton
        submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                // The first answer in the original question is always the correct one, so if our
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == vocQuestion.answers[0]) {
                    // Give feedback
                    textFeedback.text = "Correct!"
                    // collect statistics about wins")
                    updateResults(questionHeading, rightAnswer, 1, 0)

                    // Advance to the next question
                    vocQuestion = buildQuestion(jsonVocabulary)
                    displayQuestion(vocQuestion)

                } else {
                    // Give feedback
                    textFeedback.text = "Wrong\n Correct answer was: " + rightAnswer.take(170)

                    // collect statistics about losses")
                    updateResults(questionHeading, rightAnswer, 0, 1)

                    // Advance to the next question
                    vocQuestion = buildQuestion(jsonVocabulary)
                    displayQuestion(vocQuestion)
                }
            } else {
                // There should not be no else.
                // Give feedback
                textFeedback.text = "Something went wrong, sorry!"
            }
        }
    }

    // Look at the answer of the randomly selected Word and find 3 other answers that are very similar
    private fun getClosestWord(wordIndex: Int, jsonVocabulary: List<List<Word>>?): MutableList<String> {
        val allDistances: MutableMap<String, Int> = mutableMapOf()

        // Find distance between the correct answer and all other words in the vocabulary
        if (jsonVocabulary != null) {
            for (translation in jsonVocabulary) {
                allDistances[translation[0].text] = jsonVocabulary[wordIndex][0].editDistance(translation[0])
            }
        }

        // Remove identical words and return 3 closest translations
        val sortedByDistance = allDistances.toList().sortedBy { (_, value) -> value }.toMap()
        val removedDuplicates = sortedByDistance.filter { (_, value) -> value > 0 }
        val threeAlternativeAnswers = mutableListOf<String>()
        for (i in 0..2) {
            threeAlternativeAnswers.add(removedDuplicates.keys.elementAt(i))
        }
        if (jsonVocabulary != null) {
            Log.d("otazka", jsonVocabulary[wordIndex][0].text)
        }
        Log.d("alternative answers", threeAlternativeAnswers.toString())
        return threeAlternativeAnswers
    }

    // Display question in the QuizActivity
    private fun displayQuestion(vocQuestion: Question) {
        // randomize the answers into a copy of the array
        answers = vocQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        questionText.text = vocQuestion.text
        firstAnswerRadioButton.text = answers[0]
        secondAnswerRadioButton.text = answers[1]
        thirdAnswerRadioButton.text = answers[2]
        fourthAnswerRadioButton.text = answers[3]
    }

    // Generates new question from Word in Vocabulary and alternative answers
    private fun buildQuestion(jsonVocabulary: List<List<Word>>?): Question {

        // 1. Pick a random word from the vocabulary
        if (jsonVocabulary != null) {
            wordIndex = (0..jsonVocabulary.size).shuffled().first()
            Log.d("wordIndex", wordIndex.toString())
        }
        questionHeading = jsonVocabulary?.get(wordIndex)?.get(1)?.text ?: "chyba"
        rightAnswer = jsonVocabulary?.get(wordIndex)?.get(0)?.text ?: "chyba"
        Log.d("question", questionHeading)
        Log.d("right answer", rightAnswer)

        // 2. Find alternative answers and build a question
        val cropped = getClosestWord(wordIndex, jsonVocabulary)
        return Question(text = questionHeading, answers = listOf(rightAnswer, cropped[0], cropped[1], cropped[2]))
    }

    // Depending on what user answered, increase the number of passes or fails for particular question
    private fun updateResults(questionHeading: String, rightAnswer: String, passIncrease: Int, failIncrease: Int) {

        // is Result with the sme question already in the list?
        if (results.find { it == Result(questionHeading, rightAnswer, 0, 0) } != null) {
            // if yes, add passes or fails
            val originalPasses = results.find { it == Result(questionHeading, rightAnswer, 0, 0) }!!.passes
            val originalFails = results.find { it == Result(questionHeading, rightAnswer, 0, 0) }!!.fails
            results.remove((Result(questionHeading, rightAnswer, 5, 0)))
            val newPasses = originalPasses + passIncrease
            val newFails = originalFails + failIncrease
            results.add(Result(questionHeading, rightAnswer, newPasses, newFails))
            Log.d("lookup", "result WAS found")

        } else {
            // if not, add it
            results.add(Result(questionHeading, rightAnswer, passIncrease, failIncrease))
            Log.d("lookup", "result was not found")
        }
        Log.d("results", results.toString())
    }
}


