package com.ladlp.projet_final_mobile;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    Connection conn = null;
    EditText password;
    EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gestionConnection();
        getFields();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    void connecterClient(View view)
    {
        if(!password.getText().toString().isEmpty() && !username.getText().toString().isEmpty())
        {
            try{
                String sql = "select * from ProjetDB.dbo.Clients WHERE MotDePasse = ? and Username = ?";
                PreparedStatement login = conn.prepareStatement(sql);
                login.setString(1, password.getText().toString());
                login.setString(2, username.getText().toString());
                ResultSet rs = login.executeQuery();
                if(rs.next())
                    Toast.makeText(this,"Youpi", Toast.LENGTH_LONG).show();
                //START Activity
                else
                    Toast.makeText(this,"Le nom d'utilisateur ou le mot de passe est invalide", Toast.LENGTH_LONG).show();
            }catch(SQLException exc){
            }
        }
        else
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
    }
    void connecterLivreur(View view)
    {
        if(!password.getText().toString().isEmpty() && !username.getText().toString().isEmpty())
        {
            try{
                String sql = "select * from ProjetDB.dbo.Livreurs WHERE motdepasse = ? and username = ?";
                PreparedStatement login = conn.prepareStatement(sql);
                login.setString(1, password.getText().toString());
                login.setString(2, username.getText().toString());
                ResultSet rs = login.executeQuery();
                if(rs.next())
                    Toast.makeText(this,"Youpi", Toast.LENGTH_LONG).show();
                    //START Activity
                else
                    Toast.makeText(this,"Le nom d'utilisateur ou le mot de passe est invalide", Toast.LENGTH_LONG).show();
            }catch(SQLException exc){

            }
        }
        else
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
    }
    void getFields()
    {
        password = findViewById(R.id.pwdLogin);
        username = findViewById(R.id.usernameLogin);
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

                } catch (SQLException se) {
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {}
            }
        };
        thread.start();
    }
}
