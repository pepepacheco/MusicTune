package controlador;

import vista.Vista;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;

import modelo.AlbumDTO;

/**
 * @author Rafael Vargas del Moral
 */
public class Main {
    public static void main(String args[]) {
    		java.awt.EventQueue.invokeLater(() -> {
    			new Vista().getFrame().setVisible(true);
    		});
    }
}
