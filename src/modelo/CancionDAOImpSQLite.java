package modelo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class CancionDAOImpSQLite implements CancionDAO {
	private Connection conexion = ConexionBD.getConexion();
	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	
	
	@Override
	public boolean crearTalba() {
		String sqlDelete = "DROP TABLE IF EXISTS cancion";
		String sql = "CREATE TABLE cancion ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " nombre TEXT,"
				+ " album TEXT,"
				+ " artista TEXT,"
				+ " genero TEXT,"
				+ " duracion INTEGER,"
				+ " numero INTEGER,"
				+ " FOREIGN KEY(album) REFERENCES album(nombre),"
				+ " FOREIGN KEY(artista) REFERENCES artista(nombre));";
		try {
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sqlDelete);
			sentencia.executeUpdate(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}	
		return true;
	}

	@Override
	public boolean ModificarCancion(CancionDTO cancionNueva, CancionDTO cancionAntigua) {
		String sql = "UPDATE cancion SET"
				+ "nombre=?, album=?, artista=?, genero=?, duracion=?, numero=?"
				+ " where nombre=?;";		
		try {
			sentenciaPreparada = conexion.prepareStatement(sql);
			sentenciaPreparada.setString(1, cancionNueva.getNombreCancion());
			sentenciaPreparada.setString(2, cancionNueva.getNombreAlbum());
			sentenciaPreparada.setString(3, cancionNueva.getNombreArtista());
			sentenciaPreparada.setString(4, cancionNueva.getGenero());
			sentenciaPreparada.setInt(5, cancionNueva.getDuracion());
			sentenciaPreparada.setInt(6, cancionNueva.getNumeroCancion());
			sentenciaPreparada.setString(7, cancionAntigua.getNombreCancion());
			sentenciaPreparada.executeUpdate(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean addCancion(CancionDTO c) {
		String sql = "INSERT INTO cancion VALUES ("
				+ "null, \""+c.getNombreCancion()+"\" , \""+c.getNombreAlbum()+"\" ,"
				+ " \""+c.getNombreArtista()+"\" , \""+c.getGenero()+"\" , "
				+ "\""+c.getDuracion()+"\" , \""+c.getNumeroCancion()+"\" ); ";		
		try {
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addCancion(List<CancionDTO> lista) {		
		for (CancionDTO c : lista) {
			String sql = "INSERT INTO cancion VALUES ("
					+ "null , \""+c.getNombreCancion()+"\" , \""+c.getNombreAlbum()+"\" ,"
					+ " \""+c.getNombreArtista()+"\" , \""+c.getGenero()+"\" , "
					+ ""+c.getDuracion()+" , "+c.getNumeroCancion()+" ); ";		
			System.out.println(sql);
			try {
				sentencia = conexion.createStatement();
				sentencia.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
				
			}
		}	
		return true;
	}
	
	@Override
	public boolean borrarCancion(CancionDTO c) {
		String sql = "DELETE FROM cancion WHERE nombre = ? ;";
		try {
			sentenciaPreparada = conexion.prepareStatement(sql);
			sentenciaPreparada.setString(1, c.getNombreCancion());
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

}
