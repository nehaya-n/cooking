package cook.entities;

public class Ingredient {
    private String name;
    private boolean isAvailable;

    public Ingredient(String name, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
    }

    // Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
