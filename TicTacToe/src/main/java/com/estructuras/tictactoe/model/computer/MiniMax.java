package com.estructuras.tictactoe.model.computer;

import java.util.List;

import com.estructuras.tictactoe.model.game.Movimiento;
import com.estructuras.tictactoe.model.game.Simbolo;


/**
 * Clase encargada de implementar el algoritmo MiniMax para la toma de decisiones del jugador computadora.
 * Es la encargada de calcular la útilidad y escoger el mejor movimiento.
 */
public class MiniMax {
      private Simbolo simboloComputadora;
      private Simbolo simboloHumano;

      public MiniMax(Simbolo simboloComputadora) {
        this.simboloComputadora = simboloComputadora;
        this.simboloHumano = (simboloComputadora == Simbolo.X) ? Simbolo.O : Simbolo.X;
      }

      /**
       * Recorre el arbol de estados recursivamente, aplicando el algoritmo MiniMax.
       * Maximiza la utilidad en los niveles correspondientes a la computadora
       * y la minimiza en los niveles correspondientes al humano.
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
        if(turnoActual == simboloComputadora) {
        int utilidadMax = -1;
          for(ArbolTablero hijo : listaHijos) {
            int utilidadHijo = calcularUtilidad(hijo.getRaiz(), simboloHumano);
            if( utilidadHijo > utilidadMax) { utilidadMax = utilidadHijo; }
          }
          return utilidadMax;
        }
        if(turnoActual == simboloHumano) {
          int utilidadMin = 1;
          for(ArbolTablero hijo : listaHijos) {
            int utilidadHijo = calcularUtilidad(hijo.getRaiz(), simboloComputadora);
            if( utilidadHijo < utilidadMin) { utilidadMin = utilidadHijo; }
          }
          return utilidadMin;
        }
        return 0;
      }

      /**
       * Recorre los hijos de la raiz del arbol de estados y selecciona el
       * movimiento que maximiza la utilidad para el jugador segun el algoritmo
       * MiniMax
       * @param nodo nodo raiz del arbol de estados
       * @param simboloJugador simbolo del jugador que desea el mejor movimiento
       * @return movimiento con mayor utilidad
       */
      public Movimiento obtenerMejorMovimiento (NodoTablero nodo, Simbolo simboloJugador) {
        if(nodo == null) {return null;}

        int utilidadMax = -1;
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
        return mejorMovimiento;
      }
}
