package com.example.tpmaps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ObjetivoFragment extends Fragment {

    View layoutRoot;
    Button btnJugar, btnRanking, btnCambiarNombre;
    TextView tvJugador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_objetivo, container, false);

            ObtenerReferencias();

            SetListeners();
        }

        tvJugador.setText("Jugador: " + MySession.currentPlayer);

        return layoutRoot;
    }

    public void ObtenerReferencias() {
        btnJugar = layoutRoot.findViewById(R.id.btnJugar);
        btnRanking = layoutRoot.findViewById(R.id.btnRanking);
        btnCambiarNombre = layoutRoot.findViewById(R.id.btnCambiarNombre);
        tvJugador = layoutRoot.findViewById(R.id.tvJugador);
    }

    public void SetListeners() {
        btnJugar.setOnClickListener(btnJugarOnClick);
        btnRanking.setOnClickListener(btnRankingOnClick);
        btnCambiarNombre.setOnClickListener(btnCambiarNombreOnClick);
    }

    View.OnClickListener btnJugarOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity activityContenedora = (MainActivity) getActivity();
            Log.d("logURI", "apreto jugar");
            activityContenedora.InicializarJuego();
            activityContenedora.IrAFragmentJuego();
        }
    };

    View.OnClickListener btnRankingOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity activityContenedora = (MainActivity) getActivity();
            activityContenedora.IrAFragmentRanking();
        }
    };

    View.OnClickListener btnCambiarNombreOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity activityContenedora = (MainActivity) getActivity();
            activityContenedora.IrAFragmentInicio();
        }
    };
}