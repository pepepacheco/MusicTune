package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
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
import vista.Creditos;
import vista.VistaGeneral;
import vista.MiTableModel;

public class Controlador {
	private VistaGeneral vista;
	private CancionDAOImpSQLite cancionDAO;
	private ArtistaDAOImpSQLite artistaDAO;
	private AlbumDAOImpSQLite albumDAO;
	private List<CancionDTO> listaResultado; //lista dinámica que almacena los resultados de una búsqueda en el JTable
	private boolean busquedaRealidada = false; //variable booleana que determina si se ha realizado una búsqueda en el JTable
	private boolean botonEliminar = false; //variable booleana que determina si se está entrando a un método desde un botón
	private final String[] CABEZERA = {"Nombre","Álbum","Artista","Año","Género","Duración", "Número"}; //Cabezera del TableModel
	private int antiguoIndice = -1; //variable que va almacenando el indice anterior en el JTable
	
	public Controlador(VistaGeneral v){
		vista = v; //Pasamos una referencia de la vista para acceder a sus Clases.
    	artistaDAO = new ArtistaDAOImpSQLite();
    	albumDAO = new AlbumDAOImpSQLite();
    	cancionDAO = new CancionDAOImpSQLite();
		eventos(); //inicializamos los eventos
		loadBD(); // inicializamos la base de datos		
	}
	
	//================================EVENTOS INTERFAZ GRÁFICA================================
	
	private void eventos(){
		
		//Evento del meńu abrir que al seleccionar un archivo JSON, lo carga en un JTable, crea las tablas e inserta los valores
		vista.getAbrir().addActionListener(r->{
	    	vista.getFile().setFileFilter(vista.getFiltro());	       	
	        if (vista.getFile().showOpenDialog(vista.getFrame()) == JFileChooser.APPROVE_OPTION){
				try {
		        	Service.loadJson(vista.getFile().getSelectedFile());		        	
		        	JOptionPane.showMessageDialog(vista.getFrame(), "Introduciendo datos en la base de datos", "Espere...", JOptionPane.INFORMATION_MESSAGE);
		        	artistaDAO.crearTalba();
		        	artistaDAO.addArtista(PlayList.getListaArtistas());
		        	albumDAO.crearTalba();
		        	albumDAO.addAlbum(PlayList.getListaAlbumes());
		        	cancionDAO.crearTabla();
		        	cancionDAO.addCancion(PlayList.getListaCanciones());
		        	Service.crearVista();
		    		Service.createTrigger();
					vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));
					vista.getAbrir().setEnabled(false);
	
				} catch (IOException | InvalidYearException | InvalidDurationException | InvalidTackNumberException | EmptyFieldsException e) {
					//e.printStackTrace();
					JOptionPane.showMessageDialog(vista.getFrame(), "Archivo de datos incorrecto", "Error de lectura", JOptionPane.ERROR_MESSAGE);
				} catch (IllegalStateException i){
					//i.printStackTrace();
					JOptionPane.showMessageDialog(vista.getFrame(), "Seleccione un archivo válido", "JSON Incorecto", JOptionPane.INFORMATION_MESSAGE);
				}				 
	        }
		});
		
		//Evento que al pulsar en el menú salir cierra la aplicación
		vista.getSalir().addActionListener(r->{
			System.exit(0);
		});
		
		//Evento que realiza búsquedas si se pulsa la tecla "ENTER"
		vista.getTextAreaBuscar().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
					if (PlayList.getListaCanciones().size() > 0)
						buscarPorCategoria();
				}
			}
		});
		
		//Evento que realiza búsquedas si se pulsa sobre el boton "Mostrar resultado"
		vista.getBtnMostrarResultado().addActionListener(r->{
			if (PlayList.getListaCanciones().size() > 0)
				buscarPorCategoria();
		});		
		
		//Evento que muestra la siguiete fila del TableModel
		vista.getBtnSiguiente().addActionListener(r->{			
			if (vista.getTabla().getSelectedRow() < (vista.getTabla().getRowCount()-1))
				vista.getTabla().setRowSelectionInterval(vista.getTabla().getSelectedRow()+1, vista.getTabla().getSelectedRow()+1);
			
		});
		
		//Evento que muestra la fila anterior del TableModel
		vista.getBtnAnterior().addActionListener(r->{			
			if (vista.getTabla().getSelectedRow() > 0)
				vista.getTabla().setRowSelectionInterval(vista.getTabla().getSelectedRow()-1, vista.getTabla().getSelectedRow()-1);
		});
		
		//Evento que añade una nuevo campo al Jtable y lo inserta en la base de datos
		vista.getBtnAnadirCampo().addActionListener(r->{
			try{
				if (Service.contarFilas("album", vista.getTextAreaAlbum().getText()) == 0)
					albumDAO.addAlbum(new AlbumDTO(vista.getTextAreaAlbum().getText(), vista.getTextAreaAnio().getText()));
				
				if (Service.contarFilas("artista", vista.getTextAreaArtista().getText()) == 0)
					artistaDAO.addArtista(new ArtistaDTO(vista.getTextAreaArtista().getText()));
				
				addCancion();
			} catch (Exception e){
				//e.printStackTrace();
			}
		});
		
		//Evento que modifica un campo en el Jtable y lo actualiza en la base de datos
		vista.getBtnModificar().addActionListener(r->{	
			try {				
				if (Service.contarFilas("album", vista.getTextAreaAlbum().getText()) == 0)
					albumDAO.addAlbum(new AlbumDTO(vista.getTextAreaAlbum().getText(), vista.getTextAreaAnio().getText()));
				
				if (Service.contarFilas("artista", vista.getTextAreaArtista().getText()) == 0){
					artistaDAO.addArtista(new ArtistaDTO(vista.getTextAreaArtista().getText()));
				}		
				
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
		
		//Evento que al pulsar sobre el botón "Borrar", elimina el campo activo en el JTable y tambíen en la base de datos
		vista.getBtnBorrarCampo().addActionListener(r->{
			botonEliminar = true;
			//Compruebo si el campo a borrar es resultado de una búsqueda o del listado global
			if (listaResultado != null && listaResultado.size() > 0){
				borrarCancion(listaResultado);
			}		
			else
				borrarCancion(PlayList.getListaCanciones());			
		});
		
		//Evento que limpia el formulario tras una eliminación
		vista.getBtnLimpiarFormulario().addActionListener(r->{
			vista.getTextAreaNombre().setText("");
			vista.getTextAreaArtista().setText("");
			vista.getTextAreaAlbum().setText("");
			vista.getTextAreaGenero().setText("");
			vista.getTextAreaAnio().setText("");
			vista.getTextAreaDuracion().setText("");
			vista.getTextAreaNumero().setText("");
		});

		//Evento que controla los cambios en el TableModel
		vista.getTabla().getSelectionModel().addListSelectionListener(r->{
			if (antiguoIndice != -1 && (!botonEliminar))
				descartarCambios();
			else 
				botonEliminar = false;
			
			rellenarFormulario();
			cambiarImagen();
			actualizarBarraEstado();
			
			antiguoIndice = vista.getTabla().getSelectedRow();		
		});		
		
		//Evento que exporta un archivo PDF que contiene una tabla con los campos más siginificativos de cada registro
		vista.getMntmExportarPdf().addActionListener(r->{			
			Document documento = new Document();	
			try {
				if (vista.getFile().showSaveDialog(vista.getFrame()) == JFileChooser.APPROVE_OPTION){
					final String[]  CABEZERA_PDF = {"NOMBRE","ÁLBUM","ARTISTA"};
					if (PlayList.getListaCanciones().size() > 0){
						PdfWriter.getInstance(documento, new FileOutputStream(vista.getFile().getSelectedFile()));				
						documento.open();
						PdfPTable tabla = new PdfPTable(3);
						tabla.setHeaderRows(1);
						for (String campo : CABEZERA_PDF) {
							tabla.addCell(campo);
						}					
						for (CancionDTO cancion : PlayList.getListaCanciones()) {
							tabla.addCell(new Phrase(cancion.getNombreCancion(), FontFactory.getFont(FontFactory.HELVETICA, 6)));
							tabla.addCell(new Phrase(cancion.getNombreAlbum(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
							tabla.addCell(new Phrase(cancion.getNombreArtista(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
						//	tabla.addCell(new Phrase(cancion.getYearAlbum()+"", FontFactory.getFont(FontFactory.HELVETICA, 12)));
						//	tabla.addCell(new Phrase(cancion.getGenero(), FontFactory.getFont(FontFactory.HELVETICA, 10)));
						//	tabla.addCell(new Phrase(cancion.getDuracion()+"", FontFactory.getFont(FontFactory.HELVETICA, 12)));
						//	tabla.addCell(new Phrase(cancion.getNumeroCancion()+"", FontFactory.getFont(FontFactory.HELVETICA, 12)));
						}					
						documento.add(tabla);
						documento.close();
					}
					else
						JOptionPane.showMessageDialog(vista.getFrame(), "La tabla no contiene datos", "Error", JOptionPane.ERROR_MESSAGE);

				}
			} catch (IOException | DocumentException e) {
				JOptionPane.showMessageDialog(vista.getFrame(), "No ha sido posible exportar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			} 
		});
		
		//Evento que lanza un dialgo modál con información acerca de la aplicación y su creador.
		vista.getMntmCreditos().addActionListener(r->{
			Creditos cr = new Creditos();
			cr.setLocationRelativeTo(vista.getFrame());
			cr.setVisible(true);
		});
	}
	
	//================================MÉTODOS AUXILIARES================================
	
	//Método que carga la base de datos, si existe
	private void loadBD() {
		File bd = new File("database.db");
		if (bd.exists() && bd.length() > 4000){
			try {
				Service.autoLoad();
				vista.getAbrir().setEnabled(false);
			} catch (InvalidYearException e) {
				//e.printStackTrace();
			} catch (InvalidDurationException e) {
				//e.printStackTrace();
			} catch (InvalidTackNumberException e) {
				//e.printStackTrace();
			} catch (EmptyFieldsException e) {
				//e.printStackTrace();
			}
			vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));			
		}
	}
	
	//Método que añade una canción, siempre y cuando el contenido sea correcto y esta no esté reptida.
	private void addCancion() {
		CancionDTO cancion = null;
		try {
			cancion = new CancionDTO(vista.getTextAreaNombre().getText(), vista.getTextAreaAlbum().getText(),
			vista.getTextAreaArtista().getText(), vista.getTextAreaAnio().getText(), vista.getTextAreaGenero().getText(),
			vista.getTextAreaDuracion().getText(), vista.getTextAreaNumero().getText());
			
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
		if ((!(cancionRepetida(cancion, false)))){
			cancionDAO.addCancion(cancion);
			vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));
			listaResultado = PlayList.getListaCanciones();
			JOptionPane.showMessageDialog(vista.getFrame(), "Pulse aceptar para continuar", "Canción guardada correctamente", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//Método que comprueba si una canción está repetida en la lista
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
	
	//Método que modifica un registro en el TableModel y en la base de datos
	private void modificarCancion(CancionDTO posibleCancion){
		int result = JOptionPane.showConfirmDialog(vista.getFrame(), "¿Desea modificar el registro anterior");
		if (result == JOptionPane.YES_OPTION){
			if (listaResultado != null && listaResultado.size() > 0){
				CancionDTO cancionAModificar = listaResultado.get(antiguoIndice);
				cancionDAO.modificarCancion(posibleCancion, cancionAModificar);
				int indice = PlayList.getListaCanciones().indexOf(cancionAModificar);
				PlayList.getListaCanciones().set(indice, posibleCancion);				
				PlayList.getListaCanciones().remove(PlayList.getListaCanciones().size()-1);				
			}
			else{
				cancionDAO.modificarCancion(posibleCancion, PlayList.getListaCanciones().get(antiguoIndice));
				PlayList.getListaCanciones().set(antiguoIndice, posibleCancion);
				PlayList.getListaCanciones().remove(PlayList.getListaCanciones().size()-1);							
			}					
			listaResultado = PlayList.getListaCanciones();
			vista.getTabla().setModel(new MiTableModel(PlayList.getListaCanciones(), CABEZERA));
			JOptionPane.showMessageDialog(vista.getFrame(), "Pulse aceptar para continuar", "registro modificado correctamente", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			PlayList.getListaCanciones().remove(posibleCancion);
		}
	}
	
	//Método que rellena los campos de una fila del TableModel en el formulario
	private void rellenarFormulario() {
		String[] campos = new String[7];
		for (int i = 0; i < 7; i++) {
			if (vista.getTabla().getSelectedRow() != -1)
				campos[i] = (String) vista.getTabla().getModel().getValueAt(vista.getTabla().getSelectedRow(), i);
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
	
	}
	//Método que modifica las canciones desde el evento de un boton, no del JTable
	private void modificarCancionDesdeBoton(CancionDTO posibleCancion){
		int result = JOptionPane.showConfirmDialog(vista.getFrame(), "¿Desea modificar este registro");
		if (result == JOptionPane.YES_OPTION){
			if (listaResultado != null && listaResultado.size() > 0){
				CancionDTO cancionAModificar = listaResultado.get(vista.getTabla().getSelectedRow());
				int indice = PlayList.getListaCanciones().indexOf(cancionAModificar);
				PlayList.getListaCanciones().set(indice, posibleCancion);				
				PlayList.getListaCanciones().remove(PlayList.getListaCanciones().size()-1);
				cancionDAO.modificarCancion(posibleCancion, cancionAModificar);
			}
			else{
				cancionDAO.modificarCancion(posibleCancion, PlayList.getListaCanciones().get(antiguoIndice));
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
	
	//Método que nuestra una imagen de carátula en función del artista
	private void cambiarImagen() {
		String artistaActual="";
		String artistaElegido = vista.getTextAreaArtista().getText();
	
		//Comprobamos si el registro siguiente contiene un artista diferente,
		//en ese caso, modificaremos su imágen.
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
	}
	
	//Método que actualiza la barra de estado, ya sea desde el resultado de una búsqueda o de la lista global.	
	private void actualizarBarraEstado() {

		if (!busquedaRealidada)
			vista.getLblRegistro().setText("Registro "+(vista.getTabla().getSelectedRow()+1)+" de "+PlayList.getListaCanciones().size());
		else
			vista.getLblRegistro().setText("Registro "+(vista.getTabla().getSelectedRow()+1)+" de "+listaResultado.size());
	}
	
	//Método que muestra un mensaje al usuario en el caso de que el formulario haya recibido cambios
	private void descartarCambios() {
		try {
			if (Service.contarFilas("album", vista.getTextAreaAlbum().getText()) == 0)
				albumDAO.addAlbum(new AlbumDTO(vista.getTextAreaAlbum().getText(), vista.getTextAreaAnio().getText()));
			
			if (Service.contarFilas("artista", vista.getTextAreaArtista().getText()) == 0){
				artistaDAO.addArtista(new ArtistaDTO(vista.getTextAreaArtista().getText()));
			}		
			
			CancionDTO posibleCancion = new CancionDTO(vista.getTextAreaNombre().getText(), vista.getTextAreaAlbum().getText(), vista.getTextAreaArtista().getText(),
			vista.getTextAreaAnio().getText(), vista.getTextAreaGenero().getText(), vista.getTextAreaDuracion().getText(), vista.getTextAreaNumero().getText());
		
			if (!(cancionRepetida(posibleCancion, true))){
				modificarCancion(posibleCancion);
			}				
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	//Método para el borrado de canciones
	private void borrarCancion(List<CancionDTO> lista) {
		if (vista.getTabla().getSelectedRow() != -1){
			CancionDTO cancionEliminar = null;
			int result = JOptionPane.showConfirmDialog(vista.getFrame(), "¿Desea eliminar "
			+(lista.get(vista.getTabla().getSelectedRow())).getNombreCancion()+" ?", "Aviso", JOptionPane.YES_NO_OPTION);
			
			if (result == JOptionPane.YES_OPTION){
				cancionEliminar = lista.get(vista.getTabla().getSelectedRow());
				lista.remove(cancionEliminar);			
				vista.getTabla().setModel(new MiTableModel(lista, CABEZERA));
				cancionDAO.borrarCancion(cancionEliminar);
			}
			
			//Vamos a comprobar si la canción borrada es resultado de una búsqueda,
			//en ese caso la eliminaremos tambíen de la lista general		
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
				
			case "Álbum":
				
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
				
			case "Año":
				
				listaResultado = CancionDTO.BuscarCancion(PlayList.getListaCanciones(), p->{
				return (p.getYearAlbum()+"").toLowerCase().equals(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				
				busquedaRealidada = true;
				break;
				
			case "Género":
				
				listaResultado = CancionDTO.BuscarCancion(PlayList.getListaCanciones(), p->{
				return p.getGenero().toLowerCase().contains(vista.getTextAreaBuscar().getText().toLowerCase());
				});
				
				busquedaRealidada = true;
				break;
				
			case "Número":
				
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
