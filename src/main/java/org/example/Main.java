package org.example;

import cook.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserRegistrationService userService = new UserRegistrationService();
    private static List<Chef> chefs = new ArrayList<>();
    public static String result;
    private static List<Customer> customers = new ArrayList<>();
    private static List<Ingredient> ingredients = new ArrayList<>();
    private static List<String> suppliers = new ArrayList<>();


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

        System.out.print("Enter role (Kitchen Manager, Chef, Customer): ");
        String role = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        result = userService.registerUser(username, email, role, password, confirmPassword);
        System.out.println(result);
    }

    private static void handleLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean isValid = userService.isValidUsername(username) && userService.isValidPassword(password);

        if (isValid) {
            String role = userService.getRoleByUsername(username);
            if (role != null) {
                switch (role) {
                case "Kitchen Manager":
                    adminMenu();
                    break;
                    case "Chef":
                        Chef chef = new Chef(username, "General");
                        loadChefTasksFromFile(username, chef);
                        chefMenu(chef);
                        saveChefTasksToFile(username, chef);
                        break;
                    case "Customer":
                        Customer customer = new Customer(username, isValid, isValid);
                        customerMenu(customer);
                        break;
                    default:
                        System.out.println("Unknown role.");
                }
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    
    private static void customerMenu(Customer customer) {
        boolean logout = false;
        Scanner scanner = new Scanner(System.in);

        while (!logout) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. Add New Order");
            System.out.println("2. View My Orders");
            System.out.println("3. Set Dietary Preferences and Allergies");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                	System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
         
                    System.out.print("Enter Order ID: ");
                    String orderId = scanner.nextLine();
                    System.out.print("Enter Meal Name: ");
                    String mealName = scanner.nextLine();
                    System.out.print("Enter Order Date (e.g., 2025-05-18): ");
                    String orderDate = scanner.nextLine();
                    System.out.print("Enter Amount: ");
                    double amount = 0.0;
                    try {
                        amount = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Setting amount to 0.");
                    }
                    System.out.print("Enter Status: ");
                    String status = scanner.nextLine();

                    Order newOrder = new Order(orderId, mealName, orderDate, status);
                    customer.addPastOrders(newOrder);

                    try (FileWriter writer = new FileWriter("orders.txt", true)) {
                    	writer.write("Order ID: " + orderId +", Customer name: "+name+ ", Meal: " + mealName + ", Date: " + orderDate + ", Amount: " + amount + ", Status: " + status + "\n");
                    } catch (IOException e) {
                        System.out.println("Error saving order: " + e.getMessage());
                    }

                    System.out.println("Order added and saved to file.");
                    break;

                case 2:
                    List<Order> orders = customer.getPastOrders();
                    if (orders.isEmpty()) {
                        System.out.println("You have no past orders.");
                    } else {
                        for (Order o : orders) {
                            System.out.println("Order ID: " + o.getOrderId() +
                                    ", Meal: " + o.getMealName() +
                                    ", Date: " + o.getOrderDate() +
                                    ", Status: " + o.getStatus());
                        }
                    }
                    break;

                case 3:
                    System.out.println("Enter dietary restrictions for " + Customer.name + " (type 'done' to finish):");
                    CustomerDietaryPreferences prefs = new CustomerDietaryPreferences(Customer.name);
                    while (true) {
                        System.out.print("Add restriction: ");
                        String input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("done")) break;
                        prefs.addDietaryRestriction(input);
                    }

                  
                 
                    try (FileWriter writer = new FileWriter("preferences.txt", true)) {
                        writer.write("Customer: " + Customer.name + "\n");
                        writer.write("Dietary Restrictions:\n");
                        for (String restriction : prefs.getDietaryRestrictions()) {
                            writer.write("- " + restriction + "\n");
                        }
                        writer.write("-----\n");
                        System.out.println("Dietary preferences saved.");
                    } catch (IOException e) {
                        System.out.println("Error saving dietary preferences: " + e.getMessage());
                    }
                    break;


                case 4:
                    logout = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    
    
    
    
    
    
    private static void chefMenu(Chef chef) {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- Chef Menu ---");
            System.out.println("1. View Assigned Tasks");
            System.out.println("2. Mark Task as Completed");
            System.out.println("3. View Workload Summary");
            System.out.println("4. View Upcoming Task Reminder");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    System.out.println("\n--- Assigned Tasks ---");
                    int index = 1;
                    for (Task task : chef.getTaskList()) {
                        System.out.printf("%d. Dish: %s | Due: %s | Status: %s\n", index++, task.getDishName(), task.getDueTime(), task.getStatus());
                    }
                    break;
                case 2:
                    System.out.println("\nSelect task to mark as completed:");
                    for (int i = 0; i < chef.getTaskList().size(); i++) {
                        Task task = chef.getTaskList().get(i);
                        System.out.printf("%d. %s (Status: %s)\n", i + 1, task.getDishName(), task.getStatus());
                    }
                    System.out.print("Enter task number: ");
                    int taskNumber = readInt();
                    if (taskNumber >= 1 && taskNumber <= chef.getTaskList().size()) {
                        chef.getTaskList().get(taskNumber - 1).markAsCompleted();
                        System.out.println("Task marked as completed.");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;
                case 3:
                    System.out.println("\n--- Daily Workload Summary ---");
                    System.out.println(chef.generateDailySummary());
                    break;
                case 4:
                    System.out.println("\n--- Upcoming Task Reminder ---");
                    if (!chef.getTaskList().isEmpty()) {
                        System.out.println(chef.getUpcomingTaskReminder());
                    } else {
                        System.out.println("No upcoming tasks.");
                    }
                    break;
                case 5:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void loadChefTasksFromFile(String username, Chef chef) {
        File file = new File("chef_" + username + ".txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Task task = new Task(parts[0], parts[1]);
                    if (parts[2].equalsIgnoreCase("Completed")) {
                        task.markAsCompleted();
                    }
                    chef.getTaskList().add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    private static void saveChefTasksToFile(String username, Chef chef) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chef_" + username + ".txt"))) {
            for (Task task : chef.getTaskList()) {
                writer.write(task.getDishName() + "," + task.getDueTime() + "," + task.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static void adminMenu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Assign Task to Chef");
            System.out.println("2. View Finnancial Reports");
            System.out.println("3. Manage Suppliers");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    assignTaskToChef();
                    break;
               
                case 2:
                	viewFinancialReports();
                    break;
                case 3:
                	viewSuppliers();

                    break;
                case 4:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void viewSuppliers() {
        System.out.println("=== Supplier List ===");
        try {
            List<String> suppliers = Files.readAllLines(Paths.get("suppliers.txt"));
            for (String supplier : suppliers) {
                System.out.println(supplier);
            }
        } catch (IOException e) {
            System.out.println("No supplier data found.");
        }
    }

    
    
    private static void viewFinancialReports() {
        List<FoodOrder> orders = loadOrdersFromFile(); 
        FinancialReport dailyReport = FinancialReport.generateDailyReport(orders);
        FinancialReport monthlyReport = FinancialReport.generateMonthlyReport(orders);

        System.out.println("=== Daily Financial Report ===");
        System.out.println("Date: " + dailyReport.getDate());
        System.out.println("Total Revenue: $" + dailyReport.getTotalRevenue());
        System.out.println("Number of Orders: " + dailyReport.getNumberOfOrders());

        System.out.println("\n=== Monthly Financial Report ===");
        System.out.println("Month: " + monthlyReport.getDate());
        System.out.println("Total Revenue: $" + monthlyReport.getTotalRevenue());
        System.out.println("Number of Orders: " + monthlyReport.getNumberOfOrders());
    }
    
    private static List<FoodOrder> loadOrdersFromFile() {
        List<FoodOrder> orders = new ArrayList<>();
        Path path = Paths.get("orders.txt");

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String orderId = parts[0].trim();
                    String Name = parts[1].trim();
                    String mealName = parts[2].trim();
                    String date = parts[3].trim();
                    double totalAmount = Double.parseDouble(parts[4].trim());
                    String status = parts[5].trim();
                    Order ord= new Order( orderId, mealName, date, status);
                    FoodOrder order = new FoodOrder(orderId, Name, "14:00");
                    order.setStatus(status);
                    order.setTotalAmount(totalAmount); 
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading orders file: " + e.getMessage());
        }
        return orders;
    }




    private static void assignTaskToChef() {
        System.out.print("Enter Chef's username: ");
        String chefUsername = scanner.nextLine();
        System.out.print("Enter Dish Name: ");
        String dishName = scanner.nextLine();
        System.out.print("Enter Due Time (e.g., 14:00): ");
        String dueTime = scanner.nextLine();

        Chef chef = new Chef(chefUsername, "General");
        Task task = new Task(dishName, dueTime);
        chef.getTaskList().add(task);
        saveChefTasksToFile(chefUsername, chef);
        System.out.println("Task assigned successfully to Chef " + chefUsername + ".");
    }
    private static void loadIngredients() {
        ingredients.clear();
        File file = new File("ingredients.txt");
        if (!file.exists()) {
            // Create sample file with some ingredients
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Tomato,100,0.5\n");
                writer.write("Cheese,50,1.2\n");
                writer.write("Lettuce,80,0.3\n");
                writer.write("Chicken,30,3.5\n");
                writer.write("Peanuts,10,2.0\n"); // For allergy example
            } catch (IOException e) {
                System.out.println("Error creating sample ingredients file: " + e.getMessage());
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int stock = Integer.parseInt(parts[1].trim());
                    double price = Double.parseDouble(parts[2].trim());
                    ingredients.add(new Ingredient(name, stock, price));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading ingredients: " + e.getMessage());
        }
    }

    private static void saveIngredients() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ingredients.txt"))) {
            for (Ingredient ing : ingredients) {
                writer.write(ing.getName() + "," + ing.getStock() + "," + ing.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving ingredients: " + e.getMessage());
        }
    }

    private static void loadSuppliers() {
        suppliers.clear();
        File file = new File("suppliers.txt");
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("FreshFarm Suppliers\n");
                writer.write("Organic Goods Ltd.\n");
                writer.write("Premium Ingredients Co.\n");
            } catch (IOException e) {
                System.out.println("Error creating sample suppliers file: " + e.getMessage());
            }
        }
        try {
            suppliers = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            System.out.println("Error loading suppliers: " + e.getMessage());
        }
    }


    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}
