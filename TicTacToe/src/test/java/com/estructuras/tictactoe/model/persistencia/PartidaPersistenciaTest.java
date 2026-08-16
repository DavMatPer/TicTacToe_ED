package com.estructuras.tictactoe.model.persistencia;

import com.estructuras.tictactoe.model.computer.JugadorComputador;
import com.estructuras.tictactoe.model.game.JugadorHumano;
import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Simbolo;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para verificar la serialización y carga de partidas con JugadorComputador.
 */
public class PartidaPersistenciaTest {

    private String rutaArchivo;
    private File archivo;

    @BeforeEach
    public void setUp() {
        rutaArchivo = "test_partidas.dat";
        archivo = new File(rutaArchivo);
        
        // Limpiar archivo de prueba si existe
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    /**
     * Verifica que se puede guardar una partida con JugadorComputador.
     */
    @Test
    public void testGuardarPartidaConJugadorComputador() throws IOException {
        Jugador humano = new JugadorHumano(Simbolo.X);
        Jugador computador = new JugadorComputador(Simbolo.O);
        Partida partida = new Partida(humano, computador, 0);

        RegistroPartida registro = new RegistroPartida("Partida PC", partida);
        
        assertDoesNotThrow(() -> PartidaSerializer.guardarPartida(registro, rutaArchivo));
        
        assertTrue(archivo.exists(),"El archivo debe existir");
    }

    /**
     * Verifica que se puede cargar una partida guardada con JugadorComputador.
     */
    @Test
    public void testCargarPartidaConJugadorComputador() throws IOException, ClassNotFoundException {
        Jugador humano = new JugadorHumano(Simbolo.X);
        Jugador computador = new JugadorComputador(Simbolo.O);
        Partida partidaOriginal = new Partida(humano, computador, 0);

        RegistroPartida registro = new RegistroPartida("Partida Test", partidaOriginal);
        PartidaSerializer.guardarPartida(registro, rutaArchivo);

        Partida partidaCargada = PartidaSerializer.cargarPartida("Partida Test", rutaArchivo);

        assertNotNull(partidaCargada, "La partida cargada no debe ser null");
        assertNotNull( partidaCargada.getTablero() , "El tablero no debe ser null");
        assertNotNull(partidaCargada.getJugadorActual(), "El jugador actual no debe ser null");
    }

    /**
     * Verifica que se puede obtener la lista de partidas guardadas.
     */
    @Test
    public void testObtenerListaDePartidas() throws IOException, ClassNotFoundException {
        Jugador humano = new JugadorHumano(Simbolo.X);
        Jugador computador = new JugadorComputador(Simbolo.O);
        Partida partida1 = new Partida(humano, computador, 0);
        Partida partida2 = new Partida(computador, humano, 1);

        RegistroPartida registro1 = new RegistroPartida("Partida 1", partida1);
        RegistroPartida registro2 = new RegistroPartida("Partida 2", partida2);

        PartidaSerializer.guardarPartida(registro1, rutaArchivo);
        PartidaSerializer.guardarPartida(registro2, rutaArchivo);

        List<String> partidas = PartidaSerializer.obtenerPartidasGuardadas(rutaArchivo);

        assertTrue( partidas.size() == 2, "Debe haber 2 partidas");
        assertTrue( partidas.contains("Partida 1") ,"Debe contener 'Partida 1'");
        assertTrue( partidas.contains("Partida 2") ,"Debe contener 'Partida 2'");
    }

    /**
     * Verifica que al cargar una partida, esta se elimina del archivo.
     */
    @Test
    public void testAlCargarSeEliminaDelArchivo() throws IOException, ClassNotFoundException {
        Jugador humano = new JugadorHumano(Simbolo.X);
        Jugador computador = new JugadorComputador(Simbolo.O);
        Partida partida = new Partida(humano, computador, 0);

        RegistroPartida registro = new RegistroPartida("Partida Temporal", partida);
        PartidaSerializer.guardarPartida(registro, rutaArchivo);

        List<String> partidasAntes = PartidaSerializer.obtenerPartidasGuardadas(rutaArchivo);
        assertTrue(partidasAntes.size() == 1, "Debe haber 1 partida antes");

        PartidaSerializer.cargarPartida("Partida Temporal", rutaArchivo);

        List<String> partidasDespues = PartidaSerializer.obtenerPartidasGuardadas(rutaArchivo);
        assertTrue(partidasDespues.isEmpty(), "No debe haber partidas después");
    }

    /**
     * Limpia el archivo de prueba.
     */
    @AfterEach
    public void limpiar() {
        if (archivo.exists()) {
            archivo.delete();
        }
    }
}
