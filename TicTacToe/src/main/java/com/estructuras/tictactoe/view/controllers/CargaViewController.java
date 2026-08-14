/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.estructuras.tictactoe.view.controllers;

import com.estructuras.tictactoe.model.game.Partida;
import com.estructuras.tictactoe.model.persistencia.PartidaSerializer;
import com.estructuras.tictactoe.model.persistencia.RutaGuardado;
import com.estructuras.tictactoe.view.Navegacion;
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
    
    private String RUTA_ARCHIVO = RutaGuardado.getRuta();
    
    @FXML
    public void initialize() {
        cargarListaDePartidas();
    }
    
    public void volverAlMenu(Event evento) {
        System.out.println("Volver al Menú.");
        Navegacion.avanzar(evento, this, "MenuView.fxml" );
    }
    
    private void cargarListaDePartidas() {
        try {
            List<String> partidas = PartidaSerializer.obtenerPartidasGuardadas(RUTA_ARCHIVO);
            
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
        String estiloNormal = "-fx-background-color: white; -fx-text-fill: #011627; -fx-padding: 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-font-size: 18px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10, 0, 0, 3);";
        String estiloHover = "-fx-background-color: #2EC4B6; -fx-text-fill: white; -fx-padding: 20; -fx-background-radius: 15; -fx-cursor: hand; -fx-font-size: 18px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(46,196,182,0.4), 15, 0, 0, 5);";
        
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
                Partida partidaRestaurada = PartidaSerializer.cargarPartida(nombrePartida, RUTA_ARCHIVO);
                
                System.out.println("Partida cargada exitosamente. ¡A jugar!");
                
                PartidaViewController controladorDestino = Navegacion.avanzarConControlador(
                    e, 
                    this, 
                    "PartidaView.fxml"
                );
                
                if ( controladorDestino != null) {
                    controladorDestino.setPartida(partidaRestaurada);
                }
                
            } catch (Exception ex) {
                System.err.println("No se pudo cargar la partida: " + ex.getMessage());
            }
        });
    
        return item;
    }
    
    
}
