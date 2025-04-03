package cook.entities;

public class Meal {
    public  String name;
    public  boolean isVegetarian;
    public  boolean isGlutenFree;

    public Meal(String name, boolean isVegetarian, boolean isGlutenFree) {
        this.name = name;
        this.isVegetarian = isVegetarian;
        this.isGlutenFree = isGlutenFree;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }
}