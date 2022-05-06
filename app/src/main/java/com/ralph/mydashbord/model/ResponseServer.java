package com.ralph.mydashbord.model;

public class ResponseServer {

    private String selected_date;
    private String jour;

    private String HEURE;
    private int NombreVendu;

    private double vola;
    private float boby;

    public float getBoby() {
        return boby;
    }

    public void setBoby(float boby) {
        this.boby = boby;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }
    public double getVola() {
        return vola;
    }

    public void setVola(double vola) {
        this.vola = vola;
    }

    public String getSelected_date() {
        return selected_date;
    }

    public void setSelected_date(String selected_date) {
        this.selected_date = selected_date;
    }

    public String getHEURE() {
        return HEURE;
    }

    public void setHEURE(String HEURE) {
        this.HEURE = HEURE;
    }

    public int getNombreVendu() {
        return NombreVendu;
    }

    public void setNombreVendu(int nombreVendu) {
        NombreVendu = nombreVendu;
    }

}
