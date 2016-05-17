package modelo;

import modelo.exceptions.InvalidTackNumberException;
import modelo.exceptions.InvalidYearException;
import modelo.exceptions.EmptyFieldsException;
import modelo.exceptions.InvalidDurationException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
//import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

import javax.swing.JProgressBar;

/**
 * @author Rafael Vargas Del Moral
 * @version 1.0
 */
public final class Cancion {
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
    public Cancion(String nombre, String album, String artista, String year, String genero, String duracion, String numero)
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
        
        addCancion();
    }

    //geters and setters
    public String getNombreCancion() {
        return nombreCancion;
    }
     
    public String getNombreAlbum() {
		return nombreAlbum;
	}

	public String getNombreArtista() {
		return nombreArtista;
	}

	public int getYearAlbum() {
		return yearAlbum;
	}

    public String getGenero() {
        return genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getNumeroCancion() {
        return numeroCancion;  
    }
    
    public void setNombreCancion(String nombreCancion) {
		this.nombreCancion = nombreCancion;
	}

	public void setNombreAlbum(String nombreAlbum) {
		this.nombreAlbum = nombreAlbum;
	}

	public void setNombreArtista(String nombreArtista) {
		this.nombreArtista = nombreArtista;
	}

	public void setYearAlbum(int yearAlbum) {
		this.yearAlbum = yearAlbum;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public void setNumeroCancion(int numeroCancion) {
		this.numeroCancion = numeroCancion;
	}

	private boolean addCancion() {
    	return PlayList.getListaCanciones().add(this);
    }
    
    /**
     * @param c lista de canciones que contiene la PlayList
     * @param b interfaz funcional para implementar critero de busqueda
     * @return
     */
    public static List<Cancion> BuscarCancion(List<Cancion> listaCanciones, Buscar b){
        List<Cancion> cancionesEncontradas = new ArrayList<Cancion>();
        for (Cancion cancion : listaCanciones) {
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
		Cancion other = (Cancion) obj;
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
	
	public static void addCancionBD() throws SQLException{

		Statement sentencia = ConexionBD.getConexion().createStatement(); 
		String insertCancion = "";
		
		//INSERT INTO cancion VALUES (null, Nombre, Género, Duración, Número)				
		for (Cancion cancion : PlayList.getListaCanciones()) {
			
			insertCancion = "INSERT INTO cancion VALUES ("
					+ "null, \""+cancion.getNombreCancion()+"\", "
					+ "\""+cancion.getGenero()+"\", "+
					cancion.getDuracion()+", "+
					cancion.getNumeroCancion()+")";	
			
			sentencia.executeUpdate(insertCancion);
		}			
	}
   
/*  
    //Main Testeo   
    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("sources/discographies.csv"))) {
            in.nextLine();
            while (in.hasNextLine()){
                String[] campos = in.nextLine().split(";");
                new Cancion(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5], campos[6]);
            }
        } catch (Exception ex) {
            
        }        
        //System.out.println(PlayList.getListaReproduccion());
        //System.out.println(Cancion.BuscarCancion(PlayList.getListaReproduccion(), b-> ((Cancion) b).getNombreAlbum().contains("Nevermind")));
        System.out.println(Cancion.BuscarCancion(PlayList.getListaReproduccion(), new Buscar() {
            @Override
            public boolean criterio(PlayList p) {
                return ((Cancion) p).getNombreCancion().toLowerCase().startsWith("h");
            }
        }));
    }
*/ 
}