package modelo;

/**
 * @author Rafel Vargas del Moral
 */
public abstract class Artista extends PlayList {
    private String nombreArtista;

    /**
     * @param nombre
     */
    public Artista(String nombre) {
        this.nombreArtista = nombre;
    }

    /**
     * @return nombreArtista
     */
    public String getNombreArtista() {
        return nombreArtista;
    }

    @Override
    public String toString() {
        return "Artista: "+nombreArtista;
    }

    @Override
    public abstract boolean addCancion();
}