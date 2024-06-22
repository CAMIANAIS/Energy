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

public class JDBC_Subestacion {
    private static final String URL = "jdbc:postgresql://localhost:5432/Energy";
    private static final String USER = "postgres";
    private static final String PASSWORD = "aqp123";

    private static final String INSERT_SUBESTACION_SQL = "INSERT INTO subestacion (\"subestacionCodigo\", \"subestacionRed\", \"subestacionLinea\") VALUES (?, ?, ?)";
    private static final String UPDATE_SUBESTACION_SQL = "UPDATE subestacion SET \"subestacionRed\" = ?, \"subestacionLinea\" = ? WHERE \"subestacionCodigo\" = ?";
    private static final String DELETE_SUBESTACION_SQL = "DELETE FROM subestacion WHERE \"subestacionCodigo\" = ?";
    private static final String CONSULTA_SUBESTACION_SQL = "SELECT * FROM subestacion";

    private static PreparedStatement preparedStatement;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión: " + e.getMessage());
            return null;
        }
    }

    public static int subestacionInsert(int codigo, int red, int linea) {
        int cantidad = -1;
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(INSERT_SUBESTACION_SQL);
                preparedStatement.setInt(1, codigo);
                preparedStatement.setInt(2, red);
                preparedStatement.setInt(3, linea);
                cantidad = preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al insertar la subestación: " + ex.getMessage());
        }
        return cantidad;
    }

    public static int subestacionUpdate(int codigo, int red, int linea) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(UPDATE_SUBESTACION_SQL);
                preparedStatement.setInt(1, red);
                preparedStatement.setInt(2, linea);
                preparedStatement.setInt(3, codigo);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al actualizar la subestación: " + ex.getMessage());
        }
        return -1;
    }

    public static int subestacionDelete(int codigo) {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                preparedStatement = con1.prepareStatement(DELETE_SUBESTACION_SQL);
                preparedStatement.setInt(1, codigo);
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println("Error al eliminar la subestación: " + ex.getMessage());
        }
        return -1;
    }

    public static ResultSet consultaSubestacion() {
        try (Connection con1 = getConnection()) {
            if (con1 != null) {
                Statement s = con1.createStatement();
                return s.executeQuery(CONSULTA_SUBESTACION_SQL);
            }
        } catch (SQLException sqe) {
            System.err.println("Error al consultar las subestaciones: " + sqe.getMessage());
        }
        return null;
    }
}
