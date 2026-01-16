package taskmanager.service;

import taskmanager.model.Task;

import java.time.LocalDate;

public class TaskFactory {

    public Task create(String title, LocalDate dueDate){
        return new Task(title, dueDate);
    }
}
