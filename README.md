# Expense Tracker Finance Manager

Simple Java Personal Finance Tracker with:
- Console menu (add transaction, view all, view summary)
- Swing GUI for adding and viewing transactions
- CSV-based local persistence

## Tech Stack
- Java (JDK 8+)
- Standard Java libraries only (no external dependencies)

## Project Structure
```
ExpenseTracker/
	src/
		com/mycompany/expensetracker/
			Main.java
			TrackerGUI.java
			dao/TransactionDAO.java
			model/Transaction.java
			model/Category.java
			service/ExpenseService.java
		resources/data/transactions.csv
	bin/
```

## How It Works
1. `Main` starts the console menu.
2. Data operations go through `ExpenseService`.
3. `TransactionDAO` reads/writes CSV data.
4. Transactions are saved to `src/resources/data/transactions.csv`.


## CSV Format
File: `src/resources/data/transactions.csv`

Each row:
```text
id,date,description,amount,type,category
```

Example:
```text
1,2025-09-22,Ice Cream,200.00,Expense,FOOD
```
<img width="982" height="735" alt="image" src="https://github.com/user-attachments/assets/5deb0abd-923b-49a7-bf0e-c92d8ffa5840" />

## Troubleshooting
- If using PowerShell, do not use `&&` command chaining. Use `;`.
- If PowerShell blocks script execution, run this once in the same terminal:
  `Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass`
- Date input must be `YYYY-MM-DD`.
- Type should be `Expense` or `Income`.
- Category should match enum values (for consistency):
	`FOOD, BILLS, TRANSPORTATION, ENTERTAINMENT, SALARY, GIFTS, HEALTH, MISCELLANEOUS`

