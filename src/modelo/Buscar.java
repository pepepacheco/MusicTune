package modelo;

/**
 * @author Rafael Vargas del Moral
 */
@FunctionalInterface
public interface Buscar {
    boolean criterio (Cancion c); //Criterio el cual implementaremos segun nos convenga
}