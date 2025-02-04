package com.example.trafficcam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trafficcam.BD.DBManager;
import com.example.trafficcam.Objects.Incidencia;
import com.example.trafficcam.R;

import java.util.List;

public class AdapterIncidence  extends BaseAdapter {
    private Context context;
    private List<Incidencia> incidenciaListAdapter;





    private DBManager dbManager;

    public AdapterIncidence(Context context, List<Incidencia> incidenciaList) {
        this.context = context;
        this.incidenciaListAdapter = incidenciaList;
        this.dbManager = new DBManager(context);
    }



    @Override
    public int getCount() {
        return incidenciaListAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return incidenciaListAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adaptercamara_incidence, viewGroup, false);
        }

        Incidencia incidencia = incidenciaListAdapter.get(i);

        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtDescripcion = view.findViewById(R.id.txtDescripcion);
        TextView txtKilometer = view.findViewById(R.id.txtKilometer);
        TextView txtRoad = view.findViewById(R.id.txtRoad);
        ImageView imgFavorito = view.findViewById(R.id.imgFavorito);


        txtTitulo.setText(incidencia.getIncidenceType());
        txtDescripcion.setText("Causa: " + incidencia.getCause());
        txtKilometer.setText("Dirección: " + incidencia.getDirection());
        txtRoad.setText("Ciudad: " + incidencia.getCityTown());

        // Cambiar el icono de favorito según el estado de la cámara

        imgFavorito.setImageResource(R.drawable.star_on);  // Icono de favorito activado

        //imgFavorito.setImageResource(R.drawable.star_of);  // Icono de favorito desactivado


        // Configurar el clic sobre el icono de favorito
        imgFavorito.setOnClickListener(v -> {

            incidenciaListAdapter.remove(i);
            dbManager.eliminarIncidenciaFavoritos(incidencia);




            notifyDataSetChanged();

        });

        return view;
    }

}
