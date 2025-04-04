package cook.entities;

public class Task {
    private String dishName;
    public String dueTime;
    private String assignedChef;

    public Task(String dishName, String dueTime, String assignedChef) {
        this.dishName = dishName;
        this.dueTime = dueTime;
        this.assignedChef = assignedChef;
    }


    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }


    public String getAssignedChef() {
        return assignedChef;
    }

    public void setAssignedChef(String assignedChef) {
        this.assignedChef = assignedChef;
    }
}
