/**
 * This class is a linked list composed of train nodes.
 * It also serves as nodes for the linked list in the station class
 */
public class Track {
    Train head;
    Train tail;
    Train cursor;
    Train tempCursor;
    Track next;
    Track prev;
    double utilizationRate;
    int trackNumber = 0, totalTime = 0, trains;

    /**
     * Constructor
     */
    public Track(){
        this.head = null;
        this.tail = null;
        this.cursor = null;
        this.tempCursor = null;
    }

    /**
     * Constructor with track number
     * @param trackNumber
     *      user inputted track number
     */
    public Track(int trackNumber){
        this.head = null;
        this.tail = null;
        this.cursor = null;
        this.tempCursor = null;
        this.trackNumber = trackNumber;
    }

    /**
     * Method to set reference to next track node
     * @param nextTrack
     *      next track node
     */
    public void setNext(Track nextTrack){ this.next = nextTrack; }

    /**
     * Method to set reference to previous track node
     * @param prevTrack
     *      previous track node
     */
    public void setPrev(Track prevTrack){ this.prev = prevTrack; }

    /**
     * Method to get next referenced node
     * @return
     *      next node
     */
    public Track getNext(){ return next; }

    /**
     * Method to get previous referenced node
     * @return
     *      previous node
     */
    public Track getPrev(){ return prev; }

    /**
     * Method to get track number of train
     * @return
     *      track number
     */
    public int getTrackNumber(){ return trackNumber; }

    /**
     * Method to get number of trains on track
     * @return
     *      number of trains
     */
    public int getTrains(){ return trains; }

    /**
     * Method to return the formatted utilization rate
     * @return
     *      utilization rate
     */
    public String getUtilizationRate(){
        return String.format("%.2f", utilizationRate);
    }

    /**
     * Method to add train to the track.
     * It considers three cases: emptylist, one head/tail, and train in the middle
     * @param newTrain
     *      train to be added
     */
    public void addTrain(Train newTrain){
        if(cursor == null){             //list empty
            cursor = newTrain;
            head = newTrain;
            tail = newTrain;
        }
        else if(cursor.getNext() == null){      //one train head/tail
            cursor.setNext(newTrain);
            newTrain.setPrev(cursor);
            cursor = newTrain;
            tail = newTrain;
        }else{                                          //in middle
            cursor.getNext().setPrev(newTrain);
            newTrain.setNext(cursor.getNext());
            cursor.setNext(newTrain);
            newTrain.setPrev(cursor);
            cursor = newTrain;
        }

        trains++;

        totalTime += newTrain.getTransferTime();
        utilizationRate = calcUtilizationRate(totalTime);

    }

    /**
     * Method prints the information of the train that cursor is on
     */
    public void printSelectedTrain(){
        if(cursor == null){
            System.out.println("No selected train\n");
        }else{
            System.out.println("Selected Train:");
            System.out.println("    Train Number: " + cursor.getTrainNumber());
            System.out.println("    Train Destination: " + cursor.getDestination());
            System.out.println("    Arrival Time: " + cursor.getArrivalTime());
            System.out.println("    Departure Time: " + cursor.getTransferTime() + "\n");
        }
    }

    /**
     * Method prints all trains and respective train information on the selected track
     */
    public void printAllTrains(){
        if(head != null) {
            tempCursor = head;
            while(tempCursor != null){
                if (tempCursor.equals(cursor)) {
                    System.out.format("*         %-17d%-26s%-17d%-15d\n",
                            cursor.getTrainNumber(), cursor.getDestination(),
                            cursor.getArrivalTime(), cursor.getTransferTime());
                } else{
                    System.out.println(tempCursor.toString());
                }

                tempCursor = tempCursor.getNext();
            }
        }else{
            System.out.println();
        }
    }

    /**
     * Method removes train that the cursor is on
     * It considers five cases: empty list, one node in list, cursor is head, cursor is tail, and cursor is in between.
     * @return
     *      the removed train
     */
    public Train removeSelectedTrain(){
        Train removedTrain = new Train();

        if(cursor == null) {                        //list empty
            return null;
        }

        removedTrain = cursor;

        if(cursor.getNext() == null && cursor.getPrev() == null) {      //one node in list
            cursor = null;
            head = null;
            tail = null;
        }
        else if(cursor.getPrev() == null) {         //cursor is head
            cursor.getNext().setPrev(null);
            cursor = cursor.getNext();
            head = cursor;
        }
        else if(cursor.getNext() == null) {     //cursor is tail
            cursor.getPrev().setNext(null);
            cursor = cursor.getPrev();
            tail = cursor;
        }else{                                              //in b/w
            cursor.getNext().setPrev(cursor.getPrev());
            cursor.getPrev().setNext(cursor.getNext());
            cursor = cursor.getNext();
        }

        trains--;

        totalTime -= removedTrain.getTransferTime();
        utilizationRate = calcUtilizationRate(totalTime);
        return removedTrain;
    }

    /**
     * Method moves cursor to next train
     * @return
     *      true if moved
     */
    public boolean selectNextTrain(){
        try{
            cursor = cursor.getNext();
            System.out.println("Cursor has been moved to next train.");
            return true;
        }catch(NullPointerException e){
            System.out.println("No next train\n");
            return false;
        }
    }

    /**
     * Method moves cursor to previous train
     * @return
     *      true if moved
     */
    public boolean selectPrevTrain(){
        try{
            cursor = cursor.getPrev();
            System.out.println("Cursor has been moved to previous train.");
            return true;
        }catch(NullPointerException e){
            System.out.println("No next train\n");
            return false;
        }
    }

    /**
     * Method calculates the utilization rate
     * @param totalTime
     *      total transfer times
     * @return
     *      returns utilization rate
     */
    public double calcUtilizationRate(int totalTime){ return (totalTime/1440.0); }

    /**
     * Method checks if train exists already
     * @param t
     *      the train number
     * @return
     *      returns true if train already exists, false if otherwise
     */
    public boolean exists(int t){
        try {
            if (head != null) {
                tempCursor = head;
                while(tempCursor != null)
                    if (tempCursor.getTrainNumber() == t) {
                        return true;
                    }

                    tempCursor = tempCursor.getNext();

            }
            return false;
            }catch(NullPointerException e){
                System.out.println();
            }

        return false;
    }

    public String toString(){
        return super.toString();
    }
}
