package com.mycompany.crud;

import java.sql.*;
import java.util.Scanner;

public class CRUD {

    public static void main(String[] args) {
        try {
            // conexión a la base de datos que nos proporciono el profe jheyson 
            String url = "jdbc:mysql://formacionsena.c9bfqfswwhqk.us-east-1.rds.amazonaws.com:3306/eform";
            String usuario = "root";
            String contraseña = "8Fg2Rc7hHChxibkbqjSI";

            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);
            Scanner sn = new Scanner(System.in);
            boolean salir = false;
            int opcion;

            while (!salir) {
                System.out.println("\n--- Que desea hacer? ---");
                System.out.println("1. Validar si hay usuarios");
                System.out.println("2. Eliminar usuario");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opcion: ");

                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        validarSiHayDatos(conexion);
                        break;
                    case 2:
                        deleteUser(conexion);
                        break;
                    case 3:
                        salir = true;
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                }
            }

            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ese metodo es para verificar si hay datos en la tabla usuarios
    public static void validarSiHayDatos(Connection conexion) throws SQLException {

        String sql = "SELECT COUNT(*) FROM usuarios";

        try (PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            if (resultado.next()) {
                int total = resultado.getInt(1);

                if (total > 0) {
                    System.out.println("La tabla usuarios tiene registros: " + total);
                } else {
                    System.out.println("La tabla usuarios esta vacia.");
                }
            }
        }
    }

    // mi metodo delete para eliminar usuario
    public static void deleteUser(Connection conexion) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Eliminar usuario");
        System.out.print("Ingrese el idUsuario que desea eliminar: ");
        int idUsuarios = scanner.nextInt();

        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idUsuarios);

            int filas = consulta.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario eliminado correctamente");
            } else {
                System.out.println("No existe ningun usuario con ese id en la base de datos");
            }
        }
    }
}