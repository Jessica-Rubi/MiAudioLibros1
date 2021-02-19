package com.example.miaudiolibros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SelectorFragment selectorFragment = new SelectorFragment();
        if(findViewById(R.id.contenedor_pequeno) != null &&
                getSupportFragmentManager().findFragmentById(R.id.contenedor_pequeno) == null){
            getSupportFragmentManager().beginTransaction().add(R.id.contenedor_pequeno, selectorFragment).commit();
        }
        Bundle bundle = getIntent().getExtras();
        String val = bundle.getString("notify");
        int libid = bundle.getInt("idlib");
        System.out.println(val);
        prueba(val, libid);
    }

    public void prueba(String valor, int id1){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        if(valor.equals("4")){
            bundle.putInt("origen", 5);
            bundle.putInt("libro", id1);
            DetalleFragment detalleFragment = new DetalleFragment();
            detalleFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentManager.beginTransaction().replace(R.id.contenedor_pequeno, detalleFragment).addToBackStack(null).commit();
        }
    }

    public void mostrarDetalle(int index){
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(fragmentManager.findFragmentById(R.id.detalle_fragment)!=null){

            DetalleFragment fragment =
                    (DetalleFragment)
                            fragmentManager.findFragmentById(R.id.detalle_fragment);

            fragment.ponInfoLibro(index);


        }else{
            DetalleFragment detalleFragment =
                    new DetalleFragment();

            Bundle bundle = new Bundle();

            bundle.putInt(DetalleFragment.ARG_ID_LIBRO, index);
            bundle.putInt("origen", 6);

            detalleFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentManager.beginTransaction().replace(R.id.contenedor_pequeno
                    , detalleFragment).addToBackStack(null).commit();

        }
    }
}