package com.estructuras.tictactoe.model.computer;

import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.Movimiento;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.game.Tablero;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para verificar la serialización y deserialización de JugadorComputador.
 */
public class JugadorComputadorSerializationTest {

    /**
     * Verifica que JugadorComputador se puede serializar y deserializar correctamente.
     */
    @Test
    public void testJugadorComputadorSerializationDeserialization() throws IOException, ClassNotFoundException {
        JugadorComputador jugadorOriginal = new JugadorComputador(Simbolo.X);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(jugadorOriginal);
        oos.close();
        
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        JugadorComputador jugadorRestaurado = (JugadorComputador) ois.readObject();
        ois.close();
        
        assertNotNull(jugadorRestaurado);
        assertEquals(Simbolo.X, jugadorRestaurado.getSimbolo());
    }

    /**
     * Verifica que después de deserializar, JugadorComputador puede realizar movimientos.
     */
    @Test
    public void testJugadorComputadorPuedeJugarDespuesDeDeserializar() throws IOException, ClassNotFoundException {
        JugadorComputador jugadorOriginal = new JugadorComputador(Simbolo.O);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(jugadorOriginal);
        oos.close();
        
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        JugadorComputador jugadorRestaurado = (JugadorComputador) ois.readObject();
        ois.close();
        
        Tablero tablero = new Tablero();
        Movimiento movimiento = jugadorRestaurado.realizarMovimiento(tablero);
        
        assertNotNull(movimiento);
        assertTrue(movimiento.getRow() >= 0 && movimiento.getRow() < 3);
        assertTrue(movimiento.getCol() >= 0 && movimiento.getCol() < 3);
    }

    /**
     * Verifica que el símbolo se mantiene después de serialización.
     */
    @Test
    public void testSimboloSeMantieneEnSerializacion() throws IOException, ClassNotFoundException {
        for (Simbolo simbolo : new Simbolo[]{Simbolo.X, Simbolo.O}) {
            JugadorComputador jugador = new JugadorComputador(simbolo);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(jugador);
            oos.close();
            
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            JugadorComputador jugadorRestaurado = (JugadorComputador) ois.readObject();
            ois.close();
            
            assertEquals(simbolo, jugadorRestaurado.getSimbolo(), "El símbolo debe mantenerse");
        }
    }
}
