// CardListController.java
package controllers;

import entities.Supplement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import services.SupplementService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CardListController{


    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;






    public void initialize() {
        // Retrieve all recipes from the data source
        SupplementService recetteService = new SupplementService();
        List<Supplement> allRecettes = recetteService.readAll();

        // Load and display each recipe in a CardRecette
        int columnIndex = 0;
        int rowIndex = 1;

        for (Supplement recette : allRecettes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/card.fxml"));
                Node cardRecetteNode = loader.load();

                CardController cardController = loader.getController();
                cardController.displayAvisData(recette.getId());
                columnIndex++;
                if (columnIndex == 4) { // Change this number according to your desired number of columns
                    columnIndex = 0;
                    ++rowIndex;
                }
                grid.add(cardRecetteNode, columnIndex, rowIndex);
                this.grid.setMinWidth(-1.0);
                this.grid.setPrefWidth(-1.0);
                this.grid.setMaxWidth(Double.NEGATIVE_INFINITY);
                this.grid.setMinHeight(-1.0);
                this.grid.setPrefHeight(-1.0);
                this.grid.setMaxHeight(Double.NEGATIVE_INFINITY);
                GridPane.setMargin(cardRecetteNode, new Insets(10.0));

            } catch (IOException e) {
                e.printStackTrace(); // Handle exception appropriately
            }

        }
    }

    @FXML
    private void versListeMenus(ActionEvent event) {
        try {
            // Load the Add_idee.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cardlistmenu.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller
            MenuCardList menuCardList = fxmlLoader.getController();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the button and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
