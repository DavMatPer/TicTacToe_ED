package com.estructuras.tictactoe.model.game;

import java.io.Serializable;

/**
 *
 * @author david
 */
public class Partida implements Serializable{
    private static final long serialVersionUID = 1L;
    private Tablero tablero;
    private Jugador[] jugadores;
    private int indiceJugadorActual;
    private int movimientosRealizados;

    public Partida(Tablero tablero, Jugador[] jugadores, int indiceJugadorActual) {
        
        if (jugadores == null) {
            throw new IllegalArgumentException("Jugadores inválidos");
        }
        if ( jugadores.length != 2) {
            throw new IllegalArgumentException("Debe haber exactamente dos jugadores");
        }
        if (indiceJugadorActual < 0 || indiceJugadorActual >= jugadores.length) {
            throw new IllegalArgumentException("Índice de jugador actual inválido");
        }
        if (tablero == null) {
            throw new IllegalArgumentException("Tablero inválido");
        }

        this.tablero = tablero;
        this.jugadores = jugadores;
        this.indiceJugadorActual = indiceJugadorActual;
        this.movimientosRealizados = 0;
    }

    public Partida( Jugador[] jugadores, int indiceJugadorActual) {
        this(new Tablero(), jugadores, indiceJugadorActual);
    }

    public Partida(Jugador jugador1, Jugador jugador2, int indiceJugadorActual) {
        this(new Tablero(), new Jugador[]{jugador1, jugador2}, indiceJugadorActual);
    }

    public void iniciarPartida() {
        
    }

    /**
     * Cambia el turno al siguiente jugador.
     */
    public void cambiarTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.length;
    }

    /**
     * Realiza un movimiento en el tablero de juego.
     * @param col la columna donde se desea realizar el movimiento.
     * @param row la fila donde se desea realizar el movimiento.
     * @return true si el movimiento se realizó correctamente, false en caso contrario.
     */
    public boolean realizarMovimiento(int col , int row) {
        Movimiento movimiento = new Movimiento(row, col, jugadores[indiceJugadorActual].getSimbolo());
        if (tablero.realizarMovimiento(movimiento)) {
            movimientosRealizados++;
            return true;
        }
        return false;
    }

    /**
     * Verifica si hay un ganador en la partida.
     * @return 
     */
    public boolean hayGanador() {
        if (movimientosRealizados < 5) {
            return false; 
        }
        if (tablero.esEmpate()) return false;
        Simbolo simboloActual = jugadores[indiceJugadorActual].getSimbolo();
        return tablero.esGanador(simboloActual);
    }

    public boolean esEmpate() {
        return tablero.esEmpate();
    }

    /**
     * Verifica si la partida ha terminado, ya sea por un ganador o por empate.
     * @return true si la partida ha terminado, false en caso contrario.
     */
    public boolean isGameOver() {
        return tablero.isFull() || hayGanador() || esEmpate();
    }

    /**
     * Retorna una copia del tablero de juego.
     * @return copia del tablero de juego.
     */
    public Tablero getTablero() {
        return tablero.clone();
    }

    /**
     * Retorna el jugador actual.
     * @return jugador actual.
     */
    public Jugador getJugadorActual() {
        return jugadores[indiceJugadorActual];
    }

    /**
     * Retorna el número de movimientos realizados en la partida.
     * @return número de movimientos realizados.
     */
    public int getMovimientosRealizados() {
        return movimientosRealizados;
    }


}
