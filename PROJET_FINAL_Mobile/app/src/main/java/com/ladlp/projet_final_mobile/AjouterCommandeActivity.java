package com.ladlp.projet_final_mobile;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class AjouterCommandeActivity extends AppCompatActivity {

    EditText infosSup;
    CheckBox estMajeur;
    EditText adresse;
    EditText codePostal;
    EditText nomObjet;
    EditText prixApprox;
    Spinner categorie;
    Spinner city;
    Connection conn = null;
    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
<<<<<<< HEAD
    ArrayList<String> arcity = new ArrayList<String>();
    String[] arraycity = null;
    ArrayList<String> arcateg = new ArrayList<>();
    String[] arraycateg = null;

=======
    ArrayList<String> ar = new ArrayList<String>();
    String[] array = null;
    int ID;
>>>>>>> 1cde4710510f95ca4909cd7a58b41b11f1ef88c4


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_commande);
        getFields();
        gestionConnection();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent = getIntent();
        ID = intent.getIntExtra("ID",0);
    }

    void ajouterLaCommande(View view)    {
        if (checkAllFilled(new EditText[] {infosSup,adresse,codePostal,nomObjet,prixApprox})) {
            insertCommande();
            Toast.makeText(this, "Commande inserer avec succes", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
        }
    }
    void getFields()    {
        infosSup = findViewById(R.id.AC_infoSupp);
        estMajeur = findViewById(R.id.AC_estMajeur);
        adresse = findViewById(R.id.AC_adresseLivraison);
        codePostal = findViewById(R.id.AC_codePostale);
        nomObjet = findViewById(R.id.AC_nomObjet);
        prixApprox = findViewById(R.id.AC_prixObjet);
        categorie = findViewById(R.id.AC_spinnercat);
        city = findViewById(R.id.AC_spinnercity);
    }
    void gestionConnection() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                }
                try {
                    conn = DriverManager.getConnection(connectionUrl);
                    Statement stm = conn.createStatement();
                    ResultSet rs = stm.executeQuery("SELECT NOMVILLE FROM ProjetDB.dbo.VILLES");
                    while(rs.next())
                    {
                        arcity.add(rs.getString("NOMVILLE"));
                    }
                    arraycity = arcity.toArray(new String[0]);
                    ResultSet rs1 = stm.executeQuery("SELECT NOMCATEGORIE FROM ProjetDB.dbo.CATEGORIES");
                    while(rs1.next())
                    {
                        arcateg.add(rs1.getString("NOMCATEGORIE"));
                    }
                    arraycateg = arcateg.toArray(new String[0]);
                    refreshUI();


                } catch (SQLException se) {
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        };
        thread.start();

    }
    public void refreshUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter NoCoreAdapter = new ArrayAdapter(AjouterCommandeActivity.this,android.R.layout.simple_list_item_1, arraycity);
                city.setAdapter(NoCoreAdapter);
                ArrayAdapter NoCoreAdapter1 = new ArrayAdapter(AjouterCommandeActivity.this,android.R.layout.simple_list_item_1, arraycateg);
                categorie.setAdapter(NoCoreAdapter1);
            }
        });
    }
    boolean checkAllFilled(EditText[] listField){
        for(int i = 0; i < listField.length; i++){
            EditText currentField = listField[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        return true;
    }
    void insertCommande(){
        String proc =  "exec ProjetDB.dbo.AjoutCommande ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        //idclient,infosup,nomobjet,prix,categorie,estmajeur,adresse,ville,codepostal,temps
        Date date = new Date();
        try {
            PreparedStatement ajoutcommande = conn.prepareStatement(proc);
            ajoutcommande.setInt(1, ID);
            ajoutcommande.setString(2, infosSup.getText().toString());
            ajoutcommande.setString(3, nomObjet.getText().toString());
            ajoutcommande.setString(4, prixApprox.getText().toString());
            ajoutcommande.setString(5, categorie.getSelectedItem().toString());
            ajoutcommande.setBoolean(6, estMajeur.isChecked());
            ajoutcommande.setString(7, adresse.getText().toString());
            ajoutcommande.setString(8, city.getSelectedItem().toString());
            ajoutcommande.setString(9, codePostal.getText().toString());
            ajoutcommande.setTimestamp(10, new java.sql.Timestamp(date.getTime()));
            ajoutcommande.execute();
            //PARTIR ACTIVITY
        } catch (SQLException exc) {
            Toast.makeText(this, exc.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
