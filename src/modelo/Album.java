package modelo;

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
    public abstract boolean addCancion();    
}
