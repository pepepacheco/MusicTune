package modelo;

import java.sql.SQLException;
import java.sql.Statement;

import modelo.exceptions.InvalidYearException;

/**
 * @author Rafael Vargas del Moral
 * @version 1.0
 */
public class Album {
    private String nombreAlbum;
    private int anio;

    /**
     * @param nombre
     * @param anio
     * @param artista
     */
    public Album(String nombre, String anio) throws InvalidYearException {       
        this.nombreAlbum = nombre;
        if (anio.matches("[0-9]+"))
            this.anio = Integer.parseInt(anio);   
        else
            throw new InvalidYearException();
        
        addAlbum();
    }

    /**
     * @return nombreAlbum
     */
    public String getNombreAlbum() {
        return nombreAlbum;
    }

    /**
     * @return anio 
     */
    public int getAnio() {
        return anio;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreAlbum == null) ? 0 : nombreAlbum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (nombreAlbum == null) {
			if (other.nombreAlbum != null)
				return false;
		} else if (!nombreAlbum.equals(other.nombreAlbum))
			return false;
		return true;
	}
	
	private boolean addAlbum() {
		return PlayList.getListaAlbumes().add(this);
	}

 
	
	public static void addAlbumBD() throws SQLException{
		Statement sentencia = ConexionBD.getConexion().createStatement(); 
		String insertAlbum = "";
		//INSERT INTO album VALUES (null, nombreAlbum, año, null)				
		for (Album album : PlayList.getListaAlbumes()) {			
			insertAlbum = "INSERT INTO album VALUES ("
					+ "null,\""+album.getNombreAlbum()+"\","
					+ album.getAnio()+","
					+ " null);";
			sentencia.executeUpdate(insertAlbum);
		}			
	}

}
