package com.example.tpmaps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.tpmaps.entities.PuntajeJugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RankingFragment extends Fragment {

    View layoutRoot;
    Button btnObjetivo;
    ListView lvPuntajes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_ranking, container, false);

            ObtenerReferencias();

            SetListeners();
        }

        SetAdaper();

        return layoutRoot;
    }

    public void onBackPressed() {
        MainActivity activityContenedora = (MainActivity) getActivity();
        activityContenedora.IrAFragmentObjetivo();
    }

    public void ObtenerReferencias() {
        btnObjetivo = (Button) layoutRoot.findViewById(R.id.btnObjetivo);
        lvPuntajes = (ListView) layoutRoot.findViewById(R.id.lvPuntajes);
    }

    public void SetListeners() {
        if(btnObjetivo != null) {
            btnObjetivo.setOnClickListener(btnInicioOnClick);
        }
    }

    public void SetAdaper() {
        MainActivity activityContenedora = (MainActivity) getActivity();
        PuntajeAdapter adapter = new PuntajeAdapter(getContext(), R.layout.my_list_item, ArrayOrdenado(MySession.puntajes));
        lvPuntajes.setAdapter(adapter);
    }

    View.OnClickListener btnInicioOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity activityContenedora = (MainActivity) getActivity();
            activityContenedora.IrAFragmentObjetivo();
        }
    };

    public ArrayList<PuntajeJugador> ArrayOrdenado(ArrayList<PuntajeJugador> arr) {
        Collections.sort(arr, new Comparator<PuntajeJugador>() {
            int c;
            @Override
            public int compare(PuntajeJugador o1, PuntajeJugador o2) {
                c = o2.cantCorrectas - o1.cantCorrectas;
                if (c == 0) {
                    c = o1.tiempo.intValue() - o2.tiempo.intValue();
                }
                return c;
            }
        });
        return arr;
    }
}