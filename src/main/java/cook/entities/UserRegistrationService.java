package cook.entities;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class UserRegistrationService {

    // مسار ملف خارجي لتخزين البيانات
	private final String DATA_FILE_PATH = "src/main/resources/users.txt";


    private final Set<String> registeredEmails = Collections.synchronizedSet(new HashSet<>());
    private final Set<String> registeredUsernames = Collections.synchronizedSet(new HashSet<>());

    // اسم المستخدم: حروف وأرقام فقط وطول من 1 إلى 15
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,15}$");

    // الإيميل: نمط بسيط للتحقق من الصيغة
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

    // كلمة السر: 6 أحرف على الأقل وبها حرف واحد على الأقل
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z]).{6,}$");

    // الأدوار المسموح بها فقط
    private static final Set<String> VALID_ROLES = Set.of("Customer", "Chef", "Kitchen Manager");

    public UserRegistrationService() {
        loadDataFromFile();
    }

    public synchronized String registerUser(String username, String email, String role, String password, String confirmPassword) {
        if (!isValidUsername(username)) {
            return "Invalid username (alphanumeric ≤15 chars).";
        }
        if (isUsernameRegistered(username)) {
            return "Username already in use.";
        }
        if (!isValidEmail(email)) {
            return "Invalid email format.";
        }
        if (isEmailRegistered(email)) {
            return "Email already in use.";
        }
        if (!VALID_ROLES.contains(role)) {
            return "Role must be Customer, Chef or Manager.";
        }
        if (!isValidPassword(password)) {
            return "Password ≥6 chars & at least one letter.";
        }
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }

        registeredUsernames.add(username.toLowerCase());
        registeredEmails.add(email.toLowerCase());

        saveUserToFile(username, email, role, password);


        return "Account created successfully";
    }



    private void loadDataFromFile() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            return; // الملف غير موجود - أول تشغيل
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    registeredUsernames.add(parts[0].toLowerCase());
                    registeredEmails.add(parts[1].toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveUserToFile(String username, String email, String role, String password) {
        try {
            File file = new File(DATA_FILE_PATH);
            file.getParentFile().mkdirs(); // إنشاء المجلد إذا لم يكن موجودًا

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                // تنسيق التخزين: username,email,role,password
                writer.write(username + "," + email + "," + role + "," + password);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    private boolean isUsernameRegistered(String username) {
        return username != null && registeredUsernames.contains(username.toLowerCase());
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isEmailRegistered(String email) {
        return email != null && registeredEmails.contains(email.toLowerCase());
    }

    public boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
    public boolean isValidLogin(String username, String password) {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            return false; // الملف غير موجود
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storedUsername = parts[0].toLowerCase();
                    String storedPassword = parts[3]; // أو إذا كانت كلمة المرور مشفرة، قم باستخدام التحقق المناسب
                    if (storedUsername.equals(username.toLowerCase()) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false; // المستخدم غير موجود
    }
    
    // دالة للحصول على الدور بناءً على اسم المستخدم
    public String getRoleByUsername(String username) {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storedUsername = parts[0].toLowerCase();
                    String role = parts[2]; // الدور في الملف
                    if (storedUsername.equals(username.toLowerCase())) {
                        return role;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null; // الدور غير موجود
    }

}
