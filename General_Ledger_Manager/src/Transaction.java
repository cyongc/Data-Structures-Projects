public class Transaction implements Cloneable {
    private String date;
    private double amount;
    private String description;

    public Transaction() {
    }

    /**
     * Constructor for transaction
     * @param date
     *     date of transaction
     * @param amount
     *     credit/debit of transaction
     * @param description
     *      description of transaction
     */
    public Transaction(String date, double amount, String description){
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /**
     * getter for date
     * @return
     *      returns date
     */
    public String getDate(){
        return date;
    }

    /**
     * getter for amount
     * @return
     *      returns amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * getter for description
     * @return
     *      returns description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Deep copies transaction
     * @return
     *      The copy of transaction
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        Transaction transactionClone = new Transaction();
        transactionClone = (Transaction) super.clone();
        return transactionClone;
    }

    /**
     * Checks if obj is equals to a transaction
     * @param obj
     *      Object being type casted into transaction and then checked
     * @return
     *      returns true if obj is equals to transaction
     */
    public boolean equals(Object obj){
        if(((Transaction) obj).getDate().equals(this.getDate()) &&
                ((Transaction) obj).getAmount() == (this.getAmount()) &&
                ((Transaction) obj).getDescription().equals(this.getDescription())){
            return true;
        }else {
            return false;
        }
    }

}

