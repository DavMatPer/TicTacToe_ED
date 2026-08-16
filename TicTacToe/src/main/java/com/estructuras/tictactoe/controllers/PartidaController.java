package com.estructuras.tictactoe.controllers;

import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.view.PartidaView;

public class PartidaController {

    private Partida partida;
    private PartidaView view;

    public PartidaController(Partida partida, PartidaView view) {
        this.partida = partida;
        this.view = view;
    }

    /**
     * Inicia el juego.
     */
    public void iniciarJuego() {
        partida.iniciarPartida();
        actualizarEstadoVisual();
    }

    /**
     * Maneja la selección de una casilla en el tablero.
     * @param row Fila de la casilla seleccionada.
     * @param col Columna de la casilla seleccionada.
     */
    public void onCasillaSeleccionada(int row, int col) {
        if (!partida.realizarMovimiento(row, col)) return;

        view.actualizarTablero(partida.getTablero());
        if (partida.hayGanador()) {
            view.mostrarGanador(partida.getJugadorActual());
        } else if (partida.isGameOver()) {
            view.mostrarEmpate();
        } else {
            partida.cambiarTurno();
        }
    }

    /**
     * Procesa el turno de la computadora.
     */
    private void procesarTurnoComputadora() {
        // Lógica para procesar el turno de la computadora
    }

    /**
     * Cambia la pantalla, para mostrar el turno dle jugador
     * También muestra el tablero.
     */
    private void actualizarEstadoVisual() {
        view.mostrarTurnoActual(partida.getJugadorActual());
        view.actualizarTablero(partida.getTablero());
    }
    
    /**
     * Indica si la partida ya ha finalizado
     * @return true si ya finalizó
     */
    public boolean finPartida() {
        return partida.isGameOver();
    }


}
