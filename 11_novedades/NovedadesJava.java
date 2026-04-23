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
}
