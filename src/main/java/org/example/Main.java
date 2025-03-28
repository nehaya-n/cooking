package org.example;
import cook.entities.InventorySystem;
import cook.entities.Ingredient;
import data.IngredientData;
import java.util.List;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InventorySystem inventorySystem = new InventorySystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("======== Kitchen Inventory System ========");

        // Display initial stock levels
        System.out.println("\nCurrent Ingredient Stock:");
        displayStockLevels(inventorySystem);

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Check for low-stock alerts");
            System.out.println("2. Update stock levels");
            System.out.println("3. View all ingredients");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nChecking for low-stock alerts...");
                    String notification = inventorySystem.checkLowStockAlerts();
                    if (!notification.isEmpty()) {
                        System.out.println(notification);
                    } else {
                        System.out.println("✅ No low-stock alerts at the moment.");
                    }
                    break;

                case 2:
                    System.out.print("\nEnter ingredient name: ");
                    String ingredientName = scanner.next();
                    System.out.print("Enter new stock level: ");
                    int newStock = scanner.nextInt();
                    inventorySystem.updateStock(ingredientName, newStock);
                    System.out.println("✅ Stock updated successfully!");
                    break;

                case 3:
                    System.out.println("\nAll Ingredients:");
                    displayStockLevels(inventorySystem);
                    break;

                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void displayStockLevels(InventorySystem inventorySystem) {
        List<Ingredient> ingredients = IngredientData.getAllIngredients();
        for (Ingredient ingredient : ingredients) {
            System.out.println(ingredient.getName() + " - " + ingredient.getStock() + " units (Threshold: " + ingredient.getLowStockThreshold() + ")");
        }
    }
        }

