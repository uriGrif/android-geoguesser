package com.example.tpmaps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tpmaps.entities.CiudadDTO;
import com.example.tpmaps.helpers.GoogleMapsHelper;

import java.util.ArrayList;
import java.util.Random;

public class JuegoFragment extends Fragment {

    View layoutRoot;
    Button btnOption1, btnOption2, btnOption3, btnOption4;
    TextView tvTimer, tvRonda, tvPuntaje;
    MapsFragment mapa = new MapsFragment();
    private int ronda, contPuntaje;
    private CiudadDTO currentCity;
    private long startTime, endTime;
    CountDownTimer timer = new CountDownTimer(7000, 10) {
        @Override
        public void onTick(long millisUntilFinished) {
            //Log.d("logURI", String.valueOf(millisUntilFinished));
            if(tvTimer != null) {
                tvTimer.setText(String.valueOf(millisUntilFinished/1000) + "." + String.valueOf(millisUntilFinished % 10));
            }
        }

        @Override
        public void onFinish() {
            Responder("");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (layoutRoot == null) {
            layoutRoot = inflater.inflate(R.layout.fragment_juego, container, false);

            ObtenerReferencias();

            SetListeners();
        }

        contPuntaje = 0;

        ronda = 0;

        ReemplazarFragment(mapa, false); //xq sino al ir para atras no lo vuelve a inflar y no funciona.

        MostrarNuevoPunto();

        startTime = System.currentTimeMillis()/1000;

        return layoutRoot;
    }

    public void ObtenerReferencias() {
        btnOption1 = layoutRoot.findViewById(R.id.btnOption1);
        btnOption2 = layoutRoot.findViewById(R.id.btnOption2);
        btnOption3 = layoutRoot.findViewById(R.id.btnOption3);
        btnOption4 = layoutRoot.findViewById(R.id.btnOption4);
        tvTimer = layoutRoot.findViewById(R.id.tvTimer);
        tvRonda = layoutRoot.findViewById(R.id.tvRonda);
        tvPuntaje = layoutRoot.findViewById(R.id.tvPuntaje);
    }

    public void SetListeners() {
        btnOption1.setOnClickListener(btnOptionOnClick);
        btnOption2.setOnClickListener(btnOptionOnClick);
        btnOption3.setOnClickListener(btnOptionOnClick);
        btnOption4.setOnClickListener(btnOptionOnClick);
    }

    View.OnClickListener btnOptionOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button b = (Button)view;
            String buttonText = b.getText().toString();
            Responder(buttonText);
        }
    };

    public void MostrarNuevoPunto() {
        int pos = MySession.posicionesCiudades[ronda];
        CiudadDTO city = MySession.listaCiudades.get(pos);
        //Log.d("logURI", city.name);
        tvRonda.setText("Ronda: " + String.valueOf(ronda + 1));
        tvPuntaje.setText("Correctas: " + String.valueOf(contPuntaje));
        mapa.lat = city.lat;
        mapa.lng = city.lng;
        currentCity = city;
        mapa.AgregarMarker(city.lat, city.lng);
        MostrarOpciones();
        timer.start();
    }

    public void Responder(String respuesta) {
        timer.cancel();
        if (respuesta == currentCity.name) {
            contPuntaje++;
            if (getContext() != null) {
                Toast.makeText(getContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
            }
        }
        if(ronda < 9) {
            ronda++;
            MostrarNuevoPunto();
        }
        else {
            endTime = System.currentTimeMillis()/1000;
            SavePuntaje();
        }
    }

    private void MostrarOpciones() {
        MainActivity activityContenedora = (MainActivity) getActivity();
        Random rand = new Random();
        int correcta = rand.nextInt(4) + 1;
        ArrayList<CiudadDTO>opcionesAux = new ArrayList<CiudadDTO>();
        //Log.d("logURI", "correcta " + String.valueOf(correcta));

        for (int i = 1; i < 5; i++){
            String buttonName = "btnOption" + String.valueOf(i);
            int id = getResources().getIdentifier(buttonName, "id", getContext().getPackageName());
            if(id > 0) {
                Button btn = (Button) layoutRoot.findViewById(id);
                if (i == correcta) {
                    btn.setText(currentCity.name);
                }
                else {
                    CiudadDTO aux = activityContenedora.GetCiudadRandom();
                    while (aux.name.equals(currentCity.name) || opcionesAux.contains(aux)
                    || !IsOutside200KM(aux, opcionesAux)) {
                        aux = activityContenedora.GetCiudadRandom();
                        Log.d("logURI", "loop 200km");
                    }
                    opcionesAux.add(aux);
                    btn.setText(aux.name);
                }
                //Log.d("logURI", String.valueOf(i) + " " + btn.getText().toString());
            }
        }
    }

    private boolean IsOutside200KM(CiudadDTO ciudad, ArrayList<CiudadDTO> ciudades) {
        if (GoogleMapsHelper.distanceInKM(ciudad.lat, ciudad.lng, currentCity.lat, currentCity.lng) < 200) {
            return false;
        }
        for (int i = 0; i < ciudades.size(); i++) {
            if (GoogleMapsHelper.distanceInKM(ciudad.lat, ciudad.lng, ciudades.get(i).lat, ciudades.get(i).lng) < 200) {
                return false;
            }
        }
        return true;
    }

    private void SavePuntaje() {
        Long tiempo = endTime - startTime;
        MainActivity activityContenedora = (MainActivity) getActivity();
        activityContenedora.AddToPuntajes(tiempo, contPuntaje);
    }

    private void ReemplazarFragment(Fragment fragmento, boolean blnAddToBackStack) {
        FragmentManager manager;
        FragmentTransaction transaction;

        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frameContMapa, fragmento,null);
        if (blnAddToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}