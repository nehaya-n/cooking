package org.example;

import cook.entities.*;
import java.util.Random;
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
    public static double totalRevenue;
    public static int numberOfOrders ;

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

            while (!logout) {
          System.out.println("\n--- Customer Menu ---");
          System.out.println("1. Add New Order");
          System.out.println("2. View My Orders");
          System.out.println("3. Set Dietary Preferences and Allergies");
          System.out.println("4. Show my invoices");
          System.out.println("5. View Delivery Reminders"); 
          System.out.println("6. Logout");
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
                    System.out.print("Enter Delivary time: ");
                    String delivaryT = scanner.nextLine();

                    Order newOrder = new Order(orderId, mealName, orderDate, status);
                    customer.addPastOrders(newOrder);

                    try (FileWriter writer = new FileWriter("orders.txt", true)) {
                    	writer.write(orderId + "," + name + "," + mealName + "," + orderDate + "," + amount + "," + status + "," + delivaryT + "\n");

                    } catch (IOException e) {
                        System.out.println("Error saving order: " + e.getMessage());
                    }

                    System.out.println("Order for '" + mealName + "' added and saved successfully.");
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
                    System.out.println("Enter dietary restrictions for " + Customer.getName() + " (type 'done' to finish):");
                    CustomerDietaryPreferences prefs = new CustomerDietaryPreferences(Customer.getName());
                    while (true) {
                        System.out.print("Add restriction: ");
                        String input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("done")) break;
                        prefs.addDietaryRestriction(input);
                    }

                   
                 
                    try (FileWriter writer = new FileWriter("preferences.txt", true)) {
                        writer.write("Customer: " + Customer.getName() + "\n");
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
                	 System.out.println("Enter your name please : ");
                			 String customerName = scanner.nextLine();
                			 showCustomerInvoices(customerName); 
                    break;

                case 5: 
                    sendCustomerReminders(customer);
                    break;

                case 6:
                    logout = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    
    private static void sendCustomerReminders(Customer customer) {
        System.out.println("\n--- Your Upcoming Orders ---");
        for (Order order : customer.getPastOrders()) {
            if (order.getStatus().equals("pending")) {
                MealOrder mealOrder = new MealOrder(order.getOrderId(), customer.getName(), order.getOrderDate());
                System.out.println(mealOrder.generateReminderNotification());
            }
        }
    }

    private static void sendChefReminders(Chef chef) {
        System.out.println("\n--- Your Task Reminders ---");
        if (!chef.getTaskList().isEmpty()) {
            System.out.println(chef.getUpcomingTaskReminder());
            System.out.println("\n" + chef.generateDailySummary());
        } else {
            System.out.println("No pending tasks.");
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
            System.out.println("3.Manage Ingredient Inventory and Suppliers");
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
                    String fileName = "ingredient_inventory.txt";
                    
                 // Notify for low-stock ingredients
                	File file = new File(fileName);
                	if (file.exists()) {
                	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                	        String line;
                	        boolean lowStockFound = false;
                	        while ((line = reader.readLine()) != null) {
                	            String[] parts = line.split(",");
                	            if (parts.length == 5) {
                	                String ingName = parts[0];
                	                int ingQuantity = Integer.parseInt(parts[1]);
                	                int ingThreshold = Integer.parseInt(parts[4]);

                	                if (ingQuantity <= ingThreshold) {
                	                    if (!lowStockFound) {
                	                        System.out.println("\n⚠️  Low Stock Alert:");
                	                        lowStockFound = true;
                	                    }
                	                    System.out.println("- " + ingName + ": only " + ingQuantity + " in stock (threshold: " + ingThreshold + ")");
                	                }
                	            }
                	        }
                	    } catch (IOException e) {
                	        System.out.println("Error checking low stock: " + e.getMessage());
                	    }
                	}

                    while (true) {

                        System.out.println("\n--- Manage Ingredient Inventory and Suppliers ---");
                        System.out.println("1. Add Ingredient");
                        System.out.println("2. View Inventory");
                        System.out.println("3. Back to Main Menu");
                        System.out.print("Enter your choice: ");

                        int subChoice;
                        try {
                            subChoice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            continue;
                        }

                        switch (subChoice) {
                            case 1:
                                System.out.print("Enter ingredient name: ");
                                String name = scanner.nextLine();
                                System.out.print("Enter quantity: ");
                                int quantity = Integer.parseInt(scanner.nextLine());
                                System.out.print("Enter supplier name: ");
                                String supplier = scanner.nextLine();
                                System.out.print("Enter price per unit: ");
                                double price = Double.parseDouble(scanner.nextLine());
                                System.out.print("Enter low stock threshold: ");
                                int threshold = Integer.parseInt(scanner.nextLine());

                                // Save to file
                                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                                    writer.write(name + "," + quantity + "," + supplier + "," + price + "," + threshold);
                                    writer.newLine();
                                    System.out.println("Ingredient added successfully.");
                                } catch (IOException e) {
                                    System.out.println("Error writing to file: " + e.getMessage());
                                }
                                break;

                            case 2:
                                File file1 = new File(fileName);
                                if (!file1.exists()) {
                                    System.out.println("Inventory is empty.");
                                    break;
                                }

                                System.out.println("\n--- Ingredient Inventory ---");
                                try (BufferedReader reader = new BufferedReader(new FileReader(file1))) {
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        String[] parts = line.split(",");
                                        if (parts.length == 5) {
                                            String ingName = parts[0];
                                            int ingQuantity = Integer.parseInt(parts[1]);
                                            String ingSupplier = parts[2];
                                            double ingPrice = Double.parseDouble(parts[3]);
                                            int ingThreshold = Integer.parseInt(parts[4]);

                                            Ingredient ingredient = new Ingredient(ingName, ingQuantity, ingThreshold);
                                            System.out.println("Name: " + ingredient.getName()
                                                    + ", Quantity: " + ingredient.getStock()
                                                    + ", Supplier: " + ingSupplier
                                                    + ", Price per unit: $" + ingPrice
                                                    + ", Low stock threshold: " + ingredient.getLowStockThreshold());
                                        }
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error reading from file: " + e.getMessage());
                                }
                                break;

                            case 3:
                                return;

                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                    }
                    
                case 4:
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
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
                if (parts.length >= 7) {
                    String orderId = parts[0].trim();
                    String Name = parts[1].trim();
                    String mealName = parts[2].trim();
                    String date = parts[3].trim();
                    double totalAmount = Double.parseDouble(parts[4].trim());
                    String status = parts[5].trim();
                    String delivary = parts[6].trim();
                    FoodOrder order = new FoodOrder(orderId, Name, delivary);
                    order.setStatus(status);
                    order.setTotalAmount(totalAmount); 
                    orders.add(order);
                    totalRevenue = 0;
                    numberOfOrders = 0;
                    for (FoodOrder order1 : orders) {
                        totalRevenue += order1.getTotalAmount(); 
                        numberOfOrders += 1;
                    }
                    FinancialReport FR = new FinancialReport(date, totalRevenue, numberOfOrders);
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
        chef.addTask(task);
        saveChefTasksToFile(chefUsername, chef);
        
        System.out.println("Task assigned successfully!");
        System.out.println(chef.getUpcomingTaskReminder()); // إظهار التذكير فورًا
    }

    
    public static void showCustomerInvoices(String customerName) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        Random rand = new Random();
        double totalAllOrders = 0; 
        double Amount=0;
        try (BufferedReader br = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[1].equalsIgnoreCase(customerName)) {
                    String orderId = parts[0];
                    String items = parts[2];
                    double amount = Double.parseDouble(parts[4]);
                    String paymentStatus = parts[5];
                    Amount= amount;

                    int unitPrice = 10 + rand.nextInt(16);
                    double totalAmount = amount * unitPrice;

                    Invoice invoice = new Invoice("INV-" + orderId, orderId, items, totalAmount);
                    invoice.updatePaymentStatus(paymentStatus);

                    invoices.add(invoice);
                    totalAllOrders += totalAmount; // جمع السعر الكلي
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading orders file: " + e.getMessage());
            return;
        }

        if (invoices.isEmpty()) {
            System.out.println("No invoices found for customer: " + customerName);
        } else {
            System.out.println("Invoices for " + customerName + ":");
            for (Invoice inv : invoices) {
                System.out.println("-----------------------------");
                System.out.println("Invoice Number: " + inv.getInvoiceNumber());
                System.out.println("Order ID: " + inv.getOrderId());
                System.out.println("Items: " + inv.getItems());
                System.out.println("Total amount: " + Amount); 
                System.out.println("Total price: " + inv.getTotalAmount());
                System.out.println("Payment Status: " + inv.getPaymentStatus());
            }
            System.out.println("-----------------------------");
            System.out.println("Total Price for All Orders: " + totalAllOrders);
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
