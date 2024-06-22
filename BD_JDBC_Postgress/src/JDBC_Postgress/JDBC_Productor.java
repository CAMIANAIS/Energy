/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JDBC_Postgress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JDBC_Productor {
    // URL de la base de datos, usuario y contraseña
    private static final String URL = "jdbc:postgresql://localhost:5432/Energy";
    private static final String USER = "postgres";
    private static final String PASSWORD = "aqp123";

    // Consultas SQL
    private static final String INSERT_PRODUCTOR_SQL = "INSERT INTO productor (\"productorCodigo\", \"productorNombre\", \"productorCapacidadMaximaMW\", \"productorFechaInicio\") VALUES (?, ?, ?, ?)";
    private static final String UPDATE_PRODUCTOR_SQL = "UPDATE productor SET \"productorNombre\" = ?, \"productorCapacidadMaximaMW\" = ?, \"productorFechaInicio\" = ? WHERE \"productorCodigo\" = ?";
    private static final String DELETE_PRODUCTOR_SQL = "DELETE FROM productor WHERE \"productorCodigo\" = ?";
    private static final String CONSULTA_PRODUCTOR_SQL = "SELECT * FROM productor";

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

    public static int productorInsert(int codigo, String nombre, int capacidadMaxima, String fechaInicioString) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(INSERT_PRODUCTOR_SQL);
                preparedStatement.setInt(1, codigo);
                preparedStatement.setString(2, nombre);
                preparedStatement.setInt(3, capacidadMaxima);

                // Convertir la cadena de fecha a java.sql.Date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = dateFormat.parse(fechaInicioString);
                java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                preparedStatement.setDate(4, sqlDate);

                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al insertar el productor: " + ex.getMessage());
        } catch (ParseException ex) {
            System.err.println("Error al parsear la fecha: " + ex.getMessage());
        }
        return -1;
    }

    // Método para actualizar un productor
    public static int productorUpdate(int codigo, String nombre, int capacidadMaximaMW, java.sql.Date fechaInicio) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(UPDATE_PRODUCTOR_SQL);
                preparedStatement.setString(1, nombre);
                preparedStatement.setInt(2, capacidadMaximaMW);
                preparedStatement.setDate(3, fechaInicio);
                preparedStatement.setInt(4, codigo);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al actualizar el productor: " + ex.getMessage());
        }
        return -1;
    }

    // Método para eliminar un productor
    public static int productorDelete(int codigo) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(DELETE_PRODUCTOR_SQL);
                preparedStatement.setInt(1, codigo);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al eliminar el productor: " + ex.getMessage());
        }
        return -1;
    }

    // Método para consultar todos los productores
    public static ResultSet consultarProductores() {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                Statement s = con1.createStatement();
                return s.executeQuery(CONSULTA_PRODUCTOR_SQL);
            }
        } catch (SQLException sqe) {
            System.err.println("Error al consultar los productores: " + sqe.getMessage());
        }
        return null;
    }
}
