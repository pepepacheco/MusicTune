package controlador;

import vista.Vista;

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
