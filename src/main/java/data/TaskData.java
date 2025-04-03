package data;

import cook.entities.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskData {

    private static final List<Task> tasks = new ArrayList<>();

    static {
        initializeTasks();
    }

    // Initializes some sample tasks for testing
    public static void initializeTasks() {
        tasks.add(new Task("Grilled Chicken", "6:00 PM", "Chef John"));
        tasks.add(new Task("Pasta Alfredo", "7:30 PM", "Chef Alex"));
        tasks.add(new Task("Vegan Burger", "8:00 PM", "Chef Maria"));
        tasks.add(new Task("Grilled Salmon", "8:30 PM", "Chef John"));
    }

    // Adds a new task to the list
    public static void addTask(Task task) {
        tasks.add(task);
    }

    // Get all tasks
    public static List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    // Get a task by its dish name
    public static Task getTaskByDishName(String dishName) {
        for (Task task : tasks) {
            if (task.getDishName().equalsIgnoreCase(dishName)) {
                return task;
            }
        }
        return null;
    }

    // Get tasks assigned to a specific chef
    public static List<Task> getTasksByChef(String chefName) {
        List<Task> assignedTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getAssignedChef().equalsIgnoreCase(chefName)) {
                assignedTasks.add(task);
            }
        }
        return assignedTasks;
    }

    // Updates the assigned chef for a task
    public static void updateTaskAssignment(String dishName, String newChef) {
        Task task = getTaskByDishName(dishName);
        if (task != null) {
            task.setAssignedChef(newChef);
        }
    }
}
