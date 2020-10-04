package com.example.fingularium.view


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fingularium.R
import com.example.fingularium.VocabularyRepository
import com.example.fingularium.model.Word
import com.example.fingularium.viewmodel.MainViewModelFactory
import com.example.fingularium.viewmodel.VocabularyViewModel
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    data class Question(
            val text: String,
            val answers: List<String>)

    // The first answer is the correct one.  We randomize the answers before showing the text.
    // All questions must have four answers.  We'd want these to contain references to string
    // resources so we could internationalize. (Or better yet, don't define the questions in code...)
    private val questions: MutableList<Question> = mutableListOf(
            Question(text = "Maito",
                    answers = listOf("Milk", "Tea", "Coffee", "Water")),
            Question(text = "Puu",
                    answers = listOf("Tree", "Toilet", "Free", "Head")),
            Question(text = "Liha",
                    answers = listOf("Meat", "Fat", "Gain weight", "Leg"))
    )

    //NEW
    private lateinit var viewModel: VocabularyViewModel
    var wordIndex: Int = 0
    var question = ""
    var rightAnswer = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Retrieve json vocabulary into local variable jsonVocabulary
        val repository = VocabularyRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(VocabularyViewModel::class.java)
        viewModel.getCustomPosts()
        viewModel.myVocabulary.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val jsonVocabulary = response.body()
                playGame(jsonVocabulary)
                Log.d("celej json", jsonVocabulary.toString())

            } else {
                questionText.text = response.code().toString()
            }
        })
    }

    fun playGame(jsonVocabulary: List<List<Word>>?) {
        // 1. Pick random word
        wordIndex = (0..200).shuffled().first()
        question = jsonVocabulary?.get(wordIndex)?.get(0)?.text ?: "chyba"
        rightAnswer = jsonVocabulary?.get(wordIndex)?.get(1)?.text ?: "chyba"

        // 2. Find alternative answers and build a question
        // 2a Find 3 words that is very close to the original word
        // 2b Get their answers

        // 3. Display question
        //questionText.text = jsonVocabulary[0][0].text
        //firstAnswerRadioButton.text = response.body()?.get(1)?.get(1)?.text ?:"chyba"
        //secondAnswerRadioButton.text = response.body()?.get(1)?.get(1)?.text ?:"chyba"
        //thirdAnswerRadioButton.text = response.body()?.get(1)?.get(1)?.text ?:"chyba"
        //fourthAnswerRadioButton.text = response.body()?.get(1)?.get(1)?.text ?:"chyba"

        // 4. Decide, if it was the right answer
        // 5. Update statistics

        // Display the desired data
    }
//OLD

    /*
    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val questionRadioGroup = findViewById<RadioGroup>(R.id.questionRadioGroup)
        var questionText = findViewById<TextView>(R.id.questionText)
        var firstAnswerRadioButton = findViewById<RadioButton>(R.id.firstAnswerRadioButton)
        var secondAnswerRadioButton = findViewById<RadioButton>(R.id.secondAnswerRadioButton)
        var thirdAnswerRadioButton = findViewById<RadioButton>(R.id.thirdAnswerRadioButton)
        var fourthAnswerRadioButton = findViewById<RadioButton>(R.id.fourthAnswerRadioButton)


        // Inflate the layout for this fragment


        // Shuffles the questions and sets the question index to the first question.
        randomizeQuestions()

        // Bind this fragment class to the layout


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
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()

                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                        submitButton.text = "Correct Take next!"

                    }
                } else {
                    // Game over! A wrong answer sends us to the gameOverFragment.
                    submitButton.text = "That was wrong. Try next!"

                }
            }
        }

    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {

        currentQuestion = questions[questionIndex]
        // randomize the answers into a copy of the array
        answers = currentQuestion.answers.toMutableList()
        // and shuffle them
        answers.shuffle()
        questionText.text = currentQuestion.text
        firstAnswerRadioButton.text = answers[0]
        secondAnswerRadioButton.text = answers[1]
        thirdAnswerRadioButton.text = answers[2]
        fourthAnswerRadioButton.text = answers[3]

    }*/
}