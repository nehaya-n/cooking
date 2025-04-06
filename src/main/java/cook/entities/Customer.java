package cook.entities;

public class Customer {
    public static String name;
    public boolean isVegetarian;
    public boolean isGlutenFree;

    public Customer(String name, boolean isVegetarian, boolean isGlutenFree) {
        Customer.name = name;
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