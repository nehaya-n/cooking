package cook.entities;

public class Ingredient {
    private String name;
    private int stock;
    private int lowStockThreshold;
    private boolean alertAcknowledged;

    // Constructor (اختياري، لتعيين القيم الأولية عند الإنشاء)
    public Ingredient(String name, int stock, int lowStockThreshold) {
        this.name = name;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        this.alertAcknowledged = false;
    }

    // Getters and Setters فقط

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
}
