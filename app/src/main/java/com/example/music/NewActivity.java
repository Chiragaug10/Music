package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class NewActivity extends AppCompatActivity {
    TextView textView;
    ImageView play,next,rewind;
    ArrayList<File> songs;
    MediaPlayer mediaPlayer;
    String songName;
    int position;
    SeekBar seekBar;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new2);
        textView=findViewById(R.id.textView);
        play=findViewById(R.id.imageView5);
        next=findViewById(R.id.imageView3);
        rewind=findViewById(R.id.imageView4);
        seekBar=findViewById(R.id.seekBar);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        songs=(ArrayList)bundle.getParcelable("song");
        songName=intent.getStringExtra("Current Song");
        textView.setText(songName);
        position=intent.getIntExtra("position",0);
        Uri uri=Uri.parse(songs.get(position).toString());
        mediaPlayer=MediaPlayer.create(this,uri);
        mediaPlayer.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    play.setImageResource(R.drawable.images);
                    mediaPlayer.pause();
                }
                else
                {
                    play.setImageResource(R.drawable.image2);
                    mediaPlayer.start();
                }
            }
        });
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position!=0)
                {
                    position=position-1;
                }
                else
                {
                    position=songs.size()-1;
                }
                Uri uri=Uri.parse(songs.get(position).toString());
                mediaPlayer=MediaPlayer.create(NewActivity.this,uri);
                mediaPlayer.start();
                songName=songs.get(position).toString();
                textView.setText(songName);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position!= songs.size()-1)
                {
                    position=position+1;
                }
                else
                {
                    position=0;
                }
                Uri uri=Uri.parse(songs.get(position).toString());
                mediaPlayer=MediaPlayer.create(NewActivity.this,uri);
                mediaPlayer.start();
                songName=songs.get(position).toString();
                textView.setText(songName);
            }
        });



    }
}