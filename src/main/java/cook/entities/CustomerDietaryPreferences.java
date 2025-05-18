package cook.entities;

import java.util.HashSet;
import java.util.List;
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
    
    public Set<String> getDietaryRestrictions() {
        return new HashSet<>(dietaryRestrictions);
    }



  
}
