package com.estructuras.tictactoe.model.game;

/**
 * 
 * @author david
 */
public class Movimiento {
    private int row;
    private int col;
    private Simbolo simbolo;

    public Movimiento(int row, int col, Simbolo simbolo) {
        if (!Tablero.esPosValida(row, col)) {
            throw new IllegalArgumentException("Posición inválida");
        }
        if (simbolo == null || simbolo == Simbolo.V) {
            throw new IllegalArgumentException("Símbolo inválido. Debe ser X o O");
        }
        this.row = row;
        this.col = col;
        this.simbolo = simbolo;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }
}
