package com.mycompany.expensetracker.service;

import com.mycompany.expensetracker.dao.TransactionDAO;
import com.mycompany.expensetracker.model.Category;
import com.mycompany.expensetracker.model.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseService {

    private TransactionDAO transactionDAO;

    public ExpenseService() {
        this.transactionDAO = new TransactionDAO();
    }

    public void addTransaction(LocalDate date, String description, double amount, String type, String category) {
        int nextId = transactionDAO.getNextId();
        Transaction newTransaction = new Transaction(nextId, date, description, amount, type, category);
        transactionDAO.saveTransaction(newTransaction);
        System.out.println("Transaction added successfully!");
    }

    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    public List<Transaction> getTransactionsByType(String type) {
        return getAllTransactions().stream()
                .filter(t -> t.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByCategory(String category) {
        return getAllTransactions().stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public double calculateTotalAmountByType(String type) {
        return getAllTransactions().stream()
                .filter(t -> t.getType().equalsIgnoreCase(type))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    public void printAvailableCategories() {
        System.out.println("Available Categories:");
        for (Category cat : Category.values()) {
            System.out.println("- " + cat);
        }
    }
}