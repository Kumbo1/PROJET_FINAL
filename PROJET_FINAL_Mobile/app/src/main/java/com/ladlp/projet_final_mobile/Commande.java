package com.ladlp.projet_final_mobile;

import java.util.Date;

public class Commande {
    private String objet;
    private int id;
    private String date;
    private int idLivreur;


    public Commande(String Objet, int Id, String Date, int IdLivreur) {
        super();
        this.objet = Objet;
        this.id = Id;
        this.date = Date;
        this.idLivreur = IdLivreur;
    }
    public String getObjet() {
        return objet;
    }
    public String getId() { return Integer.toString(id); }
    public String getDate() {
        return date;
    }
    public int getIdLivreur() { return idLivreur; }
}
