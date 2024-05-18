package org.example;
import entities.Menu;
import entities.Supplement;
import services.MenuService;
import services.SupplementService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        SupplementService supplementService = new SupplementService();

        Menu menu = new Menu();
        Supplement supplement = new Supplement();

        // Add a menu
        menu.setNom("panuzzo");
        menu.setPrix(4);
        menu.setDescription("delicieux");
        menu.setCalories(1000);
        menu.setImage("image.jpg");
        menuService.add(menu);

        // Update a menu
        menu.setId(13); // Assuming the ID of the menu to update is 13
        menu.setNom("kaskrout");
        menu.setPrix(5000);
        menu.setDescription("");
        menu.setCalories(50);
        menu.setImage("");
        menuService.update(menu);

        // Delete a menu
        menu.setId(50); // Assuming the ID of the menu to delete is 28
        menuService.delete(menu);

        // Read all menus
        List<Menu> menuList = menuService.readAll();
        System.out.println("Liste des menus :");
        for (Menu m : menuList) {
            System.out.println(m);
        }

        // Read a menu by ID
        int id = 40; // Change this to the ID you want to read
        Menu readMenu = menuService.readById(id);
        if (readMenu != null) {
            System.out.println("Menu trouvé :");
            System.out.println(readMenu);
        } else {
            System.out.println("Menu non trouvé pour l'ID : " + id);
        }

        // Add a supplement
        supplement.setNom("coca");
        supplement.setPrix(3.5);
        supplement.setImage("supp.jpg");

        supplementService.add(supplement);

        // Update a supplement

        supplement.setId(1); // Assuming the ID of the supplement to update is 1
        supplement.setNom("fanta");
        supplement.setPrix(15);
        supplement.setImage("coca.jpg");
        supplementService.update(supplement);

        // Delete a supplement

        supplement.setId(11); // Assuming the ID of the supplement to delete is 11
        supplementService.delete(supplement);

        // Read all supplements
        List<Supplement> supplementList = supplementService.readAll();
        System.out.println("Liste des supplements :");
        for (Supplement s : supplementList) {
            System.out.println(s);
        }

        // Read a supplement by ID
        int Id = 40; // Change this to the ID you want to read
        Supplement readSupplement = supplementService.readById(Id);
        if (readSupplement != null) {
            System.out.println("Supplement trouvé :");
            System.out.println(readSupplement);
        } else {
            System.out.println("Supplement non trouvé pour l'ID : " + Id);
        }
    }
}
