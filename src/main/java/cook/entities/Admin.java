package cook.entities;

public class Admin {
    private String name;
    private String password;

    // Constructor
    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method for logging in
    public void login() {
        System.out.println("Admin " + name + " logged in.");
    }

    // Method to retrieve order history (dummy method for illustration)
    public void retrieveOrderHistory(String timePeriod) {
        System.out.println("Admin " + name + " retrieved order history for " + timePeriod);
    }
}