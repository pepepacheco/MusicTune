package modelo;

public interface AlbumDAO {
	boolean crearTalba();
	boolean ModificarAlbum(AlbumDTO albumNuevo, AlbumDTO albumAntiguo);
	boolean addAlbum(AlbumDTO al);
	boolean borrarAlbum(AlbumDTO al);
}
