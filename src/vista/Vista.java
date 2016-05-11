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
import javax.swing.JSeparator;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;


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
	private GroupLayout groupLayout;
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
	private GroupLayout gl_JPanelInferior;
	private Controlador controlador;

	
	public Vista(){
		inicialize();
		file = new JFileChooser();
		controlador = new Controlador(this); 

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
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPanelSuperior = new JPanel();
		splitPane.setLeftComponent(JPanelSuperior);
		
		panelBody = new JPanel();
		
				tabla = new JTable();

						tabla.setBackground(Color.WHITE);
						tabla.setColumnSelectionAllowed(true);
						tabla.setCellSelectionEnabled(true);
						panelBody.add(tabla);
						scroll = new JScrollPane(tabla);
						scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
						scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						
						panelHead = new JPanel();
						
						JSeparator separator = new JSeparator();
						gl_panelBody = new GroupLayout(panelBody);
						gl_panelBody.setHorizontalGroup(
							gl_panelBody.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelBody.createSequentialGroup()
									.addContainerGap()
									.addGroup(gl_panelBody.createParallelGroup(Alignment.TRAILING)
										.addComponent(scroll, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
										.addComponent(separator, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
										.addComponent(panelHead, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE))
									.addContainerGap())
						);
						gl_panelBody.setVerticalGroup(
							gl_panelBody.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelBody.createSequentialGroup()
									.addComponent(panelHead, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(0))
						);
						
						
						lblBuscar = new JLabel("Buscar por:");
						
						comboBox = new JComboBox<String>();
						comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Nombre", "\u00C1lbum", "Artista", "A\u00F1o", "G\u00E9nero", "N\u00FAmero"}));
						
						textAreaBuscar = new JTextField();
						
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
		
		textAreaNumero = new JTextField();
		
		lblNumero = new JLabel("Numero");
		
		lblDuracion = new JLabel("Duraci\u00F3n");
		
		textAreaDuracion = new JTextField();
		
		textAreaGenero = new JTextField();
		
		lblGenero = new JLabel("G\u00E9nero");
		
		lblAnio = new JLabel("A\u00F1o");
		
		textAreaAnio = new JTextField();
		
		textAreaArtista = new JTextField();
		
		lblArtista = new JLabel("Artista");
		
		lblAlbum = new JLabel("\u00C1lbum");
		
		textAreaAlbum = new JTextField();
		
		textAreaNombre = new JTextField();
		
		lblNombre = new JLabel("Nombre");
		gl_JPanelInferior = new GroupLayout(JPanelInferior);
		gl_JPanelInferior.setHorizontalGroup(
			gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_JPanelInferior.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNombre)
							.addGap(18)
							.addComponent(textAreaNombre, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(13)
							.addComponent(lblAlbum)
							.addGap(18)
							.addComponent(textAreaAlbum, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(10)
							.addComponent(lblArtista)
							.addGap(18)
							.addComponent(textAreaArtista, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(23)
							.addComponent(lblAnio)
							.addGap(18)
							.addComponent(textAreaAnio, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(7)
							.addComponent(lblGenero)
							.addGap(18)
							.addComponent(textAreaGenero, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addComponent(lblDuracion)
							.addGap(18)
							.addComponent(textAreaDuracion, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNumero)
							.addGap(19)
							.addComponent(textAreaNumero, GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_JPanelInferior.setVerticalGroup(
			gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_JPanelInferior.createSequentialGroup()
					.addGap(38)
					.addGroup(gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNombre))
						.addComponent(textAreaNombre, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
					.addGroup(gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(14)
							.addComponent(lblAlbum))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(11)
							.addComponent(textAreaAlbum)))
					.addGroup(gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(14)
							.addComponent(lblArtista))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(11)
							.addComponent(textAreaArtista)))
					.addGroup(gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(14)
							.addComponent(lblAnio))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(11)
							.addComponent(textAreaAnio)))
					.addGroup(gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(14)
							.addComponent(lblGenero))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(11)
							.addComponent(textAreaGenero)))
					.addGroup(gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(14)
							.addComponent(lblDuracion))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(11)
							.addComponent(textAreaDuracion)))
					.addGroup(gl_JPanelInferior.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(14)
							.addComponent(lblNumero))
						.addGroup(gl_JPanelInferior.createSequentialGroup()
							.addGap(10)
							.addComponent(textAreaNumero, GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)))
					.addContainerGap())
		);
		JPanelInferior.setLayout(gl_JPanelInferior);
		groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
		);

		
		frame.getContentPane().setLayout(groupLayout);
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

	public void setTextAreaBuscar(JTextField textAreaBuscar) {
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
	
	public JButton getBtnMostrarResultado(){
		return btnIrMostrarResultado;
	}
	
	public JTextField getTextAreaBuscar(){
		return textAreaBuscar;
	}
	
	public JComboBox<String> getComboBox(){
		return comboBox;
	}

}
