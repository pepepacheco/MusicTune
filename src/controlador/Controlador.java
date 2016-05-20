package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import modelo.AlbumDAOImpSQLite;
import modelo.AlbumDTO;
import modelo.ArtistaDAOImpSQLite;
import modelo.ArtistaDTO;
import modelo.CancionDAOImpSQLite;
import modelo.CancionDTO;
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
	private List<CancionDTO> listaResultado;
	private int numeroRegistro;
	private boolean busquedaRealidada = false;
	private final String[] CABEZERA = {"Nombre","Álbum","Artista","Año","Género","Duración", "Número"};
	private int antiguoIndice = -1;
	private CancionDAOImpSQLite cancionDAO;
	private ArtistaDAOImpSQLite artistaDAO;
	private AlbumDAOImpSQLite albumDAO;
	
	//Pasamos una referencia de la vista para acceder a sus Clases.
	public Controlador(Vista v){
		vista = v;
		eventos();
		loadBD();
		
	}
	private void loadBD() {
		File bd = new File("database.db");
		if (bd.exists() && bd.length() > 4000){
			try {
				Service.autoLoad();
			} catch (InvalidYearException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidDurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidTackNumberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EmptyFieldsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));
		}
	}
	
	
	
	//Método donde se van a manejar todos los eventos.
	private void eventos(){
		
		vista.getAbrir().addActionListener(r->{
	    	vista.getFile().setFileFilter(vista.getFiltro());	       	
	        if (vista.getFile().showOpenDialog(vista.getFrame()) == JFileChooser.APPROVE_OPTION){
				try {
		        	Service.loadJson(vista.getFile().getSelectedFile());		        	
		        	JOptionPane.showMessageDialog(vista.getFrame(), "Introduciendo datos en la base de datos", "Espere...", JOptionPane.INFORMATION_MESSAGE);
		        	artistaDAO = new ArtistaDAOImpSQLite();
		        	artistaDAO.crearTalba();
		        	artistaDAO.addArtista(PlayList.getListaArtistas());
		        	albumDAO = new AlbumDAOImpSQLite();
		        	albumDAO.crearTalba();
		        	albumDAO.addAlbum(PlayList.getListaAlbumes());
		        	cancionDAO = new CancionDAOImpSQLite();
		        	cancionDAO.crearTalba();
		        	cancionDAO.addCancion(PlayList.getListaCanciones());
		        	Service.crearVista();
					vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));
	
				} catch (IOException | InvalidYearException | InvalidDurationException | InvalidTackNumberException | EmptyFieldsException e) {
					//e.printStackTrace();
					JOptionPane.showMessageDialog(vista.getFrame(), "Archivo de datos incorrecto", "Error de lectura", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalStateException i){
					//i.printStackTrace();
					JOptionPane.showMessageDialog(vista.getFrame(), "Seleccione un archivo válido", "JSON Incorecto", JOptionPane.INFORMATION_MESSAGE);
				}				 
	        }
		});

		vista.getSalir().addActionListener(r->{
			System.exit(0);
		});

		
		vista.getTextAreaBuscar().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					if (PlayList.getListaCanciones().size() > 0)
						buscarPorCategoria();
				}
			}
		});
		
		vista.getBtnMostrarResultado().addActionListener(r->{
			if (PlayList.getListaCanciones().size() > 0)
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

			if (!artistaElegido.equals(artistaActual)){
				artistaActual = artistaElegido;
		        String url;
				switch (vista.getTextAreaArtista().getText()){
					case "Nirvana":
						url = "resources/nirvana.jpg";
						break;
					case "Pink Floyd":
						url = "resources/pink_floyd.jpg";
						break;
					case "Dire Straits":
						url = "resources/dire_straits.jpeg";
						break;
					case "AC/DC":
						url = "resources/ac_dc.jpeg";
						break;
					case "Queen":
						url = "resources/queen.jpg";
						break;
					case "Muse":
						url = "resources/muse.jpg";
						break;
					default:
						url = "resources/no_image.jpg";	
				}
				vista.getJLabelImagen().setIcon(new ImageIcon(url));
			}
			
			if (!busquedaRealidada)
				vista.getLblRegistro().setText("Registro "+(numeroRegistro+1)+" de "+PlayList.getListaCanciones().size());
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
			addCancion();
		});
		
		vista.getBtnModificar().addActionListener(r->{
			try {
				CancionDTO posibleCancion = new CancionDTO(vista.getTextAreaNombre().getText(), vista.getTextAreaAlbum().getText(), vista.getTextAreaArtista().getText(),
				vista.getTextAreaAnio().getText(), vista.getTextAreaGenero().getText(), vista.getTextAreaDuracion().getText(), vista.getTextAreaNumero().getText());
				if (!cancionRepetida(posibleCancion, false))
					modificarCancionDesdeBoton(posibleCancion);

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
		});
		
		vista.getBtnBorrarCampo().addActionListener(r->{
			//Compruebo si el campo a borrar es resultado de una búsqueda o del listado global
			if (listaResultado != null && listaResultado.size() > 0){
				borrarCancion(listaResultado);
			}		
			else
				borrarCancion(PlayList.getListaCanciones());
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

		
		vista.getTabla().getSelectionModel().addListSelectionListener(r->{
			if (antiguoIndice != -1){	
				try {
					CancionDTO posibleCancion = new CancionDTO(vista.getTextAreaNombre().getText(), vista.getTextAreaAlbum().getText(), vista.getTextAreaArtista().getText(),
					vista.getTextAreaAnio().getText(), vista.getTextAreaGenero().getText(), vista.getTextAreaDuracion().getText(), vista.getTextAreaNumero().getText());
				
					if (!(cancionRepetida(posibleCancion, true))){
						modificarCancion(posibleCancion);
					}				
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
			antiguoIndice = vista.getTabla().getSelectedRow();
		});		
		
		vista.getMntmExportarPdf().addActionListener(r->{
			
			Document documento = new Document();	
			try {
				if (vista.getFile().showSaveDialog(vista.getFrame()) == JFileChooser.APPROVE_OPTION){
					PdfWriter.getInstance(documento, new FileOutputStream(vista.getFile().getSelectedFile()));				
					documento.open();
					PdfPTable tabla = new PdfPTable(7);
					tabla.setHeaderRows(1);
					for (String campo : CABEZERA) {
						tabla.addCell(campo);
					}					
					for (CancionDTO cancion : PlayList.getListaCanciones()) {
						tabla.addCell(new Phrase(cancion.getNombreCancion(), FontFactory.getFont(FontFactory.HELVETICA, 6)));
						tabla.addCell(new Phrase(cancion.getNombreAlbum(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
						tabla.addCell(new Phrase(cancion.getNombreArtista(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
						tabla.addCell(new Phrase(cancion.getYearAlbum()+"", FontFactory.getFont(FontFactory.HELVETICA, 12)));
						tabla.addCell(new Phrase(cancion.getGenero(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
						tabla.addCell(new Phrase(cancion.getDuracion()+"", FontFactory.getFont(FontFactory.HELVETICA, 12)));
						tabla.addCell(new Phrase(cancion.getNumeroCancion()+"", FontFactory.getFont(FontFactory.HELVETICA, 12)));
					}					
				documento.add(tabla);
				documento.close();
				}
			} catch (FileNotFoundException | DocumentException e) {
				JOptionPane.showMessageDialog(vista.getFrame(), "No ha sido posible exportar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			} 
		});
	}
	
	
	private void addCancion() {
		CancionDTO cancion = null;
		try {
			cancion = new CancionDTO(vista.getTextAreaNombre().getText(), vista.getTextAreaAlbum().getText(), vista.getTextAreaArtista().getText(),
					vista.getTextAreaAnio().getText(), vista.getTextAreaGenero().getText(), vista.getTextAreaDuracion().getText(), vista.getTextAreaNumero().getText());
			
			new AlbumDTO(vista.getTextAreaAlbum().getText(), vista.getTextAreaAnio().getText());
			new ArtistaDTO(vista.getTextAreaArtista().getText());
			
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

		//Comprobamos si la canción está repetida o que se esté accediendo a este método mediante el evento de un botón
		// Si no está repetida, añadiremos la canción a la lista
		if (!(cancionRepetida(cancion, false))){
			vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));
			listaResultado = PlayList.getListaCanciones();
			JOptionPane.showMessageDialog(vista.getFrame(), "Pulse aceptar para continuar", "Canción guardada correctamente", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private boolean cancionRepetida(CancionDTO cancion, boolean formulario){
		if (cancion != null){
			for (int i = 0; i < PlayList.getListaCanciones().size()-1; i++) {
				if (PlayList.getListaCanciones().get(i) instanceof CancionDTO ){
					if (PlayList.getListaCanciones().get(i).equals(cancion)){
						PlayList.getListaCanciones().remove(PlayList.getListaCanciones().size()-1);
						if (formulario == true)					
							return true;						
						else{
							JOptionPane.showMessageDialog(vista.getFrame(), "Esta canción ya existe", "Alerta", JOptionPane.WARNING_MESSAGE);
							return true;
						}
					};
				}
			}
		}
		return false;
	}
	
	private void modificarCancion(CancionDTO posibleCancion){
		int result = JOptionPane.showConfirmDialog(vista.getFrame(), "¿Desea modificar el registro anterior");
		if (result == JOptionPane.YES_OPTION){
			if (listaResultado != null && listaResultado.size() > 0){
				CancionDTO cancionAModificar = listaResultado.get(antiguoIndice);
				int indice = PlayList.getListaCanciones().indexOf(cancionAModificar);
				PlayList.getListaCanciones().set(indice, posibleCancion);				
				PlayList.getListaCanciones().remove(PlayList.getListaCanciones().size()-1);
			}
			else{
				PlayList.getListaCanciones().set(antiguoIndice, posibleCancion);
				PlayList.getListaCanciones().remove(PlayList.getListaCanciones().size()-1);
			}		
			
			listaResultado = PlayList.getListaCanciones();
			vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));
			JOptionPane.showMessageDialog(vista.getFrame(), "Pulse aceptar para continuar", "registro modificado correctamente", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			PlayList.getListaCanciones().remove(posibleCancion);
	}
	
	private void modificarCancionDesdeBoton(CancionDTO posibleCancion){
		int result = JOptionPane.showConfirmDialog(vista.getFrame(), "¿Desea modificar este registro");
		if (result == JOptionPane.YES_OPTION){
			if (listaResultado != null && listaResultado.size() > 0){
				CancionDTO cancionAModificar = listaResultado.get(vista.getTabla().getSelectedRow());
				int indice = PlayList.getListaCanciones().indexOf(cancionAModificar);
				PlayList.getListaCanciones().set(indice, posibleCancion);				
				PlayList.getListaCanciones().remove(PlayList.getListaCanciones().size()-1);
			}
			else{
				PlayList.getListaCanciones().set(vista.getTabla().getSelectedRow(), posibleCancion);
				PlayList.getListaCanciones().remove(PlayList.getListaCanciones().size()-1);
			}		
			
			listaResultado = PlayList.getListaCanciones();
			vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));
			JOptionPane.showMessageDialog(vista.getFrame(), "Pulse aceptar para continuar", "registro modificado correctamente", JOptionPane.INFORMATION_MESSAGE);
		}
		else
			PlayList.getListaCanciones().remove(posibleCancion);
	}
	
	
	private void borrarCancion(List<CancionDTO> lista) {
		if (vista.getTabla().getSelectedRow() != -1){
			CancionDTO cancionEliminar = null;
			int result = JOptionPane.showConfirmDialog(vista.getFrame(), "¿Desea eliminar "
			+(lista.get(vista.getTabla().getSelectedRow())).getNombreCancion()+" ?", "Aviso", JOptionPane.YES_NO_OPTION);
			
			if (result == JOptionPane.YES_OPTION){
				cancionEliminar =(CancionDTO) lista.get(vista.getTabla().getSelectedRow());
				lista.remove(cancionEliminar);
				vista.getTabla().setModel(new MiTableModel(lista, CABEZERA));
			}
			
			//Vamos a comprobar si la canción borrada es resultado de una búsqueda, en ese caso la eliminaremos tambíen de la lista general		
			if (cancionEliminar != null){
				for (CancionDTO cancion : PlayList.getListaCanciones()) {

					if (cancionEliminar.equals(cancion)){
						PlayList.getListaCanciones().remove(cancion);
						break;
					}
				}
			}			
		}
	}
	//Método que modifica el JTable según un critero de búsqueda.
	private  void buscarPorCategoria(){
		listaResultado = new ArrayList<CancionDTO>();

		switch (vista.getComboBox().getSelectedItem().toString()){
		
			case "Nombre":
				
				listaResultado = CancionDTO.BuscarCancion(PlayList.getListaCanciones(), p->{
				return p.getNombreCancion().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				
				busquedaRealidada = true;
				break;
				
			case "\u00C1lbum":
				
				listaResultado = CancionDTO.BuscarCancion(PlayList.getListaCanciones(), p->{
				return p.getNombreAlbum().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				
				busquedaRealidada = true;
				break;
				
			case "Artista":
				
				listaResultado = CancionDTO.BuscarCancion(PlayList.getListaCanciones(), p->{
				return p.getNombreArtista().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				
				busquedaRealidada = true;
				break;
				
			case "A\u00F1o":
				
				listaResultado = CancionDTO.BuscarCancion(PlayList.getListaCanciones(), p->{
				return (p.getYearAlbum()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				
				busquedaRealidada = true;
				break;
				
			case "G\u00E9nero":
				
				listaResultado = CancionDTO.BuscarCancion(PlayList.getListaCanciones(), p->{
				return p.getGenero().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				
				busquedaRealidada = true;
				break;
				
			case "N\u00FAmero":
				
				listaResultado = CancionDTO.BuscarCancion(PlayList.getListaCanciones(), p->{
				return (p.getNumeroCancion()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				
				busquedaRealidada = true;
				break;
		}
		//Modifico el modelo del JTable con la lista que me devuelve el switch
		vista.getTabla().setModel(new MiTableModel(listaResultado, CABEZERA));		
	}
}
