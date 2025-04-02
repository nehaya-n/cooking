
package org.example;

import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // تعريف الأسعار للمكونات المختلفة من الموردين
        Map<String, Double> tomatoPrices = Map.of("Supplier A", 2.50, "Supplier B", 3.30, "Supplier C", 2.75);
        Map<String, Double> oliveOilPrices = Map.of("Supplier A", 5.00, "Supplier B", 4.80, "Supplier C", 5.20);
        Map<String, Double> chickenPrices = Map.of("Supplier A", 8.00, "Supplier B", 7.50, "Supplier C", 7.80);

        // تعريف المخزون للمكونات
        Map<String, Integer> stockLevels = Map.of(
                "Tomatoes", 10,
                "Olive Oil", 2,
                "Chicken", 15
        );

        // إنشاء كائن Scanner لقراءة المدخلات من المستخدم
        Scanner scanner = new Scanner(System.in);

        // حلقة لتمكين المستخدم من إدخال أكثر من منتج
        while (true) {
            System.out.println("Enter the product to compare prices (Tomatoes, Olive Oil, Chicken), or type 'exit' to quit:");
            String ingredientName = scanner.nextLine().trim();

            // التحقق إذا كان المستخدم يريد الخروج
            if (ingredientName.equalsIgnoreCase("exit")) {
                break;  // الخروج من الحلقة
            }

            // تنفيذ السيناريو بناءً على اختيار المستخدم
            switch (ingredientName.toLowerCase()) {
                case "tomatoes":
                    displayPrices("Tomatoes", tomatoPrices);
                    processOrder("Tomatoes", tomatoPrices, stockLevels);
                    break;
                case "olive oil":
                    displayPrices("Olive Oil", oliveOilPrices);
                    processOrder("Olive Oil", oliveOilPrices, stockLevels);
                    break;
                case "chicken":
                    displayPrices("Chicken", chickenPrices);
                    processOrder("Chicken", chickenPrices, stockLevels);
                    break;
                default:
                    System.out.println("Invalid product name: " + ingredientName);
                    break;
            }
        }

        scanner.close();  // غلق الـ Scanner
    }

    // دالة لعرض الأسعار للمكونات من الموردين
    public static void displayPrices(String ingredientName, Map<String, Double> prices) {
        System.out.println("-------- Price for " + ingredientName + " --------");
        prices.forEach((supplier, price) -> {
            System.out.println("Price from " + supplier + ": $" + price);
        });

        // تحديد المورد الأفضل بناءً على السعر
        String bestSupplier = getBestPriceSupplier(prices);
        System.out.println("Best price supplier for " + ingredientName + ": " + bestSupplier);
        System.out.println("Preparing purchase order for " + ingredientName + " from " + bestSupplier);
    }

    // دالة للحصول على أفضل مورد بناءً على السعر الأقل
    public static String getBestPriceSupplier(Map<String, Double> prices) {
        return prices.entrySet().stream()
                .min(Map.Entry.comparingByValue())  // العثور على المورد الذي يقدم أفضل سعر (السعر الأقل)
                .map(Map.Entry::getKey)
                .orElse("No supplier found");
    }

    // دالة لمعالجة الطلبات وإنتاج أمر شراء تلقائي عند انخفاض المخزون
    public static void processOrder(String ingredientName, Map<String, Double> prices, Map<String, Integer> stockLevels) {
        // تحقق من مستوى المخزون
        int stock = stockLevels.getOrDefault(ingredientName, 0);
        if (stock <= 2) {
            System.out.println("Stock for " + ingredientName + " is low, auto-generating purchase order...");
            generateAutoOrder(ingredientName, prices);
        } else {
            System.out.println("Stock for " + ingredientName + " is sufficient.");
        }
    }

    // دالة لإنشاء أمر شراء تلقائي
    public static void generateAutoOrder(String ingredientName, Map<String, Double> prices) {
        String bestSupplier = getBestPriceSupplier(prices);
        double price = prices.get(bestSupplier);

        // إعداد أمر الشراء التلقائي
        System.out.println("Auto-Purchase Order:");
        System.out.println("- Ingredient: " + ingredientName);
        System.out.println("- Quantity: 5 liters");
        System.out.println("- Preferred Supplier: " + bestSupplier + " ($" + price + "/liter)");
        System.out.println("- Total Cost: $" + (5 * price));
        System.out.println("Notifying kitchen manager for purchase order approval.");
    }
}
