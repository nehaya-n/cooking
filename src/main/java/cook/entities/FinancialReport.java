package cook.entities;

import java.util.List;

public class FinancialReport {
    private String date;
    private double totalRevenue;
    private int numberOfOrders;

    public FinancialReport(String date, double totalRevenue, int numberOfOrders) {
        this.date = date;
        this.totalRevenue = totalRevenue;
        this.numberOfOrders = numberOfOrders;
    }

    public String getDate() {
        return date;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public static FinancialReport generateDailyReport(List<MealOrder> orders) {
        double totalRevenue = 0;
        int orderCount = orders.size();
        for (MealOrder order : orders) {
            totalRevenue += order.getTotalAmount(); // Assume MealOrder has a method getTotalAmount
        }
        return new FinancialReport("2025-03-03", totalRevenue, orderCount);
    }

    public static FinancialReport generateMonthlyReport(List<MealOrder> orders) {
    	 double totalRevenue = 0;
    	    int orderCount = orders.size();
    	    for (MealOrder order : orders) {
    	        totalRevenue += order.getTotalAmount(); // Assume MealOrder has a method getTotalAmount
    	    }
    	    double averageOrderValue = totalRevenue / orderCount;

    	    // Return a new report for the whole month
    	    return new FinancialReport("February 2025", totalRevenue, orderCount);
    }
}