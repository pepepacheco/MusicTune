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

class Tabla extends AbstractTableModel {
	  private String[][] table;
	  List<String> campos;

	public Tabla(List<PlayList> l, int nAtributos) {  		  
		table = new String[l.size()][l.size()*nAtributos];
		campos = new ArrayList<String>();
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i) instanceof Cancion ){
				Cancion c = (Cancion) l.get(i);				
				for (int j = 0; j < nAtributos; j++) {
					campos.add(c.getNombreCancion()); campos.add(c.getNombreAlbum()); campos.add(c.getNombreArtista());
					campos.add(c.getAnio()+""); campos.add(c.getGenero()); campos.add(c.getDuracion()/100+" Seg");
					campos.add(c.getNumeroCancion()+"");
					table[i][j] = campos.get(j);
					if (j != campos.size()-1)
						campos = new ArrayList<String>();
				}
			}
		}
	}

	@Override
	public int getColumnCount() {
		return campos.size();
	}

	@Override
	public int getRowCount() {
		return table.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return table[rowIndex][columnIndex];
	}
}