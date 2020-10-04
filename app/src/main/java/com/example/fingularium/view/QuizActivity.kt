package com.example.fingularium.view


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fingularium.R
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
                    answers = listOf("Meat", "Fat", "Gain weight", "Leg")),
            Question(text = "What do you use to push structured data into a layout?",
                    answers = listOf("Data binding", "Data pushing", "Set text", "An OnClick method")),
            Question(text = "What method do you use to inflate layouts in fragments?",
                    answers = listOf("onCreateView()", "onActivityCreated()", "onCreateLayout()", "onInflateLayout()")),
            Question(text = "What's the build system for Android?",
                    answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
            Question(text = "Which class do you use to create a vector drawable?",
                    answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
            Question(text = "Which one of these is an Android navigation component?",
                    answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
            Question(text = "Which XML element lets you register an activity with the launcher activity?",
                    answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
            Question(text = "What do you use to mark a layout for data binding?",
                    answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )


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

    }
}