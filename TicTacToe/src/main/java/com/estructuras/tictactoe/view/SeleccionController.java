package com.estructuras.tictactoe.view;

import com.estructuras.tictactoe.model.game.Jugador;
import com.estructuras.tictactoe.model.computer.JugadorComputador;
import com.estructuras.tictactoe.model.game.JugadorHumano;
import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.game.Simbolo;
import com.estructuras.tictactoe.model.game.Tablero;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SeleccionController implements Initializable {

    @FXML private RadioButton rbX1, rbO1, rbHumano1, rbPC1;
    
    @FXML private RadioButton rbX2, rbO2, rbHumano2, rbPC2;

    private ToggleGroup tgSimbolo1, tgTipo1, tgSimbolo2, tgTipo2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tgSimbolo1 = new ToggleGroup();
        rbX1.setToggleGroup(tgSimbolo1);
        rbO1.setToggleGroup(tgSimbolo1);

        tgTipo1 = new ToggleGroup();
        rbHumano1.setToggleGroup(tgTipo1);
        rbPC1.setToggleGroup(tgTipo1);

        tgSimbolo2 = new ToggleGroup();
        rbX2.setToggleGroup(tgSimbolo2);
        rbO2.setToggleGroup(tgSimbolo2);

        tgTipo2 = new ToggleGroup();
        rbHumano2.setToggleGroup(tgTipo2);
        rbPC2.setToggleGroup(tgTipo2);

        // LOGICA DE EXCLUSIVIDAD DE SÍMBOLOS:
        tgSimbolo1.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == rbX1) rbO2.setSelected(true);
            else if (newValue == rbO1) rbX2.setSelected(true);
        });

        tgSimbolo2.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == rbX2) rbO1.setSelected(true);
            else if (newValue == rbO2) rbX1.setSelected(true);
        });
    }    
    
    @FXML
    private void iniciarPartida(ActionEvent event) {
        System.out.println("Configurando entidades y preparando el tablero...");
        

        Simbolo sim1 = rbX1.isSelected() ? Simbolo.X : Simbolo.O;
        Simbolo sim2 = rbX2.isSelected() ? Simbolo.X : Simbolo.O;
        
        Jugador jugador1 = rbHumano1.isSelected() ? new JugadorHumano(sim1) : new JugadorComputador(sim1);
        Jugador jugador2 = rbHumano2.isSelected() ? new JugadorHumano(sim2) : new JugadorComputador(sim2);
        
        Jugador[] arregloJugadores = new Jugador[]{jugador1, jugador2};

        Partida nuevaPartida = new Partida(new Tablero(), arregloJugadores);
        
        cambiarVistaJuego(event, nuevaPartida);
    }

    @FXML
    private void volverMenu(ActionEvent event) {
        Navegacion.avanzar(event,this, "MenuView.fxml");
    }
    
    // --- Métodos Auxiliares de Navegación ---
    
    private void cambiarVistaJuego(ActionEvent event, Partida partida) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PartidaView.fxml"));
            Parent root = loader.load();
            
            // Aquí es donde inyectas la lógica de la partida a tu próximo controlador
            // PartidaViewController controller = loader.getController();
            // controller.setPartida(partida);
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error al iniciar el juego: " + e.getMessage());
        }
    }
}