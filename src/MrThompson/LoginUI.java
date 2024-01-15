package MrThompson;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame{
    /*
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int)screen.getWidth();
    int height = (int)screen.getHeight();
    */

    private JPanel panel1;
    private JPasswordField pfPass;
    private JCheckBox cbR;
    private JButton btnLogin;
    loginInit loginInit = new loginInit();

    public LoginUI() {
        setContentPane(panel1);
        setTitle("Yes");
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        //----------------------------------------------------------
        String pass = "1234"; //password is hard coded

        boolean rememberMe = loginInit.rememberMeInit(); //should the checkbox be filled and the password be filled in?
        if (rememberMe) {
            cbR.setSelected(true);
            pfPass.setText(pass); //change to json
        }
        else {
            cbR.setSelected(false);
        }

        btnLogin.addActionListener(new ActionListener() { //called on login button usage
            @Override
            public void actionPerformed(ActionEvent e) { //if password is right you can call the main class.
                if (pfPass.getText().equals(pass)) {
                    try {
                        mainUI ui = new mainUI();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(false); //closes login window
                    dispose();
                }
            }
        });

        cbR.addActionListener(new ActionListener() { //On checkbox usage
            //this writes the boolean "rememberMe" to its appropriate file
            @Override
            public void actionPerformed(ActionEvent e) {
                try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("rememberMe.dat"));) {
                    stream.writeBoolean(cbR.isSelected());
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });
    }
}
