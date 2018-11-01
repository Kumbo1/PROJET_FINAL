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

public class MyArrayAdapter extends ArrayAdapter<Commande> {
        Context context;
        int layoutResourceId;
        ArrayList<Commande> commandes = new ArrayList<Commande>();
        public MyArrayAdapter(Context context, int layoutResourceId, ArrayList<Commande> comm) {
            super(context, layoutResourceId, comm);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.commandes = comm;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View item = convertView;
            CommandeWrapper CommandeWrapper = null;
            if (item == null) {

                LayoutInflater inflater = ((Activity) context).getLayoutInflater();

                item = inflater.inflate(layoutResourceId, parent, false);
                CommandeWrapper = new CommandeWrapper();
                CommandeWrapper.id = (TextView) item.findViewById(R.id.item_IdCommande);
                CommandeWrapper.objet = (TextView) item.findViewById(R.id.item_Objet);
                CommandeWrapper.date = (TextView) item.findViewById(R.id.item_Date);
                CommandeWrapper.delete = (Button) item.findViewById(R.id.delete_btn);
                item.setTag(CommandeWrapper);
            } else {
                CommandeWrapper = (CommandeWrapper) item.getTag();
            }

            Commande commande = commandes.get(position);
            CommandeWrapper.date.setText(commande.getDate());
            CommandeWrapper.id.setText(commande.getId());
            CommandeWrapper.objet.setText(commande.getObjet());
            CommandeWrapper.delete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog(position);
                }
            });
            return item;
        }

        static class CommandeWrapper {
            TextView id;
            TextView objet;
            TextView date;
            Button delete;

        }
    public void confirmDialog(final int idlist) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Êtes-vous sûr de vouloir annuler cette commande?")
                .setPositiveButton("Oui",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //DELETE DANS LA BD
                        commandes.remove(idlist);
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

