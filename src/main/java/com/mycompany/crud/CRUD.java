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

        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}