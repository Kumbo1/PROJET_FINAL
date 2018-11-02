package com.ladlp.projet_final_mobile;

public class CommandeLivreur {
    private String NomObjet;
    private String InfosSup;
    private String Date;
    private String Adresse;
    private int IdCommande;

    public CommandeLivreur(String NomObjet, String InfosSup, String Date, String Adresse, int idCommande)
    {
        super();
        this.NomObjet = NomObjet;
        this.InfosSup = InfosSup;
        this.Date = Date;
        this.Adresse = Adresse;
        this.IdCommande = idCommande;
    }
    public String getNomObjet(){return NomObjet;}
    public String getInfosSup(){return InfosSup;}
    public String getDate(){return Date;}
    public String getAdresse(){return Adresse;}
    public int getIdCommande(){return IdCommande;}
}
