/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estructuras.tictactoe.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class Tablero implements Serializable{
    private static final long serialVersionUID = 1L;
    private Simbolo[][] tablero;

    public Tablero() {
        this.tablero = new Simbolo[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = Simbolo.V;
            }
        }
    }

    public Tablero(Simbolo[][] tablero) {
        if(tablero.length != 3 || tablero[0].length != 3) {
            throw new IllegalArgumentException("El tablero debe ser de 3x3");
        }
        this.tablero = tablero;
    }

    /**
     * Retorna el tablero de juego.
     * @return
     */
    public Simbolo[][] getTablero() {
        return tablero;
    }

    /**
     * Establece el tablero de juego.
     * @param tablero
     */
    public void setTablero(Simbolo[][] tablero) {
        this.tablero = tablero;
    }

    /**
     * Realiza un movimiento en el tablero.
     * @param movimiento
     * @return true si el movimiento se realizó correctamente, false en caso contrario.
     */
    public boolean realizarMovimiento(Movimiento movimiento) {
        int row = movimiento.getRow();
        int col = movimiento.getCol();
        Simbolo simbolo = movimiento.getSimbolo();

        if ( tablero[row][col] != Simbolo.V) return false;

        tablero[row][col] = simbolo; 
        return true;
    }
    
    /**
     * Verifica si el tablero está vacío.
     * @return
     */
    public boolean isEmpty() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] != Simbolo.V) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifica si el tablero está lleno.
     * @return
     */
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == Simbolo.V) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Verifica si un jugador ha ganado el juego.
     * @param simbolo
     * @return true si ese jugador ganó, false en caso contrario.
     */
    public boolean esGanador(Simbolo simbolo) {
        if (simbolo == null || simbolo == Simbolo.V) return false;
        if (verificarFilas(simbolo) || verificarColumnas(simbolo) || verificarDiagonales(simbolo)) {
           return true;
        }
        return false;
    }

        

    /**
     * Verifica si el juego terminó en empate.
     * @return true si el juego terminó en empate, false en caso contrario.
     */
    public boolean esEmpate() {
        if (verificarColumnas(Simbolo.X) || verificarColumnas(Simbolo.O)) return false;
        if (verificarFilas(Simbolo.X) || verificarFilas(Simbolo.O)) return false;
        if (verificarDiagonales(Simbolo.X) || verificarDiagonales(Simbolo.O)) return false;
        return isFull();
    }

    /**
     * Verifica si un jugador ha ganado el juego en alguna de las filas.
     * @param simbolo
     * @return
     */
    private boolean verificarFilas(Simbolo simbolo) {
        if (simbolo == null || simbolo == Simbolo.V) return false;
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == simbolo && tablero[i][1] == simbolo && tablero[i][2] == simbolo) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un jugador ha ganado el juego en alguna de las columnas.
     * @param simbolo
     * @return
     */
    private boolean verificarColumnas(Simbolo simbolo) {
        if (simbolo == null || simbolo == Simbolo.V) return false;
        for (int i = 0; i < 3; i++) {
            if (tablero[0][i] == simbolo && tablero[1][i] == simbolo && tablero[2][i] == simbolo) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si un jugador ha ganado el juego en alguna de las diagonales.
     * @param simbolo
     * @return
     */
    private boolean verificarDiagonales(Simbolo simbolo) {
        if (simbolo == null || simbolo == Simbolo.V) return false;
        return (tablero[0][0] == simbolo && tablero[1][1] == simbolo && tablero[2][2] == simbolo) ||
               (tablero[0][2] == simbolo && tablero[1][1] == simbolo && tablero[2][0] == simbolo);
    }

    /**
     * Retorna una lista de los movimientos disponibles en el tablero.
     * @return una lista con las coordenadas {col , row }de los movimientos disponibles en el tablero.
     */
    public List<Integer[]> getCasillasVacias() {
        List<Integer[]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == Simbolo.V) {
                    moves.add(new Integer[]{i, j});
                }
            }
        }
        return moves;
    }

    /**
     * Retorna una lista de las posiciones ocupadas por un símbolo específico en el tablero.
     * @param simbolo Símbolo a buscar en el tablero.
     * @return una lista con las coordenadas {col , row }de las posiciones ocupadas por el símbolo.
     */
    public List<Integer[]> getCasillasCon(Simbolo simbolo) {
        List<Integer[]> positions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == simbolo) {
                    positions.add(new Integer[]{i, j});
                }
            }
        }
        return positions;
    }

    /**
     * Crea una copia del tablero actual.
     * @return una nueva instancia de Tablero con los mismos valores que el tablero actual,
     * pero no apuntan al mismo array de símbolos.
     */
    public Tablero clone() {
        Simbolo[][] newTablero = new Simbolo[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newTablero[i][j] = this.tablero[i][j];
            }
        }
        return new Tablero(newTablero);
    }

    /**
     * Verifica si una posición en el tablero es válida.
     * @param row Fila de la posición a verificar.
     * @param col Columna de la posición a verificar.
     * @return true si la posición es válida, false en caso contrario.
     */
    static public boolean esPosValida( int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3;
    }
}
