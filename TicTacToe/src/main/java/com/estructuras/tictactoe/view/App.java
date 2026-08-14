package com.estructuras.tictactoe.view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage escenarioPrincipal) {
        // Leemos el archivo FXML
        // Nota: Asegúrate de que el FXML esté en la misma ruta de paquetes o en la carpeta 'resources'
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("MenuView.fxml"));
        
        // Esto lee el XML, crea los botones en memoria e instancia tu MenuController automáticamente
        Parent raiz;
        try {
            raiz = cargador.load();
        } catch (IOException e) {
            raiz  = null;
        }
        // Ponemos la vista en la ventana y la mostramos
        Scene escena = new Scene(raiz, 600, 400);
        escenarioPrincipal.setTitle("Tic Tac Toe");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }
}
