package gradebook;

import java.util.ArrayList;
import java.util.Scanner;

public class GradeBookApp {
    
    public static void main(String[] args) {
        // Initialize required data structures
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> studentNames = new ArrayList<>();
        String[] assignmentTypes = {"Homework", "Project", "Exam"};
        double[][] gradebook = new double[1][assignmentTypes.length];
        
        int choice;
        do {
            // Display menu and get user choice
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            // Process user choice
            switch (choice) {
                case 1:
                    gradebook = addStudent(gradebook, studentNames, scanner);
                    break;
                case 2:
                    enterGrades(gradebook, studentNames, assignmentTypes, scanner);
                    break;
                case 3:
                    viewAllGrades(gradebook, studentNames, assignmentTypes);
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
        
        scanner.close();
    }
    
    /**
     * Displays the main menu options
     */
    public static void displayMenu() {
        System.out.println("\n--- Gradebook Menu ---");
        System.out.println("1. Add a student");
        System.out.println("2. Enter grades for a student");
        System.out.println("3. View all grades");
        System.out.println("4. Exit");
        System.out.print("Enter choice: ");
    }
    
    /**
     * Adds a new student to the gradebook
     * @param currentGradebook The current 2D array of grades
     * @param names List of student names
     * @param sc Scanner object for input
     * @return The updated gradebook with space for the new student
     */
    public static double[][] addStudent(double[][] currentGradebook, ArrayList<String> names, Scanner sc) {
        // Create a new gradebook with one additional row
        double[][] newGradebook = new double[currentGradebook.length + 1][currentGradebook[0].length];
        
        // Copy existing grades to the new gradebook
        for (int i = 0; i < currentGradebook.length; i++) {
            for (int j = 0; j < currentGradebook[0].length; j++) {
                newGradebook[i][j] = currentGradebook[i][j];
            }
        }
        
        // Get new student name and add to list
        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        names.add(name);
        System.out.println("Student added!");
        
        return newGradebook;
    }
    
    /**
     * Enters grades for a selected student
     * @param gradebook The 2D array of grades
     * @param names List of student names
     * @param assignments Array of assignment types
     * @param sc Scanner object for input
     */
    public static void enterGrades(double[][] gradebook, ArrayList<String> names, String[] assignments, Scanner sc) {
        // Check if there are any students
        if (names.isEmpty()) {
            System.out.println("No students available. Add a student first.");
            return;
        }
        
        // Display available students
        System.out.println("Available students:");
        for (int i = 0; i < names.size(); i++) {
            System.out.println((i + 1) + ". " + names.get(i));
        }
        
        // Get student selection
        System.out.print("Select a student: ");
        int studentNumber = sc.nextInt();
        
        // Validate student number
        if (studentNumber < 1 || studentNumber > names.size()) {
            System.out.println("Invalid student number.");
            return;
        }
        
        // Get grades for each assignment type
        int studentIndex = studentNumber - 1;
        for (int j = 0; j < assignments.length; j++) {
            System.out.print("Enter grade for " + assignments[j] + ": ");
            double grade = sc.nextDouble();
            gradebook[studentIndex][j] = grade;
        }
        
        sc.nextLine(); // Consume newline
    }
    
    /**
     * Displays all grades and calculates averages
     * @param gradebook The 2D array of grades
     * @param names List of student names
     * @param assignments Array of assignment types
     */
    public static void viewAllGrades(double[][] gradebook, ArrayList<String> names, String[] assignments) {
        // Check if there are any grades to display
        if (names.isEmpty()) {
            System.out.println("No grades to display.");
            return;
        }
        
        // Print header
        System.out.println("\n--- Gradebook ---");
        System.out.printf("%-20s", "Student");
        for (String assignment : assignments) {
            System.out.printf("%8s", assignment);
        }
        System.out.printf("%8s\n", "Average");
        
        // Print each student's grades and average
        for (int i = 0; i < names.size(); i++) {
            double sum = 0;
            
            // Print student name
            System.out.printf("%-20s", names.get(i));
            
            // Print grades and calculate sum
            for (int j = 0; j < assignments.length; j++) {
                System.out.printf("%8.2f", gradebook[i][j]);
                sum += gradebook[i][j];
            }
            
            // Calculate and print average
            double average = sum / assignments.length;
            System.out.printf("%8.2f\n", average);
        }
    }
}
