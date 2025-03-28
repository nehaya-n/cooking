package org.example;
import cook.entities.Ingredient;
import data.IngredientData;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        IngredientData.initializeIngredients();

        // Get a specific ingredient (for example: Tomatoes)
        Ingredient tomato = IngredientData.getIngredientByName("Tomatoes");
        System.out.println("Ingredient: " + tomato.getName());
        System.out.println("Current Stock: " + tomato.getStock() + " kg");
        System.out.println("Low Stock Threshold: " + tomato.getLowStockThreshold() + " kg");

        // Check if the ingredient is below the low-stock threshold
        if (tomato.getStock() < tomato.getLowStockThreshold()) {
            System.out.println("Low Stock Alert: " + tomato.getName() + " stock is below threshold!");
        }

        // Update the stock of an ingredient (for example: Tomatoes)
        tomato.setStock(2);  // Set stock to 2 kg for testing low-stock alert
        System.out.println("Updated Stock: " + tomato.getStock() + " kg");

        // After updating, check if the ingredient is now below the threshold
        if (tomato.getStock() < tomato.getLowStockThreshold()) {
            System.out.println("Low Stock Alert: " + tomato.getName() + " stock is now below threshold!");
        }

        // You can similarly work with other ingredients and trigger alerts
    }
}

