package com.mycompany.expensetracker.model;

import java.time.LocalDate;
import java.util.Objects;

public class Transaction {

    private int id;
    private LocalDate date;
    private String description;
    private double amount;
    private String type; // "Expense" or "Income"
    private String category; // e.g., "Food", "Bills"

    public Transaction(int id, LocalDate date, String description, double amount, String type, String category) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.category = category;
    }

    // Getters and Setters (to access and modify private fields)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // toString() method for easy printing
    @Override
    public String toString() {
        return String.format("ID: %d | Date: %s | Description: %-20s | Amount: %.2f | Type: %-8s | Category: %s",
                id, date, description, amount, type, category);
    }
    
    // equals() and hashCode() for object comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}