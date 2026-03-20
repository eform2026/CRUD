package com.mycompany.crud;

import java.sql.*;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CRUD {
    private static final Scanner scanner = new Scanner(System.in);

    // metodo para el hash MD5
    public static String convertirMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] array = md.digest(texto.getBytes());

            StringBuilder sb = new StringBuilder();

            for (byte b : array) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error al cifrar: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {

        String url = "jdbc:mysql://formacionsena.c9bfqfswwhqk.us-east-1.rds.amazonaws.com:3306/eform";
        String usuario = "root";
        String contraseña = "8Fg2Rc7hHChxibkbqjSI";

        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexion exitosa con la base de datos");
        } catch (SQLException e) {
            System.out.println("No se pudo conectar a la base de datos.");
        }

        boolean salir = false;
        int opcion;

        while (!salir) {

            System.out.println("\n--- MENU DE GESTION DE USUARIOS ---");
            System.out.println("1. Crear usuario");
            System.out.println("2. Ver usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Salir");

            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            try {

                switch (opcion) {

                    case 1:
                        if (conexion != null) {
                            createUser(conexion);
                        } else {
                            System.out.println("No hay conexión a la base de datos.");
                        }
                        break;

                    case 2:
                        if (conexion != null) {
                            readUser(conexion); // ✅ CORREGIDO
                        } else {
                            System.out.println("No hay conexión a la base de datos.");
                        }
                        break;

                    case 3:
                        if (conexion != null) {
                            updateUser(conexion);
                        } else {
                            System.out.println("No hay conexión a la base de datos.");
                        }
                        break;

                    case 4:
                        if (conexion != null) {
                            deleteUser(conexion);
                        } else {
                            System.out.println("No hay conexión a la base de datos.");
                        }
                        break;

                    case 5:
                        salir = true;
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (SQLException e) {
                System.out.println("Error en la operación: " + e.getMessage());
            }
        }

        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión.");
        }
    }

    public static void createUser(Connection conexion) throws SQLException {

        System.out.println("=== CREAR USUARIO ===");
        System.out.print("idUsuario (número): ");

        int idUsuario;

        while (true) {
            try {
                idUsuario = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Formato incorrecto. Use solo números: ");
            }
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        System.out.print("Rol: ");
        String rol = scanner.nextLine();

        String sql = "INSERT INTO usuarios (idUsuario, nombre, correo, contrasena, rol) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idUsuario);
            consulta.setString(2, nombre);
            consulta.setString(3, correo);
            consulta.setString(4, contrasena);
            consulta.setString(5, rol);

            int filas = consulta.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario CREADO correctamente");
                System.out.println("Datos: " + idUsuario + " - " + nombre);
            } else {
                System.out.println("Error al crear usuario");
            }
        }
    }

    public static void readUser(Connection conexion) throws SQLException {

        System.out.println("\n=== LISTA DE USUARIOS ===");

        String sql = "SELECT * FROM usuarios";

        try (PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            boolean hayDatos = false;

            while (resultado.next()) {
                hayDatos = true;

                int id = resultado.getInt("idUsuario");
                String nombre = resultado.getString("nombre");
                String correo = resultado.getString("correo");
                String contrasena = resultado.getString("contrasena");
                String rol = resultado.getString("rol");

                System.out.println("----------------------------");
                System.out.println("ID: " + id);
                System.out.println("Nombre: " + nombre);
                System.out.println("Correo: " + correo);
                System.out.println("Contraseña: " + contrasena);
                System.out.println("Rol: " + rol);
            }

            if (!hayDatos) {
                System.out.println("No hay usuarios registrados.");
            }
        }
    }

    public static void updateUser(Connection conexion) throws SQLException {

        System.out.println("Actualizar Usuario");

        System.out.print("Ingrese el idUsuario que desea actualizar: ");
        int idUsuario = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo correo: ");
        String correo = scanner.nextLine();

        String sql = "UPDATE usuarios SET nombre = ?, correo = ? WHERE idUsuario = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, nombre);
            consulta.setString(2, correo);
            consulta.setInt(3, idUsuario);

            int filas = consulta.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario actualizado correctamente");
            } else {
                System.out.println("No existe un usuario con ese id");
            }
        }
    }

    public static void deleteUser(Connection conexion) throws SQLException {

        System.out.println("Eliminar Usuario");

        System.out.print("Ingrese el idUsuario: ");
        int idUsuarios = scanner.nextInt();

        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idUsuarios);

            int filas = consulta.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario eliminado correctamente");
            } else {
                System.out.println("No existe ningun usuario con ese id");
            }
        }
    }
}