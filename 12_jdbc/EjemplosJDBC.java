package jdbc;

import java.sql.*;

/**
 * Ejemplos de JDBC (Java Database Connectivity)
 * - Conexión a base de datos
 * - CRUD operations
 * - Transacciones
 */

public class EjemplosJDBC {
    
    // Configuración de conexión (ejemplo con SQLite)
    private static final String URL = "jdbc:sqlite:/tmp/ejemplo.db";
    private static final String DRIVER = "org.sqlite.JDBC";
    
    public static void main(String[] args) {
        System.out.println("=== JDBC ===\n");
        
        try {
            // Cargar driver
            Class.forName(DRIVER);
            System.out.println("Driver cargado: " + DRIVER);
            
            // Crear tabla
            crearTabla();
            
            // Insertar datos
            insertarDatos();
            
            // Consultar datos
            consultarDatos();
            
            // Actualizar datos
            actualizarDatos();
            
            // Eliminar datos
            eliminarDatos();
            
            // Transacciones
            ejemploTransacciones();
            
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        }
    }
    
    // Crear conexión
    static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    
    // Crear tabla
    static void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY," +
                    "nombre TEXT NOT NULL," +
                    "email TEXT UNIQUE," +
                    "edad INTEGER" +
                    ")";
        
        try (Connection conn = obtenerConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'usuarios' creada\n");
        } catch (SQLException e) {
            System.out.println("Error creando tabla: " + e.getMessage());
        }
    }
    
    // Insertar datos con PreparedStatement
    static void insertarDatos() {
        System.out.println("--- Insertar Datos ---");
        String sql = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";
        
        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Insertar usuario 1
            pstmt.setString(1, "Ana García");
            pstmt.setString(2, "ana@example.com");
            pstmt.setInt(3, 28);
            pstmt.executeUpdate();
            
            // Insertar usuario 2
            pstmt.setString(1, "Carlos López");
            pstmt.setString(2, "carlos@example.com");
            pstmt.setInt(3, 35);
            pstmt.executeUpdate();
            
            // Insertar usuario 3
            pstmt.setString(1, "Diana Martínez");
            pstmt.setString(2, "diana@example.com");
            pstmt.setInt(3, 25);
            pstmt.executeUpdate();
            
            System.out.println("Usuarios insertados\n");
            
        } catch (SQLException e) {
            System.out.println("Error insertando datos: " + e.getMessage());
        }
    }
    
    // Consultar datos
    static void consultarDatos() {
        System.out.println("--- Consultar Datos ---");
        String sql = "SELECT * FROM usuarios";
        
        try (Connection conn = obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("Usuarios en base de datos:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                int edad = rs.getInt("edad");
                
                System.out.println("  ID: " + id + ", Nombre: " + nombre + 
                                 ", Email: " + email + ", Edad: " + edad);
            }
            System.out.println();
            
        } catch (SQLException e) {
            System.out.println("Error consultando datos: " + e.getMessage());
        }
    }
    
    // Actualizar datos
    static void actualizarDatos() {
        System.out.println("--- Actualizar Datos ---");
        String sql = "UPDATE usuarios SET edad = ? WHERE nombre = ?";
        
        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, 29);  // Nueva edad
            pstmt.setString(2, "Ana García");  // Nombre a buscar
            
            int filasActualizadas = pstmt.executeUpdate();
            System.out.println("Filas actualizadas: " + filasActualizadas + "\n");
            
        } catch (SQLException e) {
            System.out.println("Error actualizando: " + e.getMessage());
        }
    }
    
    // Eliminar datos
    static void eliminarDatos() {
        System.out.println("--- Eliminar Datos ---");
        String sql = "DELETE FROM usuarios WHERE edad < ?";
        
        try (Connection conn = obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, 26);
            int filasEliminadas = pstmt.executeUpdate();
            System.out.println("Filas eliminadas (edad < 26): " + filasEliminadas + "\n");
            
        } catch (SQLException e) {
            System.out.println("Error eliminando: " + e.getMessage());
        }
    }
    
    // Ejemplo de transacciones
    static void ejemploTransacciones() {
        System.out.println("--- Transacciones ---");
        
        try (Connection conn = obtenerConexion()) {
            // Desactivar auto-commit para controlar transacciones
            conn.setAutoCommit(false);
            
            try {
                // Operación 1
                String sql1 = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql1)) {
                    pstmt.setString(1, "Elena Rodríguez");
                    pstmt.setString(2, "elena@example.com");
                    pstmt.setInt(3, 32);
                    pstmt.executeUpdate();
                }
                
                // Operación 2
                String sql2 = "INSERT INTO usuarios (nombre, email, edad) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
                    pstmt.setString(1, "Francisco Sánchez");
                    pstmt.setString(2, "francisco@example.com");
                    pstmt.setInt(3, 40);
                    pstmt.executeUpdate();
                }
                
                // Confirmar transacción
                conn.commit();
                System.out.println("Transacción confirmada (COMMIT)\n");
                
            } catch (SQLException e) {
                // Revertir en caso de error
                conn.rollback();
                System.out.println("Error en transacción. Revertido (ROLLBACK): " + e.getMessage());
            } finally {
                // Restaurar auto-commit
                conn.setAutoCommit(true);
            }
            
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}

/**
 * NOTA: Para ejecutar este código se necesita:
 * 1. Tener SQLite JDBC driver en el classpath
 * 2. O cambiar la configuración para otra BD (MySQL, PostgreSQL, etc.)
 * 
 * Ejemplo con MySQL:
 * String URL = "jdbc:mysql://localhost:3306/mibase?useSSL=false&serverTimezone=UTC";
 * String DRIVER = "com.mysql.cj.jdbc.Driver";
 * 
 * Ejemplo con PostgreSQL:
 * String URL = "jdbc:postgresql://localhost:5432/mibase";
 * String DRIVER = "org.postgresql.Driver";
 */
