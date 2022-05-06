package com.ralph.mydashbord.model;

import java.util.Date;

public class Vente {

    private int vente_id;
    private String nom_client;
    private String produit_vendue;
    private double prix_unitaire;
    private int quantiter_vendue;
    private Date date_achat;

    /* ------------------- Generate Geters ----------------------------*/

    public int getVente_id() {
        return vente_id;
    }

    public String getNom_client() {
        return nom_client;
    }

    public String getProduit_vendue() {
        return produit_vendue;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public int getQuantiter_vendue() {
        return quantiter_vendue;
    }

    public Date getDate_achat() {
        return date_achat;
    }
}
