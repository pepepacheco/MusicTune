package controlador;

import modelo.AlbumDTO;
import modelo.ArtistaDTO;
import modelo.CancionDTO;
import modelo.ConexionBD;
import modelo.exceptions.InvalidTackNumberException;
import modelo.exceptions.InvalidYearException;
import modelo.exceptions.EmptyFieldsException;
import modelo.exceptions.InvalidDurationException;
//import modelo.PlayList;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Rafael Vargas del Moral
 * @version 1.0
 */

public final class Service {
	private static JsonReader reader;
	private static Connection conexion = ConexionBD.getConexion();
	private static PreparedStatement sentenciaPreparada;
	private static Statement sentencia;
	private static ResultSet resultado;

	/**
	 * Método que lee nuestro fichero JSON y crea los objetos.
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidYearException
	 * @throws InvalidDurationException
	 * @throws InvalidTackNumberException
	 */
    public static void loadJson(File file) throws FileNotFoundException, IOException, InvalidYearException,
    InvalidDurationException, InvalidTackNumberException, EmptyFieldsException{
        reader = new JsonReader(new FileReader(file));
        reader.beginArray();
        String[] campo = new String[7];
        boolean comprobacion = false;
        while(reader.hasNext()){
            reader.beginObject();
            if (reader.nextName().equals("Track Name"))
                campo[0] = reader.nextString();
            if (reader.nextName().equals("Album Name"))
                campo[1] = reader.nextString();
            if (reader.nextName().equals("Artist Name"))
                campo[2] = reader.nextString();
            if (reader.nextName().equals("Year"))
                campo[3] = reader.nextString();
            if (reader.nextName().equals("Genre"))
                campo[4] = reader.nextString();
            if (reader.nextName().equals("Track Duration (ms)"))
                campo[5] = reader.nextString()+"";            
            if (reader.nextName().equals("Track Number"))
                campo[6] = reader.nextString();
            
            //Compruebo que no haya campos nulos en el JSON.
            for (String string : campo) {
                if (string != null)
                    comprobacion = true;
                else{
                    comprobacion = false;
                    break;
                }                        
            }           
            if (comprobacion) {        
                new CancionDTO(campo[0], campo[1], campo[2], campo[3], campo[4], campo[5], campo[6]);
                new AlbumDTO(campo[1], campo[3]);
                new ArtistaDTO(campo[2]);           
        	}
            reader.endObject();
        }
        reader.endArray();
        //System.out.println(PlayList.getListaReproduccion());
    }
    
	public static boolean crearVista() {
		//String sqlDelete = "DROP VIEW IF EXISTS carga_datos;";
		String sql = "create view carga_datos as"
					+ " select cancion.nombre, album.nombre, artista.nombre, album.year, cancion.genero, cancion.duracion, cancion.numero"
					+ " from artista, album, cancion"
					+ " where cancion.album = album.nombre and cancion.artista = artista.nombre;";
		
		try {
			sentencia = conexion.createStatement();
			//sentencia.executeQuery(sqlDelete);
			sentencia.execute(sql);		
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
    
    public static boolean autoLoad() throws InvalidYearException, InvalidDurationException, InvalidTackNumberException, EmptyFieldsException {
    	String sql = "SELECT * FROM CARGA_DATOS;";   
    	try {
			sentencia = conexion.createStatement();
			resultado = sentencia.executeQuery(sql);
			
			while(resultado.next()) {
				//Cuando se crea un objeto de tipo CancionDTO, AlbumDTO o ArtistaDTO, se añade automáticamente a la lista.
				new CancionDTO(resultado.getString(1), resultado.getString(2),
				resultado.getString(3), resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getString(7));
				new AlbumDTO(resultado.getString(2), resultado.getString(4));
				new ArtistaDTO(resultado.getString(3));
			}			

		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	return true;
    }
    
    public static int contarFilas(String tabla, String nombre) {
    	String sql = "SELECT COUNT(*) FROM "+tabla+" WHERE nombre = ? ";
    	try {
    		sentenciaPreparada = conexion.prepareStatement(sql);
			sentenciaPreparada.setString(1, nombre);		
			resultado = sentenciaPreparada.executeQuery();
			return resultado.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;	
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
    
    public static void createTrigger() {
		String sqlHistorial = "CREATE TABLE IF NOT EXISTS HISTORIAL("
				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "NOMBRE TEXT,"
				+ "FECHABORRADO DATE);";
		String sqlTrigger= "CREATE TRIGGER IF NOT EXISTS BORRADO "
				+ "AFTER DELETE ON CANCION FOR EACH ROW "
				+ "BEGIN "
				+ "INSERT INTO HISTORIAL VALUES (null, old.NOMBRE, date('now'));"
				+ "END";
		try {
			sentencia = conexion.createStatement();
			sentencia.addBatch(sqlHistorial);
			sentencia.addBatch(sqlTrigger);
			sentencia.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
}
