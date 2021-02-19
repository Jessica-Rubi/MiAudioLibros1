package com.example.miaudiolibros;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.example.miaudiolibros.services.MiServicio;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleFragment extends Fragment implements View.OnTouchListener, MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl {
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    public static String ARG_ID_LIBRO = "id_libro";
    Intent iSer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleFragment newInstance(String param1, String param2) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_detalle, container, false);
        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt(ARG_ID_LIBRO);
            int val = args.getInt("origen");
            int lib = args.getInt("libro");
            if (val == 5) {
                ponInfoLibro(lib, vista, val);
            } else {
                ponInfoLibro(position, vista, val);
            }
        } else {
            ponInfoLibro(0, vista, 0);
        }
        return vista;
    }

    public void ponInfoLibro(int id) {
        ponInfoLibro(id, getView(), 0);
    }

    private void ponInfoLibro(int id, View vista, int val) {
//        iSer = new Intent(getContext(), MiServicio.class);
//        getActivity().startService(iSer);
//        Intent miss = new Intent(getContext(), );

        Libro libro =
                Libro.ejemploLibros().elementAt(id);
        ((TextView) vista.findViewById(R.id.titulo)).setText(libro.titulo);
        ((TextView) vista.findViewById(R.id.autor)).setText(libro.autor);
        ((ImageView) vista.findViewById(R.id.portada)).setImageResource(libro.recursoImagen);
        (vista.findViewById(R.id.Detener)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });
//        vista.setOnTouchListener(this);
//        if (mediaPlayer != null){
//            mediaPlayer.release();
//        }
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setOnPreparedListener(this);
//        mediaController = new MediaController(getActivity());
//        Uri audio = Uri.parse(libro.urlAudio);
        if (!(val == 5)) {
            startService(libro.titulo, libro.urlAudio, id);
        }
//        try {
//            mediaPlayer.setDataSource(getActivity(), audio);
//            mediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            Log.e("Audiolibros", "ERROR: No se puede reproducir "+audio,e);
//        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d("Audiolibros", "Entramos en onPrepared de MediaPlayer");
        mediaPlayer.start();
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView());
        mediaController.setEnabled(true);
        mediaController.show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mediaController.show();
        return false;
    }

    @Override
    public void start() {
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
        stopService();
//        }else {
//            mediaPlayer.start();
//        }
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
//        stopService();
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int i) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onStop() {

//        getActivity().stopService(iSer);
//        stopService();
//        mediaController.hide();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            Log.d("Audiolibros", "Error en mediaPlayer.stop()");
        }
        super.onStop();
    }

    public void startService(String nombre, String uri, int id) {
        Intent serviceIntent = new Intent(getContext(), MiServicio.class);
        serviceIntent.putExtra("nombreLibro", nombre);
        serviceIntent.putExtra("uriLibro", uri);
        serviceIntent.putExtra("idlibro", id);
        getActivity().startService(serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(getContext(), MiServicio.class);
        getActivity().stopService(serviceIntent);
    }
}