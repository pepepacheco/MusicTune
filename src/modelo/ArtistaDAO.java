package modelo;

public interface ArtistaDAO {
	boolean crearTalba();
	boolean ModificarArtista(ArtistaDTO artistaNuevo, ArtistaDTO artistaAntiguo);
	boolean addArtista(ArtistaDTO a);
	boolean borrarArtista(ArtistaDTO a);
}
