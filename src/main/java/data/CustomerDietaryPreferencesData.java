package data;

import cook.entities.CustomerDietaryPreferences;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerDietaryPreferencesData {
    private static final List<CustomerDietaryPreferences> customerPreferencesList = new ArrayList<>();

    static {
        initializeCustomerPreferences();
    }

    // Initialize sample customer dietary preferences
    public static void initializeCustomerPreferences() {
        Map<String, Boolean> customer1DietaryPreferences = Map.of("Vegetarian", true, "Gluten-Free", true, "Dairy-Free", false);
        Map<String, String> customer1Allergies = Map.of("Peanuts", "Severe", "Shellfish", "Moderate");
        addCustomer("Customer A", customer1DietaryPreferences, customer1Allergies);
    }

    // Adds a new customer dietary preference to the list
    public static void addCustomer(String name, Map<String, Boolean> dietaryPreferences, Map<String, String> allergies) {
        CustomerDietaryPreferences newCustomer = new CustomerDietaryPreferences(name, dietaryPreferences, allergies);
        customerPreferencesList.add(newCustomer);
    }

    // Get all customer preferences
    public static List<CustomerDietaryPreferences> getCustomerPreferences() {
        return new ArrayList<>(customerPreferencesList);
    }

    // Get customer preferences by name
    public static CustomerDietaryPreferences getCustomerByName(String name) {
        for (CustomerDietaryPreferences customer : customerPreferencesList) {
            if (customer.getCustomerName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null; // Return null if customer is not found
    }

    // Update dietary preferences of a customer
    public static void updateDietaryPreferences(String name, Map<String, Boolean> newPreferences, Map<String, String> newAllergies) {
        CustomerDietaryPreferences customer = getCustomerByName(name);
        if (customer != null) {
            customer.setDietaryPreferences(newPreferences);
            customer.setAllergies(newAllergies);
        }
    }
}
