package com.example.fingularium.activities

import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fingularium.R
import com.example.fingularium.data.TopicsSingleton

class LearnActivity : AppCompatActivity() {
    //public static final String KEY_SCROLL = "scrollPosition";
    var mediaPlayer: MediaPlayer? = null
    var pauseCurrentPosition = 0
    var isPlaying = false
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
        mediaPlayer?.setLooping(false)


        // Set initial button for media player; pause or play
        if (mediaPlayer == null || mediaPlayer!!.isPlaying == false) {
            play.setImageResource(android.R.drawable.ic_media_play)
        } else {
            play.setImageResource(android.R.drawable.ic_media_pause)
        }

        //Populate text views
        //https://stackoverflow.com/questions/6945678/storing-r-drawable-ids-in-xml-array
        //File title
        (findViewById<View>(R.id.headingTextView) as TextView).setText(TopicsSingleton.getTopic(i).getTitle())
        //English Text
        (findViewById<View>(R.id.enTextView) as TextView).movementMethod = ScrollingMovementMethod()
        val enArray = resources.obtainTypedArray(R.array.en_array)
        (findViewById<View>(R.id.enTextView) as TextView).setText(enArray.getResourceId(i, -1))
        //Finnish Text
        (findViewById<View>(R.id.fiTextView) as TextView).movementMethod = ScrollingMovementMethod()
        val fiArray = resources.obtainTypedArray(R.array.fi_array)
        (findViewById<View>(R.id.fiTextView) as TextView).setText(fiArray.getResourceId(i, -1))


        //Re-establishing state after orientation or other change
        /**  if (mediaPlayer.isPlaying()) {
         * Log.d("test5", "oncreate-is playing");
         * SharedPreferences prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE);
         * pauseCurrentPosition = prefGet.getInt(KEY_PLAYER, 0);
         * mediaPlayer.seekTo(pauseCurrentPosition);
         * mediaPlayer.start();
         * } */

        // Manage media player
        // https://www.youtube.com/watch?v=4DC4XFWVFls
        // https://www.youtube.com/watch?v=pZjdrEmq36A
        play.setOnClickListener {
            Log.d("testi", "i= $i")
            if (mediaPlayer == null) {
                play.setImageResource(android.R.drawable.ic_media_pause)

                //this should be set to look up the song and time from shared preferences
                val prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
                pauseCurrentPosition = prefGet.getInt(TopicsSingleton.getTopic(i).audio.toString() + "_player", 0)
                Log.d("testi", Integer.toString(pauseCurrentPosition))
                mediaPlayer?.seekTo(pauseCurrentPosition)
                mediaPlayer?.start()
                Log.d("test1", Integer.toString(prefGet.getInt(TopicsSingleton.getTopic(i).audio.toString() + "_player", 0)))
            } else if (mediaPlayer!!.isPlaying) {
                mediaPlayer!!.pause()
                play.setImageResource(android.R.drawable.ic_media_play)

                //Store new Player position to shared preferences
                // 1. Open the file: get reference
                val prefPut = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
                //2. Open the editor to be able to define what is added to shared preferences
                val prefEditor = prefPut.edit()
                //3. Put the key value pairs
                pauseCurrentPosition = mediaPlayer!!.currentPosition
                Log.d("test2", Integer.toString(mediaPlayer!!.currentPosition))
                prefEditor.putInt(TopicsSingleton.getTopic(i).audio.toString() + "_player", pauseCurrentPosition)

                //4. Save the changes by commit
                prefEditor.commit()
            } else {
                val prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
                pauseCurrentPosition = prefGet.getInt(TopicsSingleton.getTopic(i).audio.toString() + "_player", 0)
                mediaPlayer!!.seekTo(pauseCurrentPosition)
                mediaPlayer!!.start()
                play.setImageResource(android.R.drawable.ic_media_pause)
                Log.d("test3", Integer.toString(prefGet.getInt(TopicsSingleton.getTopic(i).audio.toString() + "_player", 0)))
            }
        }
    }

    /**
     * stop.setOnClickListener(new View.OnClickListener() {
     *
     * @Override public void onClick(View v) {
     * if (mediaPlayer != null) {
     * mediaPlayer.stop();
     * mediaPlayer = null;
     * }
     * }
     * });
     */
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
            prefEditor.putInt(TopicsSingleton.getTopic(i).audio.toString() + "_player", mediaPlayer!!.currentPosition)
            //4. Save the changes by commit
            prefEditor.commit()
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
        val prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE)
        outState.putInt(TopicsSingleton.getTopic(i).audio.toString() + "_player", mediaPlayer!!.currentPosition)
        outState.putBoolean(KEY_PLAYING, mediaPlayer!!.isPlaying)
    }

    /**
     * //retrieves state after screen orientation and restores state
     * @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
     * super.onRestoreInstanceState(savedInstanceState);
     * pauseCurrentPosition = savedInstanceState.getInt(KEY_PLAYER);
     * isPlaying = savedInstanceState.getBoolean(KEY_PLAYING);
     *
     * Bundle c = getIntent().getExtras();
     * final int i = c.getInt(EXTRA, 0);
     * if (isPlaying == true) {
     *
     * int resId = getResources().getIdentifier(Singleton.getInstance().getPresident(i).getAudio(), "raw", getPackageName());
     * mediaPlayer = MediaPlayer.create(LearnActivity.this, resId);
     * //play.setImageResource(android.R.drawable.ic_media_pause);
     * mediaPlayer.seekTo(pauseCurrentPosition);
     * mediaPlayer.start();
     * }
     * }
     */
    companion object {
        // public static final String KEY_PLAYER = "playerPosition";  //Singleton.getInstance().getPresident(i).getAudio() + "_player"
        const val KEY_PLAYING = "isPlayerPlaying"
    }
}