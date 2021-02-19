package com.example.miaudiolibros.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

public class miIntentService extends IntentService {

    public miIntentService() {
        super("MiIntentSevice");
    }

    class MiTareaAsincrona extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //inicilizar recursos
            Log.d("MIIS", "Se inicio el subproceso");
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            Log.d("MIIS", "Se recibieron en el subproceso " + integers.length +" parametros" );

            for (int i=0; i<integers.length ; i++){

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d("MIIS", "Valor del parametro "+ (i+1) + " = " + integers[i]  );
                publishProgress(i, i);
            }

            return integers.length>0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("MIIS", "Progreso en subproceso " + values[0] );
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                Log.d("MIIS", "Subproceso finalizado" );
            }
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
