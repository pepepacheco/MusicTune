package controlador;

import modelo.exceptions.InvalidTackNumberException;
import modelo.exceptions.InvalidYearException;
import modelo.exceptions.InvalidDurationException;
import vista.Vista;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Rafael Vargas del Moral
 */
public class Main {
    public static void main(String args[]) {

            try {
                Service.loadJson();
                java.awt.EventQueue.invokeLater(() -> {
                    new Vista().getFrame().setVisible(true);
                });
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Windows".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("GTK+".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (IOException | InvalidYearException | InvalidDurationException | InvalidTackNumberException |
            ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
