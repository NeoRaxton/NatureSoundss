package com.example.naturesounds;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Intent.getIntent;
import static com.example.naturesounds.PlayActivity.CHANNEL_ID;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener{
    //media player
    private MediaPlayer player;
    //current position
    private int songPosn;
    private int id;
    //Binding
    private final IBinder musicBind = new MusicBinder();
    //SoundsModel class
    private SoundsModel mSoundsModel ;
    //Intent
    private Intent mIntent;

    private Context mContext;
    //Adapter
    private SoundsAdapter mSoundsAdapter;

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        songPosn=0;
        player = new MediaPlayer();
        initMusicPlayer();

    }
    public void initMusicPlayer(){
        //set player properties
        player.setWakeMode(getApplicationContext(),PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }
    @Override
    public void onCompletion(MediaPlayer mp) {

    }


    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
    mp.start();
    }
    public void setSong(int songIndex){
        songPosn=songIndex;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service has been started", Toast.LENGTH_SHORT).show();
        mIntent = intent;
        id = mIntent.getExtras().getInt("musicId");

        switch (id) {

            case 1:
                    player = MediaPlayer.create(this, R.raw.music);
                    player.start();
                    break;
            case 2:
                player = MediaPlayer.create(this,R.raw.music2);
                player.start();
                break;
        }



        final Intent notificationIntent = new Intent(this, PlayActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.play)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, builder.build());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service has been Stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);
        player.stop();
    }
}
