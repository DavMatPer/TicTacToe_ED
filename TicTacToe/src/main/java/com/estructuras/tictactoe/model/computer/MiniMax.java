package com.estructuras.tictactoe.model.computer;

import java.util.List;
import com.estructuras.tictactoe.model.game.Movimiento;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.game.Tablero;

public class MiniMax {
    private Simbolo simboloComputadora;
    private Simbolo simboloHumano;

    public MiniMax(Simbolo simbolComputadora) {
      this.simboloComputadora = simbolComputadora;
      this.simboloHumano = (simbolComputadora == Simbolo.X) ? Simbolo.O : Simbolo.X;
    }

    /**
     * Recorre el arbol de estados recursivamente aplicando el algoritmo MiniMax.
     * Maximiza la utilidad en los niveles correspondientes a la computadora y la
     * minimiza en los niveles correspondientes al humano.
     * @param nodo nodo actual del arbol de estados
     * @param turnoActual simbolo del jugador que tiene el turno en este nivel
     * @return utilidad calculada para el nodo actual
     */
    private int calcularUtilidad(NodoTablero nodo, Simbolo turnoActual) {
        if(nodo == null) {return 0;}

        if(nodo.getEstadoTablero().esGanador(simboloComputadora)) {
          return 1;
        } else if(nodo.getEstadoTablero().esGanador(simboloHumano)) {
          return -1;
        } else if(nodo.getEstadoTablero().esEmpate()) {
          return 0;
        }
        List<ArbolTablero> listaHijos = nodo.getHijos();
        if (listaHijos.isEmpty()) {
          // Nodo no terminal pero sin hijos: se alcanzó el límite de profundidad
          // del árbol antes de conocer el desenlace. Se trata como neutro.
          return 0;
        }
        if(turnoActual == simboloComputadora) {
          int utilidadMax = Integer.MIN_VALUE;
          for(ArbolTablero hijo : listaHijos) {
            int utilidadHijo = calcularUtilidad(hijo.getRaiz(), simboloHumano);
            if( utilidadHijo > utilidadMax) { utilidadMax = utilidadHijo; }
          }
            return utilidadMax;
        }
        if(turnoActual == simboloHumano) {
          int utilidadMin = Integer.MAX_VALUE;
          for(ArbolTablero hijo : listaHijos) {
            int utilidadHijo = calcularUtilidad(hijo.getRaiz(), simboloComputadora);
            if( utilidadHijo < utilidadMin) { utilidadMin = utilidadHijo; }
          }
          return utilidadMin;
        }
        return 0;
    }

    /**
     * Recorre los hijos de la raiz del arbol de estados y selecciona el movimiento que
     * maximiza la utilidad para el jugador segun el algoritmo.
     * @param nodo nodo actual del arbol de estados
     * @param simboloJugador simboloJugador simbolo del jugador que desea el mejor movimiento
     * @return movimiento con mayor utilidad
     */
    public Movimiento obtenerMejorMovimiento (NodoTablero nodo, Simbolo simboloJugador) {
      if(nodo == null) {return null;}

      int utilidadMax = Integer.MIN_VALUE;
      Movimiento mejorMovimiento = null;
      Simbolo simboloOponente = (simboloJugador == Simbolo.X) ? Simbolo.O : Simbolo.X;

      List<ArbolTablero> listaHijos = nodo.getHijos();
      for(ArbolTablero hijo : listaHijos) {
        int utilidad = calcularUtilidad(hijo.getRaiz(), simboloOponente);
        if(utilidad > utilidadMax) {
          utilidadMax = utilidad;
          mejorMovimiento = hijo.getRaiz().getMovimiento();
        }
      }
      System.out.println("Mejor movimiento: " + mejorMovimiento.toString());
      return mejorMovimiento;
    }
    
    public static Movimiento obtenerMejorMovimiento(Tablero tablero, Simbolo simboloJugador) {
        ArbolTablero arbol = new ArbolTablero();
        // Se construye el árbol a una profundidad fija. Podría ser configurable.
        int profundidadBusqueda = 5; 
        arbol.construirArbol(tablero, simboloJugador, profundidadBusqueda);

        NodoTablero raizArbol = arbol.getRaiz();

        if (raizArbol == null) {
            return null; // No se pudo construir el árbol o el tablero está vacío/finalizado
        }
        
        // Se crea una instancia de MiniMax para usar sus métodos de evaluación del árbol.
        MiniMax miniMaxEvaluador = new MiniMax(simboloJugador);
        
        return miniMaxEvaluador.obtenerMejorMovimiento(raizArbol, simboloJugador);
    }
}
