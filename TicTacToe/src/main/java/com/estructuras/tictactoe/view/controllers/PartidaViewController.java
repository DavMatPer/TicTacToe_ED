/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.estructuras.tictactoe.view.controllers;

import com.estructuras.tictactoe.controllers.PartidaController;
import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.persistencia.PartidaSerializer;
import com.estructuras.tictactoe.model.persistencia.RegistroPartida;
import com.estructuras.tictactoe.model.persistencia.RutaGuardado;
import com.estructuras.tictactoe.view.Navegacion;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
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

    @FXML private Label lblGanador;
    @FXML private StackPane modalFinal;
    
    private Partida partidaActual;
    private PartidaController controladorPartida;
    private Button[][] matrizBotones;
    private final String RUTA_ARCHIVO = RutaGuardado.getRuta();
    private final String ESTILO_BOTON_NORMAL = "-fx-font-size: 48px; -fx-font-weight: bold; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-cursor: hand;";
    private final String ESTILO_BOTON_PISTA = "-fx-font-size: 48px; -fx-font-weight: bold; -fx-background-color: #FFF9C4; -fx-border-color: #FBC02D; -fx-border-width: 4; -fx-cursor: hand;";
    
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
        //this.controladorPartida = new PartidaController( partida);
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
                btnCasilla.setStyle(ESTILO_BOTON_NORMAL);
                
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
        //TODO 
        // QUe no sea la parttida quien ghaga el movimiento más bien el controlador de ella.
        if (partidaActual.realizarMovimiento(fila, col)) {
            Button casilla = matrizBotones[fila][col];
            casilla.setText(
                    partidaActual.getJugadorActual().getSimbolo().toString()
            );
            if (partidaActual.isGameOver()) { 
                mostrarModalFinal();
                return;
            }
            partidaActual.cambiarTurno();
            actualizarVistaTablero();
        } 
    }

    private void actualizarVistaTablero() {
        // Recorremos tu Tablero (lógica) y pintamos las X y O correspondientes
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Simbolo sim = partidaActual.getTablero().getTablero()[i][j];
                if (sim == Simbolo.X) matrizBotones[j][i].setText("X");
                else if (sim == Simbolo.O) matrizBotones[j][i].setText("O");
                
                // Restauramos el estilo por si alguna casilla estaba iluminada como "pista"
                matrizBotones[j][i].setStyle(ESTILO_BOTON_NORMAL);
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
        btnSugerido.setStyle(ESTILO_BOTON_PISTA);
        
        PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));
        pausa.setOnFinished(e -> {
            actualizarVistaTablero();
        });
        pausa.play();
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
    
    private void ocultarModalGuardar() {
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
            
            Stage stage = (Stage) txtNombreGuardado.getScene().getWindow();
            mostrarToast("Guardado: " + nombre, stage);
            
            ejecutarRetornoAlMenu(event);
            
        } catch (Exception e) {
            System.err.println("Error fatal al guardar: " + e.getMessage());
            
            alertaError(e);
        }
    }

    @FXML
    private void salirSinGuardar(ActionEvent event) {
        ejecutarRetornoAlMenu(event);
    }

    private void ejecutarRetornoAlMenu(ActionEvent event) {
        Navegacion.avanzar(event, this, "MenuView.fxml");
    }
    
    private void alertaExito(String nombre) {
        Alert alertaExito = new Alert(Alert.AlertType.INFORMATION);
        alertaExito.setTitle("Partida Guardada");
        alertaExito.setHeaderText(null);
        alertaExito.setContentText("Tu partida '" + nombre + "' se guardó correctamente.");
        alertaExito.showAndWait(); // Pausa la ejecución hasta que el usuario dé OK
    }
    
    private void alertaError(Exception e) {
        Alert alertaError = new Alert(Alert.AlertType.ERROR);
        alertaError.setTitle("Error de Guardado");
        alertaError.setHeaderText("No se pudo guardar la partida.");
        alertaError.setContentText("Detalle: " + e.getMessage());
        alertaError.showAndWait();
    }
    

    private void mostrarToast(String mensaje, Stage stage) {
        Popup toast = new Popup();
        Label lblToast = new Label(mensaje);
        
        // Estilo oscuro, bordes redondeados y texto blanco
        lblToast.setStyle("-fx-background-color: rgba(50, 50, 50, 0.9); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 20; -fx-font-size: 14px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);");
        toast.getContent().add(lblToast);
        
        // Mostramos el popup para que JavaFX calcule su ancho real
        toast.show(stage);
        
        // Lo posicionamos centrado y en la parte inferior de la ventana
        toast.setY(stage.getY() + stage.getHeight() - 100);
        toast.setX(stage.getX() + (stage.getWidth() / 2) - (lblToast.getWidth() / 2));
        
        // Programamos su evaporación después de 2 segundos
        PauseTransition pausa = new PauseTransition(Duration.seconds(2));
        pausa.setOnFinished(e -> toast.hide());
        pausa.play();
    }
    
    /*
    ========================================
                  CONTROL MODAL FINAL
    ========================================
    */
    
    @FXML
    public void ocultarModalFinal(ActionEvent event) {
        modalFinal.setVisible(false);
        ejecutarRetornoAlMenu(event);
    }
    
    public void mostrarModalFinal() {
        Simbolo sim = partidaActual.getJugadorActual().getSimbolo();
        lblGanador.setText("Ganador: Jugador " + sim.toString());
        modalFinal.setVisible(true);
        
    }
    
}
