package com.estructuras.tictactoe.controllers;

import com.estructuras.tictactoe.model.computer.JugadorComputador;
import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.Movimiento;
import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Tablero;
import com.estructuras.tictactoe.view.controllers.PartidaViewController;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class PartidaController {
    private Partida partida;
    private PartidaViewController vistaPrincipal;
    private PauseTransition pauseTransitionActual;
    private boolean pausado;
    private static final double DELAY_SEGUNDOS_COMPUTADOR = 0.5;

    public PartidaController(Partida partida) {
      this.partida = partida;
      this.vistaPrincipal = null;
      this.pausado = false;
      this.pauseTransitionActual = null;
    }

    public PartidaController(Partida partida, PartidaViewController vista) {
      this.partida = partida;
      this.vistaPrincipal = vista;
      this.pausado = false;
      this.pauseTransitionActual = null;
    }

    /**
     * Inicia el flujo de la partida. Se llama una vez que la vista está lista.
     * Si el primer jugador es un computador, inicia su turno automáticamnte.
     */
    public void iniciarPartida() {
        gestionarTurnoComputador();
    }


    /**
     * Realiza el movimiento del jugador humano.
     * @param fila fila seleccionada.
     * @param col columna seleccionada.
     * @return false si el movimiento es inválido, true en caso contrario.
     */
    public boolean realizarMovimientoHumano(int fila, int col) {
        if (partida.isGameOver()) {
            return false; 
        }

        if (partida.getJugadorActual() instanceof JugadorComputador) {
            return false; // No procesar clicks durante el turno del PC
        }

        if (!partida.realizarMovimiento(fila, col)) {
            return false; 
        }

        if (partida.isGameOver()) {
            return true;
        }

        partida.cambiarTurno();

        gestionarTurnoComputador(); 
        return true;
    }

    /**
     * Si el jugador actual es un computador, ejecuta su turno tras un delay.
     */
    private void gestionarTurnoComputador() {
        if (! (partida.getJugadorActual() instanceof JugadorComputador) || partida.isGameOver()) 
          return;
        if (vistaPrincipal == null) return;
        
        pauseTransitionActual = new PauseTransition(Duration.seconds(DELAY_SEGUNDOS_COMPUTADOR));

        pauseTransitionActual.setOnFinished(event -> {
            if (pausado) return; // No ejecutar si está pausado
            
            ejecutarMovimientoComputador();
            
            if (vistaPrincipal != null) {
                vistaPrincipal.actualizarVistaTablero();
            }

            if (partida.isGameOver()) {
                if (vistaPrincipal != null) {
                    vistaPrincipal.mostrarModalFinal();
                }
                return;
            }
            
            partida.cambiarTurno();
            
            // Gestionar el siguiente turno si es del PC
            gestionarTurnoComputador();
        });

        pauseTransitionActual.play();
    }

    /**
     * Lógica interna para que el computador realice un movimiento y notifique a la UI.
     */
    private void ejecutarMovimientoComputador() {
        JugadorComputador jugadorPC = (JugadorComputador) partida.getJugadorActual();
        Movimiento mov = jugadorPC.realizarMovimiento(partida.getTablero());
        partida.realizarMovimiento(mov.getRow(), mov.getCol());
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
        partida.getJugadorActual();
      }
      return null;
    }

    public Tablero getTablero() {
      return partida.getTablero();
    }

    public Partida getPartida() {
      return partida;
    }

    /**
     * Pausa la partida actual deteniendo el turno del computador.
     */
    public void pausarPartida() {
        pausado = true;
        if (pauseTransitionActual != null) {
            pauseTransitionActual.stop();
        }
    }

    /**
     * Reanuda la partida tras estar pausada.
     */
    public void reanudarPartida() {
        pausado = false;
        if (partida.getJugadorActual() instanceof JugadorComputador && !partida.isGameOver()) {
            gestionarTurnoComputador();
        }
    }

    /**
     * Retorna si la partida está pausada.
     */
    public boolean estaPausada() {
        return pausado;
    }

}
