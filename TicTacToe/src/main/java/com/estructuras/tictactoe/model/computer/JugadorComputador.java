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

    private static final int PROFUNDIDAD_MAXIMA_TEMPRANA = 4;
    private static final int UMBRAL_BUSQUEDA_COMPLETA = 6;

    /**
     * Construye el arbol de estados posibles a partir del tablero actual
     * retorna el movimiento optimo para la computadora según el algoritmo MiniMax.
     * La profundidad de búsqueda se adapta al número de casillas vacías:
     * cuando quedan pocas se explora el árbol completo; cuando quedan muchas
     * se acota la profundidad para mantener un tiempo de respuesta aceptable.
     * @param tablero estado actual del tablero de juego
     * @return movimiento con mayor utilidad 
     */
    public Movimiento realizarMovimiento(Tablero tablero) {
        int casillasVacias = tablero.getCasillasVacias().size();
        int profundidad = (casillasVacias <= UMBRAL_BUSQUEDA_COMPLETA)
                ? casillasVacias
                : PROFUNDIDAD_MAXIMA_TEMPRANA;

        ArbolTablero arbol = new ArbolTablero();
        arbol.construirArbol(tablero, getSimbolo(), profundidad);

        return miniMax.obtenerMejorMovimiento(arbol.getRaiz(), getSimbolo());
    }

}
