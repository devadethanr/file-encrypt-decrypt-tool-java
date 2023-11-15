import java.io.*;
import java.util.Scanner;

public class FileEncryptionDecryptionTool {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("File Encryption/Decryption Tool");

        // Get the input file path
        System.out.print("Enter the path of the input file: ");
        String inputFilePath = scanner.nextLine();

        // Get the output file path
        System.out.print("Enter the path of the output file: ");
        String outputFilePath = scanner.nextLine();

        // Get the encryption key
        System.out.print("Enter the encryption key (an integer): ");
        int encryptionKey = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            // Read the content of the input file
            String fileContent = readFile(inputFilePath);

            // Encrypt the content
            String encryptedContent = encrypt(fileContent, encryptionKey);

            // Write the encrypted content to the output file
            writeFile(outputFilePath, encryptedContent);

            System.out.println("Encryption successful. Encrypted file saved at: " + outputFilePath);

            // Decryption (Optional)
            System.out.print("Do you want to decrypt the file? (yes/no): ");
            String decryptChoice = scanner.nextLine().toLowerCase();

            if (decryptChoice.equals("yes")) {
                // Read the encrypted content from the output file
                String encryptedFileContent = readFile(outputFilePath);

                // Decrypt the content
                String decryptedContent = decrypt(encryptedFileContent, encryptionKey);

                // Display the decrypted content
                System.out.println("Decrypted Content:\n" + decryptedContent);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
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

    private static String decrypt(String encryptedText, int key) {
        return encrypt(encryptedText, 26 - key); // Decryption is the same as encryption with the inverse key
    }
}
