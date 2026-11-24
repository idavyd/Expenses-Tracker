package dbmanager;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.sql.*;


public class DBConnector {

    private final String url = "jdbc:sqlite:expenses.db";

    public DBConnector() throws SQLException {
        this.createTable();

    }

    private Connection connectDB() throws SQLException {
        return DriverManager.getConnection(this.url);
    }

    private void createTable() throws SQLException {
        String CREATE_SQL =
                """

                        CREATE TABLE IF NOT EXISTS expenses (
            id INTEGER PRIMARY KEY,
            description TEXT NOT NULL,
            amount NUMERIC NOT NULL,
            created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
            updated_at TEXT
        )
        """;

        try (Connection conn = this.connectDB();
             Statement statement = conn.createStatement()) {

            statement.executeUpdate(CREATE_SQL);
        }
    }

    public void addExpense(String description, BigDecimal amount) throws SQLException {
        String INSERT_SQL = """
                INSERT INTO expenses (description, amount)
                VALUES (?, ?)
                RETURNING *
                """;

        try (Connection conn = this.connectDB();
             PreparedStatement statement = conn.prepareStatement(INSERT_SQL)) {

            statement.setString(1, description);
            statement.setBigDecimal(2, amount);

            // Why would I do extra try here ? My connection is already in try
            try (ResultSet rs = statement.executeQuery()) {
                System.out.printf("Expense added successfully (ID: %d)\n", rs.getInt("id"));
            }

        }
    }

    public void listExpenses() throws SQLException {
        String SELECT_SQL = """
                SELECT * FROM expenses
                """;
        try (Connection conn = this.connectDB();
             PreparedStatement statement = conn.prepareStatement(SELECT_SQL)) {
            ResultSet rs = statement.executeQuery();


            System.out.printf("%-5s %-20s %-10s %s%n", "ID", "Description", "Amount", "Created At");
            boolean hasRecords = false;
            while(rs.next()){
                hasRecords = true;

                System.out.printf("%-5s %-20s %-10s %s%n",
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getBigDecimal("amount"),
                        rs.getString("created_at"));
            }
            if (!hasRecords) {
                System.out.printf("%40s%n", "<< No records in Expenses Table >>");
            }
    }
    }

    public void delete() throws SQLException {
        String DELETE_SQL = """
                DELETE FROM expenses
                """;
        try (Connection conn = this.connectDB();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(DELETE_SQL);
            {
            }
        }


    }

    public void updateExpense(int id, BigDecimal amount) throws SQLException {
        String UPDATE_SQL = """
                UPDATE expenses SET amount = ? WHERE id = ?
                """;

        try (Connection conn = this.connectDB();
             PreparedStatement statement = conn.prepareStatement(UPDATE_SQL)) {

            statement.setBigDecimal(1, amount);
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.printf("Expense with ID %d successfully updated.%n", id);
            } else {
                System.out.printf("No expense found with ID %d.%n", id);
            }
        }
    }

    public void updateExpense(int id, String description) throws SQLException {
        String UPDATE_SQL = """
                UPDATE expenses SET description = ? WHERE id = ?
                """;

        try (Connection conn = this.connectDB();
             PreparedStatement statement = conn.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, description);
            statement.setInt(2, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.printf("Expense with ID %d successfully updated.%n", id);
            } else {
                System.out.printf("No expense found with ID %d.%n", id);
            }
        }
    }

    public void updateExpense(int id, BigDecimal amount, String description) throws SQLException {
        String UPDATE_SQL = """
                UPDATE expenses SET amount = ?, description = ? WHERE id = ?
                """;

        try (Connection conn = this.connectDB();
             PreparedStatement statement = conn.prepareStatement(UPDATE_SQL)) {

            statement.setBigDecimal(1, amount);
            statement.setString(2, description);
            statement.setInt(3, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.printf("Expense with ID %d successfully updated.%n", id);
            } else {
                System.out.printf("No expense found with ID %d.%n", id);
            }
        }
    }


    public void deleteExpense(int id) throws SQLException {
        String DELETE_SQL = """
                DELETE FROM expenses WHERE id = ?
                """;

        try (Connection conn = this.connectDB();
             PreparedStatement statement = conn.prepareStatement(DELETE_SQL)) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.printf("Expense with ID %d successfully deleted.%n", id);
            } else {
                System.out.printf("No expense found with ID %d.%n", id);
            }
        }
    }

    public void summaryExpense() throws SQLException {
        String Summary_SQL = """
                SELECT SUM(amount) AS total FROM expenses
                """;

        try (Connection conn = this.connectDB();
             PreparedStatement statement = conn.prepareStatement(Summary_SQL)) {

            java.sql.ResultSet rs = statement.executeQuery();

            if (rs.next()){
                BigDecimal total = rs.getBigDecimal("total");
                if (total == null) {
                    total = BigDecimal.ZERO;
                }
                System.out.println("Total expenses: " + total);

            }



        }


    }

    public void summaryExpenseByMonth(int month, int year) throws SQLException {

        String SUMMARY_SQL = """
            SELECT SUM(amount) AS total
            FROM expenses
            WHERE created_at >= ?
              AND created_at < ?
            """;

        String startDate = String.format("%04d-%02d-01", year, month);

        LocalDate firstDay = LocalDate.of(year, month, 1);
        String endDate = firstDay.plusMonths(1).toString();

        try (Connection conn = this.connectDB();
             PreparedStatement statement = conn.prepareStatement(SUMMARY_SQL)) {

            statement.setString(1, startDate);
            statement.setString(2, endDate);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    BigDecimal total = rs.getBigDecimal("total");

                    if (total == null) {
                        total = BigDecimal.ZERO;
                    }

                    System.out.printf(
                            "Total expenses for %02d/%d: %s%n",
                            month, year, total
                    );
                }
            }
        }
    }
}

