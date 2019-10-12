package br.edu.ifsc.minhasnotas;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.util.ArrayList;

public class NotaAdapter extends BaseAdapter {

    Activity contexto;
    ArrayList<Nota> listaNotas;
    private static LayoutInflater inflater;

    public NotaAdapter(Activity contexto, ArrayList<Nota> notas){
        Log.i("Resultado", "criaAdapter ");

        this.contexto = contexto;
        this.listaNotas = notas;

        inflater = (LayoutInflater) contexto .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaNotas.size();
    }

    @Override
    public Nota getItem(int i) {
        return listaNotas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaNotas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("Resultado", "getView ");

        View itemView = view;

        if (itemView == null){
            itemView = inflater.inflate(R.layout.list_item, null);
        }

        TextView item1 = (TextView) itemView.findViewById(R.id.item1);
        TextView item2 = (TextView) itemView.findViewById(R.id.item2);
        TextView item3 = (TextView) itemView.findViewById(R.id.item3);
        ImageView img1 = (ImageView) itemView.findViewById(R.id.imagemItem);

        Nota selectedNota = listaNotas.get(i);
        item1.setText(selectedNota.getTexto());
        item2.setText(selectedNota.getDataCriacao());
        item3.setText(selectedNota.getDataAlteracao());
        img1.setImageBitmap(selectedNota.getImage());

        return itemView;

    }
}
