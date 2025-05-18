package cook.entities;

import java.util.*;

public class Chef {
    private String name;
    private String expertise;
    private int currentWorkload;
    private List<Task> taskList;

    public Chef(String name, String expertise) {
        this.name = name;
        this.expertise = expertise;
        this.currentWorkload = 0;
        this.taskList = new ArrayList<>();
    }

  /*  public String getName() {
        return name;
    }

    public String getExpertise() {
        return expertise;
    }*/

    public int getCurrentWorkload() {
        return currentWorkload;
    }

    public void addTask(Task task) {
        taskList.add(task);
        currentWorkload++;
    }

    public void removeTask(Task task) {
        taskList.remove(task);
        currentWorkload--;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void updateWorkload(int workload) {
        this.currentWorkload = workload;
    }
    
    public String getUpcomingTaskReminder() {
        
        Task upcomingTask = taskList.get(0);
        return String.format("""
            Reminder: You have a scheduled cooking task for %s at %s.
            Please start preparing the meal on time.
            """, upcomingTask.getDishName(), upcomingTask.getDueTime());
    }
    public String generateDailySummary() {
        if (taskList.isEmpty()) {
            return "No tasks scheduled for today.";
        }

        StringBuilder summary = new StringBuilder("Today's Cooking Schedule:\n");
        int i = 1;
        for (Task task : taskList) {
            summary.append(String.format(Locale.ENGLISH, "- %s: %s (Order #%d)\n", task.getDueTime(), task.getDishName(), 1000 + i));
            i++;
        }
        summary.append("Please prepare meals on time. Good luck!");
        return summary.toString();
    }
    
}