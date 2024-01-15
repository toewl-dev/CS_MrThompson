package MrThompson;

import javax.swing.*;

public class Main {
    public static void main(String[] args) { //the main method
        //This part sets the look and feel of the program to the modern windows look and feel (i.e. like notepad)
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");  // Windows Look and feel
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        //Calls the loginUI class.
        LoginUI login = new LoginUI();
    }
}