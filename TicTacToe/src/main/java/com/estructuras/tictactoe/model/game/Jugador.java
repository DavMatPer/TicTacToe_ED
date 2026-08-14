package com.estructuras.tictactoe.model.game;

import java.io.Serializable;

/**
 *
 * @author david
 */
public abstract class Jugador implements Serializable{
    private static final long serialVersionUID = 1L;
    private Simbolo simbolo;

    public Jugador(Simbolo simbolo) {
        if (simbolo == null || simbolo == Simbolo.V) {
            throw new IllegalArgumentException("Símbolo inválido. Debe ser X o O");
        }
        this.simbolo = simbolo;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }
}
