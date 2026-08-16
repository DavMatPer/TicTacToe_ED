package com.estructuras.tictactoe.controllers;

import com.estructuras.tictactoe.model.computer.JugadorComputador;
import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.Movimiento;
import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Tablero;

public class PartidaController {
    private Partida partida;

    public PartidaController(Jugador[] jugadores, int indiceInicial) {
      this.partida = new Partida(new Tablero(), jugadores, indiceInicial);
    }

    public PartidaController(Partida partidaRestaurada) {
      this.partida = partidaRestaurada;
    }

    /**
     * Realiza el movimiento del jugador actual en la casilla indicada.
     * Si el juego no ha terminado, cambia el turno y ejecuta automáticamente
     * los movimientos de la computadora mientras sea su turno.
     * @param fila fila de la casilla seleccionada
     * @param col columna de la casilla seleccionada
     * @return true si el movimiento se realizó correctamente, false en caso contrario
     */
    public boolean realizarMovimiento(int fila, int col) {

      if (!partida.realizarMovimiento(fila, col)) {
        return false;
      }
      if (partida.isGameOver()) {
        return true;
      }

      partida.cambiarTurno();

      while (partida.getJugadorActual() instanceof JugadorComputador && !partida.isGameOver()) {
        Movimiento movComputador = ((JugadorComputador) partida.getJugadorActual()).realizarMovimiento(partida.getTablero());
        partida.realizarMovimiento(movComputador.getRow(), movComputador.getCol());
        if (!partida.isGameOver()) {
          partida.cambiarTurno();
        }
      }

      return true;
    }

    /**
     * Ejecuta los turnos consecutivos de la computadora hasta que el jugador
     * actual sea humano o la partida haya terminado. Se usa al inicio si la
     * computadora abre el juego, y después de cada movimiento humano si el
     * modo de juego requiere turnos adicionales de la IA.
     */
    public void turnoComputadora() {
      while (partida.getJugadorActual() instanceof JugadorComputador && !partida.isGameOver()) {
          Movimiento mov = ((JugadorComputador) partida.getJugadorActual()).realizarMovimiento(partida.getTablero());
          partida.realizarMovimiento(mov.getRow(), mov.getCol());
          if (!partida.isGameOver()) {
              partida.cambiarTurno();
          }
      }
    }

    public boolean isGameOver() {
      return partida.isGameOver();
    }



    public boolean hayGanador() {
      return partida.hayGanador();
    }

    public Jugador getJugadorActual() {
      return partida.getJugadorActual();
    }

    public Jugador getGanador() {
      if(hayGanador()) {
        return getJugadorActual();
      }
      return null;
    }

    public Tablero getTablero() {
      return partida.getTablero();
    }

    public Partida getPartida() {
      return partida;
    }

    public void setPartida(Partida partida) {
      this.partida = partida;
    }
}
