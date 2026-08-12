package com.estructuras.tictactoe.model.computer;


import java.util.ArrayList;
import java.util.List;

import com.estructuras.tictactoe.model.game.Tablero;

public class NodoTablero {
    private Tablero estadoTablero;
    private int utilidad;
    private List<ArbolTablero> hijos;

    public NodoTablero(Tablero estadoTablero) {
        this.estadoTablero = estadoTablero;
        this.utilidad = 0;
        this.hijos = new ArrayList<>();
    }

    /**
     * Agrega un subárbol como hijo de este nodo.
     *
     * @param hijo subárbol que se agregará
     */
    public void agregarHijo(ArbolTablero hijo) {
        if (hijo != null) {
            hijos.add(hijo);
        }
    }

   
    public List<ArbolTablero> getHijos() {
        return hijos;
    }

    
    public Tablero getEstadoTablero() {
        return estadoTablero;
    }

    
    public int getUtilidad() {
        return utilidad;
    }

    /**
     * Establece la utilidad del nodo.
     *
     * @param utilidad valor de utilidad
     */
    public void setUtilidad(int utilidad) {
        this.utilidad = utilidad;
    }
}
