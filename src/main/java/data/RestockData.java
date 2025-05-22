package data;

import cook.entities.Ingredient;
import cook.entities.RestockRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestockData {
    private static final List<RestockRequest> restockRequests = new ArrayList<>();

    public static void addRestockRequest(String name, int quantity, boolean isAuto) {
        if (!hasPendingRequest(name)) {
            restockRequests.add(new RestockRequest(name, quantity, isAuto));
        }
    }

    public static boolean hasPendingRequest(String name) {
        return restockRequests.stream()
                .anyMatch(req -> req.getIngredientName().equalsIgnoreCase(name) && !req.isDelivered());
    }
    

    public static RestockRequest getPendingRequest(String name) {
        return restockRequests.stream()
                .filter(req -> req.getIngredientName().equalsIgnoreCase(name) && !req.isDelivered())
                .findFirst().orElse(null);
    } //إذا وجد طلبًا غير مسلّم، ترجعه، وإذا لم يكن هناك طلب مطابق، ترجِع null

   

    public static String getStatus(Ingredient ingredient) {
        if (ingredient.getStock() == 0) return "Out of Stock";
        if (ingredient.getStock() < ingredient.getLowStockThreshold()) return "Critical";
        if (ingredient.getStock() == ingredient.getLowStockThreshold()) return "Low";
        return "Sufficient";
    }

    public static void markAsDelivered(String name, int quantity) {
        IngredientData.updateStock(name, Objects.requireNonNull(IngredientData.getIngredientByName(name)).getStock() + quantity);
        RestockRequest req = getPendingRequest(name); //اذا كان غير مسلم بترجع null
        if (req != null) req.markAsDelivered();
    }
}