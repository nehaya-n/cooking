package cook.entities;

public class Ingredient {
    public  String name;
    private int stock;
    private final int lowStockThreshold;
    private boolean alertAcknowledged;

    public Ingredient(String name, int stock, int lowStockThreshold) {
        this.name = name;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
        this.alertAcknowledged = false;
    }


    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
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

    /*
       public void setLowStockThreshold(int lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
       }
      */

    public void acknowledgeAlert() {
        this.alertAcknowledged = true;
    }
}
