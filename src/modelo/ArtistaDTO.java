package modelo;

/**
 * @author Rafel Vargas del Moral
 */
public class ArtistaDTO {
    private String nombreArtista;

    /**
     * @param nombre
     */
    public ArtistaDTO(String nombre) {
        this.nombreArtista = nombre;      
        addArtista(); //Al crearse el objeto automaticamente se añade a una lista dinámica
    }

    /**
     * @return nombreArtista
     */
    public String getNombreArtista() {
        return nombreArtista;
    }

    @Override
    public String toString() {
        return "Artista: "+nombreArtista+"\n";
    }

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
		ArtistaDTO other = (ArtistaDTO) obj;
		if (nombreArtista == null) {
			if (other.nombreArtista != null)
				return false;
		} else if (!nombreArtista.equals(other.nombreArtista))
			return false;
		return true;
	}
	//Método que añade el artista a una lista dinámica
	private boolean addArtista() {
		return PlayList.getListaArtistas().add(this);
	}


}