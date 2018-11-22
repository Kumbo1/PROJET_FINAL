package com.ladlp.projet_final_mobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {


    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    Connection conn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] perm = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };
             ActivityCompat.requestPermissions(this, perm, 1);
        }
    }


    public void startRegister(View view)
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void startLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
