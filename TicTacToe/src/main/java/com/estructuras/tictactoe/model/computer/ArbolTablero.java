package com.estructuras.tictactoe.model.computer;

import com.estructuras.tictactoe.model.game.*;

public class ArbolTablero {

  private NodoTablero raiz;

    /**
     * Constructor vacío.
     * Permite crear un árbol que posteriormente tendrá una raíz.
     */
    public ArbolTablero() {
        this.raiz = null;
    }

    /**
     * Constructor que recibe la raíz del árbol.
     *
     * @param raiz nodo raíz
     */
    public ArbolTablero(NodoTablero raiz) {
        this.raiz = raiz;
    }

    /**
     * Construye el árbol de estados posibles a partir
     * del tablero actual.
     *
     * @param tableroActual estado actual del tablero
     * @param turnoActual símbolo del jugador que debe mover
     * @param profundidad cantidad de niveles a generar
     */
    public void construirArbol(Tablero tableroActual, Simbolo turnoActual, int profundidad) {

        if (tableroActual == null) {
            throw new IllegalArgumentException(
                "El tablero no puede ser null"
            );
        }

        if (turnoActual == null || turnoActual == Simbolo.V) {
            throw new IllegalArgumentException(
                "El turno debe ser X u O"
            );
        }

        if (profundidad < 0) {
            throw new IllegalArgumentException(
                "La profundidad no puede ser negativa"
            );
        }

        raiz = new NodoTablero(tableroActual.clone());

        construirDesdeNodo(
            raiz,
            turnoActual,
            profundidad
        );
    }

    /**
     * Construye recursivamente los subárboles de un nodo.
     */
    private void construirDesdeNodo(
            NodoTablero nodo,
            Simbolo turnoActual,
            int profundidad) {

        Tablero tablero = nodo.getEstadoTablero();

        /*
         * Condiciones de parada.
         */
        if (profundidad == 0) {
            return;
        }

        if (tablero.esGanador(Simbolo.X)
                || tablero.esGanador(Simbolo.O)
                || tablero.esEmpate()) {
            return;
        }

        /*
         * Obtener todas las casillas disponibles.
         */
        for (Integer[] posicion : tablero.getCasillasVacias()) {

            int fila = posicion[0];
            int columna = posicion[1];

            /*
             * Crear una copia independiente del tablero.
             */
            Tablero nuevoTablero = tablero.clone();

            /*
             * Crear el movimiento.
             */
            Movimiento movimiento =
                new Movimiento(fila, columna, turnoActual);

            /*
             * Aplicar el movimiento sobre la copia.
             */
            nuevoTablero.realizarMovimiento(movimiento);

            /*
             * Crear el nodo correspondiente al nuevo estado.
             */
            NodoTablero nuevoNodo =
                new NodoTablero(nuevoTablero);
            nuevoNodo.setMovimiento(movimiento);
            /*
             * Crear el subárbol cuya raíz es el nuevo nodo.
             */
            ArbolTablero subArbol =
                new ArbolTablero(nuevoNodo);

            /*
             * Agregar el subárbol como hijo del nodo actual.
             */
            nodo.agregarHijo(subArbol);

            /*
             * Cambiar el turno.
             */
            Simbolo siguienteTurno =
                obtenerSiguienteSimbolo(turnoActual);

            /*
             * Continuar construyendo el árbol.
             */
            construirDesdeNodo(
                nuevoNodo,
                siguienteTurno,
                profundidad - 1
            );
        }
    }

    /**
     * Retorna el símbolo correspondiente al siguiente turno.
     */
    private Simbolo obtenerSiguienteSimbolo(Simbolo simbolo) {

        if (simbolo == Simbolo.X) {
            return Simbolo.O;
        }

        return Simbolo.X;
    }


    public NodoTablero getRaiz() {
        return raiz;
    }
}
