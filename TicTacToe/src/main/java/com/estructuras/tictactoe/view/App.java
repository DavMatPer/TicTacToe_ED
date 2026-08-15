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
        // Usamos tu clase de Navegacion para obtener la ruta absoluta segura
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(Navegacion.rutaFXML("MenuView.fxml")));
        Parent raiz;
        try {
            raiz = cargador.load();
        } catch (IOException e) {
            System.err.println("Error al cargar la vista inicial: " + e.getMessage());
            e.printStackTrace();
            return;
        }


        Scene escena = new Scene(raiz, 700, 500);
        escenarioPrincipal.setTitle("Tres en Raya");

        // Bloqueamos un tamaño mínimo para que el diseño no se aplaste
        escenarioPrincipal.setMinWidth(650);
        escenarioPrincipal.setMinHeight(450);

        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }
}
