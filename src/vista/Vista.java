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
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import javax.swing.JMenuItem;

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
	private JTextArea textAreaBuscar;
	private JButton btnIrMostrarResultado;
	private GroupLayout groupLayout;
	private GroupLayout gl_panelBody;
	private GroupLayout gl_panelHead;
	private JComboBox comboBox;
	private JLabel lblNombre;
	private JLabel lblAlbum;
	private JTextArea textAreaNombre;
	private JTextArea textAreaAlbum;
	private JLabel lblArtista;
	private JTextArea textAreaArtista;
	private JLabel lblAo;
	private JTextArea textAreaAnio;
	private JLabel lblGnero;
	private JTextArea textAreaGenero;
	private JLabel lblDuracin;
	private JTextArea textAreaDuracion;
	private JTextArea textAreaNumero;
	private JLabel lblNumero;

	
	public Vista(){
		inicialize();
		file = new JFileChooser();
		Controlador controlador = new Controlador(this); 

	}
	
	private void inicialize(){
		frame = new JFrame("MusicTune");
		frame.setBounds(100, 100, 800, 600);
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
		
		panelBody = new JPanel();
		groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelBody, GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelBody, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
					.addContainerGap())
		);

		tabla = new JTable();

		tabla.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Selecciona un archivo"
			}
		));
		tabla.setBackground(Color.WHITE);
		tabla.setColumnSelectionAllowed(true);
		tabla.setCellSelectionEnabled(true);
		panelBody.add(tabla);
		scroll = new JScrollPane(tabla);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		panelHead = new JPanel();
		
		JSeparator separator = new JSeparator();
		
		lblNombre = new JLabel("Nombre");
		
		lblAlbum = new JLabel("\u00C1lbum");
		
		textAreaNombre = new JTextArea();
		
		textAreaAlbum = new JTextArea();
		
		lblArtista = new JLabel("Artista");
		
		textAreaArtista = new JTextArea();
		
		lblAo = new JLabel("A\u00F1o");
		
		textAreaAnio = new JTextArea();
		
		lblGnero = new JLabel("G\u00E9nero");
		
		textAreaGenero = new JTextArea();
		
		lblDuracin = new JLabel("Duraci\u00F3n");
		
		textAreaDuracion = new JTextArea();
		
		textAreaNumero = new JTextArea();
		
		lblNumero = new JLabel("Numero");
		gl_panelBody = new GroupLayout(panelBody);
		gl_panelBody.setHorizontalGroup(
			gl_panelBody.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelBody.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelBody.createParallelGroup(Alignment.TRAILING)
						.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
						.addComponent(panelHead, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panelBody.createSequentialGroup()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelBody.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelBody.createSequentialGroup()
									.addGroup(gl_panelBody.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNombre)
										.addGroup(gl_panelBody.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblGnero)
											.addComponent(lblDuracin)
											.addComponent(lblAo)
											.addComponent(lblArtista)
											.addComponent(lblAlbum)))
									.addGap(18)
									.addGroup(gl_panelBody.createParallelGroup(Alignment.LEADING)
										.addComponent(textAreaArtista, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
										.addComponent(textAreaAlbum, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
										.addComponent(textAreaNombre, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
										.addComponent(textAreaAnio, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
										.addComponent(textAreaGenero, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
										.addComponent(textAreaDuracion, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)))
								.addGroup(gl_panelBody.createSequentialGroup()
									.addGap(4)
									.addComponent(lblNumero)
									.addGap(19)
									.addComponent(textAreaNumero, GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)))))
					.addContainerGap())
		);
		gl_panelBody.setVerticalGroup(
			gl_panelBody.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBody.createSequentialGroup()
					.addComponent(panelHead, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_panelBody.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelBody.createSequentialGroup()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(225))
						.addGroup(gl_panelBody.createSequentialGroup()
							.addGroup(gl_panelBody.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNombre))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelBody.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaAlbum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAlbum))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelBody.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaArtista, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblArtista))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelBody.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaAnio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblAo))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelBody.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaGenero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblGnero))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelBody.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDuracin)
								.addComponent(textAreaDuracion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panelBody.createParallelGroup(Alignment.BASELINE)
								.addComponent(textAreaNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNumero))
							.addContainerGap())))
		);
		
		
		lblBuscar = new JLabel("Buscar por:");
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Nombre", "\u00C1lbum", "Artista", "A\u00F1o", "G\u00E9nero", "N\u00FAmero"}));
		
		textAreaBuscar = new JTextArea();
		
		btnIrMostrarResultado = new JButton("Mostrar resultado");
		btnIrMostrarResultado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
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

		
		frame.getContentPane().setLayout(groupLayout);
	}
	
	//Geters
	
	public JTextArea getTextAreaNombre() {
		return textAreaNombre;
	}

	public JTextArea getTextAreaAlbum() {
		return textAreaAlbum;
	}

	public JTextArea getTextAreaArtista() {
		return textAreaArtista;
	}

	public JTextArea getTextAreaAnio() {
		return textAreaAnio;
	}

	public JTextArea getTextAreaGenero() {
		return textAreaGenero;
	}

	public JTextArea getTextAreaDuracion() {
		return textAreaDuracion;
	}

	public JTextArea getTextAreaNumero() {
		return textAreaNumero;
	}

	public void setTextAreaBuscar(JTextArea textAreaBuscar) {
		this.textAreaBuscar = textAreaBuscar;
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
	
	public JButton getBtnIrIr(){
		return btnIrMostrarResultado;
	}
	
	public JTextArea getTextAreaBuscar(){
		return textAreaBuscar;
	}
	
	public JComboBox getComboBox(){
		return comboBox;
	}
}
