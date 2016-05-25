package modelo;

public interface AlbumDAO {
	
	/**
	 * @return true si la tabla ha sido creada correctamente
	 */
	boolean crearTalba();
	/**
	 * @param albumNuevo
	 * @param albumAntiguo
	 * @return true si la modificación se ha realizado correctamente
	 */
	boolean ModificarAlbum(CancionDTO albumNuevo, CancionDTO albumAntiguo);
	/**
	 * @param al album
	 * @return true si la canción ha sido añadida correctamente
	 */
	boolean addAlbum(AlbumDTO al);
	/**
	 * @param al album
	 * @return true si la canción ha sido borrada correctamente
	 */
	boolean borrarAlbum(AlbumDTO al);
}
