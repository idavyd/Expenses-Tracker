# Expense Tracker CLI

A command-line Expense Tracker built with **Java 21, JDBC, and SQLite**. This application allows users to add, list, update, delete, and summarize expenses directly from the terminal.

## Features

- Add new expenses with a description and amount.
- List all recorded expenses.
- Update an expense's description and/or amount.
- Delete expenses by ID.
- Calculate total expenses.
- Calculate expenses for a specific month and year.
- Store expense records in an SQLite database.

## Requirements

- Java 21 or later
- Maven
- SQLite JDBC driver

## Usage

Run the application using the following command format:

```bash
java -jar expense-tracker.jar <command> [arguments]
```

If running directly through IntelliJ, enter the command and its arguments in the **Program arguments** field.

### 1. Add an expense

Provide a description and an amount.

```bash
expense-tracker add "Lunch" 15.50
```

Arguments:
- `description` – Expense description.
- `amount` – Expense amount.

Descriptions containing spaces should be enclosed in quotes.

### 2. List expenses

Display all recorded expenses.

```bash
expense-tracker list
```

No additional arguments are required.

### 3. Summarize expenses

Calculate the total amount of all recorded expenses.

```bash
expense-tracker summary
```

You can also calculate expenses for a specific month and year:

```bash
expense-tracker summary 11 2026
```

Arguments:
- `month` – Month number (1–12).
- `year` – Four-digit year.

### 4. Delete an expense

Delete an expense using its ID.

```bash
expense-tracker delete 3
```

Arguments:
- `id` – ID of the expense to delete.

### 5. Update an expense

Update both the amount and description:

```bash
expense-tracker update 3 20.00 "Dinner"
```

Arguments:
- `id` – Expense ID.
- `amount` – New expense amount.
- `description` – New expense description.

You can also update individual fields separately.

**Update amount:**

```bash
expense-tracker update-amount 3 20.00
```

**Update description:**

```bash
expense-tracker update-description 3 "Dinner with friends"
```

## Database

The application uses SQLite to persist expense records in `expenses.db`.

Each expense contains:

| Column | Description |
|---|---|
| `id` | Unique expense identifier |
| `description` | Expense description |
| `amount` | Expense amount |
| `created_at` | Record creation timestamp |
| `updated_at` | Last modification timestamp |

The database table is initialized automatically when the application starts.

## Technologies

- Java
- JDBC
- SQLite
- Maven

## Future Improvements

- Improve CLI argument validation and error handling.
- Add expense filtering by date.
- Introduce categories for expenses.
- Improve reporting and summary functionality.


https://roadmap.sh/projects/expense-tracker