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
     * Devuelve la ruta del archivo fxml. ütil si las controladores o la clase desde la que se accede no esta en el mismo
     * paquete.
     * @param archivo nombre del archivo con extensión
     * @return ruta relativa desde la raíz.
     */
    public static String rutaFXML( String archivo) {
        return "/com/estructuras/tictactoe/view/resources/" + archivo;
    }
    
    /**
     * Método para avanzar entre vistas sin salir de la ventana o abrir una ventana nueva.
     * @param evento Evento que hará que se cambie la vista.
     * @param fxml Clase o controlador de la vista donde sucedió el evento.
     * @param ruta Ruta de la nueva vista a la que avanzará.
     * @return Retorna el Stage, o null si sucedió algún error.
     */
    public static void avanzar( Event evento, Object fxml , String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(fxml.getClass().getResource(rutaFXML(ruta)));
            Parent raiz = loader.load();
            
            Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow() ;
            
            stage.getScene().setRoot(raiz);
            stage.show();
        } catch ( IOException e) {
            System.err.println("Error al regresar al menú: " + e.getMessage());
        }
    }
    
    /**
     * Método AVANZADO para cambiar de pantalla y obtener el controlador.
     * Útil para inyectar datos (como el objeto Partida) a la siguiente vista.
     * @param <T> Clase controladora de vista de un archivo fxml
     * @param evento Evento que activo al controlador.
     * @param fxml Clase, ya compilada, del controlador del fxml.
     * @param ruta Ruta de la fista a la que se va a avanzar.
     * @return El controlador de la vista origen.
     */
    public static <T> T avanzarConControlador(Event evento, Object fxml, String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(fxml.getClass().getResource(rutaFXML(ruta)));
            Parent raiz = loader.load();
            
            Stage stage = (Stage) ((Node) evento.getSource()).getScene().getWindow();
            stage.getScene().setRoot(raiz);
            stage.show();
            
            return loader.getController();
            
        } catch (IOException e) {
            System.err.println("Error de navegación con controlador hacia " + ruta + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
