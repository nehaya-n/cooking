package data;

import cook.entities.Ingredient;
import java.util.ArrayList;
import java.util.List;

public class IngredientData {
    private static final List<Ingredient> ingredients = new ArrayList<>();

    static {
        initializeIngredients();
    }

    // إضافة مكونات ابتدائية للمحاكاة
    public static void initializeIngredients() {
        ingredients.add(new Ingredient("Tomatoes", 10, 5));  // مكون مع مخزون 10 وحدات وحد المخزون المنخفض 5
        ingredients.add(new Ingredient("Onions", 15, 5));    // مكون مع مخزون 15 وحدات وحد المخزون المنخفض 5
        ingredients.add(new Ingredient("Olive Oil", 5, 3));  // مكون مع مخزون 5 لترات وحد المخزون المنخفض 3
        ingredients.add(new Ingredient("Milk", 8, 2));       // مكون مع مخزون 8 لترات وحد المخزون المنخفض 2
    }

    // إرجاع قائمة المكونات
    public static List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    // إرجاع المكون بناءً على اسمه
    public static Ingredient getIngredientByName(String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;  // إرجاع null إذا لم يتم العثور على المكون
    }

    // تحديث المخزون للمكون
    public static void updateStock(String name, int newStock) {
        Ingredient ingredient = getIngredientByName(name);
        if (ingredient != null) {
            ingredient.setStock(newStock);  // تحديث المخزون إذا تم العثور على المكون
        }
    }

    // التحقق من إذا كان المكون في حالة مخزون منخفض
    public static boolean isLowStock(String name) {
        Ingredient ingredient = getIngredientByName(name);
        return ingredient != null && ingredient.getStock() <= ingredient.getLowStockThreshold();  // تحقق من المخزون إذا كان أقل من الحد
    }

    // إقرار التنبيه للمكون
    public static void acknowledgeAlert(String name) {
        Ingredient ingredient = getIngredientByName(name);
        if (ingredient != null) {
            ingredient.setAlertAcknowledged(true);  // وضع التنبيه كتم عند الإقرار
        }
    }
}
