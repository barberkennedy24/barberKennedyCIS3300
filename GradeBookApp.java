package gradebook;

import java.util.ArrayList;
import java.util.Scanner;

public class GradeBookApp {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> studentNames = new ArrayList<>();
        String[] assignmentTypes = {"Homework", "Project", "Exam"};
        double[][] gradebook = new double[1][assignmentTypes.length];
        
        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            
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
  
    public static void displayMenu() {
        System.out.println("\n--- Gradebook Menu ---");
        System.out.println("1. Add a student");
        System.out.println("2. Enter grades for a student");
        System.out.println("3. View all grades");
        System.out.println("4. Exit");
        System.out.print("Enter choice: ");
    }
    
    public static double[][] addStudent(double[][] currentGradebook, ArrayList<String> names, Scanner sc) {
        double[][] newGradebook = new double[currentGradebook.length + 1][currentGradebook[0].length];
        
        for (int i = 0; i < currentGradebook.length; i++) {
            for (int j = 0; j < currentGradebook[0].length; j++) {
                newGradebook[i][j] = currentGradebook[i][j];
            }
        }
        
        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        names.add(name);
        System.out.println("Student added!");
        
        return newGradebook;
    }
    
    public static void enterGrades(double[][] gradebook, ArrayList<String> names, String[] assignments, Scanner sc) {
        if (names.isEmpty()) {
            System.out.println("No students available. Add a student first.");
            return;
        }
        
        System.out.println("Available students:");
        for (int i = 0; i < names.size(); i++) {
            System.out.println((i + 1) + ". " + names.get(i));
        }
        
        System.out.print("Select a student: ");
        int studentNumber = sc.nextInt();
        
        if (studentNumber < 1 || studentNumber > names.size()) {
            System.out.println("Invalid student number.");
            return;
        }
        
        int studentIndex = studentNumber - 1;
        for (int j = 0; j < assignments.length; j++) {
            System.out.print("Enter grade for " + assignments[j] + ": ");
            double grade = sc.nextDouble();
            gradebook[studentIndex][j] = grade;
        }
        
        sc.nextLine(); 
    }
    
    public static void viewAllGrades(double[][] gradebook, ArrayList<String> names, String[] assignments) {
        if (names.isEmpty()) {
            System.out.println("No grades to display.");
            return;
        }
        
        System.out.println("\n--- Gradebook ---");
        System.out.printf("%-20s", "Student");
        for (String assignment : assignments) {
            System.out.printf("%8s", assignment);
        }
        System.out.printf("%8s\n", "Average");
        
        for (int i = 0; i < names.size(); i++) {
            double sum = 0;
            
            System.out.printf("%-20s", names.get(i));
            
            for (int j = 0; j < assignments.length; j++) {
                System.out.printf("%8.2f", gradebook[i][j]);
                sum += gradebook[i][j];
            }
            
            double average = sum / assignments.length;
            System.out.printf("%8.2f\n", average);
        }
    }
}

