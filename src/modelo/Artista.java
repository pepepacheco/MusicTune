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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreArtista == null) ? 0 : nombreArtista.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artista other = (Artista) obj;
		if (nombreArtista == null) {
			if (other.nombreArtista != null)
				return false;
		} else if (!nombreArtista.equals(other.nombreArtista))
			return false;
		return true;
	}
	
	
}