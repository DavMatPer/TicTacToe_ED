package com.estructuras.tictactoe.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage escenarioPrincipal) {
        // Interfaz temporal de prueba
        Label etiqueta = new Label("¡Tres en Raya listo!");
        StackPane raiz = new StackPane(etiqueta);
        Scene escena = new Scene(raiz, 400, 300);
        
        escenarioPrincipal.setTitle("TicTacToe ED");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }
}
