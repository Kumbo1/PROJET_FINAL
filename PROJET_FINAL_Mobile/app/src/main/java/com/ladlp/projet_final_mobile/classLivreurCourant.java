package com.ladlp.projet_final_mobile;

public class classLivreurCourant {

    private String Prenom;
    private String Nom;
    private String InfosSup;
    private String Date;
    private int IdCommande;

    public classLivreurCourant(String Prenom, String Nom, String InfosSup, String Date, int idCommande)
    {
        super();
        this.Prenom = Prenom;
        this.Nom = Nom;
        this.InfosSup = InfosSup;
        this.Date = Date;
        this.IdCommande = idCommande;
    }


    public String getPrenom(){return Prenom;}
    public String getNom(){return Nom;}
    public String getInfosSup(){return InfosSup;}
    public String getDate(){return Date;}
    public int getIdCommande(){return IdCommande;}
}
