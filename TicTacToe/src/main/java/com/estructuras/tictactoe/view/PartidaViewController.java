/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.estructuras.tictactoe.view;

import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.persistencia.PartidaSerializer;
import com.estructuras.tictactoe.model.persistencia.RegistroPartida;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author david
 */
public class PartidaViewController implements Initializable {

    // --- CAPA 1: JUEGO ---
    @FXML private Label lblTurno;
    @FXML private GridPane gridTablero;
    
    // --- CAPA 2: MODAL GUARDADO ---
    @FXML private StackPane modalGuardar;
    @FXML private TextField txtNombreGuardado;

    // --- ESTADO INTERNO ---
    private Partida partidaActual;
    private Button[][] matrizBotones;
    private final String RUTA_ARCHIVO = "partidas.dat";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializamos la matriz visual de botones[cite: 14]
        matrizBotones = new Button[3][3];
        dibujarCuadriculaVacia();
    }    

    /**
     * Este método será llamado desde SeleccionController o CargaViewController
     * para inyectar la lógica al momento de cambiar a esta pantalla.
     */
    public void setPartida(Partida partida) {
        this.partidaActual = partida;
        actualizarVistaTablero();
    }

    // ==========================================
    // LÓGICA GRÁFICA DEL TABLERO
    // ==========================================
    
    private void dibujarCuadriculaVacia() {
        gridTablero.getChildren().clear();
        
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                Button btnCasilla = new Button("");
                btnCasilla.setPrefSize(120, 120);
                
                // Estilo para que parezca una cuadrícula clásica de Tres en Raya
                btnCasilla.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-cursor: hand;");
                
                // Capturamos las coordenadas de este botón específico
                final int f = fila;
                final int c = col;
                
                btnCasilla.setOnAction(e -> alClickearCasilla(f, c));
                
                matrizBotones[fila][col] = btnCasilla;
                gridTablero.add(btnCasilla, col, fila); // Añadir al GridPane (Nodo, Columna, Fila)
            }
        }
    }

    private void alClickearCasilla(int fila, int col) {
        // 1. Invocar a la lógica de tu modelo (Asegúrate de tener un método similar en Partida)
        // boolean movimientoValido = partidaActual.registrarMovimiento(fila, col);
        
        System.out.println("Jugador seleccionó casilla: " + fila + "," + col);
        
        // 2. Si el movimiento se registró en tu matriz interna del Tablero, refrescamos la GUI
        // if (movimientoValido) {
        //     actualizarVistaTablero();
        //     // Aquí puedes evaluar si hay ganador, empate, o si le toca jugar a la PC.
        // }
    }

    private void actualizarVistaTablero() {
        // Recorremos tu Tablero (lógica) y pintamos las X y O correspondientes
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Simbolo sim = partidaActual.getTablero().getTablero()[i][j];
                if (sim == Simbolo.X) matrizBotones[i][j].setText("X");
                else if (sim == Simbolo.O) matrizBotones[i][j].setText("O");
                
                // Restauramos el estilo por si alguna casilla estaba iluminada como "pista"
                matrizBotones[i][j].setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-cursor: hand;");
            }
        }
        lblTurno.setText("Turno de: " + partidaActual.getJugadorActual().getSimbolo());
        
    }

    @FXML
    private void solicitarPista(ActionEvent event) {
        System.out.println("Llamando al árbol N-ario / Minimax...");
        
        // Simulación: Supongamos que Minimax recomienda la fila 1, columna 1
        int filaSugerida = 1;
        int colSugerida = 1;
        
        Button btnSugerido = matrizBotones[filaSugerida][colSugerida];
        
        // Iluminamos la casilla de color amarillo dorado
        btnSugerido.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-background-color: #FFF9C4; -fx-border-color: #FBC02D; -fx-border-width: 4; -fx-cursor: hand;");
    }

    // ==========================================
    // CONTROL DEL MODAL SUPERPUESTO
    // ==========================================

    @FXML
    private void mostrarModalGuardar(ActionEvent event) {
        modalGuardar.setVisible(true);
    }

    @FXML
    private void ocultarModalGuardar(ActionEvent event) {
        modalGuardar.setVisible(false);
        txtNombreGuardado.clear();
        txtNombreGuardado.setPromptText("Asigna un nombre a tu partida...");
    }

    @FXML
    private void guardarYSalir(ActionEvent event) {
        String nombre = txtNombreGuardado.getText().trim();
        
        if (nombre.isEmpty()) {
            txtNombreGuardado.setStyle("-fx-border-color: red;");
            txtNombreGuardado.setPromptText("¡El nombre no puede estar vacío!");
            return;
        }

        try {
            RegistroPartida nuevoRegistro = new RegistroPartida(nombre, partidaActual);
            PartidaSerializer.guardarPartida(nuevoRegistro, RUTA_ARCHIVO);
            
            // Si guardó exitosamente, nos devolvemos al menú principal
            ejecutarRetornoAlMenu(event);
            
        } catch (Exception e) {
            System.err.println("Error fatal al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void salirSinGuardar(ActionEvent event) {
        ejecutarRetornoAlMenu(event);
    }

    private void ejecutarRetornoAlMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al regresar al menú: " + e.getMessage());
        }
    }
}
