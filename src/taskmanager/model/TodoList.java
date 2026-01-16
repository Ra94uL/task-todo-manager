package taskmanager.model;

import java.util.ArrayList;
import java.util.List;

public class TodoList {

    private String name;
    private TaskStatus status;
    private List<Task> tasks;


    public TodoList(String name, TaskStatus status) {
        this.name = name;
        this.status = status;
        this.tasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
