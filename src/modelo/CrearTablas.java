package modelo;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.SQLException;
public class CrearTablas {
	private static Statement sentencia;
	
	public static void crearTabla(Connection conexion) throws SQLException{
		String sqlDelete = "DROP TABLE IF EXISTS cancion; DROP TABLE IF EXISTS album; DROP TABLE IF EXISTS artista";
		String sql = "CREATE TABLE cancion ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT,"
				+ " genero TEXT,"
				+ " duracion INTEGER,"
				+ " numero INTEGER);"
				+ "CREATE TABLE album ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT,"
				+ " año INTEGER,"
				+ " id_cancion NUMBER,"
				+ "FOREIGN KEY(id_cancion) REFERENCES cancion(id));"
				+ "CREATE TABLE artista ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT,"
				+ " id_album,"
				+ " id_cancion,"
				+ "FOREIGN KEY(id_album) REFERENCES album(id),"
				+ "FOREIGN KEY(id_cancion) REFERENCES cancion(id));";	

			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sqlDelete);
			sentencia.executeUpdate(sql);		

	}
}
