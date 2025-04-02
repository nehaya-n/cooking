package cook.entities;

import java.util.Map;

public class CustomerDietaryPreferences {
    private Map<String, String> preferences; // Dietary preferences (e.g., Vegetarian, Gluten-Free)
    private Map<String, String> allergies;  // Allergies (e.g., Peanuts, Shellfish)
    private boolean preferencesSaved;

    // Constructor
    public CustomerDietaryPreferences(Map<String, String> preferences, Map<String, String> allergies) {
        this.preferences = preferences;
        this.allergies = allergies;
        this.preferencesSaved = false;
    }

    // Getters and Setters
    public Map<String, String> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<String, String> preferences) {
        this.preferences = preferences;
    }

    public Map<String, String> getAllergies() {
        return allergies;
    }

    public void setAllergies(Map<String, String> allergies) {
        this.allergies = allergies;
    }

    public boolean isPreferencesSaved() {
        return preferencesSaved;
    }

    public void setPreferencesSaved(boolean preferencesSaved) {
        this.preferencesSaved = preferencesSaved;
    }

    public void savePreferences() {
        this.preferencesSaved = true;
    }
}
