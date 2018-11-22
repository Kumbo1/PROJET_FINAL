package com.ladlp.projet_final_mobile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.ladlp.projet_final_mobile.R;

public class ArrayAdapterLivreurComm extends ArrayAdapter<CommandeLivreur> {
    Context context;
    int layoutResourceId;
    ArrayList<CommandeLivreur> commandesLivreur = new ArrayList<CommandeLivreur>();
    int IDLivreur;
    Connection conn = null;
    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    public ArrayAdapterLivreurComm(Context context, int layoutResourceId, ArrayList<CommandeLivreur> comm, int idLivreur) {
        super(context, layoutResourceId, comm);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.commandesLivreur = comm;
        this.IDLivreur = idLivreur;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ArrayAdapterLivreurComm.CommandeLivreurWrapper CommandeLivreurWrapper = null;
        if (item == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            item = inflater.inflate(layoutResourceId, parent, false);
            CommandeLivreurWrapper = new ArrayAdapterLivreurComm.CommandeLivreurWrapper();
            CommandeLivreurWrapper.nomobjet = (TextView) item.findViewById(R.id.itemLivreurComm_NomObjet);
            CommandeLivreurWrapper.infossup = (TextView) item.findViewById(R.id.itemLivreurComm_InfosSup);
            CommandeLivreurWrapper.date = (TextView) item.findViewById(R.id.itemLivreurComm_Date);
            CommandeLivreurWrapper.adresse = (TextView) item.findViewById(R.id.itemLivreurComm_Adresse);
            CommandeLivreurWrapper.associer = (ImageButton) item.findViewById(R.id.Associer_btn);
            item.setTag(CommandeLivreurWrapper);
        } else {
            CommandeLivreurWrapper = (ArrayAdapterLivreurComm.CommandeLivreurWrapper) item.getTag();
        }

        CommandeLivreur commandelivreur = commandesLivreur.get(position);
        CommandeLivreurWrapper.date.setText(commandelivreur.getDate());
        CommandeLivreurWrapper.nomobjet.setText(commandelivreur.getNomObjet());
        CommandeLivreurWrapper.infossup.setText(commandelivreur.getInfosSup());
        CommandeLivreurWrapper.adresse.setText(commandelivreur.getAdresse());
        CommandeLivreurWrapper.associer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(position);
            }
        });
        return item;
    }

    static class CommandeLivreurWrapper {
        TextView nomobjet;
        TextView infossup;
        TextView date;
        TextView adresse;
        ImageButton associer;

    }
    public void confirmDialog(final int idlist) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Êtes-vous sûr de vouloir prendre cette commande?")
                .setPositiveButton("Oui",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        gestionConnection(commandesLivreur.get(idlist).getIdCommande(), IDLivreur);
                        commandesLivreur.remove(idlist);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }
    void gestionConnection(int idcomm, int idlivreur){
        final int idcommande = idcomm;
        final int idlivreur1 = idlivreur;
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
                    String sql = "exec ProjetDB.dbo.associerlivreurcommande ?, ?";
                    PreparedStatement stm = conn.prepareCall(sql);
                    stm.setInt(1, idcommande);
                    stm.setInt(2, idlivreur1);
                    stm.executeUpdate();
                    LatLng posLivreur = getPosition();
                    String sql1 = "update ProjetDB.dbo.MAP set longLivreur = " + posLivreur.longitude + ", latLivreur = " + posLivreur.latitude + "where idCommande = " + idcommande + ";";
                    Statement stm1 = conn.createStatement();
                    stm1.execute(sql1);
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
    public LatLng getPosition()
    {
        Location location = null;
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }
        //if condition to check if GPS is available
        if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Log.d("TEST", Double.toString(location.getLatitude()) + " " + Double.toString(location.getLongitude()));

        }
        else if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Log.d("TEST", Double.toString(location.getLatitude()) + " " + Double.toString(location.getLongitude()));

        }
        LatLng temp = new LatLng(location.getLatitude(), location.getLongitude());
        return temp;
    }
}
