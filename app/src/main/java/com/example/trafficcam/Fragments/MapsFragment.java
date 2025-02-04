package com.example.trafficcam.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trafficcam.BD.DBManager;
import com.example.trafficcam.Objects.Incidencia;
import com.example.trafficcam.Repositorio;
import com.example.trafficcam.Objects.Camara;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygon;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.trafficcam.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MapsFragment extends Fragment {


    private Repositorio repositorio ;
    private GoogleMap mMap;
    private List<Marker> camaraMarkers = new ArrayList<>();
    private List<Marker>incidenciaMarkers = new ArrayList<>();
    private static List<Camara> camarasFavoritas = new ArrayList<>();  //Preguntar a Egoitz, si es mejor un private o un public parqa ahorrarse una funcion

    private DBManager db;
    private Switch switchCameras ;
    private Switch switchIncidence ;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng paisVasco = new LatLng(43.193902294642456, -1.7231874395612061);  // Coordenadas del centro del País Vasco

            // Añade un marcador en el País Vasco (opcional)
        /*googleMap.addMarker(new MarkerOptions()
                .position(paisVasco)
                .title("País Vasco, España"));*/

            // Mueve la cámara al País Vasco con un nivel de zoom ajustado
            float zoomLevel = 6.0f; // Ajusta el nivel de zoom según tus necesidades
            Log.d("Log de onMapReady", "Realiza las especificaciones del país vasco?");
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paisVasco, zoomLevel));

            // Añadir marcadores para las cámaras si el switch está activado
            if (switchCameras != null && switchCameras.isChecked()) {
                añadirCamarasAlMapa(googleMap);
            }

            new Thread(() -> {
                try {
                    // Descargar el GeoJSON desde la URL
                    URL url = new URL("https://raw.githubusercontent.com/codeforgermany/click_that_hood/refs/heads/main/public/data/spain-communities.geojson");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    String json = new Scanner(inputStream).useDelimiter("\\A").next();
                    inputStream.close();

                    // Procesar el GeoJSON
                    JSONObject geoJsonData = new JSONObject(json);
                    GeoJsonLayer layer = new GeoJsonLayer(googleMap, geoJsonData);

                    // Configurar estilo del polígono
                    layer.getFeatures().forEach(feature -> {
                        if (feature.hasGeometry() && feature.getGeometry() instanceof GeoJsonPolygon) {
                            GeoJsonPolygon polygon = (GeoJsonPolygon) feature.getGeometry();
                            // Verificar si el polígono pertenece al País Vasco
                            if ("País Vasco".equalsIgnoreCase(feature.getProperty("name"))) {
                                feature.getPolygonStyle().setStrokeColor(Color.RED);    // Color del borde
                                feature.getPolygonStyle().setStrokeWidth(4);           // Ancho del borde
                                feature.getPolygonStyle().setFillColor(0x5500FF00);    // Color de relleno semitransparente
                            }
                        }
                    });

                    // Añadir la capa al mapa
                    layer.addLayerToMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    };













    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        repositorio = new Repositorio(requireContext());
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        db = new DBManager(getActivity());

        switchCameras = (Switch) view.findViewById(R.id.switchCameras);
        switchIncidence = (Switch) view.findViewById(R.id.switchIncidences);


        // Configurar el mapa
         mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                mMap = googleMap; // Guardar referencia al mapa

                // Cuando el mapa esté listo (PASO 3 - INTERACCIÓN INICIAL)
                LatLng paisVasco = new LatLng(43.193902294642456, -1.7231874395612061);

                float zoomLevel = 6.0f;
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paisVasco, zoomLevel));

                // Verificar estado inicial del Switch
                if (switchCameras != null && switchCameras.isChecked()) {
                    añadirCamarasAlMapa(googleMap);
                }

                if(switchIncidence != null && switchIncidence.isChecked()){
                    anadirIncidenciasMapa(googleMap);


                }


            });
        }

        switchCameras.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(mMap!= null){

                if(isChecked){

                    añadirCamarasAlMapa(mMap);
                }
                else{

                    limpiarMarcadoresDeCamaras();


                }

            }

        });

        switchIncidence.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(mMap!= null){

                if(isChecked){

                    anadirIncidenciasMapa(mMap);
                }
                else{
                    limpiarMarcadoresDeIncidences();


                }

            }

        });

    }


    private void añadirCamarasAlMapa(GoogleMap googleMap) {
        new Thread(() -> {
            try {
                // Obtener las cámaras del repositorio
                List<Camara> camaras = repositorio.retornarListaCamaras();

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        // Crear un mapa de marcadores a cámaras
                        Map<Marker, Camara> markerCamaraMap = new HashMap<>();

                        for (Camara camara : camaras) {
                            LatLng posicion = new LatLng(camara.getLatitude(), camara.getLongitude());

                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(posicion)
                                    .title(camara.getCameraName())
                                    .snippet("Dirección: " + camara.getAddress() + "\nCarretera: " + camara.getRoad())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                            Marker marker = googleMap.addMarker(markerOptions);

                            if (marker != null) {
                                camaraMarkers.add(marker); // Añadir el marcador a la lista de marcadores de cámaras
                                markerCamaraMap.put(marker, camara); // Asociar el marcador con la cámara correspondiente
                            }
                        }

                        // Configurar el listener para el click en el marcador
                        googleMap.setOnInfoWindowClickListener(clickedMarker -> {
                            Camara camara = markerCamaraMap.get(clickedMarker); // Obtener la cámara asociada al marcador
                            if (camara != null) {
                                mostrarDialogoCamara(camara); // Mostrar el diálogo para la cámara seleccionada
                            }
                        });
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    private void limpiarMarcadoresDeCamaras() {
        for (Marker marker : camaraMarkers) {
            marker.remove(); // Eliminar el marcador del mapa
        }
        camaraMarkers.clear(); // Vaciar la lista de marcadores de cámaras
    }


    private void anadirIncidenciasMapa(GoogleMap googleMap) {
        new Thread(() -> {
            try {
                // Obtener las cámaras del repositorio
                List<Incidencia> incidencias = repositorio.retornarIncidenciasDeHoy();

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {

                        Map<Marker, Incidencia> markerIncidenciaMap = new HashMap<>();
                        for (Incidencia incidencia : incidencias) {
                            LatLng posicion = new LatLng(incidencia.getLatitude(), incidencia.getLongitude());

                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(posicion)
                                    .title(incidencia.getIncidenceType())
                                    .snippet("Dirección: " + incidencia.getCiudad() + "\nCarretera: " + incidencia.getRoad())
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                            Marker marker = googleMap.addMarker(markerOptions);
                            if (marker != null) {
                                incidenciaMarkers.add(marker); // Añadir el marcador a la lista de marcadores de cámaras
                                markerIncidenciaMap.put(marker, incidencia); // Asociar el marcador con la cámara correspondiente
                            }


                        }

                        // Configurar el listener para el click en el marcador
                        googleMap.setOnInfoWindowClickListener(clickedMarker -> {
                            Incidencia incidencia = markerIncidenciaMap.get(clickedMarker); // Obtener la cámara asociada al marcador
                            if (incidencia != null) {
                                mostrarDialogoIncidencia(incidencia); // Mostrar el diálogo para la cámara seleccionada
                            }
                        });

                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void limpiarMarcadoresDeIncidences() {
        for (Marker marker : incidenciaMarkers) {
            marker.remove(); // Eliminar el marcador del mapa
        }
        incidenciaMarkers.clear(); // Vaciar la lista de marcadores de cámaras
    }


    private void mostrarDialogoCamara(Camara camara) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.adaptercamara_incidence, null);

        TextView txtTitulo = dialogView.findViewById(R.id.txtTitulo);
        TextView txtDescripcion = dialogView.findViewById(R.id.txtDescripcion);
        TextView txtRoad = dialogView.findViewById(R.id.txtRoad);
        TextView txtKilometer = dialogView.findViewById(R.id.txtKilometer);
        ImageView imgFavorito = dialogView.findViewById(R.id.imgFavorito);

        txtTitulo.setText(camara.getCameraName());
        txtDescripcion.setText("Dirección: " + camara.getAddress());
        txtRoad.setText("Carretera: " + camara.getRoad());
        txtKilometer.setText("Kilometro: " + camara.getKilometer());

        // Si la cámara es ahora favorita, añadirla a la lista de favoritos
        if (camara.getFavorito()) {

            // Añadir a la lista de cámaras favoritas

            imgFavorito.setImageResource(R.drawable.star_on);

        } else {

            imgFavorito.setImageResource(R.drawable.star_of);
        }


        imgFavorito.setOnClickListener(v -> {


            if (camara.getFavorito()) {


                db.eliminarCamaraFavoritos(camara);
                imgFavorito.setImageResource(R.drawable.star_of);
                camara.setFavorito(false);

            } else {
                // Añadir a la lista de cámaras favoritas
                db.anadirCamaraFavoritos(camara);
                imgFavorito.setImageResource(R.drawable.star_on);
                camara.setFavorito(true);
            }




        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Configurar dimensiones después de mostrar el diálogo
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    private void mostrarDialogoIncidencia(Incidencia incidencia) {
        // Inflar el diseño del diálogo
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.adaptercamara_incidence, null);

        // Referencias a los elementos del diseño
        TextView txtTitulo = dialogView.findViewById(R.id.txtTitulo);
        TextView txtDescripcion = dialogView.findViewById(R.id.txtDescripcion);
        TextView txtRoad = dialogView.findViewById(R.id.txtRoad);
        TextView txtKilometer = dialogView.findViewById(R.id.txtKilometer);
        ImageView imgFavorito = dialogView.findViewById(R.id.imgFavorito);


        // Configurar valores de los campos
        txtTitulo.setText(incidencia.getCause());
        txtDescripcion.setText("Descripción: " + incidencia.getIncidenceType());

        txtKilometer.setText("Ciudad: " + incidencia.getCiudad());
        txtRoad.setText("Kilómetro: " + incidencia.getRoad());

        // Si la cámara es ahora favorita, añadirla a la lista de favoritos
        if (incidencia.isFavorito()) {

            // Añadir a la lista de cámaras favoritas

            imgFavorito.setImageResource(R.drawable.star_on);

        } else {

            imgFavorito.setImageResource(R.drawable.star_of);
        }

        // Configurar el botón de cerrar
        imgFavorito.setOnClickListener(v -> {
            // Si la cámara es ahora favorita, añadirla a la lista de favoritos

            if (incidencia.isFavorito()) {


                db.eliminarIncidenciaFavoritos(incidencia);
                imgFavorito.setImageResource(R.drawable.star_of);
                incidencia.setFavorito(false);

            } else {
                // Añadir a la lista de cámaras favoritas
                db.anadirIncidenciasFavoritos(incidencia);
                imgFavorito.setImageResource(R.drawable.star_on);
                incidencia.setFavorito(true);
            }

        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Configurar dimensiones después de mostrar el diálogo
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }



}