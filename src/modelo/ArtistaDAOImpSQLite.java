package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class ArtistaDAOImpSQLite implements ArtistaDAO {
	private Connection conexion = ConexionBD.getConexion();
	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;

	@Override
	public boolean crearTalba() {
		String sqlDelete = "DROP TABLE IF EXISTS artista";
		String sql = "CREATE TABLE artista ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT ,"
				+ " nombre TEXT UNIQUE);";
		try {
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sqlDelete);
			sentencia.executeUpdate(sql);
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}	
		return true;
	}

	@Override
	public boolean ModificarArtista(ArtistaDTO artistaNuevo, ArtistaDTO artistaAntiguo) {
		String sql = "UPDATE ARTISTA SET nombre=? WHERE nombre=?;";
		try {
			sentenciaPreparada = conexion.prepareStatement(sql);
			sentenciaPreparada.setString(1, artistaNuevo.getNombreArtista());
			sentenciaPreparada.setString(2, artistaAntiguo.getNombreArtista());
		} catch (SQLException e) {
			return false;
			//e.printStackTrace();
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean addArtista(ArtistaDTO a) {
		String sql = "INSERT INTO artista VALUES ("
				+ "null, \""+a.getNombreArtista()+"\") ";		
			try {
				sentencia = conexion.createStatement();
				sentencia.executeUpdate(sql);
			} catch (SQLException e) {
				//e.printStackTrace();
				return false;
			} finally {
				try {
					sentencia.close();
				} catch (SQLException e) {
					//e.printStackTrace();
				}
			}
		return true;
	}
	
	public boolean addArtista(Set<ArtistaDTO> lista) {
		for (ArtistaDTO a : lista) {		
			String sql = "INSERT INTO artista VALUES ("
				+ "null, \""+a.getNombreArtista()+"\") ";		
			try {
				sentencia = conexion.createStatement();
				sentencia.executeUpdate(sql);
			} catch (SQLException e) {
				//e.printStackTrace();
				return false;
			} finally {
				try {
					sentencia.close();
				} catch (SQLException e) {
					//e.printStackTrace();
				}
			}
		}
		return true;
	}

	@Override
	public boolean borrarArtista(ArtistaDTO a) {
		String sql = "DELETE FROM artista WHERE nombre = ? ;";
		try {
			sentenciaPreparada = conexion.prepareStatement(sql);
			sentenciaPreparada.setString(1, a.getNombreArtista());
			sentenciaPreparada.executeUpdate();
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
		return true;
	}

}
