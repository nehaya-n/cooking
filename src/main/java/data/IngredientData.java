package data;

import cook.entities.Ingredient;
import java.util.HashMap;
import java.util.Map;

public class IngredientData {

    private static Map<String, Ingredient> ingredients = new HashMap<>();

    // إضافة مكون جديد
    public static void addIngredient(String name, int stock, int lowStockThreshold) {
        ingredients.put(name, new Ingredient(name, stock, lowStockThreshold));
    }

    // تحديث المخزون للمكون
    public static void updateStock(String name, int stock) {
        Ingredient ingredient = ingredients.get(name);
        if (ingredient != null) {
            ingredient.setStock(stock);
        }
    }

    // التحقق من إذا كان المكون في حالة منخفضة المخزون
    public static boolean isLowStock(String name) {
        Ingredient ingredient = ingredients.get(name);
        if (ingredient != null) {
            return ingredient.getStock() <= ingredient.getLowStockThreshold();
        }
        return false;
    }

    // إرجاع المكون بناءً على الاسم
    public static Ingredient getIngredientByName(String name) {
        return ingredients.get(name);
    }

    // استرجاع جميع المكونات
    public static Map<String, Ingredient> getIngredients() {
        return ingredients;
    }

    // وضع التنبيه كتم
    public static void acknowledgeAlert(String name) {
        Ingredient ingredient = ingredients.get(name);
        if (ingredient != null) {
            ingredient.acknowledgeAlert();
        }
    }

    // التحقق من إذا كان المكون قد تم إقرار تنبيهه
    public static boolean isAlertAcknowledged(String name) {
        Ingredient ingredient = ingredients.get(name);
        return ingredient != null && ingredient.isAlertAcknowledged();
    }
}
