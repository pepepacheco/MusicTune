package controlador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Cancion;
import modelo.PlayList;

/**
 * @author Rafael Vargas del Moral
 * @version 1.0
 */

class MiTableModel extends AbstractTableModel {
	  private String[][] table; //Array Bidimensional que va a contener los datos del JTable.
	  List<String> campos; //Lista dinámica que va a recoger los datos para posteriormente
	  //añadirlos a la matriz.
	  
	  //Constructor que inicializa los atributos.
	  public MiTableModel(List<PlayList> lista, int nAtributos) {  		  
		  table = new String[lista.size()][lista.size()*nAtributos];
		  campos = new ArrayList<String>();
			
		  for (int i = 0; i < lista.size(); i++) {
			  if (lista.get(i) instanceof Cancion ){
				  Cancion c = (Cancion) lista.get(i);	
					
				  for (int j = 0; j < nAtributos; j++) {
					  campos.add(c.getNombreCancion()); campos.add(c.getNombreAlbum()); campos.add(c.getNombreArtista());
					  campos.add(c.getAnio()+""); campos.add(c.getGenero()); campos.add(c.getDuracion()/100+" Seg");
					  campos.add(c.getNumeroCancion()+"");
					  table[i][j] = campos.get(j);
						
					  //inicializao la lista a vacío excepto en el último paso de bucle
					  //para posteriormente usar su tamaño.
					  if (j != campos.size()-1)
						  campos = new ArrayList<String>();
				  }
			  }
		  }
	  }
	/**
	 * return int número de columnas.
	 */
	@Override
	public int getColumnCount() {
		return campos.size();
	}
	/**
	 * return int número de filas.
	 */
	@Override
	public int getRowCount() {
		System.out.println(table.length);
		return table.length;
	}
	/**
	 * return Object valor x e y de la matriz.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return table[rowIndex][columnIndex];
	}
}