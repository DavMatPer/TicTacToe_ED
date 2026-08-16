package com.estructuras.tictactoe.view.controllers;

import com.estructuras.tictactoe.controllers.PartidaController;
import com.estructuras.tictactoe.model.computer.JugadorComputador;
import com.estructuras.tictactoe.model.computer.MiniMax;
import com.estructuras.tictactoe.model.game.Movimiento;
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

public class PartidaViewController implements Initializable {

    // --- CAPA 1: JUEGO ---
    @FXML private Label lblPC;
    @FXML private Label lblTurno;
    @FXML private GridPane gridTablero;

    // --- CAPA 2: MODAL GUARDADO ---
    @FXML private StackPane modalGuardar;
    @FXML private TextField txtNombreGuardado;

    @FXML private Label lblGanador;
    @FXML private StackPane modalFinal;

    private PartidaController controladorPartida;
    private Button[][] matrizBotones;
    private PauseTransition transicionPista;
    private MiniMax minimax;

    private final String RUTA_ARCHIVO = RutaGuardado.getRuta();

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
      controladorPartida = new PartidaController(partida, this);
      this.controladorPartida.iniciarPartida();
      if (controladorPartida.estaPausada()) {
          controladorPartida.reanudarPartida();
      }
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

            ;

                btnCasilla.setMinSize(80, 80);
                btnCasilla.setMaxSize(80, 80);
                btnCasilla.setPrefSize(80, 80);

                // Aplicamos la clase CSS en lugar del texto en duro
                btnCasilla.getStyleClass().add("casilla-juego");

                final int f = fila;
                final int c = col;

                btnCasilla.setOnAction(e -> alClickearCasilla(f, c));

                matrizBotones[fila][col] = btnCasilla;
                gridTablero.add(btnCasilla, col, fila);
            }
        }
    }

    private void alClickearCasilla(int fila, int col) {
        // Bloquear clicks si es turno del computador
        if (controladorPartida.getJugadorActual() instanceof JugadorComputador) {
            return;
        }

        if (transicionPista != null && transicionPista.getStatus() == javafx.animation.Animation.Status.RUNNING) {
            transicionPista.stop();
            actualizarVistaTablero();
        }

        System.out.println("Jugador seleccionó casilla: " + fila + "," + col);
        
        controladorPartida.realizarMovimientoHumano(fila, col);
        actualizarVistaTablero();

        if (controladorPartida.isGameOver()) {
            mostrarModalFinal();
        }
    }



    public void actualizarVistaTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = matrizBotones[i][j];
                Simbolo sim = controladorPartida.getTablero().getTablero()[i][j];

                // Limpiamos estilos previos de colores o pistas
                btn.getStyleClass().removeAll("simbolo-x", "simbolo-o", "casilla-pista");

                if (sim == Simbolo.V || sim == null) {
                    btn.setText("");
                } else switch (sim) {
                    case X:
                        btn.setText("X");
                        btn.getStyleClass().add("simbolo-x"); // Pinta la X de turquesa
                        break;
                    case O:
                        btn.setText("O");
                        btn.getStyleClass().add("simbolo-o"); // Pinta la O de naranja
                        break;
                    default:
                        btn.setText(""); 
                        break;
                }
            }
        }

        lblTurno.setText("Turno de: " + controladorPartida.getJugadorActual().getSimbolo());
        boolean esPC = controladorPartida.getJugadorActual() instanceof JugadorComputador;
        
        lblPC.setVisible(esPC);
    }

    /**
     * Habilita o deshabilita los botones del tablero según si es turno del computador.
     */
    private void actualizarEstadoBotones() {
        boolean esTurnoPC = controladorPartida.getJugadorActual() instanceof JugadorComputador;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrizBotones[i][j].setDisable(esTurnoPC);
            }
        }
    }

    @FXML
    private void solicitarPista(ActionEvent event) {
        System.out.println("Llamando al árbol N-ario / Minimax...");

        if (transicionPista != null && transicionPista.getStatus() == javafx.animation.Animation.Status.RUNNING) {
            return;
        }
        
        Movimiento mov = MiniMax.obtenerMejorMovimiento(controladorPartida.getTablero(), controladorPartida.getJugadorActual().getSimbolo());
        
        // Simulación: Supongamos que Minimax recomienda la fila 1, columna 1
        int filaSugerida = mov.getRow();
        int colSugerida = mov.getCol();

        Button btnSugerido = matrizBotones[filaSugerida][colSugerida];
        if (false) return; //TODO que la pista no sea en una casilla ocupada.

        // Iluminamos la casilla de color amarillo dorado
        btnSugerido.getStyleClass().add("casilla-pista");

        transicionPista = new PauseTransition(Duration.seconds(1.5));
        transicionPista.setOnFinished(e -> {
            actualizarVistaTablero();
        });
        transicionPista.play();
    }

    // ==========================================
    // CONTROL DEL MODAL SUPERPUESTO
    // ==========================================

    @FXML
    private void mostrarModalGuardar(ActionEvent event) {
        modalGuardar.setVisible(true);
        controladorPartida.pausarPartida();
    }

    @FXML
    private void ocultarModalGuardar(ActionEvent event) {
        modalGuardar.setVisible(false);
        txtNombreGuardado.clear();
        txtNombreGuardado.setPromptText("Asigna un nombre a tu partida...");
        controladorPartida.reanudarPartida();
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
            RegistroPartida nuevoRegistro = new RegistroPartida(nombre, controladorPartida.getPartida());
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
        if ( controladorPartida.hayGanador()) {
            Simbolo sim = controladorPartida.getJugadorActual().getSimbolo();
            lblGanador.setText("Ganador: Jugador " + sim.toString());
        } else {
            lblGanador.setText("Empate");
        }
        modalFinal.setVisible(true);

    }

}
