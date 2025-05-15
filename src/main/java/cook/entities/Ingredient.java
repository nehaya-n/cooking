package cook.entities;

public class Ingredient {
    public  String name;
    private int stock;
    private int lowStockThreshold;
    private boolean alertAcknowledged;
    private String unit;

    public Ingredient(String name, int stock, int lowStockThreshold) {
        this.name = name;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        this.alertAcknowledged = false;
        this.unit = "";
    }


    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public void setLowStockThreshold(int threshold) {
        this.lowStockThreshold = threshold;
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public boolean isAlertAcknowledged() {
        return alertAcknowledged;
    }

    // Setters
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setName(String name) {
        this.name = name;
    }



    public void setAlertAcknowledged(boolean alertAcknowledged) {
        this.alertAcknowledged = alertAcknowledged;
    }


    public void acknowledgeAlert() {
        this.alertAcknowledged = true;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
    private long alertTime;


    public void setAlertTime(long time) { this.alertTime = time; }
    public long getAlertTime() { return this.alertTime; }
}