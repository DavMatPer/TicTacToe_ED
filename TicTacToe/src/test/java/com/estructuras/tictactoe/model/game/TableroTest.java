package com.estructuras.tictactoe.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.Transient;

import org.junit.jupiter.api.Test;

class TableroTest {

    @Test
    void realizarMovimientoColocaSimboloEnCasillaVacia() {
        Tablero tablero = new Tablero();

        assertTrue(tablero.realizarMovimiento(new Movimiento(0, 0, Simbolo.X)));
        assertEquals(Simbolo.X, tablero.getTablero()[0][0]);
    }

    @Test
    void realizarMovimientoEnCasillaOcupadaRetornaFalse() {
        Tablero tablero = new Tablero();
        tablero.realizarMovimiento(new Movimiento(1, 1, Simbolo.O));

        assertFalse(tablero.realizarMovimiento(new Movimiento(1, 1, Simbolo.X)));
    }

    @Test
    void esGanadorDetectaLineaHorizontal() {
        Simbolo[][] tableroConGanador = {
            {Simbolo.X, Simbolo.X, Simbolo.X},
            {Simbolo.O, Simbolo.V, Simbolo.V},
            {Simbolo.V, Simbolo.V, Simbolo.O}
        };
        Tablero tablero = new Tablero(tableroConGanador);

        assertTrue(tablero.esGanador(Simbolo.X));
        assertFalse(tablero.esGanador(Simbolo.O));
    }

    @Test
    void esGanadorDetectaLineaVertical() {
        Simbolo[][] tableroConGanador = {
            {Simbolo.X, Simbolo.O, Simbolo.V},
            {Simbolo.X, Simbolo.V, Simbolo.O},
            {Simbolo.X, Simbolo.O, Simbolo.V}
        };
        Tablero tablero = new Tablero(tableroConGanador);

        assertTrue(tablero.esGanador(Simbolo.X));
        assertFalse(tablero.esGanador(Simbolo.O));
    }

    @Test
    void esGanadorDetectaLineaDiagonalPrincipal() {
        Simbolo[][] tableroConGanador = {
            {Simbolo.X, Simbolo.O, Simbolo.V},
            {Simbolo.O, Simbolo.X, Simbolo.V},
            {Simbolo.V, Simbolo.V, Simbolo.X}
        };
        Tablero tablero = new Tablero(tableroConGanador);

        assertTrue(tablero.esGanador(Simbolo.X));
        assertFalse(tablero.esGanador(Simbolo.O));
    }

    @Test
    void esGanadorDetectaLineaDiagonalSecundaria() {
        Simbolo[][] tableroConGanador = {
            {Simbolo.V, Simbolo.O, Simbolo.X},
            {Simbolo.O, Simbolo.X, Simbolo.X},
            {Simbolo.X, Simbolo.V, Simbolo.V}
        };
        Tablero tablero = new Tablero(tableroConGanador);

        assertTrue(tablero.esGanador(Simbolo.X));
        assertFalse(tablero.esGanador(Simbolo.O));
    }  

     @Test
    void esGanadorRetornaFalseCuandoNoHayGanador() {
        Simbolo[][] tableroSinGanador = {
            {Simbolo.X, Simbolo.O, Simbolo.X},
            {Simbolo.O, Simbolo.X, Simbolo.O},
            {Simbolo.O, Simbolo.X, Simbolo.O}
        };
        Tablero tablero = new Tablero(tableroSinGanador);

        assertFalse(tablero.esGanador(Simbolo.X));
        assertFalse(tablero.esGanador(Simbolo.O));
    }

    @Test
    void esEmpateRetornaTrueParaTableroLlenoSinGanador() {
        Simbolo[][] tableroCompleto = {
            {Simbolo.X, Simbolo.O, Simbolo.X},
            {Simbolo.O, Simbolo.O, Simbolo.X},
            {Simbolo.X, Simbolo.X, Simbolo.O}
        };
        Tablero tablero = new Tablero(tableroCompleto);

        assertTrue(tablero.esEmpate());
    }

    @Test
    void esEmpateRetornaFalseCuandoHayGanador() {
        Simbolo[][] tableroConGanador = {
            {Simbolo.X, Simbolo.X, Simbolo.X},
            {Simbolo.O, Simbolo.V, Simbolo.V},
            {Simbolo.V, Simbolo.V, Simbolo.O}
        };
        Tablero tablero = new Tablero(tableroConGanador);

        assertFalse(tablero.esEmpate());
    }

    @Test
    void isFullRetornaTrueCuandoNoQuedanCasillasVacias() {
        Simbolo[][] tableroCompleto = {
            {Simbolo.X, Simbolo.O, Simbolo.X},
            {Simbolo.O, Simbolo.O, Simbolo.X},
            {Simbolo.X, Simbolo.X, Simbolo.O}
        };
        Tablero tablero = new Tablero(tableroCompleto);

        assertTrue(tablero.isFull());
        assertFalse(tablero.isEmpty());
    }

   

    @Test
    void getCasillasVaciasRetornaTodasLasCasillasVacias() {
        Tablero tablero = new Tablero();

        assertEquals(9, tablero.getCasillasVacias().size());
    }

    @Test
    void getCasillasVaciasRetornaSoloCasillasVacias() {
        Simbolo[][] tableroParcial = {
            {Simbolo.X, Simbolo.V, Simbolo.O},
            {Simbolo.V, Simbolo.X, Simbolo.V},
            {Simbolo.O, Simbolo.V, Simbolo.X}
        };
        Tablero tablero = new Tablero(tableroParcial);

        assertEquals(4, tablero.getCasillasVacias().size());
    }

    @Test
    void getCasillasConRetornaTodasLasCasillasConSimbolo() {
        Simbolo[][] tableroParcial = {
            {Simbolo.X, Simbolo.V, Simbolo.O},
            {Simbolo.V, Simbolo.X, Simbolo.V},
            {Simbolo.O, Simbolo.V, Simbolo.X}
        };
        Tablero tablero = new Tablero(tableroParcial);

        assertEquals(3, tablero.getCasillasCon(Simbolo.X).size());
        assertEquals(2, tablero.getCasillasCon(Simbolo.O).size());
    }

    @Test
    void cloneCreaUnaCopiaIndependiente() {
        Simbolo[][] tableroParcial = {
            {Simbolo.X, Simbolo.V, Simbolo.O},
            {Simbolo.V, Simbolo.X, Simbolo.V},
            {Simbolo.O, Simbolo.V, Simbolo.X}
        };
        Tablero tableroOriginal = new Tablero(tableroParcial);
        Tablero tableroClonado = tableroOriginal.clone();

        // Modificar el tablero clonado
        tableroClonado.realizarMovimiento(new Movimiento(0, 1, Simbolo.O));

        // Verificar que el tablero original no se vea afectado
        assertEquals(Simbolo.V, tableroOriginal.getTablero()[0][1]);
        assertEquals(Simbolo.O, tableroClonado.getTablero()[0][1]);
    }   

    @Test
    void esPosValidaRetornaTrueParaCoordenadasValidas() {
        assertTrue(Tablero.esPosValida(0, 0));
        assertTrue(Tablero.esPosValida(1, 1));
        assertTrue(Tablero.esPosValida(2, 2));
    }

    @Test
    void esPosValidaRetornaFalseParaCoordenadasInvalidas() {
        assertFalse(Tablero.esPosValida(-1, 0));
        assertFalse(Tablero.esPosValida(0, -1));
        assertFalse(Tablero.esPosValida(3, 0));
        assertFalse(Tablero.esPosValida(0, 3));
    }
}
