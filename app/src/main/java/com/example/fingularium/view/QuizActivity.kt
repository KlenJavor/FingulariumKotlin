package com.example.fingularium.view


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.fingularium.R
import com.example.fingularium.VocabularyRepository
import com.example.fingularium.model.Word
import com.example.fingularium.viewmodel.MainViewModelFactory
import com.example.fingularium.viewmodel.VocabularyViewModel
import kotlinx.android.synthetic.main.activity_quiz.*
import java.util.*

class QuizActivity : AppCompatActivity() {

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.
    data class Question(
            val text: String,
            val answers: List<String>)

   data class Result(
            val question: String,
            val answers: String,
            val passes: Int,
            val fails: Int)
    {
        override fun equals(other: Any?): Boolean {
            if(this === other) return true
            if(other == null || other.javaClass != this.javaClass) return false
            val o = other as Result
            return(this.question == o.question)
        }
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun hashCode(): Int {
            return Objects.hash(question)
        }
    }

    private lateinit var viewModel: VocabularyViewModel
    var wordIndex: Int = 0
    var questionHeading = ""
    lateinit var rightAnswer: String
    lateinit var answers: MutableList<String>
    var results: MutableList<Result> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Retrieve json vocabulary into local variable jsonVocabulary
        val repository = VocabularyRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VocabularyViewModel::class.java)
        viewModel.getCustomPosts()
        viewModel.myVocabulary.observe(this, { response ->
            if (response.isSuccessful) {
                val jsonVocabulary = response.body()
                playGame(jsonVocabulary)
            } else {
                questionText.text = response.code().toString()
            }
        })
    }

    fun playGame(jsonVocabulary: List<List<Word>>?) {

        // 1. Build a question
        var vocQuestion = buildQuestion(jsonVocabulary)

        // 3. Set first question
        setQuestion(vocQuestion)
        results.add(Result(questionHeading, rightAnswer, 0, 0))

        // 4. Display question
        // Set the onClickListener for the submitButton
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
                    setQuestion(vocQuestion)

                } else {
                    // Give feedback
                    textFeedback.text = "Wrong\n Correct answer was: " + rightAnswer.take(170)

                    // collect statistics about losses")
                    updateResults(questionHeading, rightAnswer, 0, 1)

                    // Advance to the next question
                    vocQuestion = buildQuestion(jsonVocabulary)
                    setQuestion(vocQuestion)
                }
            } else {
                // Game over! A wrong answer sends us to the gameOverFragment.
                submitButton.text = "That was wrong. Try next!"
            }
        }
    }

    fun getClosestWord(wordIndex: Int, jsonVocabulary: List<List<Word>>?): MutableList<String> {
        var distances: MutableMap<String, Int> = mutableMapOf()

        if (jsonVocabulary != null) {
            for (translation in jsonVocabulary) {
                distances.put(translation[0].text, jsonVocabulary.get(wordIndex).get(0).editDistance(translation[0]))
            }
        }

        // remove identical words and get 3 closest translations
        var sorted = distances.toList().sortedBy { (_, value) -> value }.toMap()
        var cleaned = sorted.filter { (key, value) -> value > 0 }
        var cropped = mutableListOf<String>()
        for (i in 0..2) {
            cropped.add(cleaned.keys.elementAt(i))
        }
        if (jsonVocabulary != null) {
            Log.d("otazka", jsonVocabulary.get(wordIndex).get(0).text)
        }
        Log.d("alternative answers", cropped.toString())
        return cropped
    }

    private fun setQuestion(vocQuestion: QuizActivity.Question) {
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

    fun buildQuestion(jsonVocabulary: List<List<Word>>?): Question {
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
        var cropped = getClosestWord(wordIndex, jsonVocabulary)
        var vocQuestion = Question(text = questionHeading, answers = listOf(rightAnswer, cropped[0], cropped[1], cropped[2]))
        return vocQuestion
    }

    fun updateResults(questionHeading: String, rightAnswer: String, passIncrease: Int, failIncrease: Int) {
        // is Result with the sme question already in the list?
        if (results.find { it.equals(Result(questionHeading, rightAnswer, 0, 0))}  != null) {
            // add passes or fails
            val originalPasses = results.find { it.equals(Result(questionHeading, rightAnswer, 0, 0))}!!.passes
            val originalFails = results.find { it.equals(Result(questionHeading, rightAnswer, 0, 0))}!!.fails
            results.remove((Result(questionHeading, rightAnswer, 5, 0)))
            val newPasses = originalPasses + passIncrease
            val newFails = originalFails + failIncrease
            results.add(Result(questionHeading, rightAnswer, newPasses, newFails))
            Log.d("lookup", "result WAS found")

        } else {
            // add it
            results.add(Result(questionHeading, rightAnswer, passIncrease, failIncrease))
            Log.d("lookup", "result was not found")
        }
        Log.d("results", results.toString())
    }
}