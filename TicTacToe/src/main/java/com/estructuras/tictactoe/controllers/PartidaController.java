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
    private static final double DELAY_SEGUNDOS_COMPUTADOR = 0.5;

    public PartidaController(Partida partida) {
      this.partida = partida;
      this.vistaPrincipal = null;
    }

    public PartidaController(Partida partida, PartidaViewController vista) {
      this.partida = partida;
      this.vistaPrincipal = vista;
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
        
        PauseTransition pause = new PauseTransition(Duration.seconds(DELAY_SEGUNDOS_COMPUTADOR));

        pause.setOnFinished(event -> {
            ejecutarMovimientoComputador();
            
            // Actualizar la vista después del movimiento del PC
            if (vistaPrincipal != null) {
                vistaPrincipal.actualizarVistaTablero();
            }
            
            // Si el juego terminó, no cambiar turno ni gestionar siguiente turno
            if (partida.isGameOver()) {
                vistaPrincipal.mostrarModalFinal();
                return;
            }
            
            partida.cambiarTurno();
            
            // Gestionar el siguiente turno si es del PC
            gestionarTurnoComputador();
        });

        pause.play();
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

}
