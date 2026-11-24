import service.ExpenseService;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {

        if (args.length > 4 || args.length == 0){System.out.println("Usage [ add | list | summary | delete ] ");return;}

        String action = args[0].toLowerCase();

        if (action.equals("exit")){System.out.println("Session terminated");return;}


        ExpenseService service = new ExpenseService();

        switch (action){

            case "add" -> {
                service.addExpense(args[1], args[2]);
            }

            case "delete" -> {
                service.deleteExpense(args[1]);
            }

            case "deletedb" -> {
                service.clearDB();
            }

            case "update" -> {
                service.updateExpense(args[1], args[2], args[3]);
            }

            case "update-amount" -> {
                service.updateExpenseAmount(args[1], args[2]);
            }

            case "update-description" -> {
                service.updateExpenseDescription(args[1], args[2]);
            }

            case "list" -> {
                service.listExpense();
            }

            case "summary" -> {
                service.summaryExpense();
            }

            default -> System.out.printf("'%s' is not a valid command", action);

        }
    }
}

