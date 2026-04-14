# Proyecto CRUD

## Descripción general
Este proyecto es una aplicación Java simple de consola que implementa operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para una tabla de usuarios en una base de datos MySQL.

## Estructura del proyecto
- `pom.xml`: archivo de configuración de Maven.
- `src/main/java/com/mycompany/crud/CRUD.java`: clase principal que contiene el menú de consola y los métodos CRUD.

## Dependencias
- `mysql-connector-j:8.0.33`: controlador JDBC para conectarse a MySQL.

## Funcionalidades implementadas
- `createUser(Connection conexion)`: Inserta un nuevo usuario en la tabla `usuarios`.
- `readUser(Connection conexion)`: Muestra todos los usuarios registrados en la tabla `usuarios`.
- `updateUser(Connection conexion)`: Actualiza el nombre y correo de un usuario existente mediante `idUsuario`.
- `deleteUser(Connection conexion)`: Elimina un usuario por `idUsuario`.
- `convertirMD5(String texto)`: Convierte la contraseña ingresada a un hash MD5 antes de guardarla en la base de datos.

## Detalles de la base de datos
La aplicación está configurada para conectarse a:
- URL: `jdbc:mysql://127.0.0.1:3306/e-form`
- Usuario: `root`
- Contraseña: `mogadex123`

La tabla utilizada es `usuarios` y se esperan estos campos:
- `idUsuario` (número)
- `nombre`
- `correo`
- `contrasena`
- `rol`

## Cambios importantes realizados
- Se corrigió el flujo del menú para evitar que el programa solicitara datos antes de mostrar las opciones.
- Se agregó limpieza de buffer con `scanner.nextLine()` después de `nextInt()` para evitar saltos de entrada.
- Se implementó el hash MD5 para las contraseñas antes de insertarlas en la base de datos.
- Se aseguró que las operaciones CRUD solo se ejecuten si la conexión con la base de datos es exitosa.

## Cómo ejecutar el proyecto
1. Asegúrate de tener Java 21 y Maven instalados.
2. Configura la base de datos MySQL y crea la base `e-form` con la tabla `usuarios`.
3. Desde la raíz del proyecto, ejecuta:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.mycompany.crud.CRUD"
```

## Notas
- El proyecto está pensado como una aplicación de consola para practicar la conexión JDBC y las operaciones básicas con bases de datos.
- Si la base de datos no está disponible, la aplicación mostrará un mensaje de error al intentar conectarse y no permitirá ejecutar las acciones CRUD.

https://github.com/eform2026/CRUD.git
