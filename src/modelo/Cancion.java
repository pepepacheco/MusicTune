package modelo;

import modelo.exceptions.InvalidTackNumberException;
import modelo.exceptions.InvalidYearException;
import modelo.exceptions.InvalidDurationException;
//import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

/**
 * @author Rafael Vargas Del Moral
 * @version 1.0
 */
public final class Cancion extends Album {
    private String nombreCancion;
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
     * @throws Modelo.InvaliddurationException
     * @throws Modelo.InvalidTackNumberException
     */
    public Cancion(String nombre, String album, String artista, String anio, String genero, String duracion, String numero) 
    throws InvalidYearException, InvalidDurationException, InvalidTackNumberException {
        
        super(album, anio, artista);
        this.nombreCancion = nombre;
        this.genero = genero;
        
        if (duracion.matches("[0-9]+"))
            this.duracion = Integer.parseInt(duracion);           
        else
            throw new InvalidDurationException();
        
        if (numero.matches("[0-9]+"))
            this.numeroCancion = Integer.parseInt(numero);
        else
            throw new InvalidTackNumberException();
        
        addCancion();
        
    }

    /**
     * @return nombreCancion
     */
    public String getNombreCancion() {
        return nombreCancion;
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
     * @param c lista de canciones que contiene la PlayList
     * @param b interfaz funcional para implementar critero de busqueda
     * @return
     */
    public static List<PlayList> BuscarCancion(List<PlayList> c, Buscar b){
        List<PlayList> cancionesEncontradas = new ArrayList<PlayList>();
        for (PlayList playList : c) {
            Cancion cancion = (Cancion) playList;
            if (b.criterio(cancion))
                cancionesEncontradas.add(cancion);
        }
        return cancionesEncontradas;
    }

    /**
     * @return true si la canción es anadida con exito
     */
    @Override
    public boolean addCancion() {
        return getListaReproduccion().add(this);
    }

    @Override
    public String toString() {
        return "Nombre: "+nombreCancion+" Álbum: "+getNombreAlbum()+" Artista: "+getNombreArtista()
                +" Año: "+getAnio()+" Género: "+genero+" Duración: "+(duracion/1000)+
                "seg. Numero en Disco: "+numeroCancion+"\n";                
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