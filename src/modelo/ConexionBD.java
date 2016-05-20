package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.sqlite.SQLiteConfig;

public class ConexionBD {
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
					//e.printStackTrace();
				}
		}
	}
	

}