package controllers;

import entities.Menu;
import entities.Supplement;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.MenuService;
import services.SupplementService;

import java.io.File;

public class CardMenuController {
    public ImageView imageView;
    public Label lnom;
    public Label lprix;
    public Label lcalories;
    public Label ldescription;
    private MenuService MenuService = new MenuService();


    public void initialize() {
        displayAvisData(106);
    }
    public void displayAvisData(int recetteId) {
        Menu recette = MenuService.readById(recetteId);
        if (recette != null) {
            lnom.setText("" + recette.getNom());
            lprix.setText("" + recette.getPrix());
            lcalories.setText("" + recette.getCalories());
            ldescription.setText("" + recette.getDescription());

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
