package com.ladlp.projet_final_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VoirCommandeActivity extends AppCompatActivity {
    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    ListView listview;
    MyArrayAdapter commandeArrayAdapter;
    ArrayList<Commande> commandeArray = new ArrayList<Commande>();
    Connection conn = null;
    int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_commande);
        Intent intent = getIntent();
        ID = intent.getIntExtra("ID", 0);
        gestionConnection();
        listview = (ListView)findViewById(R.id.listeCommClient);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Commande commande = (Commande)listview.getItemAtPosition(position);
                // START L'ACTIVITÉ POUR MODIFIER
                Intent intent = new Intent(VoirCommandeActivity.this, MapsActivity.class);
                intent.putExtra("ID",Integer.parseInt(commande.getId()));
                startActivity(intent);
            }
        });
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
                    conn.close();

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
            String sql = "exec ProjetDB.dbo.INFOCOMMANDE ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1,ID);
            ResultSet rs = stm.executeQuery();
            while(rs.next())
                commandeArray.add(new Commande(rs.getString("NomObjet"), rs.getInt("IdCommande"), rs.getDate("DateComm").toString(), rs.getInt("IdLivreur")));
           rafraichirIug();
        }catch(SQLException exc){}

    }
    public void rafraichirIug() {
        // place le Runnable dans la file d'attente du thread de l'IUG
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                commandeArrayAdapter = new MyArrayAdapter(VoirCommandeActivity.this, R.layout.item_liste_client_layout, commandeArray);
                listview.setItemsCanFocus(true);
                listview.setAdapter(commandeArrayAdapter);
            }
        });
    }



}
