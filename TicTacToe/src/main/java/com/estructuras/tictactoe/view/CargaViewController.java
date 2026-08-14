/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.estructuras.tictactoe.view;

import com.estructuras.tictactoe.model.
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author david
 */
public class CargaViewController  {

    @FXML
    private Label lblSinPartidas;
    @FXML
    private ScrollPane scrollPanePartidas;
    @FXML
    private VBox vboxListaPartidas;
    @FXML
    private Button btnVolber;
    
    private String ruta_archivo;
    
    
    
    public void volverAlMenu(Event evento) {
        System.out.println("Volver al Menú.");
    }
    
    private void cargarListaDePartidas() {
        try {
            List<String> partidas = PartidaSerializer.
        }
    }
    
}
