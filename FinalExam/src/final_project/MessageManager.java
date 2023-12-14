package final_project;

import javax.swing.*;

public class MessageManager {
    static public void printFailedMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Message",
                JOptionPane.ERROR_MESSAGE);
    }

    static public void printSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
