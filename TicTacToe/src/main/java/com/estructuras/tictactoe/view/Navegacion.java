/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estructuras.tictactoe.view;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author david
 */
public class Navegacion {
    
    /**
     * Método para avanzar entre vistas sin salir de la ventana o abrir una ventana nueva.
     * @param evento Evento que hará que se cambie la vista.
     * @param fxml Clase o controlador de la vista donde sucedió el evento.
     * @param ruta Ruta de la nueva vista a la que avanzará.
     * @return Retorna el Stage, o null si sucedió algún error.
     */
    public static Stage avanzar( Event evento, Object fxml , String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(fxml.getClass().getResource(ruta));
            Parent raiz = loader.load();
            
            Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow() ;
            
            stage.setScene(new Scene(raiz));
            return stage;
        } catch ( IOException e) {
            System.err.println("Error al regresar al menú: " + e.getMessage());
            return null;
        }
        
    }
    
}
