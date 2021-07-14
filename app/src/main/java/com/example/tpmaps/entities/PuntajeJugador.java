package com.example.tpmaps.entities;

public class PuntajeJugador {
    public Long tiempo;
    public int cantCorrectas;
    public String nombre;
    public long timestamp;

    public PuntajeJugador(Long t, int c, String n){
        tiempo = t;
        cantCorrectas = c;
        nombre = n;
        timestamp = System.currentTimeMillis()/1000;
    }
}
