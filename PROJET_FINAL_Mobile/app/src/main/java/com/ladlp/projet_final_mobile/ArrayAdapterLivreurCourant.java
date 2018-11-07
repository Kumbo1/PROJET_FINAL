package com.ladlp.projet_final_mobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArrayAdapterLivreurCourant extends ArrayAdapter<classLivreurCourant> {
    Context context;
    int layoutResourceId;
    ArrayList<classLivreurCourant> commandesLivreurCourant = new ArrayList<classLivreurCourant>();
    int IDLivreur;
    Connection conn = null;
    String connectionUrl = "jdbc:jtds:sqlserver://dbprojetfinal.czcjxlu56660.ca-central-1.rds.amazonaws.com:8080;database=ProjetDB;user=Master;password=Master123;";
    public ArrayAdapterLivreurCourant(Context context, int layoutResourceId, ArrayList<classLivreurCourant> comm, int idLivreur) {
        super(context, layoutResourceId, comm);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.commandesLivreurCourant = comm;
        this.IDLivreur = idLivreur;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ArrayAdapterLivreurCourant.CommandeLivreurCourantWrapper wrapper = null;
        if (item == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            item = inflater.inflate(layoutResourceId, parent, false);

            wrapper = new ArrayAdapterLivreurCourant.CommandeLivreurCourantWrapper();
            wrapper.Prenom = (TextView) item.findViewById(R.id.itemLivreurCourant_Prenom);
            wrapper.Nom = (TextView) item.findViewById(R.id.itemLivreurCourant_Nom);
            wrapper.date = (TextView) item.findViewById(R.id.itemLivreurCourant_Date);
            wrapper.infossup = (TextView) item.findViewById(R.id.itemLivreurCourant_InfosSup);
            wrapper.fermer = (Button) item.findViewById(R.id.Fermer_btn);
            item.setTag(wrapper);
        } else {
            wrapper = (ArrayAdapterLivreurCourant.CommandeLivreurCourantWrapper) item.getTag();
        }

        classLivreurCourant commandelivreur = commandesLivreurCourant.get(position);
        wrapper.Prenom.setText(commandelivreur.getPrenom());
        wrapper.Nom.setText(commandelivreur.getNom());
        wrapper.date.setText(commandelivreur.getDate());
        wrapper.infossup.setText(commandelivreur.getInfosSup());
        wrapper.fermer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog(position);
            }
        });
        return item;
    }

    static class CommandeLivreurCourantWrapper {
        TextView Prenom;
        TextView Nom;
        TextView infossup;
        TextView date;
        Button fermer;

    }
    public void confirmDialog(final int idlist) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Terminer cette commande?")
                .setPositiveButton("Oui",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        gestionConnection(commandesLivreurCourant.get(idlist).getIdCommande());
                        commandesLivreurCourant.remove(idlist);
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
    void gestionConnection(int idcomm){
        final int idcommande = idcomm;

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
                    //terminer la commande
                    String sql = "exec ProjetDB.dbo.terminercommande ?";
                    PreparedStatement stm = conn.prepareCall(sql);
                    stm.setInt(1, idcommande);

                    stm.executeUpdate();
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
}
