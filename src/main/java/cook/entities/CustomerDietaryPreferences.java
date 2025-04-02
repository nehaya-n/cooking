package cook.entities;

import java.util.Map;

public class CustomerDietaryPreferences {
    private String customerName;
    private Map<String, Boolean> dietaryPreferences; // e.g., Vegetarian, Gluten-Free, etc.
    private Map<String, String> allergies; // e.g., Peanut Allergy with severity (Severe, Moderate)

    // Constructor
    public CustomerDietaryPreferences(String customerName, Map<String, Boolean> dietaryPreferences, Map<String, String> allergies) {
        this.customerName = customerName;
        this.dietaryPreferences = dietaryPreferences;
        this.allergies = allergies;
    }

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<String, Boolean> getDietaryPreferences() {
        return dietaryPreferences;
    }

    public void setDietaryPreferences(Map<String, Boolean> dietaryPreferences) {
        this.dietaryPreferences = dietaryPreferences;
    }

    public Map<String, String> getAllergies() {
        return allergies;
    }

    public void setAllergies(Map<String, String> allergies) {
        this.allergies = allergies;
    }
}
