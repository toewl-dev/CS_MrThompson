package MrThompson;

import javax.swing.*;
import javax.tools.Tool;
import java.io.*;
import java.util.Arrays;

public class setGradeTable {
    //This class contains 3 methods used to make changes to the grades tab in the program.
    avgMaths Tools = new avgMaths(); //Uses avgMaths class for calculations
    public String[][] setGradeTb(String className) throws IOException {
        //This method reads the grades file for the given class. If the file does not exist
        //it creates the file.

        //make file of grades
        File gradeFile = new File(className + "Grades.dat");
        gradeFile.createNewFile(); //does nothing if already exists
        String[] students;
        String[][] gradeArray;

        //if theres already a class file
        //open it and read to it a 2d array

        try (ObjectInputStream streamIn = new ObjectInputStream(new FileInputStream(className + ".dat"))) {
             System.out.println("Opened class file: " + className + ".dat");
             //NOW, read the thing
             students = (String[]) streamIn.readObject();
             students = Arrays.copyOf(students, students.length - 1); //make it without class name
             System.out.println(Arrays.toString(students));

            //now we have the array of names
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        //need to open grades and read the file
        try (ObjectInputStream streamGradesIn = new ObjectInputStream(new FileInputStream(gradeFile))) {
            gradeArray = (String[][]) streamGradesIn.readObject();
            for (int i = 0; i < students.length; i++) {
                System.out.println(Arrays.toString(gradeArray[i]));
            }
        } catch (EOFException eo) { //couldn't read the object so just make a 2d array with one element (names)
            gradeArray = new String[students.length][1];
            for (int i = 0; i < students.length; i++) {
                gradeArray[i][0] = students[i];
                //System.out.println(Arrays.toString(gradeArray[i]));
            }
            try (ObjectOutputStream streamGradesOut = new ObjectOutputStream(new FileOutputStream(gradeFile))) {
                streamGradesOut.writeObject(gradeArray);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return gradeArray;
    }

    public String[][] addGrade(String className) throws IOException {
        //This method adds 1 element for each of the students rows with another grade.
        //It uses a JOptionPane to type in the grade for each student then writes the new (longer) 2d array to a file
        String[][] gradeArr;
        String grade;

        File fileGrades = new File(className + "Grades.dat");
        try (ObjectInputStream streamIn = new ObjectInputStream(new FileInputStream(fileGrades))) {
            gradeArr = (String[][]) streamIn.readObject();
            //System.out.println(gradeArr.length + " = length");
            for (int i = 0; i < gradeArr.length; i++) {
                gradeArr[i] = Arrays.copyOf(gradeArr[i], gradeArr[i].length + 1);
                while (true) {
                    grade = JOptionPane.showInputDialog("Enter grade for " + gradeArr[i][0] + ":");
                    try {
                        Integer.parseInt(grade);
                        break;
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Value must be a number",
                                "Oops!", JOptionPane.ERROR_MESSAGE);
                    }
                }
                //make list longer etc.
                gradeArr[i][gradeArr[i].length-1] = grade;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (ObjectOutputStream streamOut = new ObjectOutputStream(new FileOutputStream(fileGrades))) {
            streamOut.writeObject(gradeArr);
        }
        //System.out.println(Arrays.toString(gradeArr[0]));
        return gradeArr;
    }

    public String[][] averages(String className) throws IOException {
        //This method computes the averages and linear regression for the grades tab.
        String[][] gradeArr;
        File fileGrades = new File(className + "Grades.dat");
        try (ObjectInputStream streamIn = new ObjectInputStream(new FileInputStream(fileGrades))) {
            gradeArr = (String[][]) streamIn.readObject();
            String[][] avg = new String[gradeArr.length][3];

            for (int i = 0; i < avg.length; i++) {
                //make average
                System.out.println(Arrays.toString(gradeArr[i]));
                double[] neededNums = new double[gradeArr[i].length - 1];
                double[] n = new double[neededNums.length];

                for (int j = 0; j < gradeArr[i].length - 1; j++) {
                    neededNums[j] = Integer.parseInt(gradeArr[i][j + 1]);
                    System.out.println(Arrays.toString(neededNums)); //print list so far
                }
                System.out.println("hello " + Arrays.toString(neededNums));
                double mean = Tools.mean(neededNums);
                avg[i][0] = String.valueOf(mean);

                //System.out.println("======================");
                //System.out.println("length: " + neededNums.length);           -------------->> Some debugging
                //System.out.println("list: " + Arrays.toString(neededNums));
                //System.out.println("======================");

                //make regression
                double r = Tools.fitLinearRegression(neededNums); //sets R to the regression of the values
                avg[i][1] = String.valueOf(r);
                String msg;
                if (r >= 0) {
                    msg = "Student is improving";
                } else {
                    msg = "Student is worsening";
                }
                avg[i][2] = msg;

            }
            return avg; //RETURNS THE FINAL ARRAY OF AVERAGES

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
