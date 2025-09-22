package com.mycompany.expensetracker;

import com.mycompany.expensetracker.service.ExpenseService;
import com.mycompany.expensetracker.model.Transaction;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ExpenseService expenseService = new ExpenseService();

    public static void main(String[] args) {
        System.out.println("Welcome to Personal Finance Tracker!");
        boolean running = true;
        while (running) {
            displayMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

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
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        scanner.close();
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run(){
            new TrackerGUI().setVisible(true);
           } 
        });
    }

    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add a new transaction");
        System.out.println("2. View all transactions");
        System.out.println("3. View summary");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addTransaction() {
        System.out.println("\n--- Add New Transaction ---");
        System.out.print("Enter date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter type (Expense/Income): ");
        String type = scanner.nextLine();
        expenseService.printAvailableCategories();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        expenseService.addTransaction(date, description, amount, type, category);
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