package cook.entities;

public class Chef {
    private String name;
    private int workload; // عدد المهام المكلفة للطباخ

    public Chef(String name, int workload) {
        this.name = name;
        this.workload = workload;
    }

    // Getter و Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWorkload() {
        return workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }
}
