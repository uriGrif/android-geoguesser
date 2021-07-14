package com.example.tpmaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.tpmaps.entities.PuntajeJugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PuntajeAdapter extends ArrayAdapter<PuntajeJugador> {
    Context context;
    int resourceLayout;
    ArrayList<PuntajeJugador> puntajesList;

    public PuntajeAdapter(Context context, int resource, ArrayList<PuntajeJugador> datosArray) {
        super(context, resource, datosArray);

        this.context = context;
        this.resourceLayout = resource;
        this.puntajesList = datosArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        PuntajeJugador puntaje;
        TextView tvNombreJugador, tvCantCorrectas, tvTiempo;
        View layoutInterno;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layoutInterno = (View) inflater.inflate(resourceLayout, null);
        }
        else {
            layoutInterno = convertView;
        }

        puntaje = getItem(position);

        if(puntaje != null) {
            tvNombreJugador = layoutInterno.findViewById(R.id.tvNombreJugador);
            tvCantCorrectas = layoutInterno.findViewById(R.id.tvCantCorrectas);
            tvTiempo = layoutInterno.findViewById(R.id.tvTiempo);

            tvNombreJugador.setText(puntaje.nombre);
            tvCantCorrectas.setText(String.valueOf(puntaje.cantCorrectas) + " correctas");
            tvTiempo.setText(String.valueOf(puntaje.tiempo) + " seg");

            if (puntaje == getLastByTime()) {
                layoutInterno.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200));
            }
        }

        return layoutInterno;
    }

    public PuntajeJugador getLastByTime() {
        return Collections.max(puntajesList, Comparator.comparing(c -> c.timestamp));
    }
}
