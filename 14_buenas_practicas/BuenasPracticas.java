package buenas_practicas;

import java.util.*;
import java.util.logging.*;

/**
 * Ejemplos de Buenas Prácticas en Java
 * - Principios SOLID
 * - Patrones de diseño
 * - Uso de Optional
 * - Logging
 */

public class BuenasPracticas {
    
    static Logger logger = Logger.getLogger(BuenasPracticas.class.getName());
    
    public static void main(String[] args) {
        System.out.println("=== BUENAS PRÁCTICAS EN JAVA ===\n");
        
        ejemploOptional();
        ejemploSingleResponsibility();
        ejemploOpenClosed();
        ejemploLiskovSubstitution();
        ejemploPatronSingleton();
        ejemploPatronFactory();
        ejemploPatronBuilder();
        ejemploLogging();
    }
    
    // 1. EVITAR NullPointerException con Optional
    static void ejemploOptional() {
        System.out.println("--- Usar Optional (evitar null) ---");
        
        // MAL: Puede causar NullPointerException
        String[] nombres = {"Ana", null, "Carlos"};
        for (String nombre : nombres) {
            // if (nombre != null) { ... }  // Incómodo
            // System.out.println(nombre.toUpperCase());  // ERROR si null
        }
        
        // BIEN: Usar Optional
        List<String> nombresOpt = Arrays.asList("Ana", "Carlos", "Diana");
        
        nombresOpt.stream()
                 .findFirst()  // Retorna Optional<String>
                 .ifPresent(n -> System.out.println("Primer nombre: " + n.toUpperCase()));
        
        String nombreDefecto = nombresOpt.stream()
                                        .filter(n -> n.startsWith("Z"))
                                        .findFirst()
                                        .orElse("No encontrado");
        System.out.println("Nombre con Z: " + nombreDefecto);
    }
    
    // 2. SOLID - Single Responsibility Principle
    static void ejemploSingleResponsibility() {
        System.out.println("\n--- Single Responsibility Principle ---");
        
        interface Reporteable {
            String generar();
        }
        
        // BIEN: Cada clase tiene una responsabilidad
        class UsuarioService {
            public Usuario obtenerUsuario(int id) {
                return new Usuario("Ana", "ana@example.com");
            }
        }
        
        class ReporteUsuario implements Reporteable {
            private UsuarioService usuarioService;
            
            public ReporteUsuario(UsuarioService usuarioService) {
                this.usuarioService = usuarioService;
            }
            
            @Override
            public String generar() {
                Usuario u = usuarioService.obtenerUsuario(1);
                return "Reporte: " + u.nombre;
            }
        }
        
        UsuarioService svc = new UsuarioService();
        ReporteUsuario reporte = new ReporteUsuario(svc);
        System.out.println(reporte.generar());
    }
    
    // 3. SOLID - Open/Closed Principle
    static void ejemploOpenClosed() {
        System.out.println("\n--- Open/Closed Principle ---");
        
        interface Calculadora {
            double calcular(double a, double b);
        }
        
        class Suma implements Calculadora {
            @Override
            public double calcular(double a, double b) {
                return a + b;
            }
        }
        
        class Resta implements Calculadora {
            @Override
            public double calcular(double a, double b) {
                return a - b;
            }
        }
        
        // Extensible sin modificar código existente
        List<Calculadora> operaciones = Arrays.asList(
            new Suma(),
            new Resta()
        );
        
        for (Calculadora op : operaciones) {
            System.out.println("Resultado: " + op.calcular(10, 3));
        }
    }
    
    // 4. SOLID - Liskov Substitution Principle
    static void ejemploLiskovSubstitution() {
        System.out.println("\n--- Liskov Substitution Principle ---");
        
        // Los objetos de la subclase deben ser reemplazables por la superclase
        class Ave {
            public void volar() {
                System.out.println("Volando...");
            }
        }
        
        class Pajaro extends Ave {
            @Override
            public void volar() {
                System.out.println("Pájaro volando");
            }
        }
        
        // Polimorfismo correcto
        Ave ave = new Pajaro();
        ave.volar();  // Funciona correctamente
    }
    
    // 5. Patrón Singleton
    static void ejemploPatronSingleton() {
        System.out.println("\n--- Patrón Singleton ---");
        
        class ConexionBaseDatos {
            private static ConexionBaseDatos instancia;
            
            // Constructor privado
            private ConexionBaseDatos() { }
            
            // Método para obtener la instancia (única)
            public static synchronized ConexionBaseDatos obtener() {
                if (instancia == null) {
                    instancia = new ConexionBaseDatos();
                }
                return instancia;
            }
            
            public String conectar() {
                return "Conectado a la base de datos";
            }
        }
        
        ConexionBaseDatos conn1 = ConexionBaseDatos.obtener();
        ConexionBaseDatos conn2 = ConexionBaseDatos.obtener();
        System.out.println("¿conn1 == conn2? " + (conn1 == conn2));  // true
        System.out.println(conn1.conectar());
    }
    
    // 6. Patrón Factory
    static void ejemploPatronFactory() {
        System.out.println("\n--- Patrón Factory ---");
        
        interface BaseDatos {
            void conectar();
        }
        
        class MySQL implements BaseDatos {
            @Override
            public void conectar() {
                System.out.println("Conectando a MySQL...");
            }
        }
        
        class PostgreSQL implements BaseDatos {
            @Override
            public void conectar() {
                System.out.println("Conectando a PostgreSQL...");
            }
        }
        
        class FabricaBaseDatos {
            public static BaseDatos crear(String tipo) {
                return switch(tipo) {
                    case "mysql" -> new MySQL();
                    case "postgresql" -> new PostgreSQL();
                    default -> throw new IllegalArgumentException("BD no soportada");
                };
            }
        }
        
        BaseDatos bd = FabricaBaseDatos.crear("mysql");
        bd.conectar();
    }
    
    // 7. Patrón Builder
    static void ejemploPatronBuilder() {
        System.out.println("\n--- Patrón Builder ---");
        
        class Usuario {
            private String nombre;
            private String email;
            private int edad;
            private String telefono;
            
            private Usuario(ConstructorUsuario builder) {
                this.nombre = builder.nombre;
                this.email = builder.email;
                this.edad = builder.edad;
                this.telefono = builder.telefono;
            }
            
            @Override
            public String toString() {
                return "Usuario{" +
                       "nombre='" + nombre + '\'' +
                       ", email='" + email + '\'' +
                       ", edad=" + edad +
                       ", telefono='" + telefono + '\'' +
                       '}';
            }
            
            static class ConstructorUsuario {
                private String nombre;
                private String email;
                private int edad;
                private String telefono;
                
                public ConstructorUsuario(String nombre) {
                    this.nombre = nombre;
                }
                
                public ConstructorUsuario email(String email) {
                    this.email = email;
                    return this;
                }
                
                public ConstructorUsuario edad(int edad) {
                    this.edad = edad;
                    return this;
                }
                
                public ConstructorUsuario telefono(String telefono) {
                    this.telefono = telefono;
                    return this;
                }
                
                public Usuario construir() {
                    return new Usuario(this);
                }
            }
        }
        
        Usuario user = new Usuario.ConstructorUsuario("Ana")
                                  .email("ana@example.com")
                                  .edad(28)
                                  .telefono("123456789")
                                  .construir();
        System.out.println(user);
    }
    
    // 8. Logging
    static void ejemploLogging() {
        System.out.println("\n--- Logging ---");
        
        // Configurar logger
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        
        // Diferentes niveles de log
        logger.severe("Error grave");
        logger.warning("Advertencia");
        logger.info("Información general");
        logger.fine("Detalle fino");
        
        System.out.println("\nNota: Usar SLF4J + Logback en producción");
    }
}

// Clase auxiliar
class Usuario {
    String nombre;
    String email;
    
    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }
}
