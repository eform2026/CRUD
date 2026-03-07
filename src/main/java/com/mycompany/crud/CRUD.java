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

    // metodo para el hash MD5
    public static String convertirMD5(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); // se encarga de crear resumenes la "clase MessageDigest" y se indica que se utilice el algoridmo de MD5 o hashes
            byte[] array = md.digest(texto.getBytes()); // se convierte el la contraseña "ya sea textos con numeros y simbolos o solo numeros y simbolos " a un arreglo de bytes
            //en md.digest(..) es donde el algoritmo procesa los bytes y devuelve un arreglo de bytes que representan el hash (cundo de hace el hash ya no se puede revertir)
            //son datos "crudos" que no se pueden leer ni guardar facilmente en una base de datos 
            StringBuilder sb = new StringBuilder(); 
            for (byte b : array) {
                // por eso se usa el StringBuilder y un bucle 
                sb.append(String.format("%02x", b)); // en esta parte la clave conviete los bytes en un numero hexadecimal de 2 digitos ya sea del 00 al ff
                // sb.append) Va pegando esos pares de caracteres uno tras otro hasta completar los 32 caracteres típicos de un MD5.
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

            while (!salir) {
                System.out.println("\n--- MENU DE GESTION DE USUARIOS ---");
                System.out.println("1. Crear usuario");
                System.out.println("2. Ver usuarios");
                System.out.println("3. Actualizar usuario");
                System.out.println("4. Eliminar usuario");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opcin: ");
                
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        createUser();
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