package controlador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
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
	
	public Controlador(Vista v){
		vista = v;
		eventos();
	}
	
	public void eventos(){

		vista.getAbrir().addActionListener(r->{			
			try {
		        int returnVal = vista.getFile().showOpenDialog(vista.getFrame());

		        if (returnVal == JFileChooser.APPROVE_OPTION){
		        	File archivo = vista.getFile().getSelectedFile();
		        	Service.loadJson(archivo);
		        }
		       
				vista.getTabla().setModel(new MiTableModel(PlayList.getListaReproduccion(), 7));
				setCabezera();
				//vista.getTabla().setMo

			} catch (IOException | InvalidYearException | InvalidDurationException | InvalidTackNumberException  e) {
				//e.printStackTrace();
			}
		});

		vista.getSalir().addActionListener(r->{
			System.exit(0);
		});
		
		vista.getBtnIrIr().addActionListener(r->{
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
	
	private  void buscarPorCategoria(){
		List lista = new ArrayList();
		
		switch (vista.getComboBox().getSelectedItem().toString()){
			case "Nombre":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					List<PlayList> resultado = new ArrayList<PlayList>();
					Cancion c = (Cancion) p;
					return c.getNombreCancion().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "\u00C1lbum":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					List<PlayList> resultado = new ArrayList<PlayList>();
					Cancion c = (Cancion) p;
					return c.getNombreAlbum().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "Artista":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					List<PlayList> resultado = new ArrayList<PlayList>();
					Cancion c = (Cancion) p;
					return c.getNombreArtista().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "A\u00F1o":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					List<PlayList> resultado = new ArrayList<PlayList>();
					Cancion c = (Cancion) p;
					return (c.getAnio()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "G\u00E9nero":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					List<PlayList> resultado = new ArrayList<PlayList>();
					Cancion c = (Cancion) p;
					return c.getGenero().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
			case "N\u00FAmero":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					List<PlayList> resultado = new ArrayList<PlayList>();
					Cancion c = (Cancion) p;
					return (c.getNumeroCancion()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				vista.getTabla().setModel(new MiTableModel(lista, 7));
				break;
		}
		vista.getTabla().setModel(new MiTableModel(lista, 7));
		setCabezera();
	}
	
	private void setCabezera(){
		String[] cabecera = {"Nombre","Álbum","Artista","Año","Género","Duración", "Número"};
		for (int i = 0; i < cabecera.length; i++) {
			vista.getTabla().getTableHeader().getColumnModel().getColumn(i).setHeaderValue(cabecera[i]);
		}
		vista.getTabla().getTableHeader().setBackground(Color.GRAY);;
	}
	
}
