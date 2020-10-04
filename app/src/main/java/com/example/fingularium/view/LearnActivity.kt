package com.example.fingularium.view

import android.annotation.SuppressLint
import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.getAction
import androidx.core.view.accessibility.AccessibilityEventCompat.getAction
import com.example.fingularium.R
import com.example.fingularium.data.TopicsSingleton
import kotlinx.android.synthetic.main.activity_learn.*

class LearnActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var pauseCurrentPosition = 0


    // Listen to scrolling - synchronous scrolling - https://developer.android.com/training/graphics/opengl/touch
    /*override        fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> {
                enTextView.scrollTo(fiTextView.scrollX, fiTextView.scrollY)
            }
        }
        return false
    }*/

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        val play = findViewById<ImageButton>(R.id.readButton)

        // Set variable for obtaining listview choice from main activity
        val c = intent.extras
        val i = c?.getInt(MainActivity.EXTRA, 0) ?: 0

        //Load song to media player
        val resId = resources.getIdentifier(TopicsSingleton.getTopic(i).audio, "raw", packageName)
        mediaPlayer = MediaPlayer.create(this@LearnActivity, resId)
        mediaPlayer?.isLooping = false


        // Set initial button for media player; pause or play
        if (mediaPlayer == null || !mediaPlayer!!.isPlaying) {
            play.setImageResource(android.R.drawable.ic_media_play)
        } else {
            play.setImageResource(android.R.drawable.ic_media_pause)
        }

        //Populate text views
        //https://stackoverflow.com/questions/6945678/storing-r-drawable-ids-in-xml-array
        //File title
        (findViewById<View>(R.id.headingTextView) as TextView).text = TopicsSingleton.getTopic(i).getTitle()
        //English Text
        (findViewById<View>(R.id.enTextView) as TextView).movementMethod = ScrollingMovementMethod()
        val enArray = resources.obtainTypedArray(R.array.en_array)
        (findViewById<View>(R.id.enTextView) as TextView).setText(enArray.getResourceId(i, -1))
        //Finnish Text
        (findViewById<View>(R.id.fiTextView) as TextView).movementMethod = ScrollingMovementMethod()
        val fiArray = resources.obtainTypedArray(R.array.fi_array)
        (findViewById<View>(R.id.fiTextView) as TextView).setText(fiArray.getResourceId(i, -1))




        play.setOnClickListener {
            Log.d("testi", "i= $i")
            when {
                mediaPlayer == null -> {
                    play.setImageResource(android.R.drawable.ic_media_pause)

                    //this should be set to look up the song and time from shared preferences
                    val prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
                    pauseCurrentPosition = prefGet.getInt(TopicsSingleton.getTopic(i).audio + "_player", 0)
                    Log.d("testi", pauseCurrentPosition.toString())
                    mediaPlayer?.seekTo(pauseCurrentPosition)
                    mediaPlayer?.start()
                    Log.d("test1", prefGet.getInt(TopicsSingleton.getTopic(i).audio + "_player", 0).toString())
                }
                mediaPlayer!!.isPlaying -> {
                    mediaPlayer!!.pause()
                    play.setImageResource(android.R.drawable.ic_media_play)

                    //Store new Player position to shared preferences
                    // 1. Open the file: get reference
                    val prefPut = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
                    //2. Open the editor to be able to define what is added to shared preferences
                    val prefEditor = prefPut.edit()
                    //3. Put the key value pairs
                    pauseCurrentPosition = mediaPlayer!!.currentPosition
                    Log.d("test2", mediaPlayer!!.currentPosition.toString())
                    prefEditor.putInt(TopicsSingleton.getTopic(i).audio + "_player", pauseCurrentPosition)
                    //4. Save the changes by commit
                    prefEditor.apply()
                }
                else -> {
                    val prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
                    pauseCurrentPosition = prefGet.getInt(TopicsSingleton.getTopic(i).audio + "_player", 0)
                    mediaPlayer!!.seekTo(pauseCurrentPosition)
                    mediaPlayer!!.start()
                    play.setImageResource(android.R.drawable.ic_media_pause)
                    Log.d("test3", prefGet.getInt(TopicsSingleton.getTopic(i).audio + "_player", 0).toString())
                }
            }
        }
    }

    //this prevents playing on background
    override fun onPause() {

        //add here use preference: if (user wishes no playing on background) {code below}
        super.onPause()
        val c = intent.extras
        val i = c!!.getInt(MainActivity.EXTRA, 0)
        if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            //Store new Player position to shared preferences
            // 1. Open the file: get reference
            val prefPut = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
            //2. Open the editor to be able to define what is added to shared preferences
            val prefEditor = prefPut.edit()
            //3. Put the key value pairs
            prefEditor.putInt(TopicsSingleton.getTopic(i).audio + "_player", mediaPlayer!!.currentPosition)
            //4. Save the changes by commit
            prefEditor.apply()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("test4", "onresume")
    }

    //saves player state in case of screen orientation change
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val c = intent.extras
        val i = c!!.getInt(MainActivity.EXTRA, 0)
        getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
        outState.putInt(TopicsSingleton.getTopic(i).audio + "_player", mediaPlayer!!.currentPosition)
        outState.putBoolean(KEY_PLAYING, mediaPlayer!!.isPlaying)
    }

    companion object {
        // public static final String KEY_PLAYER = "playerPosition";  //Singleton.getInstance().getPresident(i).getAudio() + "_player"
        const val KEY_PLAYING = "isPlayerPlaying"
    }
}