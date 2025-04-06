package data;

import cook.entities.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartData {
    private final ShoppingCart shoppingCart;

    public ShoppingCartData() {
        shoppingCart = new ShoppingCart();
        shoppingCart.setItems(new ArrayList<>());
    }

    public void addItem(String item) {
        shoppingCart.getItems().add(item);
    }

    public void removeItem(String item) {
        shoppingCart.getItems().remove(item);
    }

    public void clearCart() {
        shoppingCart.getItems().clear();
    }

    public List<String> getShoppingCart() {
        return shoppingCart.getItems();
    }
}
