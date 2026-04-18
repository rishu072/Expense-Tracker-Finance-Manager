package com.mycompany.expensetracker;

import com.mycompany.expensetracker.model.Category;
import com.mycompany.expensetracker.service.ExpenseService;
import com.mycompany.expensetracker.model.Transaction;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ExpenseService expenseService = new ExpenseService();

    public static void main(String[] args) {
        System.out.println("Welcome to Personal Finance Tracker!");
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    addTransaction();
                    break;
                case 2:
                    viewAllTransactions();
                    break;
                case 3:
                    viewSummary();
                    break;
                case 4:
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add a new transaction");
        System.out.println("2. View all transactions");
        System.out.println("3. View summary");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int readMenuChoice() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addTransaction() {
        System.out.println("\n--- Add New Transaction ---");

        LocalDate date = readDate();

        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        while (description.isEmpty()) {
            System.out.print("Description cannot be empty. Enter description: ");
            description = scanner.nextLine().trim();
        }

        double amount = readAmount();
        String type = readType();

        expenseService.printAvailableCategories();
        String category = readCategory();

        expenseService.addTransaction(date, description, amount, type, category);
    }

    private static LocalDate readDate() {
        while (true) {
            System.out.print("Enter date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private static double readAmount() {
        while (true) {
            System.out.print("Enter amount: ");
            String input = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    System.out.println("Amount must be greater than 0.");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
    }

    private static String readType() {
        while (true) {
            System.out.print("Enter type (Expense/Income): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Expense")) {
                return "Expense";
            }
            if (input.equalsIgnoreCase("Income")) {
                return "Income";
            }
            System.out.println("Invalid type. Enter only Expense or Income.");
        }
    }

    private static String readCategory() {
        while (true) {
            System.out.print("Enter category: ");
            String input = scanner.nextLine().trim();
            try {
                return Category.valueOf(input.toUpperCase()).name();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid category. Enter one from the list shown above.");
            }
        }
    }

    private static void viewAllTransactions() {
        System.out.println("\n--- All Transactions ---");
        List<Transaction> transactions = expenseService.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            transactions.forEach(System.out::println);
        }
    }

    private static void viewSummary() {
        System.out.println("\n--- Financial Summary ---");
        double totalExpenses = expenseService.calculateTotalAmountByType("Expense");
        double totalIncome = expenseService.calculateTotalAmountByType("Income");
        double netBalance = totalIncome - totalExpenses;

        System.out.printf("Total Income: $%.2f%n", totalIncome);
        System.out.printf("Total Expenses: $%.2f%n", totalExpenses);
        System.out.printf("Net Balance: $%.2f%n", netBalance);
    }
}