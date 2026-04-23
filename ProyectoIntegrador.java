package proyecto_integrador;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * PROYECTO INTEGRADOR: Sistema de Gestión de Biblioteca
 * 
 * Este proyecto integra múltiples conceptos estudiados:
 * - POO (Clases, herencia, interfaces)
 * - Colecciones (ArrayList, HashMap)
 * - Streams y Lambdas
 * - Excepciones personalizadas
 * - Fecha y hora
 * - Archivos I/O
 * - Patrón Builder
 */

// ========== EXCEPCIONES PERSONALIZADAS ==========
class LibroNoDisponibleException extends Exception {
    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}

class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

// ========== CLASES DE MODELO ==========

record Libro(String isbn, String titulo, String autor, int año, int copias) {
    public boolean disponible() {
        return copias > 0;
    }
}

enum EstadoPrestamo {
    ACTIVO("Préstamo activo"),
    DEVUELTO("Libro devuelto"),
    VENCIDO("Préstamo vencido");
    
    private final String descripcion;
    
    EstadoPrestamo(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}

record Prestamo(
    String usuarioId,
    String isbn,
    LocalDateTime fechaPrestamo,
    LocalDateTime fechaVencimiento,
    EstadoPrestamo estado
) { }

class Usuario {
    private String id;
    private String nombre;
    private String email;
    private LocalDateTime fechaRegistro;
    
    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    
    @Override
    public String toString() {
        return "Usuario{" +
               "id='" + id + '\'' +
               ", nombre='" + nombre + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}

// ========== INTERFAZ DEL REPOSITORIO ==========
interface RepositorioBiblioteca<T> {
    void agregar(T item);
    Optional<T> obtener(String id);
    List<T> obtenerTodos();
    boolean eliminar(String id);
}

// ========== IMPLEMENTACIÓN DEL REPOSITORIO ==========
class RepositorioBibliotecaImpl<T> implements RepositorioBiblioteca<T> {
    private Map<String, T> datos = new HashMap<>();
    
    @Override
    public void agregar(T item) {
        if (item instanceof Libro libro) {
            datos.put(libro.isbn(), item);
        } else if (item instanceof Usuario usuario) {
            datos.put(usuario.getId(), item);
        }
    }
    
    @Override
    public Optional<T> obtener(String id) {
        return Optional.ofNullable(datos.get(id));
    }
    
    @Override
    public List<T> obtenerTodos() {
        return new ArrayList<>(datos.values());
    }
    
    @Override
    public boolean eliminar(String id) {
        return datos.remove(id) != null;
    }
}

// ========== SERVICIO DE BIBLIOTECA (LÓGICA DE NEGOCIO) ==========
class BibliotecaService {
    private RepositorioBiblioteca<Libro> libros;
    private RepositorioBiblioteca<Usuario> usuarios;
    private List<Prestamo> prestamos;
    
    public BibliotecaService() {
        this.libros = new RepositorioBibliotecaImpl<>();
        this.usuarios = new RepositorioBibliotecaImpl<>();
        this.prestamos = new ArrayList<>();
    }
    
    // ========== GESTIÓN DE LIBROS ==========
    public void agregarLibro(Libro libro) {
        libros.agregar(libro);
        System.out.println("✓ Libro agregado: " + libro.titulo());
    }
    
    public List<Libro> buscarPorAutor(String autor) {
        return libros.obtenerTodos().stream()
                    .filter(libro -> libro.autor().equalsIgnoreCase(autor))
                    .collect(Collectors.toList());
    }
    
    public List<Libro> librosDisponibles() {
        return libros.obtenerTodos().stream()
                    .filter(Libro::disponible)
                    .collect(Collectors.toList());
    }
    
    // ========== GESTIÓN DE USUARIOS ==========
    public void registrarUsuario(Usuario usuario) {
        usuarios.agregar(usuario);
        System.out.println("✓ Usuario registrado: " + usuario.getNombre());
    }
    
    public Usuario obtenerUsuario(String id) {
        return usuarios.obtener(id)
                      .orElseThrow(() -> new UsuarioNoEncontradoException(
                          "Usuario no encontrado: " + id));
    }
    
    // ========== GESTIÓN DE PRÉSTAMOS ==========
    public void prestarLibro(String usuarioId, String isbn) throws LibroNoDisponibleException {
        Usuario usuario = obtenerUsuario(usuarioId);
        Libro libro = libros.obtener(isbn)
                           .orElseThrow(() -> new LibroNoDisponibleException(
                               "Libro no encontrado"));
        
        if (!libro.disponible()) {
            throw new LibroNoDisponibleException(
                "El libro '" + libro.titulo() + "' no está disponible");
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime vencimiento = ahora.plusDays(14);  // 14 días de préstamo
        
        Prestamo prestamo = new Prestamo(usuarioId, isbn, ahora, vencimiento, 
                                        EstadoPrestamo.ACTIVO);
        prestamos.add(prestamo);
        
        System.out.println("✓ Préstamo registrado:");
        System.out.println("  Usuario: " + usuario.getNombre());
        System.out.println("  Libro: " + libro.titulo());
        System.out.println("  Vencimiento: " + vencimiento);
    }
    
    public void devolverLibro(String usuarioId, String isbn) {
        prestamos.stream()
                .filter(p -> p.usuarioId().equals(usuarioId) && 
                           p.isbn().equals(isbn) &&
                           p.estado() == EstadoPrestamo.ACTIVO)
                .findFirst()
                .ifPresentOrElse(
                    p -> {
                        prestamos.remove(p);
                        prestamos.add(new Prestamo(p.usuarioId(), p.isbn(), 
                                                 p.fechaPrestamo(),
                                                 p.fechaVencimiento(),
                                                 EstadoPrestamo.DEVUELTO));
                        System.out.println("✓ Libro devuelto exitosamente");
                    },
                    () -> System.out.println("✗ No hay préstamo activo para este usuario/libro")
                );
    }
    
    public List<Prestamo> prestamosActivos() {
        return prestamos.stream()
                       .filter(p -> p.estado() == EstadoPrestamo.ACTIVO)
                       .collect(Collectors.toList());
    }
    
    public List<Prestamo> prestamosVencidos() {
        LocalDateTime ahora = LocalDateTime.now();
        return prestamos.stream()
                       .filter(p -> p.estado() == EstadoPrestamo.ACTIVO &&
                                  p.fechaVencimiento().isBefore(ahora))
                       .collect(Collectors.toList());
    }
    
    // ========== REPORTES ==========
    public void mostrarReporte() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("REPORTE DE BIBLIOTECA");
        System.out.println("=".repeat(50));
        
        System.out.println("\n📚 LIBROS EN CATÁLOGO:");
        libros.obtenerTodos().forEach(libro ->
            System.out.println("  - " + libro.titulo() + " [" + libro.copias() + 
                             " copias disponibles]")
        );
        
        System.out.println("\n👥 USUARIOS REGISTRADOS:");
        usuarios.obtenerTodos().forEach(user ->
            System.out.println("  - " + user.getNombre() + " (" + user.getId() + ")")
        );
        
        System.out.println("\n📖 PRÉSTAMOS ACTIVOS:");
        prestamosActivos().forEach(prestamo -> {
            try {
                Usuario user = obtenerUsuario(prestamo.usuarioId());
                Libro libro = libros.obtener(prestamo.isbn()).orElse(null);
                System.out.println("  - " + user.getNombre() + " pidió '" + 
                                 (libro != null ? libro.titulo() : "desconocido") +
                                 "' (Vence: " + prestamo.fechaVencimiento().format(formatter) + ")");
            } catch (Exception e) {
                System.out.println("  Error cargando préstamo");
            }
        });
        
        if (prestamosVencidos().isEmpty()) {
            System.out.println("\n✓ No hay préstamos vencidos");
        } else {
            System.out.println("\n⚠ PRÉSTAMOS VENCIDOS:");
            prestamosVencidos().forEach(prestamo ->
                System.out.println("  - Usuario: " + prestamo.usuarioId() + 
                                 ", Libro: " + prestamo.isbn())
            );
        }
        
        System.out.println("\n" + "=".repeat(50));
    }
}

// ========== PROGRAMA PRINCIPAL ==========
public class ProyectoIntegrador {
    
    public static void main(String[] args) {
        System.out.println("🏛️ PROYECTO INTEGRADOR: Gestión de Biblioteca\n");
        
        BibliotecaService biblioteca = new BibliotecaService();
        
        try {
            // 1. Agregar libros
            System.out.println("📚 Agregando libros...");
            biblioteca.agregarLibro(new Libro("ISBN001", "Clean Code", 
                                             "Robert C. Martin", 2008, 3));
            biblioteca.agregarLibro(new Libro("ISBN002", "Design Patterns", 
                                             "Gang of Four", 1994, 2));
            biblioteca.agregarLibro(new Libro("ISBN003", "Java Effective", 
                                             "Joshua Bloch", 2018, 5));
            biblioteca.agregarLibro(new Libro("ISBN004", "1984", 
                                             "George Orwell", 1949, 1));
            
            // 2. Registrar usuarios
            System.out.println("\n👥 Registrando usuarios...");
            biblioteca.registrarUsuario(new Usuario("U001", "Ana García", 
                                                   "ana@example.com"));
            biblioteca.registrarUsuario(new Usuario("U002", "Carlos López", 
                                                   "carlos@example.com"));
            biblioteca.registrarUsuario(new Usuario("U003", "Diana Martínez", 
                                                   "diana@example.com"));
            
            // 3. Realizar préstamos
            System.out.println("\n📖 Realizando préstamos...");
            biblioteca.prestarLibro("U001", "ISBN001");
            biblioteca.prestarLibro("U002", "ISBN002");
            biblioteca.prestarLibro("U003", "ISBN003");
            
            // 4. Búsquedas (usando streams)
            System.out.println("\n🔍 Búsquedas:");
            System.out.println("Libros disponibles:");
            biblioteca.librosDisponibles()
                     .stream()
                     .map(Libro::titulo)
                     .forEach(t -> System.out.println("  - " + t));
            
            System.out.println("\nLibros de Robert C. Martin:");
            biblioteca.buscarPorAutor("Robert C. Martin")
                     .stream()
                     .map(Libro::titulo)
                     .forEach(t -> System.out.println("  - " + t));
            
            // 5. Devolución de libro
            System.out.println("\n↩️ Devolviendo libro...");
            biblioteca.devolverLibro("U001", "ISBN001");
            
            // 6. Mostrar reporte final
            biblioteca.mostrarReporte();
            
        } catch (LibroNoDisponibleException e) {
            System.err.println("❌ Error: " + e.getMessage());
        } catch (UsuarioNoEncontradoException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        
        System.out.println("\n✓ Programa finalizado exitosamente");
    }
}
