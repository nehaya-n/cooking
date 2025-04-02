package data;

import cook.entities.CustomerDietaryPreferences;
import java.util.HashMap;
import java.util.Map;

public class CustomerDietaryPreferencesData {
    private static Map<String, CustomerDietaryPreferences> customerPreferences = new HashMap<>();

    public static void addCustomerPreferences(String customerId, CustomerDietaryPreferences preferences) {
        customerPreferences.put(customerId, preferences);
    }

    public static CustomerDietaryPreferences getCustomerPreferences(String customerId) {
        return customerPreferences.get(customerId);
    }

    public static void savePreferences(String customerId) {
        CustomerDietaryPreferences preferences = customerPreferences.get(customerId);
        if (preferences != null) {
            preferences.savePreferences();
        }
    }
}
