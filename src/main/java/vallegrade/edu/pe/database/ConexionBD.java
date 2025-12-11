package vallegrade.edu.pe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://recuperacion.chvutexxetr1.us-east-1.rds.amazonaws.com/paleesdb";
    private static final String USER =  "admin";
    private static final String PASS = "ConcaFlores312007";

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            con.setAutoCommit(true);

            // AGREGAR ESTO PARA VERIFICAR:
            System.out.println("✅ Conexión exitosa a: " + URL);

        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}