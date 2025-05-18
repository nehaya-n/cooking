package cook.entities;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private final String DATA_FILE_PATH = "src/main/resources/users.txt";

    private Map<String, User> users = new HashMap<>();

    public AuthService() {
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            System.out.println("User file not found: " + DATA_FILE_PATH);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String username = parts[0].trim();
                    String email = parts[1].trim(); // not used for login, but could be stored if needed
                    String role = parts[2].trim();
                    String password = parts[3].trim();

                    users.put(username.toLowerCase(), new User(username, password, role));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
    }

    public AuthResult login(String username, String password) {
        if (users.containsKey(username.toLowerCase())) {
            User user = users.get(username.toLowerCase());
            if (user.getPassword().equals(password)) {
                return new AuthResult(true, user.getRole());
            }
        }
        return new AuthResult(false, null);
    }

    public static class User {
        private String username;
        private String password;
        private String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }
    }

    public static class AuthResult {
        private boolean valid;
        private String role;

        public AuthResult(boolean valid, String role) {
            this.valid = valid;
            this.role = role;
        }

        public boolean isValid() {
            return valid;
        }

        public String getRole() {
            return role;
        }
    }
}
