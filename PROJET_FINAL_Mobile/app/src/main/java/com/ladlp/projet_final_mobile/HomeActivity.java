package com.ladlp.projet_final_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        if(estLivreur)
            setContentView(R.layout.activity_home_livreur);
        else
            setContentView(R.layout.activity_home);
        welcomeMess = findViewById(R.id.welcomeText);
        welcomeMess.setText(welcomeMess.getText().toString() + name);
    }
    void startVoirCommClient(View view)
    {
        Intent intent = new Intent(this, VoirCommandeActivity.class);
        intent.putExtra("ID", ID);
        startActivity(intent);
    }

    void startAjouterCommande(View view)
    {
        Intent intent = new Intent(this, AjouterCommandeActivity.class);
        intent.putExtra("ID", ID);
        startActivity(intent);
    }

    void startVoirCommandeLivreur(View view)
    {
        Intent intent = new Intent(this,VoirCommandeLivreur.class);
        intent.putExtra("ID",ID);
        startActivity(intent);
    }

    void startVoirCommandeLivreurCourant(View view)
    {
        Intent intent = new Intent(this,LivreurCourantActivity.class);
        intent.putExtra("ID",ID);
        startActivity(intent);
    }
}
