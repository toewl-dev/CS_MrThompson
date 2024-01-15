package MrThompson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class addClass extends JFrame{
    private JList<String> listStudents;
    private JButton doneButton;
    private JButton cancelButton;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JPanel panelAdd;
    private JButton addStudentButton;
    private JTextField tfClassName;
    int students = 0;
    String[] nameList = new String[0];
    String[] allClasses;
    public boolean done = false;

    public addClass() {
        setContentPane(panelAdd);
        setTitle("Add a class");
        setSize(400, 270);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfFirstName.getText().equals("") || tfLastName.getText().equals("")) {
                    //make sure there is a first and last name value
                    JOptionPane.showMessageDialog(null, "Both first and last name fields should be filled in!");
                }
                else { //add the student to the list
                    students += 1;
                    nameList = Arrays.copyOf(nameList, students);
                    nameList[nameList.length-1] = Integer.toString(nameList.length) + ". " + tfFirstName.getText() + " " + tfLastName.getText();
                    listStudents.setListData(nameList);

                }
            }
        });

        doneButton.addActionListener(new ActionListener() {
            //This saves the class to a new file
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get all classes list
                try (ObjectInputStream streamIn = new ObjectInputStream(new FileInputStream("classNames.dat"))) {
                    allClasses = (String[]) streamIn.readObject();
                } catch (IOException | ClassNotFoundException ex) {
                    allClasses = new String[0];
                }

                //Save class to list
                if (tfClassName.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Class name must be filled in!");
                }
                try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(tfClassName.getText() + ".dat"));){
                    nameList = Arrays.copyOf(nameList, nameList.length+1);
                    nameList[nameList.length-1] = tfClassName.getText();
                    stream.writeObject(nameList); //Last item is the class NAME
                    allClasses = Arrays.copyOf(allClasses, allClasses.length+1);
                    allClasses[allClasses.length-1] = tfClassName.getText();
                    //Really fuckin spaghetti but its ok
                    try (ObjectOutputStream streamOutClasses = new ObjectOutputStream(new FileOutputStream("classNames.dat"))) {
                        streamOutClasses.writeObject(allClasses);

                    } catch (IOException ioex) {
                        throw new RuntimeException(ioex);
                    }

                    //hide current window bc its all saved
                    done = true;
                    setVisible(false);
                    dispose();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

        });

        cancelButton.addActionListener(new ActionListener() {
            //just disposes the window without saving anything.
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }
}
