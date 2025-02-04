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
import com.example.trafficcam.Adapter.AdapterIncidence;
import com.example.trafficcam.BD.DBManager;
import com.example.trafficcam.Objects.Incidencia;
import com.example.trafficcam.R;
import com.example.trafficcam.databinding.FragmentFavoriteBinding;

import java.util.List;

public class IncidencesFragment extends Fragment {

    private AdapterIncidence adapter;
    private DBManager db;  // Inicializamos el DBManager aquí, no en la declaración



    private ListView listaFavoritosIncidences;
    private List<Incidencia> incidenciasFavoritas;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    // Inicializa el adaptador con la lista de incidencias favoritas
        adapter = new AdapterIncidence(getContext(), incidenciasFavoritas);
        Log.d("FavoriteIncidences", "onViewCreated ejecutado");

        // Inicializamos DBManager aquí, después de que la actividad esté disponible
        db = new DBManager(getActivity());



        incidenciasFavoritas = db.getIncidencasFavoritos();


        // Inicializamos la lista y el adaptador
        listaFavoritosIncidences = view.findViewById(R.id.listaFavoritosCamaras);
        adapter = new AdapterIncidence(getContext(), incidenciasFavoritas);
        listaFavoritosIncidences.setAdapter(adapter);
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