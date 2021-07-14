package com.example.tpmaps;

import com.example.tpmaps.entities.CiudadDTO;
import com.example.tpmaps.entities.PuntajeJugador;

import java.util.ArrayList;

public class MySession {
    public static String currentPlayer = "";
    public static ArrayList<CiudadDTO> listaCiudades = new ArrayList<CiudadDTO>();
    public static int[] posicionesCiudades = new int[10];
    public static ArrayList<PuntajeJugador> puntajes = new ArrayList<PuntajeJugador>();
}
