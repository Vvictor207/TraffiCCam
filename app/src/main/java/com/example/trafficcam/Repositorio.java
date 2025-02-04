package com.example.trafficcam;

import android.content.Context;
import android.util.Log;

import com.example.trafficcam.BD.DBManager;
import com.example.trafficcam.Objects.Incidencia;
import com.example.trafficcam.Objects.Camara;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Repositorio {


    final  String IPClass = "http://10.10.13.174:8080/";

    final  String IPHOUSE = "http://192.168.1.55:8080/";

    private DBManager dbManager;
    public Repositorio(Context context) {
        this.dbManager = new DBManager(context); // Evita leaks
    }

//private DBManager dbManager = new DBManager();
//URL url = new URL("http://10.10.13.154:8080/login");

    public boolean comprobacionUsuario(String username, String password) {
        HttpURLConnection urlConnection = null;

        try {


            // Configurar la conexión
            URL url = new URL(IPClass + "login");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);

            // Crear un objeto JSON con los datos del usuario
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("name", username); // Asegúrate de que los nombres de campo coincidan con tu API
            jsonInput.put("password", password);

            // Escribir los datos JSON en el cuerpo de la solicitud
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonInput.toString().getBytes("utf-8"));
            os.close();

            // Obtener la respuesta del servidor
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == 200) {
                // Leer la respuesta JSON
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Analizar el JSON de respuesta
                JSONObject jsonResponse = new JSONObject(response.toString());
                //jsonResponse.getBoolean("valid"); // Asegúrate de que el campo "valid" exista y sea booleano
                // Comprobar si la respuesta indica éxito
                return true;

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return false; // Por defecto, devolver falso si algo falla
    }


    public List<Camara>  retornarListaCamaras (){


        HttpURLConnection urlConnection = null;
        List<Camara> camaras = new ArrayList<>();

        try {
            // Configurar la conexión
            URL url = new URL(IPClass + "cameras"); // Asegúrate de que la ruta sea correcta
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");

            // Obtener la respuesta del servidor
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == 200) {
                // Leer la respuesta JSON
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Analizar el JSON de respuesta
                JSONArray jsonResponse = new JSONArray(response.toString());

                // Recorrer el array de cámaras
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject camaraJson = jsonResponse.getJSONObject(i);

                    // Obtener los datos de la cámara
                    int id = camaraJson.getInt("id");
                    String cameraName = camaraJson.getString("cameraName");
                    double latitude = camaraJson.getDouble("latitude");
                    double longitude = camaraJson.getDouble("longitude");
                    String road = camaraJson.getString("road");
                    String kilometer = camaraJson.getString("kilometer");
                    String address = camaraJson.getString("address");
                    String urlImage = camaraJson.getString("urlImage");





                    // Obtener el objeto "source"
                    JSONObject sourceJson = camaraJson.getJSONObject("source");
                    int sourceId = sourceJson.getInt("id");
                    String sourceNombre = sourceJson.getString("nombre");

                    // Crear los objetos
                    Camara.Source source = new Camara.Source(sourceId, sourceNombre);

                    //**Comprobar si la cámara ya está en favoritos**
                    boolean esFavorita = dbManager.existeCamara(id);

                    Camara camara = new Camara(id, cameraName, latitude, longitude, road, kilometer, address, urlImage, source, esFavorita);

                    camaras.add(camara);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return camaras; // Devuelve la lista de cámaras (puede estar vacía si algo falla)
    }

    public  List<Incidencia> retornarIncidenciasDeHoy(){
        HttpURLConnection urlConnection = null;
        List<Incidencia> incidencias = new ArrayList<>();

        try {
            // Configurar la conexión
            URL url = new URL(IPClass + "incidences"); // Asegúrate de que la ruta sea correcta
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");

            // Obtener la respuesta del servidor
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == 200) {
                // Leer la respuesta JSON
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                Log.d("JSON Check", "Entrando a la respuesta JSON");
                Log.d("JSON Response", response.toString());


                // Analizar el JSON de respuesta
                JSONArray jsonResponse = new JSONArray(response.toString());

                // Recorrer el array de incidencias
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject incidenciaJson = jsonResponse.getJSONObject(i);

                    // Obtener los datos de la incidencia
                    int id = incidenciaJson.getInt("id");
                    boolean creado = incidenciaJson.getBoolean("creado");
                    int incidenceId = incidenciaJson.getInt("incidenceId");
                    String incidenceType = incidenciaJson.getString("incidenceType");
                    String autonomousRegion = incidenciaJson.getString("autonomousRegion");
                    String province = incidenciaJson.getString("province");
                    String carRegistration = incidenciaJson.optString("carRegistration");
                    String cause = incidenciaJson.getString("cause");
                    String cityTown = incidenciaJson.getString("cityTown");
                    String startDate = incidenciaJson.getString("startDate");
                    String endDate = incidenciaJson.getString("endDate");
                    String incidenceLevel = incidenciaJson.optString("incidenceLevel");
                    String road = incidenciaJson.optString("road");
                    int pkStart = incidenciaJson.getInt("pkStart");
                    int pkEnd = incidenciaJson.getInt("pkEnd");
                    String direction = incidenciaJson.getString("direction");
                    double latitude = incidenciaJson.getDouble("latitude");
                    double longitude = incidenciaJson.getDouble("longitude");

                    // Obtener el objeto "source"
                    JSONObject sourceJson = incidenciaJson.getJSONObject("source");
                    int sourceId = sourceJson.getInt("id");
                    String sourceNombre = sourceJson.getString("nombre");
                    Incidencia.Source source = new Incidencia.Source(sourceId, sourceNombre);

                    String ciudad = incidenciaJson.optString("ciudad");

                    boolean esFavorita = dbManager.existeIncidence(id);

                    // Crear el objeto Incidencia
                    Incidencia incidencia = new Incidencia(id, creado, incidenceId, incidenceType, autonomousRegion, province,
                            carRegistration, cause, cityTown, startDate, endDate, incidenceLevel, road, pkStart, pkEnd,
                            direction, latitude, longitude, source, ciudad, esFavorita);

                    // Añadir a la lista
                    incidencias.add(incidencia);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return incidencias; // Devuelve la lista de incidencias (puede estar vacía si algo falla)
    }


    public boolean registrarUsuario(String username, String password) {
        HttpURLConnection urlConnection = null;

        try {
            // Configurar la conexión para la URL de registro
            URL url = new URL(IPClass + "register");  // Suponiendo que la URL para registro es "register"
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setDoOutput(true);

            // Crear un objeto JSON con los datos del usuario
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("name", username); // Nombre de usuario
            jsonInput.put("password", password); // Contraseña

            // Escribir los datos JSON en el cuerpo de la solicitud
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonInput.toString().getBytes("utf-8"));
            os.close();

            // Obtener la respuesta del servidor
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == 201) {  // 201 indica que el recurso se creó correctamente
                // Leer la respuesta JSON
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Analizar el JSON de respuesta
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Aquí puedes agregar alguna lógica para verificar la respuesta del servidor
                // Por ejemplo, si el servidor devuelve un campo "success" con valor true
                return jsonResponse.getBoolean("success");  // Asegúrate de que el campo "success" exista y sea booleano

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return false;  // Si algo falla, devuelve false
    }








}