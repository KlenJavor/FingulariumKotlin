package com.example.a6_1

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.a6_1.LearnActivity

/**
 * @Details sends information to the activity_details.xml
 * MainActivity tells it which president to take  nextActivity.putExtra(EXTRA, i) -> getIntent().getExtras()
 * from the Singleton by Singleton.getInstance().getPresident(i). ...
 */
class DetailsActivity : AppCompatActivity() {
    var mediaPlayer: MediaPlayer? = null
    var pauseCurrentPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val listenButton = findViewById<Button>(R.id.listenButton)
        val readButton = findViewById<Button>(R.id.readButton)
        val speakButton = findViewById<Button>(R.id.speakButton)
        val writeButton = findViewById<Button>(R.id.writeButton)

//text views
        val b = intent.extras
        val i = b!!.getInt(MainActivity.EXTRA, 0)
        (findViewById<View>(R.id.nameTextView) as TextView)
                .setText(Singleton.instance.getPresident(i).fiName)
        //((TextView) findViewById(R.id.startTextView))
        //       .setText(Singleton.getInstance().getPresident(i).getLenght());
        //((TextView) findViewById(R.id.endTextView))
        //      .setText(Singleton.getInstance().getPresident(i).getChapters());
        (findViewById<View>(R.id.nicknameTextView) as TextView)
                .setText(Singleton.instance.getPresident(i).enName)

//listenButton
        listenButton.setOnClickListener {
            if (mediaPlayer == null) {
                val resId = resources.getIdentifier(Singleton.instance.getPresident(i).audio, "raw", packageName)
                mediaPlayer = MediaPlayer.create(this@DetailsActivity, resId)
                mediaPlayer?.start()
                //this should be set to look up the song and time from shared preferences
            } else if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
                pauseCurrentPosition = mediaPlayer!!.currentPosition
            } else {
                mediaPlayer!!.seekTo(pauseCurrentPosition)
                mediaPlayer!!.start()
            }
        }

//readButton
        readButton.setOnClickListener {
            val intent = Intent(this@DetailsActivity, LearnActivity::class.java)
            intent.putExtra(MainActivity.EXTRA, i)
            this@DetailsActivity.startActivity(intent)
            //startActivity(intent);
        }

        //writeButton aka quiz
        writeButton.setOnClickListener {
            val intent = Intent(this@DetailsActivity, LearnActivity::class.java)
            intent.putExtra(MainActivity.EXTRA, i)
            this@DetailsActivity.startActivity(intent)
            startActivity(intent);
        }
    }
}