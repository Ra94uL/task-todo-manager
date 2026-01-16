package taskmanager.ui;

import taskmanager.service.TaskManager;
import taskmanager.model.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // innan Singletorn
        //TaskManager taskManager = new TaskManager();

        // efter Singletorn
        TaskManager taskManager = TaskManager.getInstance();  // Hela programmet är kopplat till samma instans.

        while (true) {
            System.out.println("\n--- Task / Todo Manager ---");
            System.out.println("1. Skapa task");
            System.out.println("2. Visa aktiva tasks");
            System.out.println("3. Avsluta task");
            System.out.println("0. Avsluta program");
            System.out.print("Välj: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Titel: ");
                    String title = scanner.nextLine();

                    System.out.print("Slutdatum (YYYY-MM-DD) eller Enter för inget: ");
                    String dateInput = scanner.nextLine();

                    LocalDate dueDate = null;
                    if (!dateInput.isBlank()) {
                        dueDate = LocalDate.parse(dateInput);
                    }

                    taskManager.addTask(title, dueDate);
                    System.out.println("Task skapad.");
                }

                case "2" -> {
                    List<Task> tasks = taskManager.getActiveTasks();
                    if (tasks.isEmpty()) {
                        System.out.println("Inga aktiva tasks.");
                    } else {
                        int i = 1;
                        for (Task task : tasks) {
                            System.out.println(
                                    i + ". " + task.getTitle() +
                                            " | Status: " + task.getStatus() +
                                            " | Slutdatum: " + task.getDueDate()
                            );
                            i++;
                        }
                    }
                }

                case "3" -> {
                    System.out.print("Ange nummer på task att avsluta: ");
                    int number = Integer.parseInt(scanner.nextLine());

                    boolean ok = taskManager.closeActiveTask(number);
                    if (ok) {
                        System.out.println("Task avslutad.");
                    } else {
                        System.out.println("Fel nummer.");
                    }
                }

                case "0" -> {
                    System.out.println("Hej då!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Ogiltigt val.");
            }
        }
    }
}
