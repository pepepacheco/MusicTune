package modelo;

/**
 * @author Rafael Vargas del Moral
 * @version 1.0
 */

public interface CancionDAO {
	/**
	 * @return true si la tabla ha sido creada correctamente
	 */
	boolean crearTabla();
	
	/**
	 * @param cancionNueva
	 * @param cancionAntigua
	 * @return true si la canción ha sido modificada correctamente
	 */
	boolean modificarCancion(CancionDTO cancionNueva, CancionDTO cancionAntigua);
	
	/**
	 * @param c Cancion
	 * @return true si la canción ha sido añadida correctamente
	 */
	boolean addCancion(CancionDTO c);
	
	/**
	 * @param c Cancion
	 * @return true si la canción ha sido eliminada correctamente
	 */
	boolean borrarCancion(CancionDTO c);
;}
