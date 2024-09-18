
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Timesheet {
    private Long id;
    private String employeeName;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Timesheet(Long id, String employeeName, LocalDateTime entryTime, LocalDateTime exitTime) {
        this.id = id;
        this.employeeName = employeeName;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Employee: " + employeeName + ", Entry: " + entryTime +
               ", Exit: " + (exitTime != null ? exitTime : "Still working");
    }
}

public class TimesheetApp {

    private static List<Timesheet> timesheets = new ArrayList<>();
    private static long idCounter = 1;

    public static Timesheet logEntry(String employeeName) {
        Timesheet timesheet = new Timesheet(idCounter++, employeeName, LocalDateTime.now(), null);
        timesheets.add(timesheet);
        return timesheet;
    }

    public static Timesheet logExit(Long id) {
        for (Timesheet timesheet : timesheets) {
            if (timesheet.getId().equals(id) && timesheet.getExitTime() == null) {
                timesheet.setExitTime(LocalDateTime.now());
                return timesheet;
            }
        }
        return null; // If timesheet not found or exit already logged
    }

    public static void displayAllTimesheets() {
        if (timesheets.isEmpty()) {
            System.out.println("No timesheets found.");
        } else {
            for (Timesheet timesheet : timesheets) {
                System.out.println(timesheet);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an option: ");
            System.out.println("1. Log Entry");
            System.out.println("2. Log Exit");
            System.out.println("3. View All Timesheets");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee Name: ");
                    String employeeName = scanner.nextLine();
                    Timesheet entry = logEntry(employeeName);
                    System.out.println("Entry logged for " + entry.getEmployeeName() + " at " + entry.getEntryTime());
                    break;
                case 2:
                    System.out.print("Enter Timesheet ID to log exit: ");
                    Long id = scanner.nextLong();
                    Timesheet exit = logExit(id);
                    if (exit != null) {
                        System.out.println("Exit logged for " + exit.getEmployeeName() + " at " + exit.getExitTime());
                    } else {
                        System.out.println("Timesheet ID not found or exit already logged.");
                    }
                    break;
                case 3:
                    displayAllTimesheets();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
