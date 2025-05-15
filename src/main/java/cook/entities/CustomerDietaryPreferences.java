package cook.entities;

import java.util.HashSet;
import java.util.Set;

public class CustomerDietaryPreferences {

    private String customerName;
    private final Set<String> dietaryRestrictions;

    // Constructor
    public CustomerDietaryPreferences(String customerName) {
        this.customerName = customerName;
        this.dietaryRestrictions = new HashSet<>();
    }

    // Add a dietary restriction to the customer
    public void addDietaryRestriction(String restriction) {
        dietaryRestrictions.add(restriction);
    }

    // Remove a dietary restriction from the customer
    public void removeDietaryRestriction(String restriction) {
        dietaryRestrictions.remove(restriction);
    }

    // Get all dietary restrictions for this customer
    public Set<String> getDietaryRestrictions() {
        return new HashSet<>(dietaryRestrictions);
    }

    // Check if the customer has a specific dietary restriction
    public boolean hasDietaryRestriction(String restriction) {
        return dietaryRestrictions.contains(restriction);
    }

    // Get customer name
    public String getCustomerName() {
        return customerName;
    }

    // Set customer name
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "CustomerDietaryPreferences{" +
                "customerName='" + customerName + '\'' +
                ", dietaryRestrictions=" + dietaryRestrictions +
                '}';
    }
}
