public class GeneralLedger {
    static int MAX_TRANSACTIONS = 50;
    private Transaction[] ledger;
    private double totalDebitAmount;
    private double totalCreditAmount;
    private int count = 0;

    /**
     * Constructor for GeneralLedger
     */
    public GeneralLedger(){
        this.totalCreditAmount = 0;
        this.totalDebitAmount = 0;
        this.ledger = new Transaction[MAX_TRANSACTIONS];
    }

    /**
     * Getter for TotalDebitAmount
     * @return
     *      returns totalDebitAmount
     */
    public double getTotalDebitAmount(){
        return totalDebitAmount;
    }

    /**
     * Getter for TotalCreditAmount
     * @return
     *      returns totalCreditAmount
     */
    public double getTotalCreditAmount(){
        return totalCreditAmount;
    }

    /**
     * Adds newTransaction into GeneralLedger array
     * @param newTransaction
     *      Transaction being added into ledger
     */
    public void addTransaction(Transaction newTransaction){
        int ind = this.size();
        if(!(this.size() == 50)) {
            ledger[ind] = newTransaction;
            count++;

            if(newTransaction.getAmount() > 0){
                totalDebitAmount += newTransaction.getAmount();
            }
            else{
                totalCreditAmount += newTransaction.getAmount();
            }
        }
        else{
            System.out.println("Max transactions reached");
        }
    }

    /**
     * Removes transaction from GeneralLedger given position of the transaction
     * @param position
     *      position/index of transaction to be removed
     */
    public void removeTransaction(int position){
        position--;
        int a = this.size() - position;
        if (position == this.size()) {
            ledger[position] = null;
        } else {
            for (int i = position; i <= a; i++) {
                ledger[position] = ledger[position + 1];
            }
        }
        count--;
    }

    /**
     * Gets transaction given position
     * @param position
     *      Position/index of transaction wanted
     * @return
     *      Reference to the transaction at position
     */
    public Transaction getTransaction(int position){
        position--;
        return ledger[position];
    }

    /**
     * Searches and prints all transaction at a certain date
     * @param generalLedger
     *      Ledger of transaction to search in
     * @param date
     *      Date of transaction(s) to search for
     */
    public static void filter(GeneralLedger generalLedger, String date){
        for(int i = 0; i < generalLedger.size(); i++){
            if(generalLedger.ledger[i].getDate().equals(date)){
                System.out.println(generalLedger.toString(i));
            }
        }
    }

    /**
     * creates copy GeneralLedger
     * @return
     *      Returns copy as backup
     */
    public Object clone(){
        GeneralLedger clone = new GeneralLedger();
        for(int i = 0; i < this.size(); i++){
            clone.addTransaction(this.ledger[i]);
        }

        return clone;
    }

    /**
     * Checks if a transaction exists in the ledger
     * @param transaction
     *      The transaction to check
     * @return
     *      Returns true is transaction exists, false if not
     */
    public boolean exists(Transaction transaction){
        for(int i = 0; i < this.size(); i++){
            if(ledger[i].equals(transaction)){
                return true;
            }
        }

        return false;
    }

    /**
     * Get position/index of transaction
     * @param transaction
     *      The transaction to look for the position of
     * @return
     *      Returns the position
     */
    public int getPosition(Transaction transaction){
        for(int i = 0; i < this.size(); i++){
            if(ledger[i].equals(transaction)){
                return i+1;
            }
        }
        return -1;
    }

    /**
     *
     * @return
     *      number of transaction in ledger
     */
    public int size(){
        return count;
    }

    /**
     * Prints formatted table of all transaction in the ledger
     */
    public void printAllTransactions(){
        for (int i = 0; i < this.size(); i++) {
            System.out.println(this.toString(i));
        }

    }

    /**
     * Formatted String representation of a transaction within the GeneralLedger
     * @param ind
     *      Position/index of a transaction
     * @return
     *      String representation of transaction
     */
    public String toString(int ind){
        if(this.ledger[ind].getAmount() > 0) {
            return String.format("%-6d%-14s%.2f%21s", ind+1, this.ledger[ind].getDate(),
                    this.ledger[ind].getAmount(),
                    this.ledger[ind].getDescription());
        }
        else{
            return String.format("%-6d%-24s%.2f%12s", ind+1, this.ledger[ind].getDate(),
                    this.ledger[ind].getAmount(),
                    this.ledger[ind].getDescription());
        }
    }
}

