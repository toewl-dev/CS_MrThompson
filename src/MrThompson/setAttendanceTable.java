package MrThompson;

import java.io.*;
import java.util.Arrays;

public class setAttendanceTable {
    //This class contains 2 methods, one to initialize the Attendance table
    //It is run whenever the selected Classroom is changed and return a 2d Array
    //of the data to go in the table on the UI.
    public String[][] initializeTable(String className, int[] date) throws IOException {
        //Formatting the date as a string, e.g. "01-01-2024"
        String dateFormatted = date[0] + "-" + date[1] + "-" + date[2];
        String[][] data; //will be returned

        String[] temp;
        String[] students = new String[0];

        //Check if file exists
        //if it does, open it, otherwise, make <data> have blank data and list of names
        if (new File(className + "_Attendance_" + dateFormatted + ".dat").isFile()) { //file exists already
            //READ FILE RETURN THAT
            File file = new File(className + "_Attendance_" + dateFormatted + ".dat");
            try (ObjectInputStream streamInAt = new ObjectInputStream(new FileInputStream(file))) {//attendance stream in
                data = (String[][]) streamInAt.readObject();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        else { //file needs to be made, then filled with blank data.
            //System.out.println(className + "_Attendance_" + dateFormatted + ".dat ==== OPENED");//
            File file = new File(className + "_Attendance_" + dateFormatted + ".dat");
            try (ObjectInputStream streamIn = new ObjectInputStream(new FileInputStream(className + ".dat"))) {
                temp = (String[]) streamIn.readObject(); //Student list (with class at the end)
                students = Arrays.copyOf(temp, temp.length-1); //Removes class in the end
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            data = new String[students.length][3];
            //filling with blank / default data
            for (int k = 0; k < students.length; k++) {
                data[k][0] = students[k];
                data[k][1] = "false"; //defaults to not present
                data[k][2] = "Present"; //default excuse
            }

            //Save this
            try (ObjectOutputStream streamOut = new ObjectOutputStream(new FileOutputStream(file))) {
                streamOut.writeObject(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //System.out.println(Arrays.toString(students));


        return data;
    }

    public void saveTable(String className, String[][] data, int[] date) {
        //method to save  data to the appropriate file given a 2d array of data
        String dateFormatted = date[0] + "-" + date[1] + "-" + date[2];
        File fileToSave = new File(className + "_Attendance_" + dateFormatted + ".dat");

        try (ObjectOutputStream streamOut = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
            streamOut.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
