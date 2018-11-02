package com.ladlp.projet_final_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class VoirCommandeLivreur extends AppCompatActivity {

    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    ListView listview;
    ArrayAdapterLivreurComm commandeLivreurArrayAdapter;
    ArrayList<CommandeLivreur> commandeLivreurArray = new ArrayList<CommandeLivreur>();
    Connection conn = null;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_commande_livreur);
        gestionConnection();
        listview = findViewById(R.id.listeCommLivreur);
    }

    void gestionConnection(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                } catch (ClassNotFoundException e){
                }
                try
                {
                    conn = DriverManager.getConnection(connectionUrl);
                    getList();

                } catch (SQLException se) {
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {System.out.print(ie.toString());}

            }
        };
        thread.start();
    }
    void getList()
    {
        try
        {
            String sql = "exec ProjetDB.dbo.VoirCommandeLivreur";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next())
                commandeLivreurArray.add(new CommandeLivreur(rs.getString("NomObjet"), rs.getString("InfosSup"), rs.getDate("DateComm").toString(), rs.getString("Adresse")));
            rafraichirIug();
        }catch(SQLException exc){}

    }
    public void rafraichirIug() {
        // place le Runnable dans la file d'attente du thread de l'IUG
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                commandeLivreurArrayAdapter = new ArrayAdapterLivreurComm(VoirCommandeLivreur.this, R.layout.item_liste_livreurcomm, commandeLivreurArray);
                listview.setItemsCanFocus(false);
                listview.setAdapter(commandeLivreurArrayAdapter);
            }
        });
    }
}
