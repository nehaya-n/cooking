package cook.entities;

public class Ingredient {
    private String name;
    private int stock;
    private int lowStockThreshold;

    public Ingredient(String name, int stock, int lowStockThreshold) {
        this.name = name;
        this.stock = stock;
        this.lowStockThreshold = lowStockThreshold;
    }

    public String getName() {
        return name;
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
}
