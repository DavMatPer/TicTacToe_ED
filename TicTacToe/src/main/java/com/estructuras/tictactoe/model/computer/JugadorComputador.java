package com.estructuras.tictactoe.model.computer;

import java.io.Serializable;

import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.Movimiento;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.game.Tablero;

public class JugadorComputador extends Jugador implements Serializable{
    private static final long serialVersionUID = 1L;

    public JugadorComputador(Simbolo simbolo) {
        super(simbolo);
    }

    public Movimiento realizarMovimiento(Tablero tablero) {
        // Implementación de la lógica para que el jugador computador realice un movimiento.
        // Por ejemplo, podría elegir una casilla vacía al azar o usar alguna estrategia.
        return null; // Retorna un movimiento válido (esto es solo un marcador de posición).
    }

}
