package com.estructuras.tictactoe.controllers;

import com.estructuras.tictactoe.model.computer.JugadorComputador;
import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.JugadorHumano;
import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.game.Tablero;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para PartidaController sin dependencias de vista.
 */
public class PartidaControllerTest {

    private Partida partidaHvsC;
    private Partida partidaCvsC;
    private PartidaController controladorHvsC;
    private PartidaController controladorCvsC;

    @BeforeEach
    public void setUp() {
        Jugador humano = new JugadorHumano(Simbolo.X);
        Jugador computerX = new JugadorComputador(Simbolo.X);
        Jugador computerO = new JugadorComputador(Simbolo.O);

        partidaHvsC = new Partida(humano, computerX, 0);
        partidaCvsC = new Partida(computerX, computerO, 0);

        controladorHvsC = new PartidaController(partidaHvsC);
        controladorCvsC = new PartidaController(partidaCvsC);
    }

    /**
     * Verifica que PartidaController se inicializa sin vistaPrincipal.
     */
    @Test
    public void testControladorSeInicializaSinVista() {
        assertNotNull(controladorHvsC);
        assertFalse(controladorHvsC.estaPausada());
    }

    /**
     * Verifica que se puede pausar y reanudar la partida.
     */
    @Test
    public void testPausarYReanudarPartida() {
        controladorHvsC.pausarPartida();
        assertTrue(controladorHvsC.estaPausada());

        controladorHvsC.reanudarPartida();
        assertFalse(controladorHvsC.estaPausada());
    }

    /**
     * Verifica que el movimiento del humano se realiza correctamente.
     */
    @Test
    public void testMovimientoHumano() {
        boolean resultado = controladorHvsC.realizarMovimientoHumano(0, 0);
        assertTrue(resultado, "El movimiento debe ser válido");
        assertTrue(!controladorHvsC.getTablero().getTablero()[0][0].equals(Simbolo.V), "El tablero debe actualizar");
    }

    /**
     * Verifica que se rechaza un movimiento inválido (en casilla ocupada).
     */
    @Test
    public void testMovimientoInvalido() {
        controladorHvsC.realizarMovimientoHumano(0, 0);
        boolean resultado = controladorHvsC.realizarMovimientoHumano(0, 0);
        assertFalse(resultado, "No debe permitir mover en casilla ocupada");
    }

    /**
     * Verifica que no se puede jugar cuando es turno del computador.
     */
    @Test
    public void testNoPermiteClickEnTurnoComputador() {
        Jugador computerActual = new JugadorComputador(Simbolo.X);
        Jugador humano = new JugadorHumano(Simbolo.O);
        Partida partida = new Partida(computerActual, humano, 0);

        PartidaController controlador = new PartidaController(partida);

        boolean resultado = controlador.realizarMovimientoHumano(0, 0);
        assertFalse(resultado, "No debe permitir click cuando es turno del PC");
    }

    /**
     * Verifica que la partida termina correctamente.
     */
    @Test
    public void testPartidaTerminaCorrectamente() {
        // Esta prueba es más un humo test
        Tablero tablero = controladorHvsC.getTablero();
        assertNotNull(tablero);
        assertFalse( controladorHvsC.isGameOver(), "La partida no debe estar terminada al inicio");
    }

    /**
     * Verifica que se obtiene el jugador actual.
     */
    @Test
    public void testObtenerJugadorActual() {
        Jugador jugadorActual = controladorHvsC.getJugadorActual();
        assertNotNull(jugadorActual);
        assertEquals(Simbolo.X, jugadorActual.getSimbolo());
    }
}
