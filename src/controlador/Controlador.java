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

import modelo.Album;
import modelo.Artista;
import modelo.Cancion;
import modelo.ConexionBD;
import modelo.CrearTablas;
import modelo.PlayList;
import modelo.exceptions.EmptyFieldsException;
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
	private List<PlayList> listaResultado;
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
		        try {
		        	CrearTablas.crearTabla(ConexionBD.getConexion());
					Cancion.addCancionBD();
					Album.addAlbumBD();
					Artista.addArtistaBD();			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				vista.getTabla().setModel(new MiTableModel(PlayList.getListaReproduccion(), CABEZERA));

			} catch (IOException | InvalidYearException | InvalidDurationException | InvalidTackNumberException | EmptyFieldsException e) {
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
				vista.getLblRegistro().setText("Registro "+(numeroRegistro+1)+" de "+listaResultado.size());
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
			} catch (InvalidYearException y) {
				JOptionPane.showMessageDialog(vista.getFrame(), "Introduzca un año válido", "Datos Incorrectos", JOptionPane.INFORMATION_MESSAGE);
				//e.printStackTrace();
			} catch (InvalidDurationException d) {
				JOptionPane.showMessageDialog(vista.getFrame(), "Introduzca una duración válida", "Datos Incorrectos", JOptionPane.INFORMATION_MESSAGE);
				//e.printStackTrace();
			} catch (InvalidTackNumberException t) {
				JOptionPane.showMessageDialog(vista.getFrame(), "Introduzca un número de canción válido", "Datos Incorrectos", JOptionPane.INFORMATION_MESSAGE);
				//e.printStackTrace();
			} catch (EmptyFieldsException e) {
				JOptionPane.showMessageDialog(vista.getFrame(), "No puede haber campos vacíos", "Datos Incorrectos", JOptionPane.INFORMATION_MESSAGE);
				//e.printStackTrace();
			}
			boolean repetida = false;
			//Comprobamos si la canción está repetida
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
				// Si no está repetida, añadiremos la canción a la lista
				if (!repetida){
					vista.getTabla().setModel(new MiTableModel(PlayList.getListaReproduccion(), CABEZERA));
					JOptionPane.showMessageDialog(vista.getFrame(), "Pulse aceptar para continuar", "Canción guardada correctamente", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		vista.getBtnBorrarCampo().addActionListener(r->{
			//Compruebo si el campo a borrar es resultado de una búsqueda o del listado global
			if (listaResultado != null && listaResultado.size() > 0)
				borrarCancion(listaResultado);
			else
				borrarCancion(PlayList.getListaReproduccion());
		});
		
		vista.getBtnLimpiarFormulario().addActionListener(r->{
			vista.getTextAreaNombre().setText("");
			vista.getTextAreaArtista().setText("");
			vista.getTextAreaAlbum().setText("");
			vista.getTextAreaGenero().setText("");
			vista.getTextAreaAnio().setText("");
			vista.getTextAreaDuracion().setText("");
			vista.getTextAreaNumero().setText("");
		});
	}
	private void borrarCancion(List<PlayList> lista) {
		if (vista.getTabla().getSelectedRow() != -1){
			Cancion cancionEliminar = null;
			int result = JOptionPane.showConfirmDialog(vista.getFrame(), "¿Desea eliminar "
			+((Cancion)lista.get(vista.getTabla().getSelectedRow())).getNombreCancion()+" ?", "Aviso", JOptionPane.YES_NO_OPTION);
			
			if (result == JOptionPane.YES_OPTION){
				cancionEliminar =(Cancion) lista.get(vista.getTabla().getSelectedRow());
				lista.remove(cancionEliminar);
				vista.getTabla().setModel(new MiTableModel(lista, CABEZERA));
			}
			
			//Vamos a comprobar si la canción borrada es resultado de una búsqueda, en ese caso la eliminaremos tambíen de la lista general		
			if (cancionEliminar != null){
				for (PlayList playList : PlayList.getListaReproduccion()) {
					if (cancionEliminar.equals(((Cancion)playList))){
						PlayList.getListaReproduccion().remove(playList);
						break;
					}
				}
			}			
		}
	}
	//Método que modifica el JTable según un critero de búsqueda.
	private  void buscarPorCategoria(){
		listaResultado = new ArrayList<PlayList>();

		switch (vista.getComboBox().getSelectedItem().toString()){
			case "Nombre":
				listaResultado = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreCancion().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "\u00C1lbum":
				listaResultado = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreAlbum().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "Artista":
				listaResultado = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getNombreArtista().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "A\u00F1o":
				listaResultado = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return (c.getAnio()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "G\u00E9nero":
				listaResultado = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return c.getGenero().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
			case "N\u00FAmero":
				listaResultado = Cancion.BuscarCancion(PlayList.getListaReproduccion(), p->{
					Cancion c = (Cancion) p;
					return (c.getNumeroCancion()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				busquedaRealidada = true;
				break;
		}
		//Modifico el modelo del JTable con la lista que me devuelve el switch
		vista.getTabla().setModel(new MiTableModel(listaResultado, CABEZERA));		
	}
}
