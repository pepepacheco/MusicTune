package modelo;

public interface AlbumDAO {
	boolean crearTalba();
	boolean ModificarAlbum(CancionDTO albumNuevo, CancionDTO albumAntiguo);
	boolean addAlbum(AlbumDTO al);
	boolean borrarAlbum(AlbumDTO al);
}
