package controllers;

import entities.Menu;
import entities.Supplement;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.MenuService;
import services.SupplementService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterMenuController implements Initializable {

    @FXML
    private TableView<Menu> tv_menu;

    @FXML
    private TableColumn<Menu, String> col_nommenu;

    @FXML
    private TableColumn<Menu, String> col_descriptionmenu;

    @FXML
    private TableColumn<Menu, Integer> col_caloriesmenu;

    @FXML
    private TableColumn<Menu, Double> col_prixmenu;

    @FXML
    private TableColumn<Menu, String> col_imagemenu;

    @FXML
    private TextField tf_caloriesmenu;

    @FXML
    private TextArea tf_descmenu;

    @FXML
    private TextField tf_nommenu;

    @FXML
    private TextField tf_prixmenu;

    @FXML
    private ImageView iv_menu;

    private String selectedImagePath;

    @FXML
    private TextField searchTextField;

    private GridPane grid;

    private ObservableList<Menu> menuData = FXCollections.observableArrayList();

    private MenuService menuService = new MenuService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize table columns
        col_nommenu.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_descriptionmenu.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_caloriesmenu.setCellValueFactory(new PropertyValueFactory<>("calories"));
        col_prixmenu.setCellValueFactory(new PropertyValueFactory<>("prix"));
        col_imagemenu.setCellValueFactory(new PropertyValueFactory<>("image"));
        populateMenuTableView();
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
        });


    }

    private void populateMenuTableView() {
        List<Menu> menuList = menuService.readAll();
        ObservableList<Menu> observableMenuList = FXCollections.observableArrayList(menuList);
        tv_menu.setItems(observableMenuList);
    }

    @FXML
    public void btn_ajoutermenu(javafx.event.ActionEvent actionEvent) {
        try {
            String nom = tf_nommenu.getText();
            String description = tf_descmenu.getText();
            int calories = 0;
            double prix = 0.0;

            // Validate if all fields are filled
            if (nom.isEmpty() || description.isEmpty() || tf_caloriesmenu.getText().isEmpty() || tf_prixmenu.getText().isEmpty()) {
                showAlert("Tous les champs doivent être remplis.", "Le menu a été modifié avec succès.");
                return;
            }

            // Validate calorie field
            if (!tf_caloriesmenu.getText().matches("\\d+")) {
                showAlert("Le champ des calories doit contenir des chiffres uniquement.", "Le menu a été modifié avec succès.");
                return;
            } else {
                calories = Integer.parseInt(tf_caloriesmenu.getText());
            }

            // Validate price field
            if (!tf_prixmenu.getText().matches("\\d+(\\.\\d+)?")) {
                showAlert("Le champ du prix doit contenir des chiffres (et un point décimal optionnel pour les décimales).", "Le menu a été modifié avec succès.");
                return;
            } else {
                prix = Double.parseDouble(tf_prixmenu.getText());
            }

            // Vérifiez si une image a été importée
            if (iv_menu.getImage() == null) {
                showAlert("Veuillez importer une image pour le menu.", "Erreur lors de l'ajout du menu.");
                return;
            }

            // Create a new Menu object and set its properties
            Menu menu = new Menu();
            menu.setNom(nom);
            menu.setDescription(description);
            menu.setCalories(calories);
            menu.setPrix(prix);
            menu.setImage(selectedImagePath); // Set the image path


            // Add the new menu to the database or service
            menuService.add(menu);

            // Show a success message
            showAlert("Menu ajouté", "Le menu a été modifié avec succès.");

            // Add the new menu to the TableView
            tv_menu.getItems().add(menu);
            populateMenuTableView();
            clearTextFields();
            clearImageView();




        } catch (NumberFormatException e) {
            showAlert("Entrée invalide pour les nombres.", "Le menu a été modifié avec succès.");
        }
    }





    @FXML
    public void btn_importerimagemenu(javafx.event.ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            showAlert("Image sélectionnée : " + selectedFile.getName(), "Le menu a été modifié avec succès.");
            selectedImagePath = selectedFile.getAbsolutePath();
            afficherImage(selectedFile.getAbsolutePath());
        } else {
            showAlert("Aucune image sélectionnée.", "Le menu a été modifié avec succès.");
        }
    }

    void afficherImage(String chemin) {
        // Create an Image object from the specified path
        javafx.scene.image.Image image = new javafx.scene.image.Image(new File(chemin).toURI().toString());

        // Set the image in the ImageView object
        iv_menu.setImage(image);
    }

    private void clearImageView() {
        iv_menu.setImage(null);
    }
    private void showAlert(String message, String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void btn_supprimermenu(ActionEvent actionEvent) {
        Menu selectedItem = tv_menu.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Delete the selected menu item
            menuService.delete(selectedItem);
            // Remove the item from the TableView
            tv_menu.getItems().remove(selectedItem);
            showAlert("Menu supprimé avec succès", "Le menu a été modifié avec succès.");
            populateMenuTableView();

        } else {
            showAlert("Aucun menu sélectionné.", "Le menu a été modifié avec succès.");
        }
    }


    @FXML 
    private void btn_modifiermenu() {
        Menu selectedItem = tv_menu.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Populate the text fields with the selected menu item data
            tf_nommenu.setText(selectedItem.getNom());
            tf_prixmenu.setText(String.valueOf(selectedItem.getPrix()));
            tf_caloriesmenu.setText(String.valueOf(selectedItem.getCalories()));
            tf_descmenu.setText(selectedItem.getDescription());
            selectedImagePath = selectedItem.getImage(); // Store the current image path for modification
            afficherImage(selectedImagePath);
        } else {
            showAlert("Aucun menu sélectionné.", "Veuillez sélectionner un menu à modifier.");
        }
    }

    @FXML
    private void btn_save_modifications() {
        Menu selectedItem = tv_menu.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                // Update the selected menu item with the modified data
                selectedItem.setNom(tf_nommenu.getText());
                selectedItem.setPrix(Double.parseDouble(tf_prixmenu.getText()));
                selectedItem.setCalories(Integer.parseInt(tf_caloriesmenu.getText()));
                selectedItem.setDescription(tf_descmenu.getText());
                selectedItem.setImage(selectedImagePath); // Update the image path


                // Update the menu item in the database or service
                menuService.update(selectedItem);

                // Show a success message
                showAlert("Menu modifié avec succès", "Le menu a été modifié avec succès.");

                // Refresh the TableView
                populateMenuTableView();
                clearTextFields();
                clearImageView();

            } catch (NumberFormatException e) {
                showAlert("Erreur lors de la modification du menu.", "Veuillez vérifier les valeurs entrées.");
            }
        } else {
            showAlert("Aucun menu sélectionné.", "Veuillez sélectionner un menu à modifier.");
        }
    }


    @FXML
    private void versSupplement(ActionEvent event) {
        try {
            // Load the Add_idee.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ajoutSupplement.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller
            ajouterSupplementController ajouterSupplementController = fxmlLoader.getController();

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

    private void clearTextFields() {
       tf_descmenu.clear(); // Clear choice box selection
       tf_prixmenu.clear();
       tf_caloriesmenu.clear();
        tf_nommenu.clear(); // Clear choice box selection

    }

    @FXML
    void handleExitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment quitter ?");

        ButtonType yesButton = new ButtonType("Oui");
        ButtonType noButton = new ButtonType("Non");

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                // Exit the application
                Platform.exit();
            }
        });
    }

    public void recherche(ActionEvent actionEvent) {
        try {
            ObservableList<Menu> observableList = FXCollections.observableList(menuService.readAll());
            FilteredList<Menu> filteredList = new FilteredList<>(observableList, p -> true);

            // Bind the search field text to the filter predicate
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(supplement -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all items when the filter is empty
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Check if any property of the Supplement contains the filter text
                    return supplement.getNom().toLowerCase().contains(lowerCaseFilter);

                });
            });

            SortedList<Menu> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tv_menu.comparatorProperty());

            tv_menu.setItems(sortedList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void searchMenu(){
        updateMenus(searchTextField.getText());
    }

    private void updateMenus(String filter) {
        MenuService menuService = new MenuService();
        List<Menu> filteredMenus =menuService.searchByNom(filter);

        // Clear existing items in the TableView
        tv_menu.getItems().clear();

        // Add filtered supplements to the TableView
        tv_menu.getItems().addAll(filteredMenus);
    }



}
