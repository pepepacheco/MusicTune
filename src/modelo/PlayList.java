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

    private static List<CancionDTO> listaCanciones = new ArrayList<CancionDTO>();
    private static Set<AlbumDTO> listaAlbumes = new HashSet<AlbumDTO>();
    private static Set<ArtistaDTO> listaArtistas = new HashSet<ArtistaDTO>();
    

    public  static List<CancionDTO> getListaCanciones() {
        return listaCanciones;
    }
    
    public  static Set<AlbumDTO> getListaAlbumes() {
        return listaAlbumes;
    }
    
    public  static Set<ArtistaDTO> getListaArtistas() {
        return listaArtistas;
    }
   
}