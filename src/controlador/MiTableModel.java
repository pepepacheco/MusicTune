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

	private String[] cabezera; //cabezera de la tabla
	private String[][] table; //Array Bidimensional que va a contener los datos del JTable.
	List<String> campos; //Lista dinámica que va a recoger los datos para posteriormente
	//añadirlos a la matriz.
	
	//Constructor que inicializa los atributos.
	public MiTableModel(List<Cancion> lista, String[] cabezera) {  		  
		table = new String[lista.size()][lista.size()*cabezera.length];
		campos = new ArrayList<String>();
		this.cabezera = cabezera;
		for (int i = 0; i < lista.size(); i++) {
			
				Cancion c = lista.get(i);	
					
				for (int j = 0; j < cabezera.length; j++) {
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
							campos.add(c.getYearAlbum()+"");
							break;
						case 4:
							campos.add(c.getGenero());
							break;
						case 5:
							campos.add(c.getDuracion()+" ms");
							break;							
						case 6:
							campos.add(c.getNumeroCancion()+"");
							break;									
					}
					table[i][j] = campos.get(j);
				}
				campos.clear();		
		}
	}
	/**
	 * return int número de columnas.
	 */
	@Override
	public int getColumnCount() {
		return cabezera.length;
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
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return cabezera[column];
	}
	
}