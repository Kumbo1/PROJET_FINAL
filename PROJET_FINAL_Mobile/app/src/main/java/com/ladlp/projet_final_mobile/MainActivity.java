package com.ladlp.projet_final_mobile;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {

    TextView test;
    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    Connection conn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        test = findViewById(R.id.test);
        getConnection();
        afficherqqch();
    }
//YO
    public void getConnection() {
        new Thread( new Runnable() {
            public void run() {
                try{
                    Class.forName("net.sourceforge.jtds.jdbc.Driver");
                } catch(ClassNotFoundException e){}
                try{
                    conn = DriverManager.getConnection(connectionUrl);
                } catch(SQLException e){}
            }
        }).start();
    }
    void afficherqqch()
    {
        boolean fuck = true;
        while(fuck)
        {
            if(conn != null)
            {
                fuck = false;
                try
                {

                    String sql2 = "select * from ProjetDB.dbo.Commandes" ;
                    PreparedStatement pstm = conn.prepareStatement(sql2);
                    ResultSet rst1 = pstm.executeQuery();
                    while (rst1.next()) {
                        test.setText(test.getText() + "\n" + rst1.getString(4));
                    }
                } catch (SQLException se) {
                        test.setText(se.toString());
                }
            }

        }


}
}
