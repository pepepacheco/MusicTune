package controlador;

import vista.VistaGeneral;

public class Main {
    public static void main(String args[]) {
    		java.awt.EventQueue.invokeLater(() -> {
    			new VistaGeneral().getFrame().setVisible(true);
    		});
    }
}
