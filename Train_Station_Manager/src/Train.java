/**
 * This class sets up the node for the linked list in the Track class
 */
public class Train {
    Train next;
    Train prev;
    int trainNumber;
    String destination;
    int arrivalTime;
    int transferTime;

    /**
     * Empty constructor
     */
    public Train(){ }

    /**
     * Constructor for train
     * @param trainNumber
     *      user inputted train number
     * @param destination
     *      user inputted destination
     * @param arrivalTime
     *      user inputted arrival time
     * @param transferTime
     *      user inputtedtransfer time
     */
    public Train(int trainNumber, String destination, int arrivalTime, int transferTime){
        this.trainNumber = trainNumber;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.transferTime = transferTime;
    }

    /**
     * Method to set a reference to next node
     * @param nextTrain
     *      the next node
     */
    public void setNext(Train nextTrain){ this.next = nextTrain; }

    /**
     * Method to set a reference to the previous node
     * @param prevTrain
     *      the previous node
     */
    public void setPrev(Train prevTrain){ this.prev = prevTrain; }

    /**
     * @return
     *      returns the next node
     */
    public Train getNext(){ return next; }

    /**
     * @return
     *      returns the previous node
     */
    public Train getPrev(){ return prev; }

    /**
     * @return
     *      returns train number
     */
    public int getTrainNumber(){ return trainNumber; }

    /**
     * @return
     *      returns destination
     */
    public String getDestination(){ return destination; }

    /**
     * @return
     *      returns arrival time
     */
    public int getArrivalTime(){ return arrivalTime; }

    /**
     * @return
     *      returns transfer time
     */
    public int getTransferTime(){ return transferTime; }

    /**
     * Method to check if an object is equals to this train object/node
     * @param o
     *      object being checked
     * @return
     *      returns true if they are the same and false if not
     */
    public boolean equals(Object o){
        if(((Train) o).getTrainNumber() == (this.getTrainNumber()) &&
                ((Train) o).getDestination().equals(this.getDestination()) &&
                ((Train) o).getArrivalTime() == (this.getArrivalTime()) &&
                ((Train) o).getTransferTime() == (this.getTransferTime())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method to neatly print train information
     * @return
     *      returns string of train information
     */
    public String toString(){
        String t = String.format("          %-17d%-26s%-17d%-15d",
                this.getTrainNumber(), this.getDestination(),
                this.getArrivalTime(), this.getTransferTime());
        return t;
    }
}

//train = node
//track = linked list
//station = linked list
