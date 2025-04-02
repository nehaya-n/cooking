package org.example;

import cook.entities.CustomerDietaryPreferences;
import data.CustomerDietaryPreferencesData;

import java.util.Map;
import java.util.Scanner;

public class aa2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // السيناريو الأول: إدخال تفضيلات غذائية وحساسيات العميل
        System.out.println("Enter customer dietary preferences:");

        // إدخال التفضيلات الغذائية
        System.out.print("Vegetarian (Yes/No): ");
        String vegetarian = scanner.nextLine();
        System.out.print("Gluten-Free (Yes/No): ");
        String glutenFree = scanner.nextLine();
        System.out.print("Dairy-Free (Yes/No): ");
        String dairyFree = scanner.nextLine();

        // إدخال الحساسية
        System.out.print("Enter allergy (Peanuts, Shellfish, etc.): ");
        String allergy = scanner.nextLine();
        System.out.print("Enter allergy severity (Severe, Moderate, Mild): ");
        String severity = scanner.nextLine();

        // تخزين التفضيلات
        Map<String, String> preferences = Map.of(
                "Vegetarian", vegetarian,
                "Gluten-Free", glutenFree,
                "Dairy-Free", dairyFree
        );

        Map<String, String> allergies = Map.of(
                allergy, severity
        );

        // إنشاء كائن جديد للعميل مع التفضيلات المدخلة
        CustomerDietaryPreferences customerPreferences = new CustomerDietaryPreferences(preferences, allergies);

        // إضافة العميل إلى البيانات
        CustomerDietaryPreferencesData.addCustomerPreferences("CustomerA", customerPreferences);

        // حفظ التفضيلات
        CustomerDietaryPreferencesData.savePreferences("CustomerA");

        // تأكيد الحفظ
        System.out.println("Your dietary preferences and allergies have been successfully updated.");

        // عرض التفضيلات المحفوظة للطباخ
        System.out.println("\nChef is preparing a meal for the customer.");
        CustomerDietaryPreferences savedPreferences = CustomerDietaryPreferencesData.getCustomerPreferences("CustomerA");
        System.out.println("Displaying customer dietary preferences:");
        System.out.println("Vegetarian: " + savedPreferences.getPreferences().get("Vegetarian"));
        System.out.println("Gluten-Free: " + savedPreferences.getPreferences().get("Gluten-Free"));
        System.out.println("Dairy-Free: " + savedPreferences.getPreferences().get("Dairy-Free"));

        System.out.println("Allergies:");
        savedPreferences.getAllergies().forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });

        // السيناريو الثالث: منع العميل من طلب وجبة تحتوي على مكونات تحتوي على الحساسية
        String mealName = "Thai Peanut Noodles";
        if (savedPreferences.getAllergies().containsKey("Peanuts")) {
            System.out.println("\nWarning: This meal contains Peanuts, which you have marked as a severe allergy.");
        }

        // السيناريو الرابع: اقتراح الوجبات بناءً على التفضيلات الغذائية
        System.out.println("\nSuggesting meals based on dietary preferences:");
        if ("Yes".equals(savedPreferences.getPreferences().get("Vegetarian")) && "Yes".equals(savedPreferences.getPreferences().get("Gluten-Free"))) {
            System.out.println("Suggested Meal: Quinoa & Avocado Bowl");
        }
    }
}
