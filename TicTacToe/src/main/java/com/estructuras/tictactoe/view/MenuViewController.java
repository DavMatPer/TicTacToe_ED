/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.estructuras.tictactoe.view;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author david
 */
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
        // Aquí llamarás a la lógica de tu PartidaController o cambiarás de pantalla
    }

    @FXML
    private void cargarPartidaGuardada(ActionEvent event) {
        System.out.println("Clic detectado: Abriendo menú de guardados...");
        // Aquí puedes invocar a PartidaSerializer.obtenerPartidasGuardadas()
    }
    
}
