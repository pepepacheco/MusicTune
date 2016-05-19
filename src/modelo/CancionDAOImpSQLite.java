package modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class CancionDAOImpSQLite implements CancionDAO {
	private Connection conexion = ConexionBD.getConexion();
	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	
	
	@Override
	public boolean crearTalba() {
		String sqlDelete = "DROP TABLE IF EXISTS cancio";
		String sql = "CREATE TABLE cancion ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT,"
				+ " album TEXT FOREIGN KEY REFERENCES album(nombre),"
				+ " artista TEXT FOREIGN KEY REFERENCES artista(nombre),"
				+ " genero TEXT,"
				+ " duracion INTEGER,"
				+ " numero INTEGER);";
		try {
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sqlDelete);
			sentencia.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
		}	
		return true;
	}

	@Override
	public boolean ModificarCancion(CancionDTO c) {
		String sql = "UPDATE CANCION SET "
				+ "nombre=?, album=?, artista=?, genero=?, duracion=?, numero=?;";		
		try {
			sentenciaPreparada = conexion.prepareStatement(sql);
			sentenciaPreparada.setString(1, c.getNombreCancion());
			sentenciaPreparada.setString(2, c.getNombreAlbum());
			sentenciaPreparada.setString(3, c.getNombreArtista());
			sentenciaPreparada.setString(4, c.getGenero());
			sentenciaPreparada.setInt(5, c.getDuracion());
			sentenciaPreparada.setInt(6, c.getNumeroCancion());
			sentenciaPreparada.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
			//e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean addCancion(CancionDTO c) {
		String sql = "INSERT INTO cancion VALUES ("
				+ "null, ? , ? , ? , ?, ?, ? ); ";		
		try {
			sentenciaPreparada = conexion.prepareStatement(sql);
			sentenciaPreparada.setString(1, c.getNombreCancion());
			sentenciaPreparada.setString(2, c.getNombreAlbum());
			sentenciaPreparada.setString(3, c.getNombreArtista());
			sentenciaPreparada.setString(4, c.getGenero());
			sentenciaPreparada.setInt(5, c.getDuracion());
			sentenciaPreparada.setInt(6, c.getNumeroCancion());
			sentenciaPreparada.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
			//e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean borrarCancion(CancionDTO c) {
		
		return false;
	}

}
