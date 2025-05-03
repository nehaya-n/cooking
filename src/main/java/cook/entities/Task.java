package cook.entities;

public class Task {
    private String dishName;
    private String dueTime;
    private String status; // Example: "Assigned", "Completed"

    public Task(String dishName, String dueTime) {
        this.dishName = dishName;
        this.dueTime = dueTime;
        this.status = "Assigned";
    }

    public String getDishName() {
        return dishName;
    }

    public String getDueTime() {
        return dueTime;
    }

    public String getStatus() {
        return status;
    }

    public void markAsCompleted() {
        this.status = "Completed";
    }
}
