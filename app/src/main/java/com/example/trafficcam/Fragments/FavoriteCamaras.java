package com.example.trafficcam.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trafficcam.Adapter.AdapterCamara;
import com.example.trafficcam.BD.DBManager;
import com.example.trafficcam.R;
import com.example.trafficcam.databinding.FragmentFavoriteBinding;
import com.example.trafficcam.Objects.Camara;

import java.util.List;

public class FavoriteCamaras extends Fragment {

    private AdapterCamara adapter;
    private DBManager db;  // Inicializamos el DBManager aquí, no en la declaración



    private ListView listaFavoritosCamaras;
    private List<Camara> camarasFavoritas; // Lista para almacenar las cámaras favoritas


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        return view;
    }
    // El DBManager se inicializa cuando el contexto esté disponible
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Log.d("FavoriteCamaras", "onViewCreated ejecutado");

        // Inicializamos DBManager aquí, después de que la actividad esté disponible
        db = new DBManager(getActivity());


        // Obtenemos las cámaras favoritas
        camarasFavoritas = db.getCamarasFavoritas();



        // Inicializamos la lista y el adaptador
        listaFavoritosCamaras = view.findViewById(R.id.listaFavoritosCamaras);
        adapter = new AdapterCamara(getContext(), camarasFavoritas);
        listaFavoritosCamaras.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        // Cerramos la base de datos cuando el fragmento se destruya
        if (db != null) {
            db.close();
        }
    }
}