package com.example.naturesounds;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController.MediaPlayerControl;
import android.content.ServiceConnection;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class PlayActivity extends AppCompatActivity{
    private static final String TAG = "PlayActivity";
    private ArrayList<SoundsModel> mSoundsModel = new ArrayList<>();
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private ImageButton button;
    private String buttonString;
    public static final String CHANNEL_ID = "exampleServiceChannel";
    private Intent getPlayIntent;
    private int id;
    DatabaseHelper myDb;
    private ImageView mImageView;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_main);
        button = (ImageButton) findViewById(R.id.btn_play);
        button.setBackgroundResource(R.drawable.play);
        mImageView = (ImageView) findViewById(R.id.play_image);
        getPlayIntent = getIntent();
        id = getPlayIntent.getIntExtra("SoundId",0);
        switch (id)
        {
            case 1:
                mImageView.setImageResource(R.drawable.breeze);
                break;
            case 2:
                mImageView.setImageResource(R.drawable.rain);
                break;


        }


        playIntent = new Intent(getApplicationContext(),MusicService.class);
        playIntent.putExtra("musicId",id);
        buttonString = LoadButtonState();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonString.equals("Pause"))
                {
                    button.setBackgroundResource(R.drawable.play);
                    SaveButtonState("Play");
                    buttonString = LoadButtonState();
                    stopService(playIntent);
                }
                else if(buttonString.equals("Play"))
                {
                    button.setBackgroundResource(R.drawable.pause);
                    SaveButtonState("Pause");
                    buttonString = LoadButtonState();
                    startService(playIntent);
                }

            }
        });
        createNotificationChannel();
    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
              CHANNEL_ID,"Example Service CHannel", NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Override
    public void onBackPressed() {
        buttonString = LoadButtonState();
        super.onBackPressed();
    }

    public void SaveButtonState(String bstate)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PlayActivity.this);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("focus_value", bstate);
        edit.commit();
    }
    public String LoadButtonState()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PlayActivity.this);
        String buttonState = preferences.getString("focus_value", "Play");
        return buttonState;
    }
   @Override
    public void onDestroy()
   {
       super.onDestroy();
       stopService(new Intent(this,MusicService.class));
   }
}
