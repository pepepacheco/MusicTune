package modelo;

import modelo.exceptions.InvalidTackNumberException;
import modelo.exceptions.InvalidYearException;
import modelo.exceptions.EmptyFieldsException;
import modelo.exceptions.InvalidDurationException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Rafael Vargas Del Moral
 * @version 1.0
 */

public final class CancionDTO {
    private String nombreCancion;
    private String nombreAlbum;
    private String nombreArtista;
    private int yearAlbum;
    private String genero; 
    private int duracion;
    private int numeroCancion;

    /**
     * @param nombre
     * @param album
     * @param artista
     * @param anio
     * @param genero
     * @param duracion
     * @param numero
     * @throws Modelo.InvalidYearException
     * @throws InvalidDurationException 
     * @throws Modelo.InvaliddurationException
     * @throws Modelo.InvalidTackNumberException
     * @throws EmptyFieldsException 
     */
    public CancionDTO(String nombre, String album, String artista, String year, String genero, String duracion, String numero)
    throws InvalidYearException, InvalidDurationException, InvalidTackNumberException, EmptyFieldsException {
    	
        this.nombreCancion = nombre;
        this.nombreAlbum = album;
        this.nombreArtista = artista;
        
        if (year.matches("[0-9]{4}"))
        	this.yearAlbum = Integer.parseInt(year);
        else
        	throw new InvalidYearException();
        
        this.genero = genero;
        
        if (duracion.matches("[0-9]+"))
            this.duracion = Integer.parseInt(duracion);           
        else
            throw new InvalidDurationException();
        
        if (numero.matches("[0-9]+"))
            this.numeroCancion = Integer.parseInt(numero);
        else
            throw new InvalidTackNumberException();
        
        if (!(nombre.length() > 0 && album.length() > 0 && artista.length() > 0 && genero.length() > 0)){
        	throw new EmptyFieldsException();
        }
        
        addCancion(); //Al crearse el objeto automaticamente se añade a una lista dinámica
    }

    //geters and setters
    
    /**
     * @return nombreCancion
     */
    public String getNombreCancion() {
        return nombreCancion;
    }
    /**
     * @return nombreAlbum
     */
    
    public String getNombreAlbum() {
		return nombreAlbum;
	}
    /**
     * @return nombreArtista
     */
	public String getNombreArtista() {
		return nombreArtista;
	}
	/**
	 * @return yearAlbum
	 */
	public int getYearAlbum() {
		return yearAlbum;
	}
	/**
	 * @return genero
	 */
    public String getGenero() {
        return genero;
    }
    /**
     * @return duracion
     */
    public int getDuracion() {
        return duracion;
    }
    /**
     * @return numeroCancion
     */
    public int getNumeroCancion() {
        return numeroCancion;  
    }
    /**
     * @return true si la canción se añade correctamente a la lista
     */
	private boolean addCancion() {
    	return PlayList.getListaCanciones().add(this);
    }
    
    /**
     * @param c lista de canciones que contiene la PlayList
     * @param b interfaz funcional para implementar critero de busqueda
     * @return
     */
    public static List<CancionDTO> BuscarCancion(List<CancionDTO> listaCanciones, Buscar b){
        List<CancionDTO> cancionesEncontradas = new ArrayList<CancionDTO>();
        for (CancionDTO cancion : listaCanciones) {
            if (b.criterio(cancion))
                cancionesEncontradas.add(cancion);
        }
        return cancionesEncontradas;
    }

    /**
     * @return true si la canción es anadida con exito
     */

    @Override
    public String toString() {
        return "Nombre: "+nombreCancion+" Álbum: "+getNombreAlbum()+" Artista: "+getNombreArtista()
                +" Año: "+yearAlbum+" Género: "+genero+" Duración: "+(duracion/1000)+
                "seg. Numero en Disco: "+numeroCancion+"\n";                
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreAlbum == null) ? 0 : nombreAlbum.hashCode());
		result = prime * result + ((nombreArtista == null) ? 0 : nombreArtista.hashCode());
		result = prime * result + ((nombreCancion == null) ? 0 : nombreCancion.hashCode());
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
		CancionDTO other = (CancionDTO) obj;
		if (nombreAlbum == null) {
			if (other.nombreAlbum != null)
				return false;
		} else if (!nombreAlbum.equals(other.nombreAlbum))
			return false;
		if (nombreArtista == null) {
			if (other.nombreArtista != null)
				return false;
		} else if (!nombreArtista.equals(other.nombreArtista))
			return false;
		if (nombreCancion == null) {
			if (other.nombreCancion != null)
				return false;
		} else if (!nombreCancion.equals(other.nombreCancion))
			return false;
		return true;
	}
	
	//Main Testeo
/*   
    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("resources/discographies.csv"))) {
            in.nextLine();
            while (in.hasNextLine()){
                String[] campos = in.nextLine().split(";");
                new CancionDTO(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5], campos[6]);
            }
        } catch (Exception ex) {
            
        }        
        //System.out.println(PlayList.getListaCanciones());
        //System.out.println(CancionDTO.BuscarCancion(PlayList.getListaCanciones(), b->  b.getNombreAlbum().contains("Nevermind")));
        
        System.out.println(CancionDTO.BuscarCancion(PlayList.getListaCanciones(), new Buscar() {
            @Override
            public boolean criterio(CancionDTO d) {
                return d.getNombreCancion().toLowerCase().startsWith("h");
            }
        }));
    }
*/        
}