package com.ladlp.projet_final_mobile;

public class CommandeLivreur {
    private String NomObjet;
    private String InfosSup;
    private String Date;
    private String Adresse;

    public CommandeLivreur(String NomObjet, String InfosSup, String Date, String Adresse)
    {
        super();
        this.NomObjet = NomObjet;
        this.InfosSup = InfosSup;
        this.Date = Date;
        this.Adresse = Adresse;
    }
    public String getNomObjet(){return NomObjet;}
    public String getInfosSup(){return InfosSup;}
    public String getDate(){return Date;}
    public String getAdresse(){return Adresse;}
}
