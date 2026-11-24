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

            case "update" -> {
                service.updateExpense(args[1], args[2], args[3]);
            }

            case "deletedb" -> {
                service.clearDB();
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
                if (args.length == 3) service.summaryExpense(args[1], args[2]);
                if (args.length == 2){
                    System.out.println("Update Command Must have Year and Month values");
                    return;
                }
                if (args.length == 1) service.summaryExpense();

            }

            default -> System.out.printf("'%s' is not a valid command", action);

        }
    }
}

