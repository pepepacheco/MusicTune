package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Vargas del Moral
 */
public abstract class PlayList {
    //Atributo de clase que va a almacenar todas las canciones de la PlayList
    private static List<PlayList> listaReproduccion = new ArrayList<PlayList>();
    
    /**
     * @return listaReproduccion 
     */
    public static List<PlayList> getListaReproduccion() {
        return listaReproduccion;
    }
    
    public abstract boolean addCancion();
}