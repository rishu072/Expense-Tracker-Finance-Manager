package com.mycompany.expensetracker.dao;

import com.mycompany.expensetracker.model.Transaction;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private static final String SOURCE_FILE_PATH = "src/resources/data/transactions.csv";
    private static final String BIN_FILE_PATH = "bin/resources/data/transactions.csv";

    private Path resolveFilePath() {
        Path srcPath = Paths.get(SOURCE_FILE_PATH);
        if (Files.exists(srcPath)) {
            return srcPath;
        }

        Path binPath = Paths.get(BIN_FILE_PATH);
        if (Files.exists(binPath)) {
            return binPath;
        }

        try {
            Path parent = srcPath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            if (!Files.exists(srcPath)) {
                Files.createFile(srcPath);
            }
        } catch (IOException e) {
            System.err.println("Could not create transactions file: " + e.getMessage());
        }

        return srcPath;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Path filePath = resolveFilePath();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] data = line.split(",");
                if (data.length < 6) {
                    System.err.println("Skipping invalid transaction row " + lineNumber + ": " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(data[0]);
                    LocalDate date = LocalDate.parse(data[1]);
                    String description = data[2];
                    double amount = Double.parseDouble(data[3]);
                    String type = data[4];
                    String category = data[5];
                    transactions.add(new Transaction(id, date, description, amount, type, category));
                } catch (RuntimeException e) {
                    System.err.println("Skipping malformed transaction row " + lineNumber + ": " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transactions file: " + e.getMessage());
        }
        return transactions;
    }

    public void saveTransaction(Transaction transaction) {
        Path filePath = resolveFilePath();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            bw.write(String.format("%d,%s,%s,%.2f,%s,%s%n",
                    transaction.getId(),
                    transaction.getDate(),
                    transaction.getDescription().replaceAll(",", ""), // Sanitize commas
                    transaction.getAmount(),
                    transaction.getType(),
                    transaction.getCategory()));
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }

    public int getNextId() {
        List<Transaction> transactions = getAllTransactions();
        if (transactions.isEmpty()) {
            return 1;
        }
        return transactions.stream().mapToInt(Transaction::getId).max().getAsInt() + 1;
    }
}