package MrThompson;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

public class mainUI extends JFrame {
    Font font = new Font("Arial", Font.PLAIN, 14);
    Color niceGray = new Color(35, 32, 35);
    setGetDate dateVar = new setGetDate();
    int[] date = dateVar.getDate();
    setGradeTable setGradeTable = new setGradeTable();
    setAttendanceTable setAttendanceTable = new setAttendanceTable();
    setNotesArea setNotesArea = new setNotesArea();
    private JPanel mainPanel;
    private JTabbedPane tp1;
    private JList<String> listClassesGrades;
    private JButton addGradeButton;
    private JTable tbGrades;
    private JTable tbAvg;
    private JTable tbAttendance;
    private JButton pickDateButton;
    private JLabel lbDate;
    private JButton saveButton;
    private JTextArea taNotes;
    private JButton saveNotesBtn;
    static String[] allClasses = {};
    String currentClass;
    String[][] presenceData;

    public mainUI() throws IOException {
        setContentPane(mainPanel);
        setTitle("Teacher Planner");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        mainInit(); //made list of names
        listClassesGrades.setListData(allClasses);
        //currentClass = allClasses[0];
        listClassesGrades.setSelectedIndex(0);
        //----------------------------------------------------------------

        taNotes.setFont(font);
        taNotes.setForeground(niceGray);

        JComboBox<String> excuseDrop = new JComboBox<>();
        excuseDrop.addItem("Present");
        excuseDrop.addItem("No proper excuse");
        excuseDrop.addItem("Sickness");
        excuseDrop.addItem("Traffic");
        excuseDrop.addItem("Doctor appointment");
        excuseDrop.addItem("Personal reason");

        JCheckBox presentBox = new JCheckBox();

        JMenuBar menuBar = new JMenuBar();
        JMenu classes = new JMenu("Classes");
        menuBar.add(classes);
        JMenuItem addClass = new JMenuItem("Add Class");
        classes.add(addClass);
        setJMenuBar(menuBar);
        classes.setToolTipText("Add a class to the class list (Requires program restart)");
        addClass.setToolTipText("Add a class to the class list (Requires program restart)");

        addClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClass addClass = new addClass();
                mainInit();

                /*listClassesGrades.setListData(allClasses);
                /*listClassesGrades.setListData(allClasses);
                //currentClass = allClasses[allClasses.length-1];
                try {
                    //setGradeTable.setGradeTb(currentClass);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Done");
                */
            }

        });

        listClassesGrades.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    //grade table--------------------------------------------------
                    currentClass = listClassesGrades.getSelectedValue();
                    String[][] data;
                    try {
                        data = setGradeTable.setGradeTb(currentClass);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    String[] headers = new String[data[0].length];
                    headers[0] = "Student Name";
                    for (int i = 1; i < data[0].length; i++) {
                        headers[i] = "Grade " + i;
                    }
                    tbGrades.setModel(new DefaultTableModel(
                            data, headers
                    ));


                    //progress table-----------------------------------------
                    String[][] dataAvg;
                    try {
                        dataAvg = setGradeTable.averages(currentClass);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    tbAvg.setModel(new DefaultTableModel(
                            dataAvg, new String[]{"Average", "Progress", "Comment"}
                    ));

                    //attendance table================================================================
                    //================================================================================

                    String[] columnNames = {"Name", "Present", "Excuse"};
                    System.out.println(Arrays.toString(date));
                    try {
                        date = dateVar.getDate();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    //System.out.println(Arrays.toString(date));
                    lbDate.setText(date[0] + "/" + date[1] + "/" + date[2]);
                    //SET TABLE
                    try {
                        presenceData = setAttendanceTable.initializeTable(currentClass, date);
                        DefaultTableModel attendanceModel = new DefaultTableModel(presenceData, columnNames);

                        tbAttendance.setModel(attendanceModel);
                        //For second collumn, make it the checkbox
                        //for third collumn, make it the dropdown
                        TableColumn excuseColumn = tbAttendance.getColumnModel().getColumn(2);
                        excuseColumn.setCellEditor(new DefaultCellEditor(excuseDrop));

                        TableColumn presentColumn = tbAttendance.getColumnModel().getColumn(1);
                        presentColumn.setCellEditor(new DefaultCellEditor(presentBox));


                        //load check boxes
                        for (int i = 0; i < presenceData.length; i++) {
                            tbAttendance.setValueAt(presenceData[i][1], i, 1);
                        }

                        //load dropdowns
                        for (int i = 0; i < presenceData.length; i++) {
                            tbAttendance.setValueAt(presenceData[i][2], i, 2);
                        }
                        //LIke this:
                        //tbAttendance.setValueAt("No excuse", 0, 2);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                    //Notes
                    try {
                        taNotes.setText(setNotesArea.setNotes(currentClass));
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        addGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //initiation etc
                currentClass = listClassesGrades.getSelectedValue();

                //main stuff
                try {
                    String[][] data_grade = setGradeTable.addGrade(currentClass);
                    String[] headers = new String[data_grade[0].length];
                    headers[0] = "Student Name";
                    for (int i = 1; i < data_grade[0].length; i++) {
                        headers[i] = "Grade " + i;
                    }

                    tbGrades.setModel(new DefaultTableModel(data_grade, headers));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        //--------- ATTENDANCE

        pickDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pickDate pickDat = new pickDate();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //date was changed

            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < presenceData.length; i++) {
                    for (int j = 0; j < presenceData[i].length; j++) {
                        presenceData[i][j] = String.valueOf(tbAttendance.getValueAt(i, j));
                        System.out.println(presenceData[i][j]);
                        //THEN ACTUALLY SAVE IT AFTER
                        setAttendanceTable.saveTable(currentClass, presenceData, date);
                    }
                }
            }
        });


        saveNotesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setNotesArea.saveText(taNotes.getText(), currentClass);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    static void mainInit() {
        //make all the lists and that (READ FROM ALL FILES AND MAKE SHIT)
        try (ObjectInputStream streamIn = new ObjectInputStream(new FileInputStream("classNames.dat"))) {
            allClasses = (String[]) streamIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            allClasses = new String[0];
        }
    }

}