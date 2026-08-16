package com.estructuras.tictactoe.view.controllers;


import com.estructuras.tictactoe.view.Navegacion;
import com.estructuras.tictactoe.view.Navegacion;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuViewController   {

    // El nombre de la variable DEBE coincidir exactamente con el fx:id del XML
    @FXML
    private Button btnNuevaPartida;

    @FXML
    private Button btnCargarPartida;

    // El nombre del método DEBE coincidir exactamente con el onAction del XML
    @FXML
    private void iniciarNuevaPartida(ActionEvent event) {
        System.out.println("Clic detectado: Preparando nueva partida...");
        Navegacion.avanzar(event, this, "Seleccion.fxml");
    }

    @FXML
    private void cargarPartidaGuardada(ActionEvent event) {
        System.out.println("Clic detectado: Abriendo menú de guardados...");
        Navegacion.avanzar(event, this, "CargaView.fxml");
    }

}
