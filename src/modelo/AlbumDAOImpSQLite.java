package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class AlbumDAOImpSQLite implements AlbumDAO {
	private Connection conexion = ConexionBD.getConexion();
	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;

	@Override
	public boolean crearTalba() {
		String sqlDelete = "DROP TABLE IF EXISTS album";
		String sql = "CREATE TABLE album ("
				+ "id INTEGER ,"
				+ " nombre TEXT PRIMARY KEY,"
				+ " year INTEGER);";
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
	public boolean ModificarAlbum(AlbumDTO albumNuevo, AlbumDTO albumAntiguo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAlbum(AlbumDTO al) {
		String sql = "INSERT INTO album VALUES ("
				+ "null , \""+al.getNombreAlbum()+"\" , "+al.getAnio()+") ";
			try {
				sentencia = conexion.createStatement();
				sentencia.executeUpdate(sql);
			} catch (SQLException e) {
				//e.printStackTrace();
				return false;
			}
		return true;
	}
	
	public boolean addAlbum(Set<AlbumDTO> lista) {
		for (AlbumDTO al : lista) {
			String sql = "INSERT INTO album VALUES ("
					+ "null , \""+al.getNombreAlbum()+"\" , "+al.getAnio()+") ";	
				try {
					sentencia = conexion.createStatement();
					sentencia.executeUpdate(sql);
				} catch (SQLException e) {
					//e.printStackTrace();
					return false;
				}
		}
		return true;
	}
	
	@Override
	public boolean borrarAlbum(AlbumDTO al) {
		// TODO Auto-generated method stub
		return false;
	}

}
