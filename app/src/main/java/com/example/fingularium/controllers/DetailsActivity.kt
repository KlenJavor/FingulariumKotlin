package com.example.fingularium.controllers

/**
 * Created by Patricie Suppala, 1910042 in 2020.
 */

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fingularium.R
import com.example.fingularium.data.TopicsSingleton
import kotlinx.android.synthetic.main.activity_details.*

/**
 * @DetailsActivity sends information to the activity_details.xml
 * MainActivity tells it which topics to take from the TopicsSingleton
 */

class DetailsActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var pauseCurrentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

// Populate the first two text views with the name os the chosen topic in English and in Finnish
        val b = intent.extras
        val i = b!!.getInt(MainActivity.EXTRA, 0)
        (findViewById<View>(R.id.nameTextView) as TextView).text = TopicsSingleton.getTopic(i).fiName
        (findViewById<View>(R.id.nicknameTextView) as TextView).text = TopicsSingleton.getTopic(i).enName

// The listen button shall start playing the audio on click and pause on another click etc.
        listenButton.setOnClickListener {
            when {
                mediaPlayer == null -> {
                    val resId = resources.getIdentifier(TopicsSingleton.getTopic(i).audio, "raw", packageName)
                    mediaPlayer = MediaPlayer.create(this@DetailsActivity, resId)
                    mediaPlayer?.start()
                }
                mediaPlayer!!.isPlaying -> {
                    mediaPlayer!!.pause()
                    pauseCurrentPosition = mediaPlayer!!.currentPosition
                }
                else -> {
                    mediaPlayer!!.seekTo(pauseCurrentPosition)
                    mediaPlayer!!.start()
                }
            }
        }

        // The read button starts the LearnActivity
        readButton.setOnClickListener {
            val intent = Intent(this@DetailsActivity, LearnActivity::class.java)
            intent.putExtra(MainActivity.EXTRA, i)
            this@DetailsActivity.startActivity(intent)
        }

        // The write button starts the quiz. It is called "write" because in future there should be another functionlity
        quizButton.setOnClickListener {
            val intent = Intent(this@DetailsActivity, QuizActivity::class.java)
            intent.putExtra(MainActivity.EXTRA, i)
            this@DetailsActivity.startActivity(intent)
        }

        // The speak button starts the quiz. It is called "speak" because in future there should be another functionlity
        vocabularyButton.setOnClickListener {
            val intent = Intent(this@DetailsActivity, VocabularyActivity::class.java)
            intent.putExtra(MainActivity.EXTRA, i)
            this@DetailsActivity.startActivity(intent)
        }
    }
}