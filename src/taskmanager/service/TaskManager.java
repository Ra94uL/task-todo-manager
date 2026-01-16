package taskmanager.service;

import taskmanager.model.Task;
import taskmanager.model.TaskStatus;
import taskmanager.model.TodoList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private static final Path DATA_FILE = Path.of("data.txt");
    private TodoList todoList;
    private static TaskManager instance;  // Singletorn
    private final TaskFactory taskFactory = new TaskFactory();  // Factory



    // Singletorn
    public static TaskManager getInstance() {
        if (instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    private TaskManager(){
        this.todoList = new TodoList("Mina tasks", TaskStatus.ACTIVE);
        loadFromFile();
    }

    public void addTask(String title, LocalDate dueDate){
        //Task task = new Task(title,dueDate);  // före Facktory

        // Facktory
        Task task = taskFactory.create(title, dueDate);

        todoList.getTasks().add(task);
        saveToFile();
    }

    public boolean closeActiveTask(int taskNumber) {
        int index = taskNumber - 1;

        List<Task> active = getActiveTasks();
        if (index < 0 || index >= active.size()) {
            return false;
        }

        Task task = active.get(index);
        task.setStatus(TaskStatus.DONE);
        saveToFile();
        return true;
    }

    public List<Task> getActiveTasks() {

        List<Task> activeTasks = new ArrayList<>();

        for (Task task : todoList.getTasks()) {
            if (task.getStatus() == TaskStatus.ACTIVE ){
                activeTasks.add(task);
            }
        }

        return activeTasks;
    }

    private void saveToFile(){
        List<String> lines = new ArrayList<>();

        for (Task task : todoList.getTasks()){
            String dueDate = (task.getDueDate() == null)? "" : task.getDueDate().toString();
            lines.add(task.getTitle() + "|" + task.getStatus() + "|" + dueDate);
        }

        try {
            Files.write(DATA_FILE, lines);
        }catch (IOException e){
            IO.println("Kunde inte spara data.txt: " + e.getMessage());
        }
    }

    private void loadFromFile(){
        if (!Files.exists(DATA_FILE)){
            return;
        }

        try{
            List<String> lines = Files.readAllLines(DATA_FILE);
            for (String line : lines){
                if (line.isBlank()){
                    continue;
                }
                String [] parts = line.split("\\|",-1);
                if (parts.length < 3){
                    continue;
                }
                String title = parts[0].trim();
                TaskStatus status = TaskStatus.valueOf(parts[1].trim());
                String dueDateText = parts[2].trim();
                LocalDate dueDate = dueDateText.isEmpty() ? null : LocalDate.parse(dueDateText);
                Task task = new Task(title, dueDate);
                task.setStatus(status);
                todoList.getTasks().add(task);


        }

        }catch (IOException e){
            IO.println(("Kunde inte läsa data.txt: " + e.getMessage()));
        }
    }

}
