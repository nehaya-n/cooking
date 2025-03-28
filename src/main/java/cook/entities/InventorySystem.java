package cook.entities;
import data.IngredientData;
import java.util.HashMap;
import java.util.Map;

public class InventorySystem {
    private final Map<String, Ingredient> inventory = new HashMap<>();
    private String lastNotification;
    private boolean alertReviewed = false;

    public InventorySystem() {
        
        for (Ingredient ingredient : IngredientData.getAllIngredients()) {
            inventory.put(ingredient.getName(), ingredient);
        }
    }

    public void updateStock(String ingredientName, int quantity) {
        if (inventory.containsKey(ingredientName)) {
            inventory.get(ingredientName).setStock(quantity);
        }
    }

    public String checkLowStockAlerts() {
        for (Ingredient ingredient : inventory.values()) {
            if (ingredient.getStock() < ingredient.getLowStockThreshold()) {
                lastNotification = "Low Stock Alert: " + ingredient.getName() +
                        " stock is below " + ingredient.getLowStockThreshold() + ".\n" +
                        "Consider reordering to prevent shortages.";
                return lastNotification;
            }
        }
        return "";
    }

    public String checkCriticalStockAlerts() {
        for (Ingredient ingredient : inventory.values()) {
            if (ingredient.getStock() == 0) {
                lastNotification = "Out of Stock Alert: " + ingredient.getName() +
                        " is completely out of stock!\n" +
                        "Immediate restocking is required to continue kitchen operations.";
                return lastNotification;
            }
        }
        return "";
    }

    public String checkRestockRecommendation() {
        StringBuilder recommendation = new StringBuilder("Low Stock Alert:\n");
        boolean hasLowStock = false;

        for (Ingredient ingredient : inventory.values()) {
            if (ingredient.getStock() < ingredient.getLowStockThreshold()) {
                recommendation.append("- ").append(ingredient.getName()).append(": ")
                        .append(ingredient.getStock()).append(" remaining (Order Recommended)\n");
                hasLowStock = true;
            }
        }

        if (hasLowStock) {
            recommendation.append("Would you like to place an order now? [Yes] [No]");
            lastNotification = recommendation.toString();
            return lastNotification;
        }
        return "";
    }

    public void sendLowStockAlert(String ingredientName) {
        if (inventory.containsKey(ingredientName)) {
            lastNotification = "Low-stock alert sent for: " + ingredientName;
        }
    }

    public void acknowledgeAlert() {
        alertReviewed = true;
    }

    public boolean isAlertReviewed() {
        return alertReviewed;
    }

    public void logUnacknowledgedAlert(String ingredientName, int hoursAgo) {
        lastNotification = "Low-stock alert for " + ingredientName + " was sent " + hoursAgo + " hours ago.";
    }

    public String checkEscalatedAlerts() {
        return "URGENCY: An ingredient is still low on stock. No action has been taken.";
    }
}
