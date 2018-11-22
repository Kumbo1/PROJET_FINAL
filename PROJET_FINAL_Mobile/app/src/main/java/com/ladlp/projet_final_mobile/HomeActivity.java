package com.ladlp.projet_final_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    String name;
    int ID;
    boolean isAdult;
    boolean estLivreur;
    TextView welcomeMess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        name = intent.getStringExtra("Name");
        ID = intent.getIntExtra("ID", 0);
        isAdult = intent.getBooleanExtra("estMajeur", false);
        estLivreur = intent.getBooleanExtra("estLivreur", false);
        if(estLivreur) {
            setContentView(R.layout.activity_home_livreur);
        }
        else {
            setContentView(R.layout.activity_home);
        }

    }
    public void startVoirCommClient(View view)
    {
        Intent intent = new Intent(this, VoirCommandeActivity.class);
        intent.putExtra("ID", ID);
        startActivity(intent);
    }

    public void startAjouterCommande(View view)
    {
        Intent intent = new Intent(this, Client_AjouterCommandeActivity.class);
        intent.putExtra("ID", ID);
        startActivity(intent);
    }

    public void startVoirCommandeLivreur(View view)
    {
        Intent intent = new Intent(this,VoirCommandeLivreur.class);
        intent.putExtra("ID",ID);
        startActivity(intent);
    }

    public void startVoirCommandeLivreurCourant(View view)
    {
        Intent intent = new Intent(this,LivreurCourantActivity.class);
        intent.putExtra("ID",ID);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(estLivreur)
            getMenuInflater().inflate(R.menu.menu_livreur, menu);
        else
            getMenuInflater().inflate(R.menu.menu_client, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_client_deco) {
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra("ID",ID);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_client_fairecommande) {
            Intent intent = new Intent(this, Client_AjouterCommandeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_client_mescommandes){
            Intent intent = new Intent(this, VoirCommandeActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_livreur_commandeencours){
            Intent intent = new Intent(this, LivreurCourantActivity.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_livreur_commandesdispo){
            Intent intent = new Intent(this, VoirCommandeLivreur.class);
            intent.putExtra("ID",ID);
            startActivity(intent);
        } else if (item.getItemId() == R.id.menu_livreur_deco){
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putExtra("ID",ID);
            startActivity(intent);
        } else if(item.getItemId() == R.id.menu_client_home || item.getItemId() == R.id.menu_livreur_home)
        {
           return true;
        }
        return true;
    }
}
