package org.example;

import cook.entities.Ingredient1;
import data.IngredientData1;

public class Main {
    public static void main(String[] args) {
        // تهيئة المكونات في النظام
        IngredientData1.initializeIngredients();

        // السيناريو الأول: التحقق من الأسعار الحية للمكونات
        System.out.println("Scenario 1: Kitchen manager checks real-time pricing for ingredients.");
        Ingredient1 tomato = IngredientData1.getIngredientByName("Tomatoes");
        System.out.println("Ingredient: " + tomato.getName());
        System.out.println("Current Stock: " + tomato.getStock() + " kg");
        System.out.println("Low Stock Threshold: " + tomato.getLowStockThreshold() + " kg");

        // السيناريو الثاني: مقارنة الأسعار بين الموردين
        System.out.println("\nScenario 2: Kitchen manager compares supplier prices.");
        String bestSupplier = "Supplier A"; // فرضًا نختار المورد الأفضل
        System.out.println("Best Supplier for Tomatoes: " + bestSupplier);

        // السيناريو الثالث: توليد أمر شراء تلقائي عندما يكون المخزون منخفض
        System.out.println("\nScenario 3: System auto-generates purchase order for low stock.");
        if (tomato.getStock() < tomato.getLowStockThreshold()) {
            System.out.println("Low Stock Alert: " + tomato.getName() + " stock is below threshold!");
            System.out.println("Auto-Purchase Order: Ingredient: " + tomato.getName() + ", Quantity: 5 kg.");
        }

        // تحديث المخزون للطماطم
        tomato.setStock(2);  // تعيين المخزون إلى 2 كجم لاختبار التنبيه
        System.out.println("\nUpdated Stock for Tomatoes: " + tomato.getStock() + " kg");

        // السيناريو الرابع: مراجعة أمر الشراء من قبل مدير المطبخ
        System.out.println("\nScenario 4: Kitchen manager manually reviews purchase order.");
        boolean orderApproved = true; // فرضًا يقوم مدير المطبخ بالموافقة على الطلب
        if (orderApproved) {
            System.out.println("Order for Tomatoes approved and sent to the supplier.");
        } else {
            System.out.println("Order for Tomatoes canceled.");
        }

        // السيناريو الخامس: منع تكرار الطلبات للمكونات نفسها
        System.out.println("\nScenario 5: System prevents duplicate purchase orders.");
        boolean isDuplicateOrder = false; // فرضًا لا يوجد طلب مكرر
        if (isDuplicateOrder) {
            System.out.println("Warning: A purchase order for Tomatoes is already in progress.");
        } else {
            System.out.println("No duplicate order detected for Tomatoes.");
        }
    }
}
