package taskmanager.model;

import java.time.LocalDate;

public class Task {

    private String title;
    private TaskStatus status;
    private LocalDate dueDate;


    public Task(String title, LocalDate dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.status = TaskStatus.ACTIVE;
    }

    public void setStatus(TaskStatus status){
        this.status = status;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

}
