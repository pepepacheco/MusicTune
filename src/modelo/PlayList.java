package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Rafael Vargas del Moral
 */
public final class PlayList {

    private static List<Cancion> listaCanciones = new ArrayList<Cancion>();
    private static Set<Album> listaAlbumes = new HashSet<Album>();
    private static Set<Artista> listaArtistas = new HashSet<Artista>();
    

    public  static List<Cancion> getListaCanciones() {
        return listaCanciones;
    }
    
    public  static Set<Album> getListaAlbumes() {
        return listaAlbumes;
    }
    
    public  static Set<Artista> getListaArtistas() {
        return listaArtistas;
    }
   
}