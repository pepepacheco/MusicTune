package vista;

import javax.swing.JFrame;
import controlador.Controlador;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class Vista {
	private JFrame frame;
	private JMenuBar barraMenu;
	private JMenu menuArchivo;
	private JMenu editar;
	private JMenuItem abrir;
	private JMenuItem salir;
	private JPanel panelHead;
	private JPanel panelBody;
	private JScrollPane scroll;
	private JTable tabla;
	private JFileChooser file;
	private JLabel lblBuscar;
	private JTextField textAreaBuscar;
	private JButton btnIrMostrarResultado;
	private GroupLayout gl_panelBody;
	private GroupLayout gl_panelHead;
	private JComboBox<String> comboBox;
	private JLabel lblNombre;
	private JLabel lblAlbum;
	private JTextField textAreaNombre;
	private JTextField textAreaAlbum;
	private JLabel lblArtista;
	private JTextField textAreaArtista;
	private JLabel lblAnio;
	private JTextField textAreaAnio;
	private JLabel lblGenero;
	private JTextField textAreaGenero;
	private JLabel lblDuracion;
	private JTextField textAreaDuracion;
	private JTextField textAreaNumero;
	private JLabel lblNumero;
	private JSplitPane splitPane;
	private JPanel JPanelSuperior;
	private GroupLayout gl_JPanelSuperior;
	private JPanel JPanelInferior;
	private Controlador controlador;
	private JPanel footer;
	private JLabel lblRegistro;
	private GroupLayout gl_footer;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	private JLabel jLabelImagen;
	private JPanel panel_1;
	private JButton btnBorrarCampo;
	private JButton btnAnadirCampo;
	private GroupLayout gl_panel_1;
	private JButton btnLimpiarFormulario;

	
	public Vista(){
		inicialize();
		file = new JFileChooser();
		controlador = new Controlador(this); 

	}

	private void inicialize(){
		frame = new JFrame("MusicTune");
		frame.setBounds(100, 100, 950, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		barraMenu = new JMenuBar();
		frame.setJMenuBar(barraMenu);
		
		menuArchivo = new JMenu("Archivo");
		barraMenu.add(menuArchivo);
		
		abrir = new JMenuItem("Abrir");
		abrir.setIcon(null);
		abrir.setSelectedIcon(null);
		menuArchivo.add(abrir);
		
		salir = new JMenuItem("Salir");
		menuArchivo.add(salir);
		
		editar = new JMenu("Editar");
		barraMenu.add(editar);
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanelSuperior = new JPanel();
		splitPane.setLeftComponent(JPanelSuperior);
		
		panelBody = new JPanel();
		
				tabla = new JTable();
				tabla.setFont(new Font("Tahoma", Font.PLAIN, 11));
				tabla.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Selecciona un archivo JSON"
					}
				));

						tabla.setBackground(Color.WHITE);
						//tabla.setColumnSelectionAllowed(true);
						//tabla.setCellSelectionEnabled(false);
						panelBody.add(tabla);
						scroll = new JScrollPane(tabla);
						scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
						scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						
						panelHead = new JPanel();
						gl_panelBody = new GroupLayout(panelBody);
						gl_panelBody.setHorizontalGroup(
							gl_panelBody.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelBody.createSequentialGroup()
									.addContainerGap()
									.addGroup(gl_panelBody.createParallelGroup(Alignment.TRAILING)
										.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
										.addComponent(panelHead, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE))
									.addContainerGap())
						);
						gl_panelBody.setVerticalGroup(
							gl_panelBody.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelBody.createSequentialGroup()
									.addComponent(panelHead, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
									.addGap(8))
						);
						
						
						lblBuscar = new JLabel("Buscar por:");
						
						comboBox = new JComboBox<String>();
						comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Nombre", "\u00C1lbum", "Artista", "A\u00F1o", "G\u00E9nero", "N\u00FAmero"}));
						
						textAreaBuscar = new JTextField();
						
						btnIrMostrarResultado = new JButton("Mostrar resultado");

						frame.getContentPane().setLayout(new BorderLayout(0, 0));
						
						gl_panelHead = new GroupLayout(panelHead);
						gl_panelHead.setHorizontalGroup(
							gl_panelHead.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelHead.createSequentialGroup()
									.addGap(4)
									.addComponent(lblBuscar)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textAreaBuscar, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnIrMostrarResultado))
						);
						gl_panelHead.setVerticalGroup(
							gl_panelHead.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelHead.createSequentialGroup()
									.addContainerGap()
									.addGroup(gl_panelHead.createParallelGroup(Alignment.BASELINE)
										.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(textAreaBuscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(btnIrMostrarResultado)
										.addComponent(lblBuscar))
									.addContainerGap(15, Short.MAX_VALUE))
						);
						panelHead.setLayout(gl_panelHead);
						panelBody.setLayout(gl_panelBody);
		gl_JPanelSuperior = new GroupLayout(JPanelSuperior);
		gl_JPanelSuperior.setHorizontalGroup(
			gl_JPanelSuperior.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_JPanelSuperior.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBody, GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_JPanelSuperior.setVerticalGroup(
			gl_JPanelSuperior.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_JPanelSuperior.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBody, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
					.addContainerGap())
		);
		JPanelSuperior.setLayout(gl_JPanelSuperior);
		
		JPanelInferior = new JPanel();
		splitPane.setRightComponent(JPanelInferior);
		
		footer = new JPanel();
		JPanelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		jLabelImagen = new JLabel("");
		jLabelImagen.setToolTipText("sdagfdsa");
		JPanelInferior.add(jLabelImagen);
		
		panel_1 = new JPanel();
		
		lblNombre = new JLabel("Nombre");
		
		textAreaNombre = new JTextField();
		
		lblAlbum = new JLabel("\u00C1lbum");
		
		textAreaAlbum = new JTextField();
		
		lblArtista = new JLabel("Artista");
		
		textAreaArtista = new JTextField();
		
		lblAnio = new JLabel("A\u00F1o");
		
		textAreaAnio = new JTextField();
		
		lblGenero = new JLabel("G\u00E9nero");
		
		textAreaGenero = new JTextField();
		
		lblDuracion = new JLabel("Duraci\u00F3n");
		
		textAreaDuracion = new JTextField();
		
		lblNumero = new JLabel("Numero");
		
		textAreaNumero = new JTextField();
		
		btnAnterior = new JButton("Anterior");
		
		btnSiguiente = new JButton("Siguiente");
		
		btnBorrarCampo = new JButton("Borrar campo");
		
		btnAnadirCampo = new JButton("Añadir campo");
		
		btnLimpiarFormulario = new JButton("Limpiar formulario");
		gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(0)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNombre)
								.addComponent(lblAlbum)
								.addComponent(lblArtista)
								.addComponent(lblAnio)
								.addComponent(lblDuracion)
								.addComponent(lblNumero))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(btnAnterior)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnSiguiente)
									.addPreferredGap(ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
									.addComponent(btnLimpiarFormulario)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnAnadirCampo)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnBorrarCampo))
								.addComponent(textAreaAnio, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
								.addComponent(textAreaDuracion, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
								.addComponent(textAreaAlbum, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
								.addComponent(textAreaArtista, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
								.addComponent(textAreaNombre, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
								.addComponent(textAreaNumero, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(7)
							.addComponent(lblGenero)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textAreaGenero, GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textAreaNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNombre))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textAreaAlbum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAlbum))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textAreaArtista, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblArtista))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(textAreaAnio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAnio))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(62)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNumero)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaGenero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGenero))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaDuracion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDuracion))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnAnterior, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnSiguiente, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnBorrarCampo)
							.addComponent(btnAnadirCampo)
							.addComponent(btnLimpiarFormulario)))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		JPanelInferior.add(panel_1);
		frame.getContentPane().add(splitPane);
		frame.getContentPane().add(footer, BorderLayout.SOUTH);
		
		lblRegistro = new JLabel("Registro 0 de 0");
		lblRegistro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblRegistro.setHorizontalAlignment(SwingConstants.CENTER);
		gl_footer = new GroupLayout(footer);
		gl_footer.setHorizontalGroup(
			gl_footer.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_footer.createSequentialGroup()
					.addContainerGap(784, Short.MAX_VALUE)
					.addComponent(lblRegistro, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_footer.setVerticalGroup(
			gl_footer.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_footer.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblRegistro, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
		);
		footer.setLayout(gl_footer);
		frame.setMinimumSize(new Dimension(950, 700));
	}
	
	//Getters
	
	public JTextField getTextAreaNombre() {
		return textAreaNombre;
	}

	public JTextField getTextAreaAlbum() {
		return textAreaAlbum;
	}

	public JTextField getTextAreaArtista() {
		return textAreaArtista;
	}

	public JTextField getTextAreaAnio() {
		return textAreaAnio;
	}

	public JTextField getTextAreaGenero() {
		return textAreaGenero;
	}

	public JTextField getTextAreaDuracion() {
		return textAreaDuracion;
	}

	public JTextField getTextAreaNumero() {
		return textAreaNumero;
	}

	public JFrame getFrame(){
		return frame;
	}
	
	public JMenuItem getAbrir(){
		return abrir;
	}
	
	public JMenuItem getSalir(){
		return salir;
	}
	
	public JTable getTabla(){
		return this.tabla;
	}
	
	public JPanel getPanelSuperior(){
		return panelBody;
	}
	
	public JFileChooser getFile(){
		return file;
	}
	
	public JButton getBtnMostrarResultado(){
		return btnIrMostrarResultado;
	}
	
	public JTextField getTextAreaBuscar(){
		return textAreaBuscar;
	}
	
	public JComboBox<String> getComboBox(){
		return comboBox;
	}
	
	public JLabel getLblRegistro(){
		return lblRegistro;
	}
	
	public JButton getBtnAnterior(){
		return btnAnterior;
	}
	
	public JButton getBtnSiguiente(){
		return btnSiguiente;
	}	
	
	public JLabel getJLabelImagen(){
		return jLabelImagen;
	}

	public JButton getBtnBorrarCampo() {
		return btnBorrarCampo;
	}

	public JButton getBtnAnadirCampo() {
		return btnAnadirCampo;
	}
	
	public JButton getBtnLimpiarFormulario() {
		return btnLimpiarFormulario;
	}
	
	
}
