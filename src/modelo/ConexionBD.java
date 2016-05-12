package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import vista.Vista;

public class ConexionBD {
	static Vista vista = new Vista();
	private static Connection conexion;
	
	private ConexionBD(){};
	
	public static Connection getConexion(){
		//Usamos la filosofía del patrón singleton
		
		if (conexion == null){
			ResourceBundle rb = ResourceBundle.getBundle("sqlite");
			final String URL = rb.getString("url");
			final String DRIVER = rb.getString("driver");
			Runtime.getRuntime().addShutdownHook(new MiShoutdownHuk());
			try {
				//Conectamos con la base de datos
				Class.forName(DRIVER);
				//trabajamos con un fichero de propiedades
				//Cargamos la base de datos
				conexion = DriverManager.getConnection(URL);
				
			} catch (ClassNotFoundException | SQLException e) {
				JOptionPane.showMessageDialog(vista.getFrame(), "Error de Conexión a la base de datos");
				//e.printStackTrace();
			} 	
		}
		return conexion;
	}
	
	static class  MiShoutdownHuk extends Thread{
		@Override
		public void run() {
			Connection con = ConexionBD.getConexion();
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(vista.getFrame(), "Error inesperado");
					//e.printStackTrace();
				}
		}
	}
}