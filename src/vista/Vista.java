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


public class Vista {
	private JFrame frame;
	private JMenuBar barraMenu;
	private JMenu menuArchivo;
	private JMenu editar;
	private JCheckBoxMenuItem abrir;
	private JCheckBoxMenuItem salir;
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JList list;
	private JScrollPane scroll;

	
	public Vista(){
		inicialize();
		Controlador controlador = new Controlador(this); 

	}
	
	private void inicialize(){
		frame = new JFrame("MusicTune");
		frame.setBounds(100, 100, 696, 457);
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
		
		panelInferior = new JPanel();
		panelInferior.setBackground(Color.GRAY);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelSuperior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
						.addComponent(panelInferior, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panelSuperior, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelInferior, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		GroupLayout gl_panel_1 = new GroupLayout(panelInferior);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 660, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 168, Short.MAX_VALUE)
		);
		panelInferior.setLayout(gl_panel_1);
		
		list = new JList();
		list.setValueIsAdjusting(true);
		list.setFont(new Font("Leelawadee", Font.PLAIN, 14));


		scroll = new JScrollPane(list);
		panelSuperior.add(scroll);
		scroll.setViewportView(list);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	public JList getJList(){
		return list;
	}
	
	public JCheckBoxMenuItem getSalir(){
		return salir;
	}
}
