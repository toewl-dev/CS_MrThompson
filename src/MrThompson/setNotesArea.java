package MrThompson;

import java.io.*;

public class setNotesArea {
    //This class either reads or writes the notes to a file upon saving / loading.
    public String setNotes(String className) throws FileNotFoundException {
        //This method reads the notes, if there are no notes it creates them and puts a temporary string inside.
        String toShow;
        if (new File(className + "_Notes.dat").isFile()) {
            try (ObjectInputStream streamIn = new ObjectInputStream(new FileInputStream(className + "_Notes.dat"))) {
                toShow = (String) streamIn.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            toShow = "Type your notes about " + className + " here";

            saveText(toShow, className);
        }
        return toShow;
    }

    public void saveText(String text, String className) throws FileNotFoundException {
        //this method is given the text to be saved and puts it in a file.
        File f = new File(className + "_Notes.dat");
        try (ObjectOutputStream streamOut = new ObjectOutputStream(new FileOutputStream(f))) {
            streamOut.writeObject(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
