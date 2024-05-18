package controllers;

import entities.Supplement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.SupplementService;

import java.io.File;

public class CardController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label lprix;

    @FXML
    private Label lnom;

    @FXML
    private Button addButton;

    private Supplement supplement;

    private OnSupplementSelectedListener onChangeListener;

    private SupplementService supplementService = new SupplementService();

    public void initData(Supplement supplement) {
        this.supplement = supplement;
        imageView.setImage(new Image(supplement.getImage()));
        lnom.setText(supplement.getNom());
        lprix.setText(String.valueOf(supplement.getPrix()));
    }

    @FXML
    private void handleAjouter(ActionEvent event) {
        System.out.println("Supplément ajouté : " + supplement.getNom());
    }

    @FXML
    private void selectedSupplement(MouseEvent event) {
        if (onChangeListener != null) {
            onChangeListener.sendIdSupplement(supplement.getId());
        }
    }

    public void setOnSupplementSelectedListener(OnSupplementSelectedListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public interface OnSupplementSelectedListener {
        void sendIdSupplement(int id);
    }

    // Method to fill data in the card
    public void remplirData(Supplement supplement) {
        this.supplement = supplement;
        lnom.setText(supplement.getNom());
        lprix.setText(String.valueOf(supplement.getPrix()));
        // You may choose to set the image as well
        // imageView.setImage(new Image(supplement.getImage()));
    }
/*
    private SupplementService supplementService = new SupplementService();

    public void initialize() {
        displayRecetteData(26);
    }


 */

    public void initialize() {
        displayAvisData(41);
    }
    public void displayAvisData(int recetteId) {
        Supplement recette = supplementService.readById(recetteId);
        if (recette != null) {
            lnom.setText("" + recette.getNom());
            lprix.setText("" + recette.getPrix());
            if (recette.getImage() != null) {
                // Modify the path to use a File object and convert it to URL
                String imagePath = recette.getImage();
                File file = new File(imagePath);

                try {
                    // Convert the File to URL
                    String imageUrl = file.toURI().toURL().toString();
                    // Now use this URL to create the Image
                    Image image = new Image(imageUrl);
                    imageView.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();  // Handle the exception according to your needs
                }
            }

        }
    }}
