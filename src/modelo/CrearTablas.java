package modelo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
public class CrearTablas {
	private static Statement sentencia;
	
	public void crearTablaUsuario(Connection conexion){
		String sqlDelete = "DROPT TABLE IF EXISTS Canción";
		String sql = "CREATE TABLE Canción ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Nombre TEXT,"
				+ "Género TEXT,"
				+ "Duracion INTEGER,"
				+ "Número INTEGER)";		
		try {

			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql);
			sentencia.executeUpdate(sqlDelete);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
}
