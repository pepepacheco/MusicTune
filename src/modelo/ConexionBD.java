package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.sqlite.SQLiteConfig;
import vista.Vista;

public class ConexionBD {
	static Vista vista = new Vista();
	private static Connection conexion = null;
	
	private ConexionBD(){};
	
	public static Connection getConexion(){
		//Usamos la filosofía del patrón singleton
		Runtime.getRuntime().addShutdownHook(new MiShoutdownHuk());
		if (conexion == null){
			
			ResourceBundle rb = ResourceBundle.getBundle("sqlite");
			final String URL = rb.getString("url");
			final String DRIVER = rb.getString("driver");
			
			try {
				//Conectamos con la base de datos
				Class.forName(DRIVER);
				
				//establecemos una configuración
				SQLiteConfig conf = new SQLiteConfig();
				conf.enforceForeignKeys(true);				
				
				//Cargamos la base de datos a través de un fichero de configuración
				conexion = DriverManager.getConnection(URL, conf.toProperties());
				
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
			if (conexion != null)
				try {
					conexion.close();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(vista.getFrame(), "Error inesperado");
					//e.printStackTrace();
				}
		}
	}
	

}