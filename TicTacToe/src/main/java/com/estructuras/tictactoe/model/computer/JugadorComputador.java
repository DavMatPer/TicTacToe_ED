package com.estructuras.tictactoe.model.computer;

import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.Movimiento;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.game.Tablero;

public class JugadorComputador extends Jugador {
    private MiniMax miniMax;

    public JugadorComputador(Simbolo simbolo) {
        super(simbolo);
        this.miniMax = new MiniMax(simbolo);
    }

    /**
     * Construye el arbol de estados posibles a partir del tablero actual
     * y retorna el movimiento optimo para la computadora segun el alggoritmo MiniMax.
     * @param tablero estado actual del tablero de juego
     * @return movimiento con mayor utilidad para la computadora
     */
    public Movimiento realizarMovimiento(Tablero tablero) {
        ArbolTablero arbol = new ArbolTablero();
        arbol.construirArbol(tablero, getSimbolo(), 2);

        return miniMax.obtenerMejorMovimiento(arbol.getRaiz(), getSimbolo());
    }
}
