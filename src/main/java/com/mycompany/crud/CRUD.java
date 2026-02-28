/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.crud;

import java.sql.*;
import java.util.Scanner;

public class CRUD {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            // Conexión a la base de datos
            String url = "jdbc:mysql://localhost:3306/e-form";
            String usuario = "root";
            String contraseña = "cielo";

            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            System.out.println("Eliminar Usuario");
            System.out.print("Ingrese el idUsuario: ");
            int idUsuarios = scanner.nextInt();

            // Sentencia DELETE
            String sql = "DELETE FROM usuarios WHERE idUsuario = ?";
            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idUsuarios);

            int filas = consulta.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario eliminado");
            } else {
                System.out.println("No existe un usuario con ese id");
            }

            consulta.close();
            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}