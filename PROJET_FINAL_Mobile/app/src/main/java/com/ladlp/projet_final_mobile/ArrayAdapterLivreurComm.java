package com.ladlp.projet_final_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
            CommandeLivreurWrapper.associer = (Button) item.findViewById(R.id.Associer_btn);
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
        Button associer;

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
