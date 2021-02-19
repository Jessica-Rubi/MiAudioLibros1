package com.example.miaudiolibros;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Auxiliar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent siguiente = new Intent(getApplication(), MainActivity.class);
        siguiente.putExtra("notify","3");
        startActivityForResult(siguiente,1001);
    }
}
