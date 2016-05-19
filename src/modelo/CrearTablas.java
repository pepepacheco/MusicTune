package modelo;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.SQLException;
public class CrearTablas {
	private static Statement sentencia;
	
	public static void crearTabla(Connection conexion) throws SQLException{
		String sqlDelete = "DROP TABLE IF EXISTS cancion; DROP TABLE IF EXISTS album; DROP TABLE IF EXISTS artista";
		
		String sql = "CREATE TABLE artista ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT);"
				+ "CREATE TABLE album ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT,"
				+ " año INTEGER);"
				+ "CREATE TABLE cancion ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT,"
				+ " album TEXT FOREIGN KEY REFERENCES album(nombre),"
				+ " artista TEXT FOREIGN KEY REFERENCES artista(nombre)"
				+ " genero TEXT,"
				+ " duracion INTEGER,"
				+ " numero INTEGER);";
		
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sqlDelete);
			sentencia.executeUpdate(sql);		

	}

/*	
	public static void crearTrigger(Connection con) throws SQLException {
		String sql = "CREATE TABLE HISTORIAL ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nombre TEXT, apellidos);";
		String sql2 = "CRATE TRIGGER IF NOT EXISTS BORRADO BEFORE DELETE"
				+ "ON usuario"
				+ "BEGIN INSERT INTO HISTORIAL VALUES (old.id, old.nombre, deletime(\"now\");"
				+ "END";
		sentencia = con.prepareStatement(sql);
		sentencia.addBatch(sql);
		sentencia.addBatch(sql2);
		sentencia.executeBatch();
	}
*/	
}
