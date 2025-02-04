package com.example.trafficcam.Objects;

public class Incidencia {

    private int id;
    private boolean creado;
    private int incidenceId;
    private String incidenceType;
    private String autonomousRegion;
    private String province;
    private String carRegistration;
    private String cause;
    private String cityTown;
    private String startDate;
    private String endDate;
    private String incidenceLevel;
    private String road;
    private int pkStart;
    private int pkEnd;
    private String direction;
    private double latitude;
    private double longitude;
    private Source source;
    private String ciudad;


    private  boolean favorito;

    public Incidencia(int id, boolean creado, int incidenceId, String incidenceType, String autonomousRegion, String province,
                      String carRegistration, String cause, String cityTown, String startDate, String endDate,
                      String incidenceLevel, String road, int pkStart, int pkEnd, String direction, double latitude,
                      double longitude, Source source, String ciudad ,  boolean favorito) {
        this.id = id;
        this.creado = creado;
        this.incidenceId = incidenceId;
        this.incidenceType = incidenceType;
        this.autonomousRegion = autonomousRegion;
        this.province = province;
        this.carRegistration = carRegistration;
        this.cause = cause;
        this.cityTown = cityTown;
        this.startDate = startDate;
        this.endDate = endDate;
        this.incidenceLevel = incidenceLevel;
        this.road = road;
        this.pkStart = pkStart;
        this.pkEnd = pkEnd;
        this.direction = direction;
        this.latitude = latitude;
        this.longitude = longitude;
        this.source = source;
        this.ciudad = ciudad;
        this.favorito = favorito;
    }

    public Incidencia(int anInt, String string, String string1, String string2, String string3) {
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean isCreado() { return creado; }
    public void setCreado(boolean creado) { this.creado = creado; }

    public int getIncidenceId() { return incidenceId; }
    public void setIncidenceId(int incidenceId) { this.incidenceId = incidenceId; }

    public String getIncidenceType() { return incidenceType; }
    public void setIncidenceType(String incidenceType) { this.incidenceType = incidenceType; }

    public String getAutonomousRegion() { return autonomousRegion; }
    public void setAutonomousRegion(String autonomousRegion) { this.autonomousRegion = autonomousRegion; }

    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }

    public String getCarRegistration() { return carRegistration; }
    public void setCarRegistration(String carRegistration) { this.carRegistration = carRegistration; }

    public String getCause() { return cause; }
    public void setCause(String cause) { this.cause = cause; }

    public String getCityTown() { return cityTown; }
    public void setCityTown(String cityTown) { this.cityTown = cityTown; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getIncidenceLevel() { return incidenceLevel; }
    public void setIncidenceLevel(String incidenceLevel) { this.incidenceLevel = incidenceLevel; }

    public String getRoad() { return road; }
    public void setRoad(String road) { this.road = road; }

    public int getPkStart() { return pkStart; }
    public void setPkStart(int pkStart) { this.pkStart = pkStart; }

    public int getPkEnd() { return pkEnd; }
    public void setPkEnd(int pkEnd) { this.pkEnd = pkEnd; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public Source getSource() { return source; }
    public void setSource(Source source) { this.source = source; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public static class Source {
        private int id;
        private String nombre;

        public Source(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }

    public boolean isFavorito() {
        return favorito;
    }


    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public Incidencia(int id, String incidenceType, String cause, String road) {
        this.id = id;
        this.incidenceType = incidenceType;
        this.cause = cause;
        this.road = road;
    }


}
