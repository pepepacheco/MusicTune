package controlador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import modelo.Cancion;
import modelo.PlayList;
import modelo.exceptions.InvalidDurationException;
import modelo.exceptions.InvalidTackNumberException;
import modelo.exceptions.InvalidYearException;
import vista.Vista;

/**
 * @author Rafael Vargas del Moral
 * @version 1.0
 */

public class Controlador {
	private Vista vista;
	private List<PlayList> lista;
	
	//Pasamos una referencia de la vista para acceder a sus Clases.
	public Controlador(Vista v){
		vista = v;
		eventos();
	}
	
	//M�todo donde se van a manejar todos los eventos.
	private void eventos(){
		vista.getAbrir().addActionListener(r->{			
			try {
		        int returnVal = vista.getFile().showOpenDialog(vista.getFrame());

		        if (returnVal == JFileChooser.APPROVE_OPTION){
		        	File archivo = vista.getFile().getSelectedFile();
		        	Service.loadJson(archivo);
		        }
		       
				vista.getTabla().setModel(new MiTableModel(PlayList.getListaReproduccion(), 7));
				setCabezera();

			} catch (IOException | InvalidYearException | InvalidDurationException | InvalidTackNumberException  e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(vista.getFrame(), "Error de lectura de archivo");
			} catch (IllegalStateException i){
				JOptionPane.showMessageDialog(vista.getFrame(), "JSON incorrecto");
			}
			 
		});

		vista.getSalir().addActionListener(r->{
			System.exit(0);
		});
		
		vista.getBtnMostrarResultado().addActionListener(r->{
			if (PlayList.getListaReproduccion().size() > 0)
				buscarPorCategoria();
		});
		
		vista.getTabla().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int numero = vista.getTabla().getSelectedRow();
				String[] campos = new String[7];
				for (int i = 0; i < 7; i++) {
					campos[i] = (String) vista.getTabla().getModel().getValueAt(numero, i);
				}
				vista.getTextAreaNombre().setText(campos[0]);
				vista.getTextAreaAlbum().setText(campos[1]);
				vista.getTextAreaArtista().setText(campos[2]);
				vista.getTextAreaAnio().setText(campos[3]);
				vista.getTextAreaGenero().setText(campos[4]);
				vista.getTextAreaDuracion().setText(campos[5]);
				vista.getTextAreaNumero().setText(campos[6]);
				
			}
		});
	}
	
	//M�todo que modifica el JTable seg�n un critero de b�squeda.
	private  void buscarPorCategoria(){
		lista = new ArrayList<PlayList>();
		
		switch (vista.getComboBox().getSelectedItem().toString()){
			case "Nombre":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreCancion().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "\u00C1lbum":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreAlbum().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "Artista":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreArtista().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "A\u00F1o":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return (c.getAnio()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "G\u00E9nero":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getGenero().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "N\u00FAmero":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return (c.getNumeroCancion()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
		}
		vista.getTabla().setModel(new MiTableModel(lista, 7));
		setCabezera();
	}
	
	//Modificaci�n de la cabecera por defecto del JTable.
	private void setCabezera(){
		String[] cabecera = {"Nombre","Álbum","Artista","Año","Género","Duración", "Número"};
		for (int i = 0; i < cabecera.length; i++) {
			vista.getTabla().getTableHeader().getColumnModel().getColumn(i).setHeaderValue(cabecera[i]);
		}
		vista.getTabla().getTableHeader().setBackground(Color.GRAY);;
	}
	
}
