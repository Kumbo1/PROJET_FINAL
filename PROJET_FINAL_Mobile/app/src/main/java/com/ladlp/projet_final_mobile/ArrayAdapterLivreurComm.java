package com.ladlp.projet_final_mobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
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
    public ArrayAdapterLivreurComm(Context context, int layoutResourceId, ArrayList<CommandeLivreur> comm) {
        super(context, layoutResourceId, comm);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.commandesLivreur = comm;
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
                        //DELETE DANS LA BD
                        commandesLivreur.remove(idlist);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Commande annulée", Toast.LENGTH_LONG).show();
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
}
