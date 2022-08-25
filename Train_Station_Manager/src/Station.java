/**
 * This class is a linked list composed of track nodes. It also contains the driver method
 */

import java.util.InputMismatchException;
import java.util.Scanner;
public class Station {
    Track head;
    Track tail;
    Track cursor;
    Track tempCursor;

    /**
     * Constructor
     */
    public Station(){
        this.head = null;
        this.tail = null;
        this.cursor = null;
        this.tempCursor = null;
    }

    /**
     * Method adds track node to linked list.
     * It considers three cases: emptylist, one head/tail, and track in the middle
     * @param track
     *      track tobe added
     */
    public void addTrack(Track track){
        if(cursor == null){
            cursor = track;
            head = track;
            tail = track;
        }
        else if(cursor.getNext() == null){
            cursor.setNext(track);
            track.setPrev(cursor);
            cursor = track;
            tail = track;
        }else{
            cursor.getNext().setPrev(track);
            track.setNext(cursor.getNext());
            cursor.setNext(track);
            track.setPrev(cursor);
            cursor = track;
        }
    }

    /**
     * Method removes the track the cursor is on.
     * It considers five cases: empty list, one node in list, cursor is head, cursor is tail, and cursor is in between.
     * @return
     *      the removed track
     */
    public Track removeSelectedTrack(){
        Track removedTrack = new Track();

        if(cursor == null){
            return null;
        }

        removedTrack = cursor;

        if(cursor.getNext() == null && cursor.getPrev() == null){
            cursor = null;
            head = null;
            tail = null;
        }
        else if(cursor.getPrev() == null){
            cursor.getNext().setPrev(null);
            cursor =  cursor.getNext();
            head = cursor;
        }
        else if(cursor.getNext() == null){
            cursor.getPrev().setNext(null);
            cursor = cursor.getPrev();
            tail = cursor;
        }else{
            cursor.getNext().setPrev(cursor.getPrev());
            cursor.getPrev().setNext(cursor.getNext());
            cursor = cursor.getNext();
        }

        return removedTrack;
    }

    /**
     * Method prints the track the cursor is on
     */
    public void printSelectedTrack(){
        if(cursor != null) {
            System.out.println("Track " + cursor.getTrackNumber() + "(" +
                    cursor.getUtilizationRate() + "% Utilization Rate):");
            printHeader();
            cursor.printAllTrains();
        } else{
            System.out.println("No tracks");
        }
    }

    /**
     * Method prints all tracks and train information of respective tracks as well
     */
    public void printAllTracks(){
        if(head != null){
            tempCursor = head;
            while(tempCursor!= null){
                if(tempCursor.equals(cursor)){
                    System.out.println("Track " + cursor.getTrackNumber() + "* (" +
                            cursor.getUtilizationRate() + "% Utilization Rate):");
                }else{
                    System.out.println("Track " + cursor.getTrackNumber() + "(" +
                            cursor.getUtilizationRate() + "% Utilization Rate):");
                }
                printHeader();
                tempCursor.printAllTrains();

                tempCursor = tempCursor.getNext();
            }
        }else{
            System.out.println("No tracks");
        }
    }

    /**
     * Method places the cursor on a track the user chooses
     * @param trackToSelect
     *      track that user wants to select
     * @return
     *      returns true if moved
     */
    public boolean selectTrack(int trackToSelect){
        if(head != null){
            tempCursor = head;
            while(tempCursor.getNext() != null){
                if(tempCursor.getTrackNumber() == trackToSelect){
                    cursor = tempCursor;
                    return true;
                }

                tempCursor = tempCursor.getNext();
            }
            return false;
        }
        return false;
    }

    /**
     * Method checks if track already exists within station
     * @param t
     *      track number
     * @return
     *      true if exists, false otherwise
     */
    public boolean exists(int t){
        try {
            if (head != null) {
                tempCursor = head;
                while(tempCursor != null){
                    if (tempCursor.getTrackNumber() == t) {
                        return true;
                    }
                    tempCursor = tempCursor.getNext();
                }

                return false;
            }
        }catch(NullPointerException e){
            System.out.println();
        }
        return false;
    }

    /**
     * Station toString method to print station information
     * @return
     *      station information
     */
    public String toString(){
        String station = "";
        if(head != null) {
            tempCursor = head;
            while (tempCursor != null) {
                station += ("Track " + tempCursor.getTrackNumber() + ": " + tempCursor.getTrains()
                        + " trains arriving (" + tempCursor.getUtilizationRate() + "% Utilization Rate)\n");

                tempCursor = tempCursor.getNext();
            }

            return station;
        }else{
            return "";
        }
    }

    /**
     * Driver class
     * @param args
     */
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String select;
        boolean stopRunning = false;
        Station station = new Station();
        int trainNumber, trackNumber, tracks = 0;
        String arrivalTime;
        int arrivalHour, arrivalMin;
        int transferTime;
        String destination;

        do{
            select = printMenu();
            switch(select){
                case "A":
                    if(station.cursor == null){
                        System.out.println("No tracks available\n");
                    }else {
                        System.out.print("Enter train number: ");
                        trainNumber = in.nextInt();
                        if(station.cursor.exists(trainNumber)){
                            System.out.println("Train already exists\n");
                            break;
                        }else {
                            try {
                                System.out.print("Enter train destination: ");
                                destination = in.nextLine();
                                in.nextLine();
                                System.out.print("Enter train arrival time: ");
                                arrivalTime = in.nextLine();

                                arrivalHour = Integer.parseInt(arrivalTime.substring(0, 2));
                                arrivalMin = Integer.parseInt(arrivalTime.substring(2));
                                if (!(station.isTimeValid(arrivalHour, arrivalMin))) {
                                    System.out.println("Invalid time\n");
                                    break;
                                }
                                System.out.print("Enter train transfer time: ");
                                transferTime = in.nextInt();

                                Train train = new Train(trainNumber, destination, Integer.parseInt(arrivalTime), transferTime);
                                station.cursor.addTrain(train);

                                System.out.println("Train successfully added\n");
                            }catch(StringIndexOutOfBoundsException e){
                                System.out.println("Invalid time\n");
                                break;
                            }
                        }
                    }
                    break;
                case "N":
                    station.cursor.selectNextTrain();
                    break;
                case "V":
                    station.cursor.selectPrevTrain();
                    break;
                case "R":
                    Train removedTrain = station.cursor.removeSelectedTrain();
                    if(removedTrain == null){
                        System.out.println("No selected train\n");
                    }else{
                        System.out.println("Train No. " + removedTrain.getTrainNumber() + " to "
                                + removedTrain.getDestination() +" has been removed from Track "
                                + station.cursor.getTrackNumber() + "\n");
                    }
                    break;
                case "P":
                    station.cursor.printSelectedTrain();
                    break;
                case "TA":
                    try {
                        System.out.print("Enter track number: ");
                        trackNumber = in.nextInt();
                        if (station.exists(trackNumber)) {
                            System.out.println("Track already exists\n");
                        } else {
                            Track newTrack = new Track(trackNumber);
                            station.addTrack(newTrack);
                            System.out.println("Track successfully added\n");
                            tracks++;
                        }
                    }catch(InputMismatchException e){
                        System.out.println("Invalid input\n");
                    }
                    break;
                case "TR":
                    station.removeSelectedTrack();
                    break;
                case "TS":
                    System.out.print("Enter the Track number: ");
                    trackNumber = in.nextInt();
                    station.selectTrack(trackNumber);
                    break;
                case "TPS":
                    station.printSelectedTrack();
                    break;
                case "TPA":
                    station.printAllTracks();
                    break;
                case "SI":
                    System.out.println("Station (" + tracks + " tracks):");
                    System.out.println(station.toString());
                    break;
                case "Q":
                    System.out.print("Program terminating...");
                    stopRunning = true;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid operation\n");
                    break;
            }
        }while(!stopRunning);
    }

    /**
     * Method to print menu
     * @return
     *      returns the choice the user makes from the menu
     */
    public static String printMenu(){
        Scanner menu = new Scanner(System.in);

        System.out.print("|");
        for(int i = 0; i < 77; i++){
            System.out.print("-");
        }
        System.out.println("|");

        System.out.format("|%-37s|%-39s|\n", " Train Options", " Track Options");
        System.out.format("|%-37s|%7s%-32s|\n", "    A. Add new Train", "TA.", " Add Track");
        System.out.format("|%-37s|%7s%-32s|\n", "    N. Select next Train", "TR.", " Remove selected Track");
        System.out.format("|%-37s|%7s%-32s|\n", "    V. Select previous Train", "TS.", " Switch Track");
        System.out.format("|%-37s|%7s%-32s|\n", "    R. Remove selected Train", "TPS.", " Print selected Track");
        System.out.format("|%-37s|%7s%-32s|\n", "    P. Print selected Train", "TPA.", " Print all Tracks");

        System.out.print("|");
        for(int i = 0; i < 77; i++){
            System.out.print("-");
        }
        System.out.println("|");

        System.out.format("|%-77s|\n", " Station Options");
        System.out.format("|%-77s|\n", "   SI. Print Station Information");
        System.out.format("|%-77s|\n", "    Q. Quit");

        System.out.print("|");
        for(int i = 0; i < 77; i++){
            System.out.print("-");
        }
        System.out.println("|");

        System.out.print("\nChoose an operation: ");
        return menu.next().toUpperCase();
    }

    /**
     * Method prints header of track information
     */
    public void printHeader(){
        System.out.format("%-10s%-17s%-26s%-17s%-15s\n",
                "Selected", "Train Number", "Train Destination", "Arrival Time", "Departure Time");
        System.out.println("-------------------------------------------------------------------------------------");
    }

    /**
     * Method checks if arrival time is in valid format
     * @param hr
     *      hour section of arrival time
     * @param min
     *      minute section of arrival time
     * @return
     *      returns true is time format is valid
     */
    public boolean isTimeValid(int hr, int min){
        if(hr < 0 || hr > 24 || min < 0 || min > 60){
            return false;
        }

        return true;
    }

}
