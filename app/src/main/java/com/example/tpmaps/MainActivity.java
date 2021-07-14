package com.example.tpmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.tpmaps.entities.CiudadDTO;
import com.example.tpmaps.entities.PuntajeJugador;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    InicioFragment frgInicio;
    ObjetivoFragment frgObjetivo;
    JuegoFragment frgJuego;
    RankingFragment frgRanking;
//    private String currentPlayer;
//    ArrayList<CiudadDTO> listaCiudades = new ArrayList<CiudadDTO>();
//    public int[] posicionesCiudades = new int[10];
//    public ArrayList<PuntajeJugador> puntajes = new ArrayList<PuntajeJugador>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObtenerReferencias();

        ReemplazarFragment(frgInicio, false);

        TareaAsincGetCiudades task = new TareaAsincGetCiudades();
        task.execute();
    }

    private void ObtenerReferencias() {
        frgInicio = new InicioFragment();
        frgObjetivo = new ObjetivoFragment();
        frgJuego = new JuegoFragment();
        frgRanking = new RankingFragment();
    }


    private void ReemplazarFragment(Fragment fragmento) {
        ReemplazarFragment(fragmento, true);
    }

    private void ReemplazarFragment(Fragment fragmento, boolean blnAddToBackStack) {
        FragmentManager manager;
        FragmentTransaction transaction;

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frameContenedor, fragmento,null);
        if (blnAddToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void IrAFragmentInicio() {
        ReemplazarFragment(frgInicio);
    }

    public void IrAFragmentObjetivo() {
        ReemplazarFragment(frgObjetivo);
    }

    public void IrAFragmentJuego() {
        ReemplazarFragment(frgJuego);
    }

    public void IrAFragmentRanking() {
        ReemplazarFragment(frgRanking);
    }

    public void setCurrentPlayer(String currentPlayer) {
        MySession.currentPlayer = currentPlayer;
    }

    public String getCurrentPlayer() { return MySession.currentPlayer; }

    public void InicializarJuego() {
        Arrays.fill(MySession.posicionesCiudades, -1);
        Random rand = new Random();
        int randAux;
        Log.d("logURI", "valores");
        for (int i = 0; i < 10; i++) {
            Log.d("logURI", String.valueOf(MySession.posicionesCiudades[i] * i));
        }
        for (int i = 0; i < 10; i++) {
            randAux = rand.nextInt(MySession.listaCiudades.size());
            Log.d("logURI", "inicio: generado antes loop: " + String.valueOf(randAux));
            while (contains(MySession.posicionesCiudades, randAux)) {
                randAux = rand.nextInt(MySession.listaCiudades.size());
                //Log.d("logURI", "loop inicio");
                //Log.d("logURI", "inicio: generado dentro loop: " + String.valueOf(randAux));
            }
            MySession.posicionesCiudades[i] = randAux;
            //Log.d("logURI", String.valueOf(MySession.posicionesCiudades[i]));
        }
    }

    public CiudadDTO GetCiudadRandom() {
        Random rand = new Random();
        return MySession.listaCiudades.get(rand.nextInt(MySession.listaCiudades.size()));
    }

    public void AddToPuntajes(Long t, int cant) {
        PuntajeJugador p = new PuntajeJugador(t, cant, getCurrentPlayer());
        MySession.puntajes.add(p);
        IrAFragmentRanking();
    }

    private class TareaAsincGetCiudades extends AsyncTask<Void, Void, String> {

        private String query;

        @Override protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection miConexion = null;
            URL strAPIUrl;

            BufferedReader responseReader;
            String responseLine, strResultado = "";
            StringBuilder sbResponse;

            try {
                strAPIUrl = new URL("https://api.polshu.com.ar/Data/geonames.json");
                miConexion = (HttpURLConnection) strAPIUrl.openConnection();
                miConexion.setRequestMethod("GET");

                if (miConexion.getResponseCode() == 200) {
                    responseReader = new BufferedReader(new InputStreamReader(miConexion.getInputStream()));
                    sbResponse = new StringBuilder();

                    while ((responseLine = responseReader.readLine()) != null) {
                        sbResponse.append(responseLine);
                    }
                    responseReader.close();

                    strResultado = sbResponse.toString();
                }
                else {
                    Log.d("logURI", "ERROR! me pude conectar, pero algo paso");
                }
            }
            catch (Exception e){
                Log.d("logURI", String.valueOf(e));
            }
            finally {
                if (miConexion != null){
                    miConexion.disconnect();
                }
            }
            return strResultado;
        }

        @Override protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            CiudadDTO[] ciudades = ParsearResultado(resultado);
            for (int i = 0; i < ciudades.length; i++) {
                MySession.listaCiudades.add(ciudades[i]);
            }

            //Log.d("logURI", listaCiudades.get(0).name);
        }

        public CiudadDTO[] ParsearResultado(String resultado) {

            Gson parser = new Gson();
            CiudadDTO[] ciudades = parser.fromJson(resultado, CiudadDTO[].class);

            return ciudades;
        }
    }

    public static boolean contains(int[] arr, int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    @Override
    public void onBackPressed() {
        if(frgRanking.isVisible())
            frgRanking.onBackPressed();
        else
            super.onBackPressed();
    }
}