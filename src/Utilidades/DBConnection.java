package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/gestortareas";
    private static final String USER = "root";
    private static final String PASSWORD = "usbw";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println(" (DBConnection) Conexión a la base de datos establecida.");
            } catch (SQLException e) {
                System.out.println(" (DBConnection) Error al conectar a la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println(" (DBConnection) Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                System.out.println(" (DBConnection) Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

}

