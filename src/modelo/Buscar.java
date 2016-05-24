package modelo;

/**
 * @author Rafael Vargas del Moral
 */
@FunctionalInterface
public interface Buscar {
    boolean criterio (CancionDTO c); //Criterio a implementar para realizar búsquedas
}