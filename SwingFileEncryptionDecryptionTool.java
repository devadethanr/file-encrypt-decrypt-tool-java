import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SwingFileEncryptionDecryptionTool {

    private static JFrame frame;
    private static JTextField inputFileTextField, outputFileTextField, keyTextField;
    private static JTextArea resultTextArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        frame = new JFrame("File Encryption/Decryption Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        createComponents();

        frame.setVisible(true);
    }

    private static void createComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Input File Path:"));
        inputFileTextField = new JTextField();
        panel.add(inputFileTextField);

        panel.add(new JLabel("Output File Path:"));
        outputFileTextField = new JTextField();
        panel.add(outputFileTextField);

        panel.add(new JLabel("Encryption/Decryption Key:"));
        keyTextField = new JTextField();
        panel.add(keyTextField);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performEncryption();
            }
        });
        panel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDecryption();
            }
        });
        panel.add(decryptButton);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private static void performEncryption() {
        String inputFilePath = inputFileTextField.getText();
        String outputFilePath = outputFileTextField.getText();
        String keyText = keyTextField.getText();

        try {
            int key = Integer.parseInt(keyText);
            String fileContent = readFile(inputFilePath);
            String encryptedContent = encrypt(fileContent, key);
            writeFile(outputFilePath, encryptedContent);

            resultTextArea.setText("Encryption successful. Encrypted file saved at: " + outputFilePath);
        } catch (NumberFormatException | IOException ex) {
            resultTextArea.setText("Error: " + ex.getMessage());
        }
    }

    private static void performDecryption() {
        String inputFilePath = inputFileTextField.getText();
        String outputFilePath = outputFileTextField.getText();
        String keyText = keyTextField.getText();

        try {
            int key = Integer.parseInt(keyText);
            String fileContent = readFile(inputFilePath);
            String decryptedContent = decrypt(fileContent, key);
            writeFile(outputFilePath, decryptedContent);

            resultTextArea.setText("Decryption successful. Decrypted file saved at: " + outputFilePath);
        } catch (NumberFormatException | IOException ex) {
            resultTextArea.setText("Error: " + ex.getMessage());
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

    private static String decrypt(String text, int key) {
        return encrypt(text, 26 - key); // Decryption is the same as encryption with the inverse key
    }
}
