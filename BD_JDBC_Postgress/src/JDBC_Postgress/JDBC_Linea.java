/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.JDBC to edit this template
 */
package JDBC_Postgress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase JDBC para conexión y operaciones CRUD en PostgreSQL.
 * Autor: Cami
 */
public class JDBC_Linea {
    // URL de la base de datos, usuario y contraseña
    private static final String URL = "jdbc:postgresql://localhost:5432/Energy";
    private static final String USER = "postgres";
    private static final String PASSWORD = "aqp123";

    // Consultas SQL
    private static final String INSERT_LINEA_SQL = "INSERT INTO linea (\"lineaRed\", \"lineaNumero\", \"lineaLongitud\") VALUES (?, ?, ?)";
    private static final String UPDATE_LINEA_SQL = "UPDATE linea SET \"lineaLongitud\" = ? WHERE \"lineaRed\" = ? AND \"lineaNumero\" = ?";
    private static final String DELETE_LINEA_SQL = "DELETE FROM linea WHERE \"lineaRed\" = ? AND \"lineaNumero\" = ?";
    private static final String CONSULTA_LINEA_SQL = "SELECT * FROM linea";

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

    // Método para insertar una nueva línea
    public static int lineaInsert(int lineaRed, int lineaNumero, int lineaLongitud) {
        int cantidad = -1;
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(INSERT_LINEA_SQL);
                preparedStatement.setInt(1, lineaRed);
                preparedStatement.setInt(2, lineaNumero);
                preparedStatement.setInt(3, lineaLongitud);
                cantidad = preparedStatement.executeUpdate();
            }
        } catch (SQLException ex1) {
            System.err.println("Error al insertar la línea: " + ex1.getMessage());
        }
        return cantidad;
    }

    // Método para actualizar la longitud de una línea
    public static int lineaUpdate(int lineaRed, int lineaNumero, int lineaLongitud) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(UPDATE_LINEA_SQL);
                preparedStatement.setInt(1, lineaLongitud);
                preparedStatement.setInt(2, lineaRed);
                preparedStatement.setInt(3, lineaNumero);
                preparedStatement.executeUpdate();
                return 1;
            }
        } catch (SQLException ex1) {
            System.err.println("Error al actualizar la línea: " + ex1.getMessage());
        }
        return -1;
    }

    // Método para eliminar una línea
    public static int lineaDelete(int lineaRed, int lineaNumero) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(DELETE_LINEA_SQL);
                preparedStatement.setInt(1, lineaRed);
                preparedStatement.setInt(2, lineaNumero);
                preparedStatement.executeUpdate();
                return 1;
            }
        } catch (SQLException ex1) {
            System.err.println("Error al eliminar la línea: " + ex1.getMessage());
        }
        return -1;
    }

    // Método para consultar todas las líneas
    public static ResultSet consultaLinea() {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                Statement s = con1.createStatement();
                return s.executeQuery(CONSULTA_LINEA_SQL);
            }
        } catch (SQLException sqe) {
            System.err.println("Error al consultar las líneas: " + sqe.getMessage());
        }
        return null;
    }
}