package vista;

import javax.swing.JFrame;

import controlador.Controlador;
import modelo.PlayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.util.List;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class Vista {
	private JFrame frame;
	private JMenuBar barraMenu;
	private JMenu menuArchivo;
	private JMenu editar;
	private JCheckBoxMenuItem abrir;
	private JCheckBoxMenuItem salir;
	private JPanel panelSuperior;
	private JScrollPane scroll;
	private JTable tabla;
	private JFileChooser file;
	private JLabel lblBuscar;
	private JTextArea textAreaBuscar;
	private JButton btnIrMostrarResultado;
	private GroupLayout groupLayout;
	private GroupLayout gl_panelSuperior;
	private JComboBox comboBox;

	
	public Vista(){
		inicialize();
		file = new JFileChooser();
		Controlador controlador = new Controlador(this); 

	}
	
	private void inicialize(){
		frame = new JFrame("MusicTune");
		frame.setBounds(100, 100, 768, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		barraMenu = new JMenuBar();
		frame.setJMenuBar(barraMenu);
		
		menuArchivo = new JMenu("Archivo");
		barraMenu.add(menuArchivo);
		
		abrir = new JCheckBoxMenuItem("Abrir");
		menuArchivo.add(abrir);
		
		salir = new JCheckBoxMenuItem("Salir");
		menuArchivo.add(salir);
		
		editar = new JMenu("Editar");
		barraMenu.add(editar);
		
		panelSuperior = new JPanel();
		groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelSuperior, GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(panelSuperior, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
					.addGap(93))
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
		panelSuperior.add(tabla);
		scroll = new JScrollPane(tabla);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel panel = new JPanel();
		gl_panelSuperior = new GroupLayout(panelSuperior);
		gl_panelSuperior.setHorizontalGroup(
			gl_panelSuperior.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSuperior.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelSuperior.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
						.addComponent(scroll, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelSuperior.setVerticalGroup(
			gl_panelSuperior.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelSuperior.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scroll, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
					.addContainerGap())
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
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblBuscar)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textAreaBuscar, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
					.addGap(12)
					.addComponent(btnIrMostrarResultado)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBuscar)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textAreaBuscar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnIrMostrarResultado))
					.addContainerGap(50, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		panelSuperior.setLayout(gl_panelSuperior);

		
		frame.getContentPane().setLayout(groupLayout);
	}
	
	//Geters
	
	public JFrame getFrame(){
		return frame;
	}
	
	public JCheckBoxMenuItem getAbrir(){
		return abrir;
	}
	
	public JCheckBoxMenuItem getSalir(){
		return salir;
	}
	
	public JTable getTabla(){
		return this.tabla;
	}
	
	public JPanel getPanelSuperior(){
		return panelSuperior;
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
