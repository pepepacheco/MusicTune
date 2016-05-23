package modelo;

public interface CancionDAO {
	boolean crearTabla();
	boolean modificarCancion(CancionDTO cancionNueva, CancionDTO cancionAntigua);
	boolean addCancion(CancionDTO c);
	boolean borrarCancion(CancionDTO c);
;}
