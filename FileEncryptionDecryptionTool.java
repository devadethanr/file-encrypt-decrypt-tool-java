import java.io.*;
import java.util.Scanner;

public class FileEncryptionDecryptionTool {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("File Encryption/Decryption Tool");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Exit");

            System.out.print("Choose an option (1, 2, or 3): ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            if (option == 1) {
                performEncryption(scanner);
            } else if (option == 2) {
                performDecryption(scanner);
            } else if (option == 3) {
                System.out.println("Exiting the program. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    private static void performEncryption(Scanner scanner) {
        System.out.println("\nEncryption Selected");
        String inputFilePath = getFilePath("Enter the path of the input file: ", scanner);
        String outputFilePath = getFilePath("Enter the path of the output file: ", scanner);
        int key = getKey("Enter the encryption key (an integer): ", scanner);

        try {
            String fileContent = readFile(inputFilePath);
            String encryptedContent = encrypt(fileContent, key);
            writeFile(outputFilePath, encryptedContent);

            System.out.println("Encryption successful. Encrypted file saved at: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void performDecryption(Scanner scanner) {
        System.out.println("\nDecryption Selected");
        String inputFilePath = getFilePath("Enter the path of the input file: ", scanner);
        String outputFilePath = getFilePath("Enter the path of the output file: ", scanner);
        int key = getKey("Enter the decryption key (an integer): ", scanner);

        try {
            String fileContent = readFile(inputFilePath);
            String decryptedContent = decrypt(fileContent, key);
            writeFile(outputFilePath, decryptedContent);

            System.out.println("Decryption successful. Decrypted file saved at: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String getFilePath(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getKey(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    private static String readFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        }
    }

    private static void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }

    private static String encrypt(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char shifted = (char) (((character - 'a' + key) % 26) + 'a');
                encryptedText.append(shifted);
            } else {
                encryptedText.append(character);
            }
        }
        return encryptedText.toString();
    }

    private static String decrypt(String text, int key) {
        return encrypt(text, 26 - key); // Decryption is the same as encryption with the inverse key
    }
}
