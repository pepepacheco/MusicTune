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

	private int columnas; //variable que va a definir el numero de columnas en funcion del nº de atrib.
	private String[][] table; //Array Bidimensional que va a contener los datos del JTable.
	List<String> campos; //Lista din�mica que va a recoger los datos para posteriormente
	//añadirlos a la matriz.
	
	//Constructor que inicializa los atributos.
	public MiTableModel(List<PlayList> lista, int nAtributos) {  		  
		table = new String[lista.size()][lista.size()*nAtributos];
		campos = new ArrayList<String>();
		columnas = nAtributos;

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i) instanceof Cancion ){
				Cancion c = (Cancion) lista.get(i);	
					
				for (int j = 0; j < nAtributos; j++) {
					switch (j){
						case 0:
							campos.add(c.getNombreCancion());
							break;
						case 1:
							campos.add(c.getNombreAlbum());
							break;
						case 2:
							campos.add(c.getNombreArtista());
							break;
						case 3:
							campos.add(c.getAnio()+"");
							break;
						case 4:
							campos.add(c.getGenero());
							break;
						case 5:
							campos.add(c.getDuracion()/100+" Seg");
							break;							
						case 6:
							campos.add(c.getNumeroCancion()+"");
							break;									
					}
					table[i][j] = campos.get(j);
					//vacio la lista en el ultimo paso del bucle anidado.
					if (j == nAtributos-1)
						campos.clear();
				}
			}
		}
	}
	/**
	 * return int número de columnas.
	 */
	@Override
	public int getColumnCount() {
		return columnas;
	}
	/**
	 * return int número de filas.
	 */
	@Override
	public int getRowCount() {
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