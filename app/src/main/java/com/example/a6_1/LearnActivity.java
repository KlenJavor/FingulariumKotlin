package com.example.a6_1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.a6_1.MainActivity.EXTRA;
import static java.lang.reflect.Array.getInt;

public class LearnActivity extends AppCompatActivity {

    // public static final String KEY_PLAYER = "playerPosition";  //Singleton.getInstance().getPresident(i).getAudio() + "_player"
    public static final String KEY_PLAYING = "isPlayerPlaying";
    //public static final String KEY_SCROLL = "scrollPosition";

    MediaPlayer mediaPlayer;

    int pauseCurrentPosition;
    boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        final ImageButton play = findViewById(R.id.readButton);

        // Set variable for obtaining listview choice from main activity
        Bundle c = getIntent().getExtras();
        final int i = null == c ? 0 : c.getInt(EXTRA, 0);

        //Load song to media player
        int resId = getResources().getIdentifier(Singleton.getInstance().getPresident(i).getAudio(), "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(LearnActivity.this, resId);
        mediaPlayer.setLooping(false);


        // Set initial button for media player; pause or play
        if (mediaPlayer == null || mediaPlayer.isPlaying() == false) {
            play.setImageResource(android.R.drawable.ic_media_play);
        } else {
            play.setImageResource(android.R.drawable.ic_media_pause);
        }

        //Populate text views
        //https://stackoverflow.com/questions/6945678/storing-r-drawable-ids-in-xml-array
        //File title
        ((TextView) findViewById(R.id.headingTextView)).setText(Singleton.getInstance().getPresident(i).getTitle());
        //English Text
        ((TextView) findViewById(R.id.enTextView)).setMovementMethod(new ScrollingMovementMethod());
        TypedArray enArray = getResources().obtainTypedArray(R.array.en_array);
        ((TextView) findViewById(R.id.enTextView)).setText(enArray.getResourceId(i, -1));
        //Finnish Text
        ((TextView) findViewById(R.id.fiTextView)).setMovementMethod(new ScrollingMovementMethod());
        TypedArray fiArray = getResources().obtainTypedArray(R.array.fi_array);
        ((TextView) findViewById(R.id.fiTextView)).setText(fiArray.getResourceId(i, -1));


        //Re-establishing state after orientation or other change
        /**  if (mediaPlayer.isPlaying()) {
         Log.d("test5", "oncreate-is playing");
         SharedPreferences prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE);
         pauseCurrentPosition = prefGet.getInt(KEY_PLAYER, 0);
         mediaPlayer.seekTo(pauseCurrentPosition);
         mediaPlayer.start();
         }**/

        // Manage media player
        // https://www.youtube.com/watch?v=4DC4XFWVFls
        // https://www.youtube.com/watch?v=pZjdrEmq36A
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("testi", "i= " + i );
                if (mediaPlayer == null) {
                    play.setImageResource(android.R.drawable.ic_media_pause);

                    //this should be set to look up the song and time from shared preferences
                    SharedPreferences prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE);


                        pauseCurrentPosition = prefGet.getInt(Singleton.getInstance().getPresident(i).getAudio() + "_player", 0);
                    Log.d("testi", Integer.toString(pauseCurrentPosition));


                    mediaPlayer.seekTo(pauseCurrentPosition);
                    mediaPlayer.start();


                    Log.d("test1", Integer.toString(prefGet.getInt(Singleton.getInstance().getPresident(i).getAudio() + "_player", 0)));

                } else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();

                    play.setImageResource(android.R.drawable.ic_media_play);

                    //Store new Player position to shared preferences
                    // 1. Open the file: get reference
                    SharedPreferences prefPut = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE);
                    //2. Open the editor to be able to define what is added to shared preferences
                    SharedPreferences.Editor prefEditor = prefPut.edit();
                    //3. Put the key value pairs
                    pauseCurrentPosition = mediaPlayer.getCurrentPosition();
                    Log.d("test2", Integer.toString(mediaPlayer.getCurrentPosition()));
                    prefEditor.putInt(Singleton.getInstance().getPresident(i).getAudio() + "_player", pauseCurrentPosition);

                    //4. Save the changes by commit
                    prefEditor.commit();


                } else {
                    SharedPreferences prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE);
                    pauseCurrentPosition = prefGet.getInt(Singleton.getInstance().getPresident(i).getAudio() + "_player", 0);
                    mediaPlayer.seekTo(pauseCurrentPosition);
                    mediaPlayer.start();
                    play.setImageResource(android.R.drawable.ic_media_pause);
                    Log.d("test3", Integer.toString(prefGet.getInt(Singleton.getInstance().getPresident(i).getAudio() + "_player", 0)));
                }
            }
        });
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
     **/

    //this prevents playing on background
    @Override
    protected void onPause() {

        //add here use preference: if (user wishes no playing on background) {code below}
        super.onPause();
        Bundle c = getIntent().getExtras();
        final int i = c.getInt(EXTRA, 0);

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            //Store new Player position to shared preferences
            // 1. Open the file: get reference
            SharedPreferences prefPut = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE);
            //2. Open the editor to be able to define what is added to shared preferences
            SharedPreferences.Editor prefEditor = prefPut.edit();
            //3. Put the key value pairs

            prefEditor.putInt(Singleton.getInstance().getPresident(i).getAudio() + "_player", mediaPlayer.getCurrentPosition());
            //4. Save the changes by commit
            prefEditor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test4", "onresume");

    }

    //saves player state in case of screen orientation change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle c = getIntent().getExtras();
        final int i = c.getInt(EXTRA, 0);
        SharedPreferences prefGet = getSharedPreferences("PlayerAndScrollState", Activity.MODE_PRIVATE);
        outState.putInt(Singleton.getInstance().getPresident(i).getAudio() + "_player", mediaPlayer.getCurrentPosition());
        outState.putBoolean(KEY_PLAYING, mediaPlayer.isPlaying());
    }
/**
 //retrieves state after screen orientation and restores state
 @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
 super.onRestoreInstanceState(savedInstanceState);
 pauseCurrentPosition = savedInstanceState.getInt(KEY_PLAYER);
 isPlaying = savedInstanceState.getBoolean(KEY_PLAYING);

 Bundle c = getIntent().getExtras();
 final int i = c.getInt(EXTRA, 0);
 if (isPlaying == true) {

 int resId = getResources().getIdentifier(Singleton.getInstance().getPresident(i).getAudio(), "raw", getPackageName());
 mediaPlayer = MediaPlayer.create(LearnActivity.this, resId);
 //play.setImageResource(android.R.drawable.ic_media_pause);
 mediaPlayer.seekTo(pauseCurrentPosition);
 mediaPlayer.start();
 }
 }**/
}

//pause media player on pause onstop and ondestroy
//add rew buttons + show position
//manage media player with seekbar
//remember scrolling position too
//synchronise scrolling with media player
//have synchronised view
//adjust text size
//add settings where use can switch off playing on background
