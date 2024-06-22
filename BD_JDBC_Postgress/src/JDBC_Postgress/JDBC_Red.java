/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// JDBC_Red.java
package JDBC_Postgress;

import static JDBC_Postgress.JDBC_Linea.getConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC_Red {
    // URL de la base de datos, usuario y contraseña
    private static final String URL = "jdbc:postgresql://localhost:5432/Energy";
    private static final String USER = "postgres";
    private static final String PASSWORD = "aqp123";

    // Consultas SQL
    private static final String INSERT_RED_SQL = "INSERT INTO red (\"redCodigo\", \"redNombre\", \"redProductor\") VALUES (?, ?, ?)";
    private static final String UPDATE_RED_SQL = "UPDATE red SET \"redNombre\" = ?, \"redProductor\" = ? WHERE \"redCodigo\" = ?";
    private static final String DELETE_RED_SQL = "DELETE FROM red WHERE \"redCodigo\" = ?";
    private static final String CONSULTA_RED_SQL = "SELECT * FROM red";

    private static PreparedStatement preparedStatement;

    // Método para obtener la conexión a la base de datos
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión: " + e.getMessage());
            return null;
        }
    }

    // Método para insertar una nueva red
    public static int redInsert(int codigo, String nombre, int productor) {
        int cantidad = -1;
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(INSERT_RED_SQL);
                preparedStatement.setInt(1, codigo);
                preparedStatement.setString(2, nombre);
                preparedStatement.setInt(3, productor);
                cantidad = preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al insertar la red: " + ex.getMessage());
        }
        return cantidad; // Devuelve la cantidad de filas afectadas por la inserción
    }

    // Método para actualizar una red
    public static int redUpdate(int codigo, String nombre, int productor) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(UPDATE_RED_SQL);
                preparedStatement.setString(1, nombre);
                preparedStatement.setInt(2, productor);
                preparedStatement.setInt(3, codigo);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al actualizar la red: " + ex.getMessage());
        }
        return -1;
    }

    // Método para eliminar una red
    public static int redDelete(int codigo) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(DELETE_RED_SQL);
                preparedStatement.setInt(1, codigo);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al eliminar la red: " + ex.getMessage());
        }
        return -1;
    }

    // Método para consultar todas las redes
    public static ResultSet consultarRedes() {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                Statement s = con1.createStatement();
                return s.executeQuery(CONSULTA_RED_SQL);
            }
        } catch (SQLException sqe) {
            System.err.println("Error al consultar las redes: " + sqe.getMessage());
        }
        return null;
    }
}

