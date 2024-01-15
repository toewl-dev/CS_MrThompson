package MrThompson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.Arrays;

public class pickDate extends JFrame {
    setGetDate dateVar = new setGetDate();
    setAttendanceTable setAttendanceTable = new setAttendanceTable();
    private JPanel pickDatePanel;
    private JButton doneButton;
    private JSpinner spinnerDay;
    private JComboBox cbMonth;
    private JSpinner spinnerYear;
    int[] date = dateVar.getDate();

    public pickDate() throws IOException {
        setContentPane(pickDatePanel);
        setTitle("Pick Date");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        File file = new File("date.dat");
        spinnerYear.setValue(2024);

        spinnerDay.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //If the value foes below 1 or above 31 set it back to the previous value.
                if ((int)spinnerDay.getValue() > 31) {
                    spinnerDay.setValue(31);
                }
                else if ((int)spinnerDay.getValue() < 1) {
                    spinnerDay.setValue(1);
                }
            }
        });

        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                date = new int[]{(int) spinnerDay.getValue(), cbMonth.getSelectedIndex() + 1, (int) spinnerYear.getValue()};
                try {
                    dateVar.setDate(date); //refers to setGetDate.java
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                //System.out.println(Arrays.toString(date));

                //close window
                setVisible(false);
                dispose();
            }
        });
    }
}
