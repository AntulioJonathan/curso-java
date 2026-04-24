package novedades;

/**
 * Ejemplos de novedades destacadas en Java 8 a 21
 */

public class NovedadesJava {
    
    public static void main(String[] args) {
        System.out.println("=== NOVEDADES POR VERSIÓN ===\n");
        
        novedadesJava10();
        novedadesJava12();
        novedadesJava14();
        novedadesJava16();
        novedadesJava17();
        novedadesJava21();
        novedadesJava25();
    }
    
    // Java 10: var (inferencia de tipos para variables locales)
    static void novedadesJava10() {
        System.out.println("--- Java 10: var (Local Variable Type Inference) ---");
        
        var mensaje = "Hola Java 10";  // Tipo inferido: String
        var numero = 42;               // Tipo inferido: int
        var lista = java.util.Arrays.asList(1, 2, 3);  // Tipo inferido: List<Integer>
        var mapa = new java.util.HashMap<String, Integer>();  // Tipo inferido: HashMap<String, Integer>
        
        System.out.println("mensaje: " + mensaje + " (" + mensaje.getClass().getSimpleName() + ")");
        System.out.println("numero: " + numero + " (" + ((Object)numero).getClass().getSimpleName() + ")");
        System.out.println("lista: " + lista);
        System.out.println("mapa: " + mapa);
        
        // var NO se puede usar con:
        // - Fields (atributos de clase)
        // - Parámetros de métodos
        // - Tipos null
    }
    
    // Java 12: Switch expressions (preview)
    static void novedadesJava12() {
        System.out.println("\n--- Java 12: Switch Expressions ---");
        
        int dia = 3;
        String nombreDia = switch(dia) {
            case 1 -> "Lunes";
            case 2 -> "Martes";
            case 3 -> "Miércoles";
            case 4 -> "Jueves";
            case 5 -> "Viernes";
            default -> "Fin de semana";
        };
        
        System.out.println("Día " + dia + ": " + nombreDia);
    }
    
    // Java 14: Records (preview) e instanceof pattern matching
    static void novedadesJava14() {
        System.out.println("\n--- Java 14: Records e instanceof Pattern Matching ---");
        
        // Records (Java 16: standard feature)
        record Persona(String nombre, int edad) {
            // Genera automáticamente: constructor, getters, equals, hashCode, toString
        }
        
        var persona = new Persona("Ana", 28);
        System.out.println("Record: " + persona);
        System.out.println("  nombre: " + persona.nombre());
        System.out.println("  edad: " + persona.edad());
        
        // Pattern matching cons instanceof (Java 16: standard feature)
        System.out.println("\n--- Pattern Matching con instanceof ---");
        Object objeto = "Texto";
        
        // Forma antigua
        if (objeto instanceof String) {
            String s = (String) objeto;
            System.out.println("Es String (antiguo): " + s.toUpperCase());
        }
        
        // Forma nueva (pattern matching)
        if (objeto instanceof String s) {
            System.out.println("Es String (pattern): " + s.toUpperCase());
        }
    }
    
    // Java 16: Records y Sealed Classes (standard)
    static void novedadesJava16() {
        System.out.println("\n--- Java 16: Sealed Classes ---");
        
        // Sealed classes: controlar quién puede extender una clase
        sealed class Animal permits Perro, Gato { }
        
        final class Perro extends Animal { }
        final class Gato extends Animal { }
        
        System.out.println("Sealed classes: Solo Perro y Gato pueden extender Animal");
    }
    
    // Java 17: Sealed Classes standard, Pattern Matching switch (preview)
    static void novedadesJava17() {
        System.out.println("\n--- Java 17: LTS ---");
        
        // Sealed classes (standard)
        sealed class Forma permits Circulo, Rectangulo { }
        final class Circulo extends Forma { }
        final class Rectangulo extends Forma { }
        
        System.out.println("Java 17 LTS: Sealed classes son estándar");
    }
    
    // Java 21: Virtual threads, Pattern matching switch (standard), Record patterns (standard)
    static void novedadesJava21() {
        System.out.println("\n--- Java 21: LTS ---");
        
        // 1. Virtual Threads (Project Loom)
        System.out.println("\n1. Virtual Threads (Demostración - similar a Runnable):");
        // En Java 21: Thread.ofVirtual().start(...)
        // Los virtual threads son ligeros y el JVM puede ejecutar millones
        System.out.println("   Permiten crear millones de hilos ligeros");
        System.out.println("   Basados en continuaciones del sistema operativo");
        
        // 2. Pattern Matching switch (standard)
        System.out.println("\n2. Pattern Matching switch (standard):");
        
        Object valor = 42;
        
        String resultado = switch(valor) {
            case Integer i when i > 0 -> "Entero positivo: " + i;
            case Integer i when i < 0 -> "Entero negativo: " + i;
            case Integer i -> "Cero";
            case String s -> "String: " + s;
            default -> "Desconocido";
        };
        
        System.out.println("   " + resultado);
        
        // 3. Record Patterns (standard)
        System.out.println("\n3. Record Patterns (deconstruction):");
        
        record Punto(int x, int y) { }
        
        Punto punto = new Punto(5, 10);
        
        if (punto instanceof Punto(int x, int y) && x > 0 && y > 0) {
            System.out.println("   Punto en cuadrante positivo: (" + x + ", " + y + ")");
        }
        
        // 4. Secuenced Collections
        System.out.println("\n4. Secuenced Collections:");
        var lista = java.util.List.of(1, 2, 3, 4, 5);
        System.out.println("   Lista original: " + lista);
        // En Java 21 hay métodos: getFirst(), getLast(), reversed()
        System.out.println("   Primer elemento: " + lista.getFirst());
        System.out.println("   Último elemento: " + lista.getLast());
        
        // 5. String templates (preview en Java 21)
        System.out.println("\n5. String Templates (Preview):");
        String nombre = "Java";
        int version = 21;
        // Nota: String templates son preview, aquí mostramos la sintaxis futura
        System.out.println("   Sintaxis futura: \\\"\\(nombre) versión \\(version)\\\"");
        System.out.println("   Resultado: \"" + nombre + " versión " + version + "\"");
    }
    
    // Java 25: Pattern Matching avanzado, Structured Concurrency, Text Blocks mejorados
    static void novedadesJava25() {
        System.out.println("\n--- Java 25 (2025) ---");
        
        // 1. Pattern Matching Exhaustiveness (preview)
        System.out.println("\n1. Pattern Matching Exhaustiveness (mejorado):");
        
        sealed class Numero permits Entero, Decimal { }
        record Entero(int valor) implements Numero { }
        record Decimal(double valor) implements Numero { }
        
        Numero numero = new Entero(42);
        
        String tipo = switch(numero) {
            case Entero e -> "Entero: " + e.valor();
            case Decimal d -> "Decimal: " + d.valor();
            // El compilador verifica que TODOS los casos estén cubiertos
        };
        
        System.out.println("   " + tipo);
        
        // 2. Array y Nested Pattern Matching (mejorado)
        System.out.println("\n2. Nested Pattern Matching:");
        
        record Punto(int x, int y) { }
        record Linea(Punto inicio, Punto fin) { }
        
        Linea linea = new Linea(new Punto(0, 0), new Punto(10, 10));
        
        if (linea instanceof Linea(Punto(int x1, int y1), Punto(int x2, int y2))) {
            System.out.println("   Línea de (" + x1 + "," + y1 + ") a (" + x2 + "," + y2 + ")");
        }
        
        // 3. Mejoras en Structured Concurrency
        System.out.println("\n3. Structured Concurrency (API en preview):");
        System.out.println("   Permite gestionar grupos de hilos virtuales de forma coordinada");
        System.out.println("   Ejemplo:");
        System.out.println("   try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {");
        System.out.println("       var task1 = scope.fork(() -> tareaA());");
        System.out.println("       var task2 = scope.fork(() -> tareaB());");
        System.out.println("       scope.join();
        System.out.println("   }");
        
        // 4. Text Blocks mejorado
        System.out.println("\n4. Text Blocks (now standard feature):");
        
        String json = """
            {
              "nombre": "Java 25",
              "version": 25,
              "features": [
                "Pattern Matching",
                "Structured Concurrency",
                "Virtual Threads"
              ]
            }
            """;
        
        System.out.println("   JSON con Text Blocks:");
        System.out.println(json);
        
        // 5. Virtual Threads - Mejoras de Rendimiento
        System.out.println("\n5. Virtual Threads - Optimizaciones:");
        System.out.println("   - Mejor gestión de memoria");
        System.out.println("   - Optimizaciones en context switching");
        System.out.println("   - Mejor debugging de virtual threads");
        
        // 6. Mejoras en Garbage Collection
        System.out.println("\n6. Garbage Collection - Mejoras:");
        System.out.println("   - Zing GC optimizations");
        System.out.println("   - Reducción de pausas GC");
        System.out.println("   - Mejor rendimiento en heap grandes");
        
        // 7. String methods mejorados
        System.out.println("\n7. String Methods (nuevos en Java 25):");
        String texto = "  Hola Java 25  ";
        System.out.println("   Texto original: '" + texto + "'");
        System.out.println("   Con trim(): '" + texto.trim() + "'");
        // Java 25 añade métodos adicionales para manipulación de strings
        
        // 8. Performance improvements
        System.out.println("\n8. Performance Improvements:");
        System.out.println("   - Inlining mejoras");
        System.out.println("   - Loop optimizations");
        System.out.println("   - Escape analysis mejorado");
    }
}
