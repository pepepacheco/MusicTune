package modelo;

/**
 * @author Rafael Vargas del Moral
 * @version 1.0
 */

@FunctionalInterface
public interface Buscar {
	
	/**
	 * Criterio a implementar para realizar búsquedas
	 * @param c Cancion
	 * @return true si el criterio se cumple
	 */
    boolean criterio (CancionDTO c);
}