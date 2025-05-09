package testPackage;

import cook.entities.Customer;
import cook.entities.Chef;
import cook.entities.Admin;
import cook.entities.OrderSystem; 
import cook.entities.Order;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import static org.junit.Assert.*;

public class MealOrderSteps {

    private Customer customer;
    private Chef chef;
    private Admin admin; 
    private OrderSystem system;

    // Scenario 1: Customer accesses order history
    @Given("they have previously ordered the following meals:")
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
        // التحقق من أن النظام يحتوي على نفس الطلبات
        assertEquals(customer.getPastOrders().size(), system.getPastOrders().size());
        for (Order order : customer.getPastOrders()) {
            assertTrue(system.getPastOrders().contains(order));
        }
    }

    @Then("each order should include:")
    public void checkOrderDetails(DataTable data) {
        for (int i = 0; i < data.height(); i++) {
            String mealName = data.cell(i, 1);
            String orderDate = data.cell(i, 2);
            String status = data.cell(i, 3);

            // استخدام البناء الجديد الذي يحتوي فقط على 3 معلمات
            Order expectedOrder = new Order(mealName, orderDate, status);

            // طباعة للكائنات للتحقق من القيم
            System.out.println("Expected Order: " + expectedOrder);
            for (Order order : customer.getPastOrders()) {
                System.out.println("Customer Past Order: " + order);
            }

            // مقارنة باستخدام equals()
            boolean isPresent = false;
            for (Order order : customer.getPastOrders()) {
                if (order.equals(expectedOrder)) {
                    isPresent = true;
                    break;
                }
            }

            // إضافة التحقق من العثور على الطلب المتوقع
            assertTrue("Expected order not found in past orders", isPresent);
        }
    }





    // Scenario 2: Customer reorders a previously liked meal
    @When("the customer selects \"Reorder\" for {string}")
    public void customerReordersMeal(String mealName) {
        system.addMealToCart(mealName);
    }

    @Then("the system should add {string} to the shopping cart")
    public void mealAddedToCart(String mealName) {
        assertTrue(system.cartContains(mealName));
    }

   /* @Then("display a confirmation message:")
    public void displayConfirmationMessage(String message) {
        assertEquals(message, system.getConfirmationMessage());
    }*/

    // Scenario 3: Chef accesses customer order history for meal plan suggestions
    // Scenario 3: Chef accesses customer order history for meal plan suggestions
    @Given("a chef is logged into their account")
    public void chefIsLoggedIn() {
        if (chef == null) {
            chef = new Chef("Chef A", 5); // تهيئة الطاهي إذا لم يكن مهيأ
        }
        chef.login(); // تسجيل الدخول للطاهي
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
        // التأكد من أن النظام يحتوي على قائمة الوجبات السابقة للعميل
        assertNotNull(system.getPastMealsForCustomer(customer));

        // التحقق من أن الوجبات السابقة في النظام تحتوي على نفس الوجبات كما في البيانات المدخلة
        for (int i = 0; i < data.height(); i++) {
            String mealName = data.cell(i, 0);
            assertTrue("Meal " + mealName + " not found in system's past meals", system.getPastMealsForCustomer(customer).contains(mealName));
        }
    }

  

    // Scenario 4: System administrator retrieves customer order history for analysis
    @Given("a system administrator is logged into the dashboard")
    public void adminIsLoggedIn() {
        admin = new Admin("Admin", "admin123");
        admin.login();
    }

    @When("they retrieve the order history for the past 3 months")
    public void adminRetrievesOrderHistory() {
        admin.retrieveOrderHistory("last 3 months");
    }

    @Then("the system should generate a report showing:")
    public void systemGeneratesReport(DataTable data) {
        for (int i = 0; i < data.height(); i++) {
            String mealName = data.cell(i, 0);  // اسم الوجبة
            String orders = data.cell(i, 1);   // عدد الطلبات كـ String

            try {
                // محاولة تحويل العدد إلى int
                int ordersCount = Integer.parseInt(orders);  // حاول تحويل النص إلى رقم
                assertEquals("Expected order count for " + mealName, ordersCount, system.getOrderHistoryReport(mealName));
            } catch (NumberFormatException e) {
                // التعامل مع الخطأ في حال كان الإدخال ليس رقماً
                System.err.println("Invalid number format for meal: " + mealName + ". Expected a number, got: " + orders);
                fail("Failed to parse the number: " + orders);
            }
        }
    }





    @Then("display insights such as:")
    public void systemDisplaysInsights(String insights) {
        assertTrue(system.getInsights().contains(insights));
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
