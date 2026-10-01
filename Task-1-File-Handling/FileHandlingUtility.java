import java.io.*;
import java.util.Scanner;

public class FileHandlingUtility {

    private static final String FILE_NAME = "sample.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        System.out.println("======================================");
        System.out.println("       JAVA FILE HANDLING UTILITY");
        System.out.println("======================================");

        do {
            displayMenu();

            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        createFile();
                        break;

                    case 2:
                        writeToFile();
                        break;

                    case 3:
                        readFile();
                        break;

                    case 4:
                        modifyFile();
                        break;

                    case 5:
                        deleteFile();
                        break;

                    case 6:
                        displayFileInformation();
                        break;

                    case 7:
                        System.out.println("\nExiting application...");
                        break;

                    default:
                        System.out.println("\nInvalid choice. Please select 1-7.");
                }

            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number.");
                choice = 0;
            }

        } while (choice != 7);

        scanner.close();
    }

    // Displays the main menu
    private static void displayMenu() {

        System.out.println("\n--------------- MENU ---------------");
        System.out.println("1. Create Text File");
        System.out.println("2. Write to File");
        System.out.println("3. Read File");
        System.out.println("4. Modify File");
        System.out.println("5. Delete File");
        System.out.println("6. Display File Information");
        System.out.println("7. Exit");
        System.out.println("------------------------------------");
    }

    // Creates a new text file
    private static void createFile() {

        File file = new File(FILE_NAME);

        try {

            if (file.createNewFile()) {
                System.out.println("\nFile created successfully.");
                System.out.println("File name: " + FILE_NAME);
            } else {
                System.out.println("\nFile already exists.");
            }

        } catch (IOException e) {
            System.out.println("\nError while creating the file.");
            System.out.println("Reason: " + e.getMessage());
        }
    }

    // Writes new content to the file
    private static void writeToFile() {

        System.out.print("\nEnter text to write into the file: ");
        String text = scanner.nextLine();

        try (FileWriter writer = new FileWriter(FILE_NAME)) {

            writer.write(text);

            System.out.println("\nData written successfully.");

        } catch (IOException e) {

            System.out.println("\nError while writing to the file.");
            System.out.println("Reason: " + e.getMessage());
        }
    }

    // Reads and displays the contents of the file
    private static void readFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("\nFile does not exist.");
            return;
        }

        System.out.println("\n----------- FILE CONTENT -----------");

        try (BufferedReader reader = new BufferedReader(
                new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("------------------------------------");

        } catch (IOException e) {

            System.out.println("\nError while reading the file.");
            System.out.println("Reason: " + e.getMessage());
        }
    }

    // Adds new content to an existing file
    private static void modifyFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("\nFile does not exist.");
            return;
        }

        System.out.print("\nEnter additional text: ");
        String additionalText = scanner.nextLine();

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            writer.write(System.lineSeparator());
            writer.write(additionalText);

            System.out.println("\nFile modified successfully.");

        } catch (IOException e) {

            System.out.println("\nError while modifying the file.");
            System.out.println("Reason: " + e.getMessage());
        }
    }

    // Deletes the text file
    private static void deleteFile() {

        File file = new File(FILE_NAME);

        if (file.exists()) {

            if (file.delete()) {
                System.out.println("\nFile deleted successfully.");
            } else {
                System.out.println("\nUnable to delete the file.");
            }

        } else {
            System.out.println("\nFile does not exist.");
        }
    }

    // Displays information about the file
    private static void displayFileInformation() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("\nFile does not exist.");
            return;
        }

        System.out.println("\n----------- FILE INFORMATION -----------");
        System.out.println("File Name: " + file.getName());
        System.out.println("File Path: " + file.getAbsolutePath());
        System.out.println("File Size: " + file.length() + " bytes");
        System.out.println("Readable: " + file.canRead());
        System.out.println("Writable: " + file.canWrite());
        System.out.println("----------------------------------------");
    }
}
