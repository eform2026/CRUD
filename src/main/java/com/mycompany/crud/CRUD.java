/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.crud;

import java.sql.*;
import java.util.Scanner;

public class CRUD {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        

        try {

            // Conexión a la base de datos
            String url = "jdbc:mysql://formacionsena.c9bfqfswwhqk.us-east-1.rds.amazonaws.com:3306/eform";
            String usuario = "root";
            String contraseña = "8Fg2Rc7hHChxibkbqjSI";

            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            deleteUser(conexion);
            updateUser(conexion);
            

        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        //scanner.close();
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
            System.out.print("Formato incorrecto. Use solo números sin letras ni símbolos");
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

    // Sentencia UPDATE
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
    
    public static void deleteUser(Connection conexion) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Eliminar Usuario");
        System.out.print("Ingrese el idUsuario: ");
        int idUsuarios = scanner.nextInt();

        // Sentencia DELETE
        String sql = "DELETE FROM usuarios WHERE idUsuario = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, idUsuarios);
            int filas = consulta.executeUpdate();
            if (filas > 0) {
                System.out.println("Usuario eliminado");
            } else {
                System.out.println("No existe un usuario con ese id");
            }
            //conexion.close();
        }
    }
    
    
    
    
}