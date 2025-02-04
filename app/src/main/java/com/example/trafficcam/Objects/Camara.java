package com.example.trafficcam.Objects;

public class Camara {

    private int id;
    private String cameraName;
    private double latitude;
    private double longitude;
    private String road;
    private String kilometer;
    private String address;
    private String urlImage;
    private Source source;
    private Boolean favorito;

    // Constructor
    public Camara(int id, String cameraName, double latitude, double longitude, String road,
                  String kilometer, String address, String urlImage, Source source , boolean favorito) {
        this.id = id;
        this.cameraName = cameraName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.road = road;
        this.kilometer = kilometer;
        this.address = address;
        this.urlImage = urlImage;
        this.source = source;
        this.favorito = favorito;
    }

    public Camara(int id, String cameraName, String road, String kilometer, String address) {
        this.id = id;
        this.cameraName = cameraName;
        this.road = road;
        this.kilometer = kilometer;
        this.address = address;

    }
// Getters y Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getKilometer() {
        return kilometer;
    }

    public void setKilometer(String kilometer) {
        this.kilometer = kilometer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }



    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    // Clase anidada para el atributo 'source'
    public static class Source {
        private int id;
        private String nombre;

        // Constructor
        public Source(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        // Getters y Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }



}
