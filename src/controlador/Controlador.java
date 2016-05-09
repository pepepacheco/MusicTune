package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractListModel;
import modelo.PlayList;
import vista.Vista;

/**
 * @author Rafael Vargas del Moral
 */

public class Controlador {
	private Vista vista;
	private List<PlayList> lista;
	
	public Controlador(Vista v){
		vista = v;
		inicializar();
	}
	
	public void inicializar(){
		lista = PlayList.getListaReproduccion();
		vista.getJList().setModel(new AbstractListModel() {
			
			public int getSize() {
				return lista.size();
			}
			public Object getElementAt(int index) {
				return lista.get(index);
			}
		});
		
		vista.getSalir().addActionListener(r->{
			System.exit(0);
		});
		
	}
}
