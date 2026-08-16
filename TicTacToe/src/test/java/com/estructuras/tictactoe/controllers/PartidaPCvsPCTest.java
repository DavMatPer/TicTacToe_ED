package com.estructuras.tictactoe.controllers;

import com.estructuras.tictactoe.model.computer.JugadorComputador;
import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Simbolo;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para escenarios de juego Computador vs Computador.
 */
public class PartidaPCvsPCTest {

    private Partida partidaCvsC;
    private PartidaController controlador;

    @BeforeEach
    public void setUp() {
        Jugador computerX = new JugadorComputador(Simbolo.X);
        Jugador computerO = new JugadorComputador(Simbolo.O);
        
        partidaCvsC = new Partida(computerX, computerO, 0);
        controlador = new PartidaController(partidaCvsC);
    }

    /**
     * Verifica que un controlador sin vista no falla.
     */
    @Test
    public void testControladorSinVistaNoFalla() {
        assertNotNull(controlador);
        assertFalse(controlador.estaPausada());
    }

    /**
     * Verifica que se puede pausar en un juego PC vs PC.
     */
    @Test
    public void testPausarEnPCvsPC() {
        controlador.pausarPartida();
        assertTrue(controlador.estaPausada());
    }

    /**
     * Verifica que la partida tiene dos computadores.
     */
    @Test
    public void testAmbosSonComputadores() {
        assertTrue(controlador.getPartida().getJugadores()[0] instanceof JugadorComputador, 
            "Jugador 1 debe ser Computador" );
        assertTrue(
            controlador.getPartida().getJugadores()[1] instanceof JugadorComputador,
            "Jugador 2 debe ser Computador" );
    }

    /**
     * Verifica que se obtiene el tablero correctamente.
     */
    @Test
    public void testObtenerTablero() {
        assertNotNull(controlador.getTablero());
        assertNotNull(controlador.getTablero().getTablero());
    }
}
