package org.example;

import cook.entities.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserRegistrationService userService = new UserRegistrationService();
   

    // قائمة العملاء المسجلين في النظام (مؤقتًا في الذاكرة)
    private static List<Customer> customers = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Welcome to Special Cook Management System");

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Screen ---");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    handleSignUp();
                    break;
                case 2:
                    handleLogin();
                    break;
                case 3:
                    System.out.println("Exiting... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    private static void handleSignUp() {
        System.out.println("\n--- Sign Up ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter role (Admin, Chef, Customer): ");
        String role = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        String result = userService.registerUser(username, email, role, password, confirmPassword);
        System.out.println(result);
    }

    private static void handleLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

    }
          

          


    // قائمة العميل مع دعم إدخال تفضيلات النظام الغذائي والحساسية
    private static void customerMenu(Customer customer) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Add New Order (Not implemented)");
            System.out.println("2. View My Orders (Not implemented)");
            System.out.println("3. Set Dietary Preferences and Allergies");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    System.out.println("Add New Order selected.");
                    break;
                case 2:
                    System.out.println("View My Orders selected.");
                    break;
                case 3:
                   // setDietaryPreferences(customer);
                    break;
                case 4:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // قائمة الشيف لعرض تفضيلات العملاء
    private static void chefMenu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- Chef Menu ---");
            System.out.println("1. View Orders (Not implemented)");
            System.out.println("2. Update Meal Status (Not implemented)");
            System.out.println("3. View Customer Dietary Preferences");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    System.out.println("View Orders selected.");
                    break;
                case 2:
                    System.out.println("Update Meal Status selected.");
                    break;
                case 3:
                //    viewAllCustomerPreferences();
                    break;
                case 4:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void adminMenu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Manage Users (Not implemented)");
            System.out.println("2. View Reports (Not implemented)");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    System.out.println("Manage Users selected.");
                    break;
                case 2:
                    System.out.println("View Reports selected.");
                    break;
                case 3:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // دالة ضبط تفضيلات النظام الغذائي والحساسية للعميل


    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }
}
