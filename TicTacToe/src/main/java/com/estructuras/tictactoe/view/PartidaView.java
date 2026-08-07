package com.estructuras.tictactoe.view;

import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.Tablero;

public interface PartidaView {
    public void  actualizarTablero( Tablero tablero);
    public void mostrarTurnoActual(Jugador jugadorActual);
    public void mostrarGanador(Jugador ganador);
    public void mostrarEmpate();
    public void mostrarMensaje(String mensaje);
}
