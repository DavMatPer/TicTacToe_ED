package com.estructuras.tictactoe.model.game;

import java.io.Serializable;

/**
 * 
 * @author davidç
 */
public class JugadorHumano extends Jugador implements Serializable{
    private static final long serialVersionUID = 1L;

    public JugadorHumano(Simbolo simbolo) {
        super(simbolo);
    }

}
