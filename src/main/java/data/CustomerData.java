package data;
import cook.entities.Customer;
import java.util.HashMap;
import java.util.Map;

public class CustomerData {
    private static final Map<String, Customer> customers = new HashMap<>();

    public static void addCustomer(Customer customer) {
        customers.put(customer.getName(), customer);
    }

    public static Customer getCustomerByName(String name) {
        return customers.get(name);
    }
}

