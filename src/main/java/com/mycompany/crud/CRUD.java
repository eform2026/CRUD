/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.crud;

import java.sql.*;
import java.util.Scanner;
// agregue las librerias para el metodo del hash MD5
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CRUD {
    private static final Scanner scanner = new Scanner(System.in);

    // metodo para el hash MD5
    public static String convertirMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            // se encarga de crear resumenes la "clase MessageDigest" y se indica que se utilice el algoridmo de MD5 o hashes

            byte[] array = md.digest(texto.getBytes()); 
            // se convierte la contraseña a un arreglo de bytes

            StringBuilder sb = new StringBuilder(); 

            for (byte b : array) {
                // por eso se usa el StringBuilder y un bucle
                sb.append(String.format("%02x", b)); 
                // convierte los bytes en números hexadecimales de 2 dígitos
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error al cifrar: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        try {

            // Conexión a la base de datos
            String url = "jdbc:mysql://formacionsena.c9bfqfswwhqk.us-east-1.rds.amazonaws.com:3306/eform";
            String usuario = "root";
            String contraseña = "8Fg2Rc7hHChxibkbqjSI";

            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            Scanner sn = new Scanner(System.in);

            boolean salir = false;
            int opcion;

            /*
            IMPORTANTE:
            Antes aquí estaban estas dos líneas:

            deleteUser(conexion);
            updateUser(conexion);

            Se eliminaron porque hacían que el programa pidiera datos
            antes de mostrar el menú, lo que parecía que el menú no funcionaba.
            */

            while (!salir) {

                System.out.println("\n--- MENU DE GESTION DE USUARIOS ---");
                System.out.println("1. Crear usuario");
                System.out.println("2. Ver usuarios");
                System.out.println("3. Actualizar usuario");
                System.out.println("4. Eliminar usuario");
                System.out.println("5. Salir");

                System.out.print("Seleccione una opcion: ");

                opcion = sn.nextInt();

                /*
                IMPORTANTE:

                nextInt() NO consume el salto de línea del teclado.
                Si no se limpia el buffer, el siguiente nextLine()
                puede saltarse la entrada del usuario.

                Por eso se agrega esta línea para limpiar el buffer.
                */
                sn.nextLine();

                switch (opcion) {

                    case 1:
                        createUser(conexion);
                        break;

                    case 2:
                        readUser();
                        break;

                    case 3:
                        updateUser(conexion);
                        break;

                    case 4:
                        deleteUser(conexion);
                        break;

                    case 5:
                        salir = true;
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            }

            conexion.close();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
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
                System.out.print("Formato incorrecto. Use solo números sin letras ni símbolos: ");
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

    public static void readUser(){

    }

    public static void validarSiHayDatos() {

    }

    public static void updateUser(Connection conexion) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Actualizar Usuario");

        System.out.print("Ingrese el idUsuario que desea actualizar: ");
        int idUsuario = scanner.nextInt();

        scanner.nextLine(); // limpiar buffer

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

        Scanner scanner = new Scanner(System.in);

        System.out.println("Eliminar Usuario");

        System.out.print("Ingrese el idUsuario: ");
        int idUsuarios = scanner.nextInt();

        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";

        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idUsuarios);

            int filas = consulta.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario eliminado");
            } else {
                System.out.println("No existe un usuario con ese id");
            }
        }
    }
}