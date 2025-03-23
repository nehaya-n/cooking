package cook.entities;



import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class InventorySystem {

    private static final Logger logger = Logger.getLogger(InventorySystem.class.getName());

    private Map<String, Integer> stockLevels;
    private boolean alertReviewed;
    private String lowStockAlert;
    private String criticalAlert;
    private String restockRecommendation;
    private String escalatedAlert;

    public InventorySystem() {
        stockLevels = new HashMap<>();
        alertReviewed = false;
        lowStockAlert = "";
        criticalAlert = "";
        restockRecommendation = "";
        escalatedAlert = "";
    }

    // Updates the stock level
    public void updateStock(String ingredient, int stockLevel) {
        stockLevels.put(ingredient, stockLevel);
    }

    // Checks if the stock for a given ingredient is low
    public String checkLowStockAlerts() {
        for (Map.Entry<String, Integer> entry : stockLevels.entrySet()) {
            String ingredient = entry.getKey();
            int stock = entry.getValue();
            if (stock < 10) {  // Threshold for low stock is 10 units
                lowStockAlert = "Low stock alert for " + ingredient + ": " + stock + " kg remaining.";
                logger.info(lowStockAlert);
                return lowStockAlert;
            }
        }
        return "No low stock alerts.";
    }

    // Checks if the stock for any ingredient is critically low (e.g., 0 stock)
    public String checkCriticalStockAlerts() {
        for (Map.Entry<String, Integer> entry : stockLevels.entrySet()) {
            String ingredient = entry.getKey();
            int stock = entry.getValue();
            if (stock == 0) {  // Threshold for critical stock is 0 units
                criticalAlert = "Critical stock alert for " + ingredient + ": 0 liters remaining.";
                logger.info(criticalAlert);
                return criticalAlert;
            }
        }
        return "No critical stock alerts.";
    }

    // Checks if restock is needed based on the stock levels of all ingredients
    public String checkRestockRecommendation() {
        for (Map.Entry<String, Integer> entry : stockLevels.entrySet()) {
            String ingredient = entry.getKey();
            int stock = entry.getValue();
            if (stock < 10) {
                restockRecommendation = "Restock recommendation for " + ingredient + " as stock is low.";
                logger.info(restockRecommendation);
                return restockRecommendation;
            }
        }
        return "No restock recommendations.";
    }

    // Sends low stock alert
    public void sendLowStockAlert(String ingredient) {
        lowStockAlert = "Low stock alert for " + ingredient + ".";
        logger.info("Low-stock alert sent for: " + ingredient);
    }

    // Marks an alert as reviewed
    public void acknowledgeAlert() {
        alertReviewed = true;
        logger.info("Alert acknowledged.");
    }

    // Checks if an alert has been reviewed
    public boolean isAlertReviewed() {
        return alertReviewed;
    }


    public void logUnacknowledgedAlert(String ingredient, int hoursAgo) {
        escalatedAlert = "Alert for " + ingredient + " escalated after " + hoursAgo + " hours of no action.";
        logger.info(escalatedAlert);
    }

    // Checks if any escalated alerts are present
    public String checkEscalatedAlerts() {
        return escalatedAlert;
    }
}

