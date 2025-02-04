package com.example.trafficcam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trafficcam.BD.DBManager;
import com.example.trafficcam.R;
import com.example.trafficcam.Objects.Camara;

import java.util.List;

public class AdapterCamara extends BaseAdapter {

    private Context context;
    private List<Camara> CamaraListAdapter;





    private DBManager dbManager;

    public AdapterCamara(Context context, List<Camara> camaraList) {
        this.context = context;
        this.CamaraListAdapter = camaraList;
        this.dbManager = new DBManager(context);
    }



    @Override
    public int getCount() {
        return CamaraListAdapter.size();
    }

    @Override
    public Object getItem(int position) {
        return CamaraListAdapter.get(position);
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

        Camara camara = CamaraListAdapter.get(i);

        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtDescripcion = view.findViewById(R.id.txtDescripcion);
        TextView txtKilometer = view.findViewById(R.id.txtKilometer);
        TextView txtRoad = view.findViewById(R.id.txtRoad);
        ImageView imgFavorito = view.findViewById(R.id.imgFavorito);


        txtTitulo.setText(camara.getCameraName());
        txtDescripcion.setText("Dirección: " + camara.getAddress());
        txtKilometer.setText("Kilómetro: " + camara.getKilometer());
        txtRoad.setText("Carretera: " + camara.getRoad());

        // Cambiar el icono de favorito según el estado de la cámara

            imgFavorito.setImageResource(R.drawable.star_on);  // Icono de favorito activado

            //imgFavorito.setImageResource(R.drawable.star_of);  // Icono de favorito desactivado


        // Configurar el clic sobre el icono de favorito
        imgFavorito.setOnClickListener(v -> {

            CamaraListAdapter.remove(i);
            dbManager.eliminarCamaraFavoritos(camara);


            //boolean nuevoEstadoFavorito = !camara.getFavorito();  // Alternar el estado de favorito

            notifyDataSetChanged();
            // Cambiar el icono dependiendo del nuevo estado de favorito
            //imgFavorito.setImageResource(nuevoEstadoFavorito ? R.drawable.star_on : R.drawable.star_of);
        });

        return view;
    }


}
