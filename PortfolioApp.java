package investments;

import java.io.*;
import java.util.*;

public class PortfolioApp {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        process(sc);
        sc.close();
    }

    public static void process(Scanner sc) {
        try {
            // Step 1: Ask user for filenames
            System.out.print("Enter input filename: ");
            String inputFile = sc.nextLine();
            System.out.print("Enter error filename: ");
            String errorFile = sc.nextLine();
            System.out.print("Enter valid filename: ");
            String validFile = sc.nextLine();

            Scanner fileScanner = new Scanner(new File("files/" + inputFile));
            ArrayList<String> errors = new ArrayList<>();
            ArrayList<String> cleanData = new ArrayList<>();
            
            int lineNum = 0;
            while (fileScanner.hasNextLine()) {
                lineNum++;
                String line = fileScanner.nextLine();
                Scanner lineScan = new Scanner(line);

                boolean hasError = false;
                ArrayList<String> tokens = new ArrayList<>();
                int pos = 0;

                // Expect: int, string, int, double, boolean
                if (lineScan.hasNextInt()) {
                    tokens.add(String.valueOf(lineScan.nextInt()));
                } else {
                    errors.add(buildError(lineNum, ++pos, lineScan.next()));
                    hasError = true;
                }

                pos++;
                if (lineScan.hasNext()) {
                    tokens.add(lineScan.next());
                } else {
                    errors.add(buildError(lineNum, pos, "MISSING"));
                    hasError = true;
                }

                pos++;
                if (lineScan.hasNextInt()) {
                    tokens.add(String.valueOf(lineScan.nextInt()));
                } else {
                    errors.add(buildError(lineNum, pos, lineScan.next()));
                    hasError = true;
                }

                pos++;
                if (lineScan.hasNextDouble()) {
                    tokens.add(String.format("%.6f", lineScan.nextDouble()));
                } else {
                    errors.add(buildError(lineNum, pos, lineScan.next()));
                    hasError = true;
                }

                pos++;
                if (lineScan.hasNextBoolean()) {
                    tokens.add(lineScan.next().toUpperCase());
                } else {
                    errors.add(buildError(lineNum, pos, lineScan.next()));
                    hasError = true;
                }

                // If no errors, build CSV line
                if (!hasError) {
                    String csvLine = String.join(",", tokens);
                    cleanData.add(csvLine);
                }

                lineScan.close();
            }

            fileScanner.close();

            // Step 2: Write results
            printToFile(errors, errorFile);
            printToFile(cleanData, validFile);

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    // Error builder
    public static String buildError(int lineNum, int pos, String value) {
        return String.format("Error: Line #%d, Position %d, String Found - %s", lineNum, pos, value);
    }

    // Generic print method
    public static void printToFile(ArrayList<String> list, String filename) throws IOException {
        PrintWriter writer = new PrintWriter(new FileOutputStream("files/" + filename));
        for (String line : list) {
            writer.println(line);
        }
        writer.close();
    }
}
