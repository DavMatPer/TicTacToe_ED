package com.estructuras.tictactoe.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PartidaTest {

    @Test
    void cambiarTurnoAlternaElJugadorActual() {
        Partida partida = crearPartidaBase();

        assertEquals(Simbolo.X, partida.getJugadorActual().getSimbolo());

        partida.cambiarTurno();

        assertEquals(Simbolo.O, partida.getJugadorActual().getSimbolo());
    }

    @Test
    void realizarMovimientoGuardaElSimboloYActualizaElConteo() {
        Partida partida = crearPartidaBase();

        assertTrue(partida.realizarMovimiento(0, 0));
        assertEquals(1, partida.getMovimientosRealizados());
        assertEquals(Simbolo.X, partida.getTablero().getTablero()[0][0]);
    }

    @Test
    void realizarMovimientoEnCasillaOcupadaRetornaFalse() {
        Partida partida = crearPartidaBase();

        assertTrue(partida.realizarMovimiento(1, 1));
        assertFalse(partida.realizarMovimiento(1, 1));
        assertEquals(1, partida.getMovimientosRealizados());
    }

    @Test
    void hayGanadorRetornaTrueCuandoHayUnaLineaCompleta() {
        Partida partida = crearPartidaBase();

        assertTrue(partida.realizarMovimiento(0, 0));
        partida.cambiarTurno();
        assertTrue(partida.realizarMovimiento(0, 1));
        partida.cambiarTurno();
        assertTrue(partida.realizarMovimiento(1, 0));
        partida.cambiarTurno();
        assertTrue(partida.realizarMovimiento(1, 1));
        partida.cambiarTurno();
        assertTrue(partida.realizarMovimiento(2, 0));

        assertTrue(partida.hayGanador());
    }

    @Test
    void isGameOverRetornaTrueCuandoElTableroEstaLleno() {
        Simbolo[][] tableroCompleto = {
            {Simbolo.X, Simbolo.O, Simbolo.X},
            {Simbolo.O, Simbolo.O, Simbolo.X},
            {Simbolo.X, Simbolo.X, Simbolo.O}
        };
        Partida partida = new Partida(
                new Tablero(tableroCompleto),
                new Jugador[]{new JugadorHumano(Simbolo.X), new JugadorHumano(Simbolo.O)},
                0
        );

        assertTrue(partida.isGameOver());
    }

    @Test
    void empateRetornaTrueParaTableroLlenoSinGanador() {
        Simbolo[][] tableroCompleto = {
            {Simbolo.X, Simbolo.O, Simbolo.X},
            {Simbolo.O, Simbolo.O, Simbolo.X},
            {Simbolo.X, Simbolo.X, Simbolo.O}
        };
        Partida partida = new Partida(
                new Tablero(tableroCompleto),
                new Jugador[]{new JugadorHumano(Simbolo.X), new JugadorHumano(Simbolo.O)},
                0
        );

        assertTrue(partida.esEmpate());
    }

    private Partida crearPartidaBase() {
        return new Partida(new JugadorHumano(Simbolo.X), new JugadorHumano(Simbolo.O), 0);
    }
}
