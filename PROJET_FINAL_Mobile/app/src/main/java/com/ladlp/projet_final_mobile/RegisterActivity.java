package com.ladlp.projet_final_mobile;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText password;
    EditText passwordConfirm;
    EditText username;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText address;
    EditText phoneNumber;
    EditText zipCode;
    CheckBox isAdult;
    Spinner city;
    Connection conn = null;
    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    ArrayList<String> ar = new ArrayList<String>();
    String[] array = null;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getFields();
        phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        gestionConnection();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    void registerClient(View view){
        if(checkAllFilled(new EditText[] {passwordConfirm, password, username, firstName, lastName, email, address, phoneNumber, zipCode}))
        {
            if(checkEmail()) {
                if(checkZip()) {
                    if (checkPassword()) {
                        boolean existe = false;
                        try {
                            Statement stm = conn.createStatement();
                            ResultSet rs = stm.executeQuery("Select * from ProjetDB.dbo.Clients where Username = '" + username.getText().toString() + "'");
                            while (rs.next())
                                existe = true;
                        } catch (SQLException exc) { }
                        if (!existe) {
                            insertClient();
                            getIDClient();
                            Intent intent = new Intent(this, HomeActivity.class);
                            intent.putExtra("Name", firstName.getText().toString());
                            intent.putExtra("estMajeur", isAdult.isChecked());
                            intent.putExtra("ID", id);
                            intent.putExtra("estLivreur", false);
                            startActivity(intent);
                        } else
                            Toast.makeText(this, "Le nom d'identifiant est déjà utilisé", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(this, "Le code postal est invalide", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this,"Le format du email doit etre 'exemple@exemple.com'", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this,"Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();

    }
    void registerLivreur(View view){
        if(checkAllFilled(new EditText[] {passwordConfirm, password, username, firstName, lastName, email, address, phoneNumber, zipCode}))
        {
            if(checkEmail()) {
                if(checkZip()) {
                    if (checkPassword()) {
                        boolean existe = false;
                        try {
                            Statement stm = conn.createStatement();
                            ResultSet rs = stm.executeQuery("Select * from ProjetDB.dbo.Livreurs where Username = '" + username.getText().toString() + "'");
                            while (rs.next())
                                existe = true;
                        } catch (SQLException exc) {
                        }
                        if (!existe) {
                            insertLivreur();
                            getIDLivreur();
                            Intent intent = new Intent(this, HomeActivity.class);
                            intent.putExtra("Name", firstName.getText().toString());
                            intent.putExtra("estMajeur", isAdult.isChecked());
                            intent.putExtra("ID", id);
                            intent.putExtra("estLivreur", true);
                            startActivity(intent);
                        } else
                            Toast.makeText(this, "Le nom d'identifiant est déjà utilisé", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(this, "Le code postal est invalide", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this,"Le format du email doit etre 'exemple@exemple.com'", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(this,"Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();

    }
    boolean checkZip(){
        String regex = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zipCode.getText().toString());
        return matcher.matches();
    }
    boolean checkEmail(){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }
    boolean checkPassword(){
        return password.getText().toString().equals(passwordConfirm.getText().toString());
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
    void getFields(){
        password = findViewById(R.id.pwd);
        passwordConfirm = findViewById(R.id.pwdConfirm);
        username = findViewById(R.id.username);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phone);
        zipCode = findViewById(R.id.zipCode);
        isAdult = findViewById(R.id.isAdult);
        city = findViewById(R.id.citySpinner);
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
                    Statement stm = conn.createStatement();
                    ResultSet rs = stm.executeQuery("SELECT NOMVILLE FROM ProjetDB.dbo.VILLES");
                    while(rs.next())
                    {
                        ar.add(rs.getString("NOMVILLE"));
                    }
                    array = ar.toArray(new String[0]);
                    refreshUI();

                } catch (SQLException se) {
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {}
            }
        };
        thread.start();
    }
    public void refreshUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter NoCoreAdapter = new ArrayAdapter(RegisterActivity.this,android.R.layout.simple_list_item_1, array);
                city.setAdapter(NoCoreAdapter);
            }
        });
    }
    void insertLivreur(){
        String proc =  "exec ProjetDB.dbo.INSERTLIVREURS ?, ?, ?, ?, ?, ?, ?";
        //prenom,nom,ville,username,adresse,zip,phone,adult,admin,mdp,email
        try {
            PreparedStatement register = conn.prepareStatement(proc);
            register.setString(1, firstName.getText().toString());
            register.setString(2, lastName.getText().toString());
            register.setString(3, username.getText().toString());
            register.setString(4, phoneNumber.getText().toString());
            register.setBoolean(5, isAdult.isChecked());
            register.setString(6, password.getText().toString());
            register.setString(7, email.getText().toString());
            register.execute();
        } catch (SQLException exc) {
            Toast.makeText(this, exc.toString(), Toast.LENGTH_LONG).show();
        }
    }
    void insertClient(){
        String proc =  "exec ProjetDB.dbo.INSERTCLIENTS ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        //prenom,nom,ville,username,adresse,zip,phone,adult,admin,mdp,email
        try {
            PreparedStatement register = conn.prepareStatement(proc);
            register.setString(1, firstName.getText().toString());
            register.setString(2, lastName.getText().toString());
            register.setString(3, city.getSelectedItem().toString());
            register.setString(4, username.getText().toString());
            register.setString(5, address.getText().toString());
            register.setString(6, zipCode.getText().toString());
            register.setString(7, phoneNumber.getText().toString());
            register.setBoolean(8, isAdult.isChecked());
            register.setBoolean(9, false);
            register.setString(10, password.getText().toString());
            register.setString(11, email.getText().toString());
            register.execute();
            //PARTIR ACTIVITY
        } catch (SQLException exc) {
            Toast.makeText(this, exc.toString(), Toast.LENGTH_LONG).show();
        }
    }
    void getIDClient()
    {
        String sql = "SELECT idClient from ProjetDB.dbo.Clients where Username = '" + username.getText().toString() + "' AND MotDePasse = '" + password.getText().toString() + "'";
        try
        {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next())
               id = rs.getInt("idClient");
        }
        catch (SQLException e )
        {
            e.printStackTrace();
        }

    }
    void getIDLivreur()
    {
        String sql = "SELECT idLivreur from ProjetDB.dbo.Livreurs where Username = '" + username.getText().toString() + "' AND MotDePasse = '" + password.getText().toString() + "'";
        try
        {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next())
                id = rs.getInt("idLivreur");
        }
        catch (SQLException e )
        {
            e.printStackTrace();
        }

    }
}
