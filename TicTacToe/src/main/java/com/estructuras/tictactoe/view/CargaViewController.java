/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.estructuras.tictactoe.view;

import com.estructuras.tictactoe.model.persistencia.PartidaSerializer;
import java.io.IOException;
import java.util.List;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    
    private String ruta_archivo = "partida.dat";
    
    @FXML
    public void initialize() {
        cargarListaDePartidas();
    }
    
    public void volverAlMenu(Event evento) {
        System.out.println("Volver al Menú.");
        Stage stage = Navegacion.avanzar(evento, this, "MenuView.fxml" );
        if (stage == null) {
            return;
        }
        stage.show();
    }
    
    private void cargarListaDePartidas() {
        try {
            List<String> partidas = PartidaSerializer.obtenerPartidasGuardadas(ruta_archivo);
            
            lblSinPartidas.setVisible(partidas.isEmpty());
            scrollPanePartidas.setVisible(!partidas.isEmpty());
            
            if (partidas.isEmpty()) return;
            vboxListaPartidas.getChildren().clear();
            
            for ( String nombre : partidas) {
                Label itemPartida = crearElementoVisual(nombre);
                vboxListaPartidas.getChildren().add(itemPartida);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar las partidas: " + e.getMessage());
            lblSinPartidas.setText("Error al leer el archivo de guardado.");
            lblSinPartidas.setVisible(true);
            scrollPanePartidas.setVisible(false);
        }
    }
    
    private Label crearElementoVisual(String nombrePartida) {
        Label item = new Label(nombrePartida);
        
        // Estilización
        item.setMaxWidth(Double.MAX_VALUE);
        String estiloNormal = "-fx-background-color: #e0e0e0; -fx-padding: 15; -fx-background-radius: 5; -fx-cursor: hand; -fx-font-size: 16px;";
        String estiloHover = "-fx-background-color: #b3d9ff; -fx-padding: 15; -fx-background-radius: 5; -fx-cursor: hand; -fx-font-size: 16px;";
        
        item.setStyle(estiloNormal);
        
        item.setOnMouseEntered(e -> {
            item.setStyle(estiloHover);
        });
        
        item.setOnMouseExited(e -> {
            item.setStyle(estiloNormal);
        });
        
        item.setOnMouseClicked(e -> {
            System.out.println("Intentando restaurar partida: " + nombrePartida);
            try {
                // Restauramos y consumimos la partida
                var partidaRestaurada = PartidaSerializer.cargarPartida(nombrePartida, ruta_archivo);
                
                System.out.println("Partida cargada exitosamente. ¡A jugar!");
                // Aquí deberías cambiar de escena al Tablero y pasarle la partidaRestaurada
                
            } catch (Exception ex) {
                System.err.println("No se pudo cargar la partida: " + ex.getMessage());
            }
        });
    
        return item;
    }
    
    
}
