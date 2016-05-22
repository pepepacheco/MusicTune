package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

public class Creditos extends JDialog implements ActionListener {
	private JLabel lblCrditos;
	private JPanel panel;
	private JLabel lblnombreDeLa;
	private GroupLayout gl_panel;
	private JLabel lblMusictune;
	private JLabel lblDesarrolladoPor;
	private JLabel lblRafaelVargasDel;
	private JLabel lblFecha;
	private JLabel label;

	public Creditos () {
		initcomponents();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.dispose();
	}	
	
	private void initcomponents () {
		lblCrditos = new JLabel("Créditos");
		lblCrditos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCrditos.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblCrditos, BorderLayout.NORTH);
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		lblnombreDeLa = new JLabel("Nombre de la aplicación:");
		lblnombreDeLa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblnombreDeLa.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblMusictune = new JLabel("MusicTune");
		lblMusictune.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblDesarrolladoPor = new JLabel("Desarrollado por:");
		lblDesarrolladoPor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDesarrolladoPor.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblRafaelVargasDel = new JLabel("Rafael Vargas del Moral");
		lblRafaelVargasDel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		
		label = new JLabel("22/05/2016");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDesarrolladoPor, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
						.addComponent(lblnombreDeLa, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
						.addComponent(lblMusictune, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
						.addComponent(lblRafaelVargasDel, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
						.addComponent(lblFecha, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(lblnombreDeLa)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMusictune)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDesarrolladoPor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblRafaelVargasDel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFecha)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addContainerGap(101, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setModal(true);
		getContentPane().add(panel);
		this.pack();
	}
}
