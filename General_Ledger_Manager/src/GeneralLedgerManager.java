import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GeneralLedgerManager {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        GeneralLedger generalLedger = new GeneralLedger();
        GeneralLedger backupLedger = new GeneralLedger();
        Transaction transaction;
        String date, description;
        double amount;
        int position;
        String selection = printMenu();
        boolean stopRunning = false;

        do {
            switch (selection) {
                /**
                 * Selection A adds transaction to ledger after user inputs date, amount, and description
                 */
                case "A":
                    System.out.print("Enter Date: ");
                    date = input.next();
                    if(isDateValid(date, "mm/dd/yyyy")) {
                        try {
                            System.out.print("Enter Amount ($): ");
                            amount = input.nextInt();
                            System.out.print("Enter Description: ");
                            description = input.next();

                            transaction = new Transaction(date, amount, description);

                            generalLedger.addTransaction(transaction);
                            System.out.print("Transaction successfully added to the general ledger. \n");
                        }catch(InputMismatchException e){
                            System.out.println("Invalid input.\n");
                        }
                    }

                    selection = printMenu();
                    break;


                /**
                 * Selection G get transaction from ledger after user inputs position and prints transaction returned
                 */
                case "G":
                    try {
                        System.out.println("Enter position: ");
                        position = input.nextInt();

                        transaction = generalLedger.getTransaction(position);

                        if (transaction.getAmount() >= 0) {
                            printHeader();
                            System.out.print(String.format("%-6d%-14s%.2f%21s\n", position, transaction.getDate(),
                                    transaction.getAmount(),
                                    transaction.getDescription()));
                        } else {
                            printHeader();
                            System.out.print(String.format("%-6d%-24s%.2f%12s\n", position, transaction.getDate(),
                                    transaction.getAmount(),
                                    transaction.getDescription()));
                        }
                    } catch (NullPointerException indexEx) {
                        System.out.println("No such transaction.\n");
                    }

                    selection = printMenu();
                    break;

                /**
                 * Selection R removes transaction from ledger given user inputted position
                 */
                case "R":
                    try {
                        System.out.println("Enter position: ");
                        position = input.nextInt();

                        generalLedger.removeTransaction(position);
                        System.out.println("Transaction has been successfully removed from the general ledger.\n");
                    } catch (NullPointerException indexEx) {
                        System.out.println("No such transaction.\n");
                    }

                    selection = printMenu();
                    break;

                /**
                 * Selection P prints all transactions in GeneralLedger
                 */
                case "P":
                    if(generalLedger.size() == 0) {
                        System.out.println("Ledger empty.\n");
                    }
                    else{
                        printHeader();
                        generalLedger.printAllTransactions();
                    }
                    selection = printMenu();
                    break;

                /**
                 * Selection F finds all transaction in ledger of the user inputted date and prints transaction(s)
                 */
                case "F":
                    System.out.print("Enter date: ");
                    date = input.next();
                    if(isDateValid(date, "mm/dd/yyyy")) {
                        try {
                            printHeader();
                            generalLedger.filter(generalLedger, date);
                        } catch (IndexOutOfBoundsException dateEx) {
                            System.out.println("No such transactions.\n");
                        }
                    }
                    selection = printMenu();
                    break;

                /**
                 * Selection L searches for a specific transaction given user inputted date, amount, and description
                 * and prints transaction
                 */
                case "L":
                    System.out.print("Enter Date: ");
                    date = input.next();
                    if(isDateValid(date, "mm/dd/yyyy")) {
                        System.out.print("Enter Amount ($): ");
                        amount = input.nextInt();
                        System.out.print("Enter Description: ");
                        description = input.next();

                        transaction = new Transaction(date, amount, description);
                        if (generalLedger.exists(transaction)) {
                            printHeader();
                            if (transaction.getAmount() >= 0) {
                                System.out.print(String.format("%-6d%-14s%.2f%21s\n", generalLedger.getPosition(transaction),
                                        transaction.getDate(),
                                        transaction.getAmount(),
                                        transaction.getDescription()));
                            } else {
                                System.out.print(String.format("%-6d%-24s%.2f%12s\n", generalLedger.getPosition(transaction),
                                        transaction.getDate(),
                                        transaction.getAmount(),
                                        transaction.getDescription()));
                            }

                        } else {
                            System.out.println("No such transaction.");
                        }
                    }
                    selection = printMenu();
                    break;

                /**
                 * Prints number of transaction in GeneralLedger
                 */
                case "S":
                    System.out.println("Number of transactions in ledger: " + generalLedger.size());
                    selection = printMenu();
                    break;

                /**
                 * Creates backup of GeneralLedger
                 */
                case "B":
                    backupLedger = (GeneralLedger) generalLedger.clone();
                    System.out.println("Created backup of current general ledger");
                    selection = printMenu();
                    break;

                /**
                 * Prints all transaction in backup ledger
                 */
                case "PB":
                    printHeader();
                    backupLedger.printAllTransactions();
                    selection = printMenu();
                    break;

                /**
                 * Reverts GeneralLedger back to backup ledger
                 */
                case "RB":
                    generalLedger = backupLedger;
                    System.out.println("General ledger reverted to backup ledger.");
                    selection = printMenu();
                    break;

                /**
                 * Checks if generalLedger and backupLedger are the same
                 */
                case "CB":
                    if(backupLedger.size() == 0){
                        System.out.println("Backup ledger does not exist.");
                    }
                    if(generalLedger.size() != backupLedger.size()){
                        System.out.println("The current general ledger is NOT the same as the backup copy.\n");
                    }
                    else{
                        for (int i = 1; i < Math.max(generalLedger.size(), backupLedger.size()); i++) {
                            if (!(generalLedger.exists(backupLedger.getTransaction(i)) &&
                                backupLedger.exists(generalLedger.getTransaction(i)))){
                                System.out.println("The current general ledger is NOT the same as the backup copy.\n");
                            }
                        }
                        System.out.println("The current general ledger is the same as the backup copy.\n");
                    }

                    selection = printMenu();
                    break;

                /**
                 * Prints all financial details
                 */
                case "PF":
                    System.out.println("Total debit amount: " + generalLedger.getTotalDebitAmount());
                    System.out.println("Total credit amount: " + generalLedger.getTotalCreditAmount());
                    System.out.println("Account net worth: " + (generalLedger.getTotalDebitAmount() - generalLedger.getTotalCreditAmount()));
                    selection = printMenu();
                    break;

                case "Q":
                    System.out.println("Quitting program...");
                    stopRunning = true;
                    System.exit(0);
                    selection = printMenu();
                    break;

                default:
                    System.out.println("Invalid selection.\n");
                    selection = printMenu();
                    break;
            }
        }while(!(stopRunning));
    }

    /**
     * Prints menu for user selection
     * @return
     *      returns user selection
     */
    public static String printMenu(){
        System.out.println();
        System.out.println("(A) Add Transaction " +
                "\n(G) Get Transaction " +
                "\n(R) Remove Transaction " +
                "\n(P) Print Transaction in General Ledger " +
                "\n(F) Filter by Date " +
                "\n(L) Look for Transaction " +
                "\n(S) Size " +
                "\n(B) Backup " +
                "\n(PB) Print Transactions in Backup " +
                "\n(RB) Revert to Backup " +
                "\n(CB) Compare Backup to Current " +
                "\n(PF) Print Financial Information " +
                "\n(Q) Quit ");
        System.out.print("Enter a selection: ");
        Scanner input = new Scanner(System.in);
        String selection = input.nextLine();
        selection = selection.toUpperCase();
        System.out.println();

        return selection;
    }

    /**
     * Prints header of ledger
     */
    public static void printHeader(){
        System.out.println("No.   Date          Debit     Credit     Description " +
                "\n-----------------------------------------------------------------------------------");
    }

    public static boolean isDateValid(String date, String format){
        try {
            DateFormat df = new SimpleDateFormat(format);
            df.setLenient(false);
            df.parse(date);
            Integer month = Integer.parseInt(date.substring(0,2));
            Integer day = Integer.parseInt(date.substring(3,5));
            Integer year = Integer.parseInt(date.substring(6));

            if((month < 0) || (month > 12) || (day < 0) || (day > 31) || (year > 2020)){
                System.out.println("Invalid Date.\n");
                return false;
            }
            return true;
        }catch(ParseException e){
            System.out.println("Invalid Date.\n");
            return false;
        }catch(NumberFormatException e){
            System.out.println("Invalid Date.\n");
            return false;
        }

    }
}

