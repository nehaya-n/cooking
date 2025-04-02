package cook.entities;

import java.util.Map;

public class Integratewithsupp {
    private String name;
    private int stock;
    private int lowStockThreshold;
    private boolean alertAcknowledged;
    private Map<String, Double> prices;  // This will store the prices for different suppliers.

    // Constructor
    public Integratewithsupp(String name, int stock, int lowStockThreshold, Map<String, Double> prices) {
        this.name = name;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        this.alertAcknowledged = false;
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
        this.stock = stock;
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public boolean getAlertAcknowledged() {
        return alertAcknowledged;
    }

    public void setAlertAcknowledged(boolean alertAcknowledged) {
        this.alertAcknowledged = alertAcknowledged;
    }

    // Returns the supplier with the best price
    public String getBestPriceSupplier() {
        if (prices == null || prices.isEmpty()) {
            return "No suppliers available";
        }
        return prices.entrySet().stream()
                .min(Map.Entry.comparingByValue())  // Finds the supplier with the minimum price
                .map(Map.Entry::getKey)
                .orElse("No supplier found");
    }

    public Map<String, Double> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Double> prices) {
        this.prices = prices;
    }
}
