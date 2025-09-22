package com.mycompany.expensetracker.dao;

import com.mycompany.expensetracker.model.Transaction;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private static final String FILE_PATH = "src/resources/data/transactions.csv";

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                LocalDate date = LocalDate.parse(data[1]);
                String description = data[2];
                double amount = Double.parseDouble(data[3]);
                String type = data[4];
                String category = data[5];
                transactions.add(new Transaction(id, date, description, amount, type, category));
            }
        } catch (IOException e) {
            System.err.println("Error reading transactions file: " + e.getMessage());
        }
        return transactions;
    }

    public void saveTransaction(Transaction transaction) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
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