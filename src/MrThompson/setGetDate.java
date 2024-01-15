package MrThompson;

import java.io.*;

public class setGetDate {
    //This class either reads or writes a date to the "date.dat" file
    //The date is stored as an array int[3] (day, month, year)
    public int[] date = {1, 1, 2024}; //Default date incase there is no file

    public int[] getDate() throws IOException {
        //This method reads the date from the file
        try (ObjectInputStream streamIn = new ObjectInputStream(new FileInputStream("date.dat"))) {
            date = (int[]) streamIn.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public void setDate(int[] dateToSet) throws FileNotFoundException {
        //This method writes a given array to the date file.
        try (ObjectOutputStream streamOut = new ObjectOutputStream(new FileOutputStream("date.dat"))) {
            streamOut.writeObject(dateToSet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}