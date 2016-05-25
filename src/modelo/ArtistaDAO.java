package modelo;

public interface ArtistaDAO {
	
	/**
	 * @return true si la tabla se ha creado correctamente
	 */
	boolean crearTalba();
	
	/**
	 * @param artistaNuevo
	 * @param artistaAntiguo
	 * @return true si la modificación del artista se ha realizado correctamente
	 */
	boolean ModificarArtista(ArtistaDTO artistaNuevo, ArtistaDTO artistaAntiguo);
	
	/**
	 * @param a
	 * @return true si la el artista ha sido añadido correcamente
	 */
	boolean addArtista(ArtistaDTO a);
	
	/**
	 * @param a
	 * @return true si el artista ha sido borrado correctamente
	 */
	boolean borrarArtista(ArtistaDTO a);
}
