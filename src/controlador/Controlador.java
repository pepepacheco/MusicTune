package controlador;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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
	private int numeroRegistro;
	private boolean busquedaRealidada = false;
	private final String[] CABEZERA = {"Nombre","Álbum","Artista","Año","Género","Duración", "Número"};
	
	//Pasamos una referencia de la vista para acceder a sus Clases.
	public Controlador(Vista v){
		vista = v;
		eventos();
	}
	
	//Método donde se van a manejar todos los eventos.
	private void eventos(){
		vista.getAbrir().addActionListener(r->{			
			try {
		        if (vista.getFile().showOpenDialog(vista.getFrame()) == JFileChooser.APPROVE_OPTION){
		        	File archivo = vista.getFile().getSelectedFile();
		        	Service.loadJson(archivo);
		        }
		       
				vista.getTabla().setModel(new MiTableModel(PlayList.getListaReproduccion(), CABEZERA));

			} catch (IOException | InvalidYearException | InvalidDurationException | InvalidTackNumberException  e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(vista.getFrame(), "Archivo de datos incorrecto", "Error de lectura", JOptionPane.ERROR_MESSAGE);
			} catch (IllegalStateException i){
				JOptionPane.showMessageDialog(vista.getFrame(), "Seleccione un archivo válido", "JSON Incorecto", JOptionPane.INFORMATION_MESSAGE);
			}
			 
		});

		vista.getSalir().addActionListener(r->{
			System.exit(0);
		});
		
		vista.getBtnMostrarResultado().addActionListener(r->{
			if (PlayList.getListaReproduccion().size() > 0)
				buscarPorCategoria();
		});
		
		vista.getTabla().getSelectionModel().addListSelectionListener(r->{
			numeroRegistro = vista.getTabla().getSelectedRow();				
			String[] campos = new String[7];
			for (int i = 0; i < 7; i++) {
				if (numeroRegistro != -1)
					campos[i] = (String) vista.getTabla().getModel().getValueAt(numeroRegistro, i);
			}
			vista.getTextAreaNombre().setText(campos[0]);
			vista.getTextAreaAlbum().setText(campos[1]);
			vista.getTextAreaArtista().setText(campos[2]);
			vista.getTextAreaAnio().setText(campos[3]);
			vista.getTextAreaGenero().setText(campos[4]);
			if (campos[5] != null)
				campos[5] = campos[5].replace(" ms", "");
			vista.getTextAreaDuracion().setText(campos[5]);
			vista.getTextAreaNumero().setText(campos[6]);
			
			String artistaActual="";
			String artistaElegido = vista.getTextAreaArtista().getText();
			Image image = null;
			if (!artistaElegido.equals(artistaActual)){
				artistaActual = artistaElegido;
		        try {
		        	URL url = null;
		        	switch (vista.getTextAreaArtista().getText()){
		        		case "Nirvana":
		        			url = new URL("https://yt3.ggpht.com/-rWzHbL2Maq8/AAAAAAAAAAI/AAAAAAAAAAA/wIkuF8fiHeY/s100-c-k-no-rj-c0xffffff/photo.jpg");
		        			break;
		        		case "Pink Floyd":
			        		url = new URL("https://yt3.ggpht.com/-rWzHbL2Maq8/AAAAAAAAAAI/AAAAAAAAAAA/wIkuF8fiHeY/s100-c-k-no-rj-c0xffffff/photo.jpg");
			        		break;
		        		case "Dire Straits":
		        			url = new URL("http://a4.mzstatic.com/us/r30/Music/v4/c6/55/6d/c6556dd3-e25d-62d4-19ae-204c78185b96/cover100x100.jpeg");
		        			break;
		        		case "AC/DC":
		        			url = new URL("http://a3.mzstatic.com/us/r30/Music4/v4/07/4d/8b/074d8b60-3b27-4527-1243-cf12e0d3759b/cover100x100.jpeg");
		        			break;
		        		case "Queen":
		        			url = new URL("http://a1.mzstatic.com/us/r30/Music/v4/db/67/b2/db67b24d-daff-e2ba-d08f-d97227c80abb/cover100x100.jpeg");
		        			break;
		        		case "Muse":
		        			url = new URL("http://coolchaser-static.coolchaser.com/images/themes/t/-i293.photobucket.com-albums-mm51-gabster806-Muse-180px-Muse_logo.png");
		        			break;
		        		default:
		        			url = new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Imagen_no_disponible.svg/100px-Imagen_no_disponible.svg.png");	
		        	}		            
		            image = ImageIO.read(url);
		        } catch (IOException e) {
		        	//e.printStackTrace();
		        }
		        vista.getJLabelImagen().setIcon(new ImageIcon(image));
			}
			
			if (!busquedaRealidada)
				vista.getLblRegistro().setText("Registro "+(numeroRegistro+1)+" de "+PlayList.getListaReproduccion().size());
			else
				vista.getLblRegistro().setText("Registro "+(numeroRegistro+1)+" de "+lista.size());
		});
		
		vista.getBtnSiguiente().addActionListener(r->{		
			if (vista.getTabla().getSelectedRow() < (vista.getTabla().getRowCount()-1))
				vista.getTabla().setRowSelectionInterval(vista.getTabla().getSelectedRow()+1, vista.getTabla().getSelectedRow()+1);
		});
		
		vista.getBtnAnterior().addActionListener(r->{
			if (vista.getTabla().getSelectedRow() > 0)
				vista.getTabla().setRowSelectionInterval(vista.getTabla().getSelectedRow()-1, vista.getTabla().getSelectedRow()-1);
		});
		
		vista.getBtnAnadirCampo().addActionListener(r->{
			Cancion cancion = null;
			try {
				cancion = new Cancion(vista.getTextAreaNombre().getText(), vista.getTextAreaAlbum().getText(), vista.getTextAreaArtista().getText(),
						vista.getTextAreaAnio().getText(), vista.getTextAreaGenero().getText(), vista.getTextAreaDuracion().getText(), vista.getTextAreaNumero().getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(vista.getFrame(), "Vuelva a introducir los datos correctamente", "Datos Incorrectos", JOptionPane.INFORMATION_MESSAGE);
				//e.printStackTrace();
			}
			boolean repetida = false;
			if (cancion != null){
				for (int i = 0; i < PlayList.getListaReproduccion().size()-1; i++) {
					if (PlayList.getListaReproduccion().get(i) instanceof Cancion ){
						if (PlayList.getListaReproduccion().get(i).equals(cancion)){
							PlayList.getListaReproduccion().remove(PlayList.getListaReproduccion().size()-1);
							repetida = true;
							JOptionPane.showMessageDialog(vista.getFrame(), "Esta canción ya existe", "Alerta", JOptionPane.WARNING_MESSAGE);
							break;
						};
					}
				}
				if (!repetida){

					int result = JOptionPane.showConfirmDialog(vista.getFrame(), "¿Desea introducir un nuevo campo?", "Aviso", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION)
						vista.getTabla().setModel(new MiTableModel(PlayList.getListaReproduccion(), CABEZERA));
					else
						PlayList.getListaReproduccion().remove(PlayList.getListaReproduccion().size()-1);						
				}
			}
		});
	}
	//Método que modifica el JTable según un critero de búsqueda.
	private  void buscarPorCategoria(){
		lista = new ArrayList<PlayList>();

		switch (vista.getComboBox().getSelectedItem().toString()){
			case "Nombre":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreCancion().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "\u00C1lbum":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreAlbum().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "Artista":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreArtista().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "A\u00F1o":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return (c.getAnio()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "G\u00E9nero":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getGenero().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "N\u00FAmero":
				lista = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return (c.getNumeroCancion()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
		}
		vista.getTabla().setModel(new MiTableModel(lista, CABEZERA));
		
	}
	

	
}
