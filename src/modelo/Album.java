package modelo;

import java.sql.SQLException;
import java.sql.Statement;

import modelo.exceptions.InvalidYearException;

/**
 * @author Rafael Vargas del Moral
 * @version 1.0
 */
public abstract class Album extends Artista {
    private String nombreAlbum;
    private int anio;

    /**
     * @param nombre
     * @param anio
     * @param artista
     */
    public Album(String nombre, String anio, String artista) throws InvalidYearException {       
        super(artista);
        this.nombreAlbum = nombre;
        if (anio.matches("[0-9]+"))
            this.anio = Integer.parseInt(anio);   
        else
            throw new InvalidYearException();
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
		int result = super.hashCode();
		result = prime * result + ((nombreAlbum == null) ? 0 : nombreAlbum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
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

	@Override
    public abstract boolean addCancion();    
	
	public static void addAlbumBD() throws SQLException{
		Statement sentencia = ConexionBD.getConexion().createStatement(); 
		String insertAlbum = "";
		//INSERT INTO album VALUES (null, nombreAlbum, año, null)				
		for (PlayList cancion : PlayList.getListaReproduccion()) {			
			insertAlbum = "INSERT INTO album VALUES ("
					+ "null,\""+((Album) cancion).getNombreAlbum()+"\","
					+ ((Album) cancion).getAnio()+","
					+ " null);";
			sentencia.executeUpdate(insertAlbum);
		}			
	}
}
