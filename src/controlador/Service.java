package controlador;

import modelo.Cancion;
import modelo.exceptions.InvalidTackNumberException;
import modelo.exceptions.InvalidYearException;
import modelo.exceptions.InvalidDurationException;
import modelo.PlayList;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Rafael Vargas del Moral
 * @version 1.0
 */

public class Service {
	/**
	 * Método que lee nuestro fichero JSON y crea los objetos.
	 * @param file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InvalidYearException
	 * @throws InvalidDurationException
	 * @throws InvalidTackNumberException
	 */
    public static void loadJson(File file) throws FileNotFoundException, IOException, InvalidYearException,
    InvalidDurationException, InvalidTackNumberException{
        JsonReader reader = new JsonReader(new FileReader(file));
        reader.beginArray();
        String[] campo = new String[7];
        boolean comprobacion = false;
        while(reader.hasNext()){
            reader.beginObject();
            if (reader.nextName().equals("Track Name"))
                campo[0] = reader.nextString();
            if (reader.nextName().equals("Album Name"))
                campo[1] = reader.nextString();
            if (reader.nextName().equals("Artist Name"))
                campo[2] = reader.nextString();
            if (reader.nextName().equals("Year"))
                campo[3] = reader.nextString();
            if (reader.nextName().equals("Genre"))
                campo[4] = reader.nextString();
            if (reader.nextName().equals("Track Duration (ms)"))
                campo[5] = reader.nextString()+"";            
            if (reader.nextName().equals("Track Number"))
                campo[6] = reader.nextString();
            for (String string : campo) {
                if (string != null)
                    comprobacion = true;
                else{
                    comprobacion = false;
                    break;
                }                        
            }           
            if (comprobacion)         
                new Cancion(campo[0], campo[1], campo[2], campo[3], campo[4], campo[5], campo[6]);         
            reader.endObject();
        }
        reader.endArray();
        //System.out.println(PlayList.getListaReproduccion());
    }
}
