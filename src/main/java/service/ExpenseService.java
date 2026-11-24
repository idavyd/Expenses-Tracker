package service;

import dbmanager.DBConnector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.Optional;

public class ExpenseService {

    private final DBConnector dbConnector;

    public ExpenseService() throws SQLException {
        dbConnector = new DBConnector();
    }

    private Optional<BigDecimal> validateAmount(String amount) {
        try {
            BigDecimal expenseAmount = new BigDecimal(amount);

            if (expenseAmount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Amount must be more than 0");
                return Optional.empty();
            }

            return Optional.of(
                    expenseAmount.setScale(2, RoundingMode.HALF_UP)
            );

        } catch (NumberFormatException e) {
            System.out.println("<< Invalid amount value >>");
            return Optional.empty();
        }
    }

    private Optional<Integer> validateId(String input) {
            try {
                int id = Integer.parseInt(input);

                if (id <= 0) {
                    System.out.println("ID must be greater than 0");
                    return Optional.empty();
                }

                return Optional.of(id);

            } catch (NumberFormatException e) {
                System.out.println("Invalid ID value");
                return Optional.empty();
            }
    }

    private Optional<YearMonth> validateMonthYear(String month, String year) {
        try {
            int monthValue = Integer.parseInt(month);
            int yearValue = Integer.parseInt(year);

            return Optional.of(YearMonth.of(yearValue, monthValue));

        } catch (NumberFormatException | DateTimeException e) {
            System.out.println("Invalid month or year.");
            return Optional.empty();
        }
    }

    public void addExpense(String description, String userInputAmount) throws SQLException {
        Optional<BigDecimal> amount = validateAmount(userInputAmount);
        if (amount.isEmpty()) return;

        this.dbConnector.addExpense(description,amount.get());

    }

    public void listExpense() throws SQLException{
        this.dbConnector.listExpenses();
    }

    public void summaryExpense() throws SQLException{
        this.dbConnector.summaryExpense();
    }

    public void summaryExpense(String month, String year) throws SQLException{

        Optional<YearMonth> period = validateMonthYear(month, year);
        if (period.isEmpty()) return;

        this.dbConnector.summaryExpenseByMonth(
                period.get().getMonthValue(),
                period.get().getYear()
        );
    }

    public void updateExpenseAmount(String UserIDInput, String userAmountInput) throws SQLException{
        Optional<BigDecimal> amount = validateAmount(userAmountInput);
        Optional<Integer> id = validateId(UserIDInput);

        if (amount.isEmpty() || id.isEmpty()) return;

        this.dbConnector.updateExpense(id.get(),amount.get());
    }

    public void updateExpenseDescription(String UserIDInput, String description) throws SQLException{
        Optional<Integer> id = validateId(UserIDInput);

        if (id.isEmpty()) return;

        this.dbConnector.updateExpense(id.get(),description);
    }

    public void updateExpense(String UserIDInput,  String description, String userAmountInput) throws SQLException{
        Optional<BigDecimal> amount = validateAmount(userAmountInput);
        Optional<Integer> id = validateId(UserIDInput);

        if (amount.isEmpty() || id.isEmpty()) return;

        this.dbConnector.updateExpense(id.get(),amount.get(), description);
    }

    public void deleteExpense(String UserIDInput) throws SQLException{
        Optional<Integer> id = validateId(UserIDInput);

        if (id.isEmpty()) return;

        this.dbConnector.deleteExpense(id.get());
    }

    public void clearDB() throws SQLException{

        this.dbConnector.delete();
    }
}
