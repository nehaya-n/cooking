package cook.entities;

public class Task {
    private String dishName;
    private String dueTime;
    private String assignedChef;

    public Task(String dishName, String dueTime, String assignedChef) {
        this.dishName = dishName;
        this.dueTime = dueTime;
        this.assignedChef = assignedChef;
    }

    // Getter و Setter
    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getAssignedChef() {
        return assignedChef;
    }

    public void setAssignedChef(String assignedChef) {
        this.assignedChef = assignedChef;
    }
}
