package modelo;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.SQLException;
public class Trigger {
	private static Statement sentencia;

	public static void crearTrigger(Connection con) throws SQLException {
		String sql = "CREATE TABLE HISTORIAL ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nombre TEXT, apellidos);";
		String sql2 = "CRATE TRIGGER IF NOT EXISTS BORRADO BEFORE DELETE"
				+ "ON usuario"
				+ "BEGIN INSERT INTO HISTORIAL VALUES (old.id, old.nombre, deletime(\"now\");"
				+ "END";
		sentencia = con.prepareStatement(sql);
		sentencia.addBatch(sql);
		sentencia.addBatch(sql2);
		sentencia.executeBatch();
	}
}
