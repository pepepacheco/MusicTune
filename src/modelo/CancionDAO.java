package modelo;

public interface CancionDAO {
	boolean crearTalba();
	boolean ModificarCancion(CancionDTO c);
	boolean addCancion(CancionDTO c);
	boolean borrarCancion(CancionDTO c);
;}
