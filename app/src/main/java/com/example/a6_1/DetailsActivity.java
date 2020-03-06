package com.example.a6_1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.a6_1.MainActivity.EXTRA;

/**
 * @Details sends information to the activity_details.xml
 * MainActivity tells it which president to take  nextActivity.putExtra(EXTRA, i) -> getIntent().getExtras()
 * from the Singleton by Singleton.getInstance().getPresident(i). ...
 */

public class DetailsActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    int pauseCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Button listenButton = findViewById(R.id.listenButton);
        final Button readButton = findViewById(R.id.readButton);
        final Button speakButton = findViewById(R.id.speakButton);
        final Button writeButton = findViewById(R.id.writeButton);

//text views
        Bundle b = getIntent().getExtras();
        final int i = b.getInt(EXTRA, 0);

        ((TextView) findViewById(R.id.nameTextView))
                .setText(Singleton.getInstance().getPresident(i).getFiName());
        //((TextView) findViewById(R.id.startTextView))
        //       .setText(Singleton.getInstance().getPresident(i).getLenght());
        //((TextView) findViewById(R.id.endTextView))
        //      .setText(Singleton.getInstance().getPresident(i).getChapters());
        ((TextView) findViewById(R.id.nicknameTextView))
                .setText(Singleton.getInstance().getPresident(i).getEnName());

//listenButton
        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    int resId = getResources().getIdentifier(Singleton.getInstance().getPresident(i).getAudio(), "raw", getPackageName());
                    mediaPlayer = MediaPlayer.create(DetailsActivity.this, resId);
                    mediaPlayer.start();
                    //this should be set to look up the song and time from shared preferences
                }
                else if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    pauseCurrentPosition = mediaPlayer.getCurrentPosition();
                } else {
                    mediaPlayer.seekTo(pauseCurrentPosition);
                    mediaPlayer.start();
                }

            }
        });

//readButton
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, LearnActivity.class);

                intent.putExtra(EXTRA, i);
                DetailsActivity.this.startActivity(intent);
                //startActivity(intent);
            }
        });



    }


}
