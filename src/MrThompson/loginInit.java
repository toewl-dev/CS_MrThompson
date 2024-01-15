package MrThompson;
import java.io.*;
public class loginInit {
    public boolean rememberMeInit() {
        //This method simply reads the file "rememberMe.dat" and returns true if the file says so.
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream("rememberMe.dat"));){
            return stream.readBoolean();
        } catch (IOException e) {
            return false;
        }
    }
}
