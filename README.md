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
4. After console exit, Swing GUI opens.

## Run Locally

### PowerShell (recommended on Windows)
```powershell
Set-Location "C:\Users\Rishu\Desktop\Project\Expense-Tracker\ExpenseTracker"
javac -d bin src\com\mycompany\expensetracker\*.java src\com\mycompany\expensetracker\dao\*.java src\com\mycompany\expensetracker\model\*.java src\com\mycompany\expensetracker\service\*.java src\com\mycompany\expensetracker\util\*.java
java -cp bin com.mycompany.expensetracker.Main
```

### Command Prompt (CMD)
```cmd
cd /d C:\Users\Rishu\Desktop\Project\Expense-Tracker\ExpenseTracker
javac -d bin src\com\mycompany\expensetracker\*.java src\com\mycompany\expensetracker\dao\*.java src\com\mycompany\expensetracker\model\*.java src\com\mycompany\expensetracker\service\*.java src\com\mycompany\expensetracker\util\*.java
java -cp bin com.mycompany.expensetracker.Main
```

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

## Troubleshooting
- If using PowerShell, do not use `&&` command chaining. Use `;`.
- Date input must be `YYYY-MM-DD`.
- Type should be `Expense` or `Income`.
- Category should match enum values (for consistency):
	`FOOD, BILLS, TRANSPORTATION, ENTERTAINMENT, SALARY, GIFTS, HEALTH, MISCELLANEOUS`

## Push To New GitHub Repo
```powershell
Set-Location "C:\Users\Rishu\Desktop\Project\Expense-Tracker\ExpenseTracker"
git remote set-url origin https://github.com/rishu072/Expense-Tracker-Finance-Manager.git
git add .
git commit -m "Fix runtime CSV handling and add complete README"
git push -u origin main
```
