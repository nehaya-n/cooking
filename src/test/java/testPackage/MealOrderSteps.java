package testPackage;

import cook.entities.Customer;

import cook.entities.MealPlanner;
import cook.entities.Admin;
import cook.entities.OrderSystem; 
import cook.entities.Order;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import static org.junit.Assert.*;

import java.util.List;

/*hhhhhhhhhhi*/

public class MealOrderSteps {

    private Customer customer;
    private MealPlanner chef;
    private Admin admin; 
    private OrderSystem system;

    // Scenario 1: Customer views past meal orders 
    @And("they have previously ordered the following meals:")
    public void customerHasPastOrders(DataTable data) {
        if (customer == null) {
            customer = new Customer("Customer A", false, false);  // تهيئة العميل إذا لم يكن مهيأ
        }

        if (system == null) {
            system = new OrderSystem();  // تهيئة النظام إذا لم يكن مهيأ
        }

        // تحويل البيانات من DataTable إلى قائمة من الطلبات
        for (int i = 0; i < data.height(); i++) {
            String orderId = data.cell(i, 0);  // orderId
            String mealName = data.cell(i, 1);
            String orderDate = data.cell(i, 2);
            String status = data.cell(i, 3);

            // إنشاء الطلب وإضافته إلى العميل والنظام
            Order order = new Order(orderId, mealName, orderDate, status);
            customer.addPastOrders(order);  // إضافة الطلب للعميل
            system.addOrder(order);  // إضافة الطلب للنظام
        }
    }

    @When("the customer navigates to the \"Order History\" page")
    public void customerNavigatesToOrderHistory() {
        system.navigateTo("Order History");
    }

    @Then("the system should display a list of their past meal orders")
    public void systemDisplaysOrderHistory() {
       
        assertEquals(customer.getPastOrders().size(), system.getPastOrders().size());
        for (Order order : customer.getPastOrders()) {
            assertTrue(system.getPastOrders().contains(order));
        }
    }

    @Then("each order should include:")
    public void checkOrderDetails(DataTable data) {
        for (int i = 0; i < data.height(); i++) {
            String orderId = data.cell(i, 0); 
            String mealName = data.cell(i, 1);
            String orderDate = data.cell(i, 2);
            String status = data.cell(i, 3);

            Order expectedOrder = new Order(orderId, mealName, orderDate, status); 

            boolean isPresent = false;
            for (Order order : customer.getPastOrders()) {
                if (order.equals(expectedOrder)) {
                    isPresent = true;
                    break;
                }
            }

            assertTrue("Expected order not found in past orders", isPresent);
        }
    }



    // Scenario 2: Customer reorders a previously liked meal
    
    @Given("a customer has the following past meal orders:")
    public void acustomerHasPastOrders(DataTable data) {
        if (customer == null) {
            customer = new Customer("Customer A", false, false); 
        }

        if (system == null) {
            system = new OrderSystem(); 
        }

        List<List<String>> rows = data.asLists(String.class);
        
        for (int i = 1; i < rows.size(); i++) { 
            String orderId = rows.get(i).get(0);
            String mealName = rows.get(i).get(1);
            String orderDate = rows.get(i).get(2);
            String status = rows.get(i).get(3);

            Order order = new Order(orderId, mealName, orderDate, status);

            customer.addPastOrders(order);  
            system.addOrder(order);        
        }
    }

    @When("the customer selects \"Reorder\" for {string}")
    public void customerReordersMeal(String mealName) {
        system.addMealToCart(mealName);
    }

    @Then("the system should add {string} to the shopping cart")
    public void mealAddedToCart(String mealName) {
        assertTrue(system.cartContains(mealName));
    }


   
    // Scenario 3: Chef accesses customer order history for meal plan suggestions
    @Given("a chef is logged into their account")
    public void chefIsLoggedIn() {
        if (chef == null) {
            chef = new MealPlanner("Chef A", 5); // تهيئة الطاهي إذا لم يكن مهيأ
        }
        chef.login(); // تسجيل الدخول للطاهي
    }
    
    @Given("they are preparing a meal plan for Customer A")
    public void theyArePreparingAMealPlanForCustomerA() {
        if (chef == null) {
            chef = new MealPlanner("Chef A", 5);
            chef.login();
        }
        if (customer == null) {
            customer = new Customer("Customer A", false, false);
        }
        if (system == null) {
            system = new OrderSystem();
        }

        system.addOrder(new Order("301", "Vegan Pesto Pasta", "2025-01-01", "Delivered"));
        system.addOrder(new Order("302", "Vegan Pesto Pasta", "2025-01-10", "Delivered"));
        system.addOrder(new Order("303", "Vegan Pesto Pasta", "2025-01-15", "Delivered"));
        system.addOrder(new Order("304", "Quinoa Bowl", "2025-02-10", "Delivered"));
        system.addOrder(new Order("305", "Quinoa Bowl", "2025-02-15", "Delivered"));
        system.addOrder(new Order("306", "Grilled Salmon", "2025-03-01", "Delivered"));

        for (Order order : system.getPastOrders()) {
            customer.addPastOrders(order);
        }
    }


    @When("the chef accesses Customer A’s order history")
    public void chefAccessesCustomerOrderHistory() {
        if (customer == null) {
            customer = new Customer("Customer A", false, false); // تهيئة العميل إذا لم يكن مهيأ
        }
        if (system == null) {
            system = new OrderSystem(); // تهيئة النظام إذا لم يكن مهيأ
        }
        chef.accessOrderHistory(customer); // الطاهي يقوم بالوصول إلى تاريخ الطلبات للعميل
    }

    @Then("the system should display the following past meals:")
    public void systemDisplaysCustomerMeals(DataTable data) {
        List<List<String>> rows = data.asLists(String.class);

       
        List<String> actualMeals = system.getPastMealsForCustomer(customer);
        assertNotNull(actualMeals);

        for (int i = 1; i < rows.size(); i++) {  
            String mealName = rows.get(i).get(0);
            assertTrue("Meal " + mealName + " not found in system's past meals", actualMeals.contains(mealName));
        }
    }

  

    // Scenario 4: System administrator retrieves customer order history for analysis
    @Given("a system administrator is logged into the dashboard")
    public void adminIsLoggedIn() {
        admin = new Admin("Admin");
        admin.login();
    }

    @When("they retrieve the order history for the past 3 months")
    public void adminRetrievesOrderHistory() {
        admin.retrieveOrderHistory("last 3 months");
    }

    @Then("the system should generate a report showing:")
    public void systemGeneratesReport(DataTable data) {
        if (system == null) {
            system = new OrderSystem();  
        }

        List<List<String>> rows = data.asLists(String.class);

        for (int i = 1; i < rows.size(); i++) {  
            String mealName = rows.get(i).get(0);
            String orders = rows.get(i).get(1);

            try {
                int ordersCount = Integer.parseInt(orders);
                assertEquals("Expected order count for " + mealName, ordersCount, system.getOrderHistoryReport(mealName));
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format for meal: " + mealName + ". Expected a number, got: " + orders);
                fail("Failed to parse the number: " + orders);
            }
        }
    }



    // Scenario 5: System recommends reordering favorite meals
    @Given("a customer has ordered {string} {int} times")
    public void customerHasOrderedFavoriteMeal(String mealName, int timesOrdered) {
        if (customer == null) {
            customer = new Customer("Customer A", false, false);  // تهيئة العميل إذا كان null
        }
        customer.setFavoriteMeal(mealName, timesOrdered);  // تعيين الوجبة المفضلة
    }


    @When("the customer logs into their account")
    public void customerLogsIn() {
        customer.login();
    }
 
    @Then("the system should display a suggestion:")
    public void systemDisplaysSuggestion(String suggestion) {
        // تأكد من تهيئة system إذا كانت null
        if (system == null) {
            system = new OrderSystem();  // تهيئة النظام إذا كان null
        }
        
        // التحقق من وجود التوصية في النظام
        assertTrue(system.getRecommendation().contains(suggestion));
    }
}
