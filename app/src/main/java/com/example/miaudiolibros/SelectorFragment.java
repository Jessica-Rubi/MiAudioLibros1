package com.example.miaudiolibros;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectorFragment extends Fragment {

    private RecyclerView recycler;
    private AdaptadorLibros adapter;
    private GridLayoutManager layoutManager;

    MainActivity mainActivity;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if(context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout_listaselector, null);
        recycler = v.findViewById(R.id.recyclerView2);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(layoutManager);
        adapter = new AdaptadorLibros(getActivity(), Libro.ejemploLibros());
        adapter.setOnclickListener(vl -> {
            Toast.makeText(getActivity(),
                    "Elemento seleccionado: "
                            + recycler.getChildAdapterPosition(vl) ,
                    Toast.LENGTH_LONG).show();
            mainActivity.mostrarDetalle(recycler.getChildAdapterPosition(vl));
        });
        adapter.setOnLongClickListener(view ->{
            AlertDialog.Builder cuadroDialogo = new AlertDialog.Builder(context);
            cuadroDialogo.setTitle("Selecciona la opcion");
//            cuadroDialogo.setMessage("Este es un cuadro de dialogo");
            cuadroDialogo.setItems(new String[]{"Compartir", "Eliminar", "Agregar"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                 Toast.makeText(getActivity(), "Opcion seleccionada: " + i, Toast.LENGTH_LONG).show();
                }
            });
            cuadroDialogo.setPositiveButton("Ok", (dialogInterface, i) -> {

            });
            cuadroDialogo.create().show();
           return false;
        });
        recycler.setAdapter(adapter);
        return v;
    }
}
