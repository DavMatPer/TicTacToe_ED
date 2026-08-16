package com.estructuras.tictactoe.model.persistencia;

import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.JugadorHumano;
import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.game.Tablero;

import java.io.File;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PartidaSerializerIT {
    private final String RUTA_ARCHIVO = "pruebaRegistroPartida.dat";


    public PartidaSerializerIT() {
    }


    /**
     * Asegurarse de que un archivo residual no afecte la prueba
     */
    @BeforeEach
    public void setUp() {
        File file = new File(RUTA_ARCHIVO);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Asegurarse de eliminar el archivo luego de la prueba.
     */
    @AfterEach
    public void tearDown() {
        File file = new File(RUTA_ARCHIVO);
        if (file.exists()) {
            file.delete();
        }
    }


    /**
     * T-PS-001
     * Test para validar el guardado del archivo.
     */
    @Test
    public void testGuardarPartida() throws Exception {
        System.out.println("guardarPartida");

        Jugador[] jugadores = new Jugador[]{
            new JugadorHumano(Simbolo.X),
            new JugadorHumano(Simbolo.O)
        };

        Partida partida = new Partida(new Tablero(), jugadores, 0);
        RegistroPartida registro = new RegistroPartida("partida_test", partida);
        PartidaSerializer.guardarPartida(registro, RUTA_ARCHIVO);

        File file = new File(RUTA_ARCHIVO);
        assertTrue(file.exists(), "El archivo de guardado debería existir tras invocar el método.");
        assertTrue(file.length() > 0, "El archivo de guardado no debería estar vacío.");
    }

    /**
     * T-PS-002
     * Test para verificar si al guardar una partida el archivo no se sobre escribe.
     * @throws Exception
     */
    @Test
    public void testGuardarEnArchivoLleno() throws Exception {
        System.out.println("guardarPartida");

        Jugador[] jugadores = new Jugador[]{
            new JugadorHumano(Simbolo.X),
            new JugadorHumano(Simbolo.O)
        };

        Partida partida = new Partida(new Tablero(), jugadores, 0);
        RegistroPartida registro = new RegistroPartida("partida_test", partida);
        PartidaSerializer.guardarPartida(registro, RUTA_ARCHIVO);
        PartidaSerializer.guardarPartida(registro, RUTA_ARCHIVO);

        File file = new File(RUTA_ARCHIVO);

        assertTrue(file.exists(), "El aechivo de guardadi debería existir tras invocar el método,");
        assertTrue(file.length() > 1, "El archivo deberái tener más de una línea.");
    }

    /**
     * T-PS-003
     * Test of cargarPartida method, of class PartidaSerializer.
     */
    @Test
    public void testCargarPartida() throws Exception {
        System.out.println("TEST: cargarPartida");
        String nombrePartida = "partida_a_cargar";

        // 1. Preparar el terreno (Guardar una partida primero)
        Jugador[] jugadores = new Jugador[]{new JugadorHumano(Simbolo.X), new JugadorHumano(Simbolo.O)};
        Partida partidaOriginal = new Partida(new Tablero(), jugadores, 0);
        RegistroPartida registro = new RegistroPartida(nombrePartida, partidaOriginal);
        PartidaSerializer.guardarPartida(registro, RUTA_ARCHIVO);

        // 2. Ejecutar el método a probar
        Partida result = PartidaSerializer.cargarPartida(nombrePartida, RUTA_ARCHIVO);

        // 3. Validar
        assertNotNull(result, "La partida cargada no debe ser nula.");

        // 4. Validar que se consumió (si ejecutamos cargar de nuevo, debería fallar)
        assertThrows(ClassNotFoundException.class, () -> {
            PartidaSerializer.cargarPartida(nombrePartida, RUTA_ARCHIVO);
        }, "La partida debería haber sido eliminada (consumida) tras la primera carga.");
    }

    /**
     * T-PS-004
     * Test para validar el comportamiento al buscar una partida que no existe.
     */
    @Test
    public void testCargarPartidaInexistente() {
        System.out.println("TEST: cargarPartidaInexistente");

        // Intentar cargar desde un archivo que ni siquiera existe
        assertThrows(Exception.class, () -> {
            PartidaSerializer.cargarPartida("Partida_Fantasma", RUTA_ARCHIVO);
        }, "Debería lanzar una excepción (IOException o ClassNotFoundException) al no encontrar el archivo o partida.");
    }

    /**
     * T-SP-005
     * Test of obtenerPartidasGuardadas method, of class PartidaSerializer.
     */
    @Test
    public void testObtenerPartidasGuardadas() throws Exception {
        System.out.println("TEST: obtenerPartidasGuardadas");

        // 1. Guardar múltiples partidas
        Jugador[] jugadores = new Jugador[]{new JugadorHumano(Simbolo.X), new JugadorHumano(Simbolo.O)};

        RegistroPartida reg1 = new RegistroPartida("Juego1", new Partida(new Tablero(), jugadores, 0));
        RegistroPartida reg2 = new RegistroPartida("Juego2", new Partida(new Tablero(), jugadores, 0));

        PartidaSerializer.guardarPartida(reg1, RUTA_ARCHIVO);
        PartidaSerializer.guardarPartida(reg2, RUTA_ARCHIVO);

        // 2. Ejecutar el método
        List<String> result = PartidaSerializer.obtenerPartidasGuardadas(RUTA_ARCHIVO);

        // 3. Validar
        assertNotNull(result, "La lista devuelta no debe ser nula.");
        assertEquals(2, result.size(), "Deberían haberse encontrado exactamente 2 partidas.");
        assertTrue(result.contains("Juego1"), "La lista debe contener 'Juego1'");
        assertTrue(result.contains("Juego2"), "La lista debe contener 'Juego2'");
    }

    /**
     * T-SP-006
     *
     */
    @Test
    public void testObtenerPartidasGuardadasSinPartidas() throws Exception {
        System.out.println("Test: obtenerPartidasGuardadasSinPartidas");

        File file = new File(RUTA_ARCHIVO);

        List<String> result = PartidaSerializer.obtenerPartidasGuardadas(RUTA_ARCHIVO);

        assertNotNull(result, "La lista devuelta no debe ser nula.");
        assertTrue(result.isEmpty(), "La lista debería estar vacía.");
    }

}
