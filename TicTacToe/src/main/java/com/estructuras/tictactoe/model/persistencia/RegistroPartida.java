package com.estructuras.tictactoe.model.persistencia;

import java.io.Serializable;

import com.estructuras.tictactoe.model.game.Partida;

/**
 * 
 * RegistroPartida
 * 
 * Clase Wraper de partida.
 * Contiene la partida y metadatos de esta como: nombre.
 */
public class RegistroPartida implements Serializable{
    private static final long serialVersionUID = 1L;
    private String nombrePartida;
    private Partida partida;

    public RegistroPartida(String nombrePartida, Partida partida) {
        if (nombrePartida == null || partida == null) {
            throw new IllegalArgumentException("Nombre de partida o partida inválidos");
        }
        if (nombrePartida.isEmpty()) {
            throw new IllegalArgumentException("Nombre de partida inválido");
        }   
        this.nombrePartida = nombrePartida;
        this.partida = partida;
    }

    public Partida getPartida() {
        return partida;
    }

    public String getNombrePartida() {
        return nombrePartida;
    }

}
