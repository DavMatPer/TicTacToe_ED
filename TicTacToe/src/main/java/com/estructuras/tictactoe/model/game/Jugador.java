package com.estructuras.tictactoe.model.game;

/**
 *
 * @author david
 */
public abstract class Jugador {
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
