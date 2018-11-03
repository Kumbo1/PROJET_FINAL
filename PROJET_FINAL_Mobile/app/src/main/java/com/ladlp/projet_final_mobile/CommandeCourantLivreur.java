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

public class CommandeCourantLivreur extends AppCompatActivity {

    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    ListView listview;
    ArrayAdapterLivreurCourant commandeLivreurArrayAdapter;
    ArrayList<LivreurClassCourant> commandeLivreurArray = new ArrayList<>();
    Connection conn = null;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande_courant_livreur);
        Intent intent = getIntent();
        ID = intent.getIntExtra("ID", 0);
        gestionConnection();
        listview = findViewById(R.id.listeCourantLivreur);
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
            String sql = "exec ProjetDB.dbo.VoirCourantLivreur ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1,ID);
            ResultSet rs = stm.executeQuery();
            while(rs.next())
                commandeLivreurArray.add(new LivreurClassCourant(rs.getString("PrenomClient"), rs.getString("NomClient"),rs.getString("InfosSup"), rs.getDate("DateComm").toString(), rs.getInt("idcommande")));
            rafraichirIug();
        }catch(SQLException exc){}

    }
    public void rafraichirIug() {
        // place le Runnable dans la file d'attente du thread de l'IUG
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                commandeLivreurArrayAdapter = new ArrayAdapterLivreurCourant(CommandeCourantLivreur.this, R.layout.item_liste_livreurcourant, commandeLivreurArray, ID);
                listview.setItemsCanFocus(false);
                listview.setAdapter(commandeLivreurArrayAdapter);
            }
        });
    }
}
