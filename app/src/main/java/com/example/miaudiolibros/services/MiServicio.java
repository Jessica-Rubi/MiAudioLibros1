package com.example.miaudiolibros.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.miaudiolibros.MainActivity;
import com.example.miaudiolibros.R;

import java.io.IOException;

public class MiServicio extends Service implements MediaPlayer.OnPreparedListener {
    MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MSAL", "servicio creado");
    }

    private String CHANNEL_ID="CANALID";
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal para Servicios";
            String description = "Canal para Servicios";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Este metodo se manda llamar cuando invocas el servicio con startservice()
        //tarea exhaustiva/pesada debe ir en un subproceso y descadenarse aqui
        createNotificationChannel();
        String nombreLibro = intent.getStringExtra("nombreLibro");
        String uriString = intent.getStringExtra("uriLibro");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(nombreLibro)
                .setContentText("Libro actual")
                .setSmallIcon(android.R.drawable.ic_menu_slideshow)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        Uri audio = Uri.parse(uriString);
        try {
            mediaPlayer.setDataSource(this, audio);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("Audiolibros", "ERROR: No se puede reproducir " + audio, e);
        }
//        try {
//            Thread.sleep(5000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//        Log.d("MSAL","Tarea pesada finalizada");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MSAL", "Servicio destruido");
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

}
