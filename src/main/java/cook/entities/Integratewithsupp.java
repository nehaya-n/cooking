package cook.entities;

import java.util.Map;

public class Integratewithsupp {
    private String name;
    private int stock;
    private int lowStockThreshold;
    private boolean alertAcknowledged;
    private final Map<String, Double> prices;

    // Constructor
    public Integratewithsupp(String name, int stock, int lowStockThreshold, Map<String, Double> prices) {
        this.name = name;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        this.prices = prices;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock; // Adding the method to set stock value
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold; // Adding the method to set low stock threshold
    }

    public boolean isAlertAcknowledged() {
        return alertAcknowledged;
    }

    public void setAlertAcknowledged(boolean alertAcknowledged) {
        this.alertAcknowledged = alertAcknowledged; // Adding the method to set alert acknowledgment
    }

    public Map<String, Double> getPrices() {
        return prices;
    }

    public String getBestPriceSupplier() {
        if (prices == null || prices.isEmpty()) {
            return "No suppliers available";
        }
        return prices.entrySet().stream()
                .min(Map.Entry.comparingByValue())  // Finds the supplier with the minimum price
                .map(Map.Entry::getKey)
                .orElse("No supplier found");
    }
}
