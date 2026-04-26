package fundamentos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Ejemplos de casteo y conversiones en Java
 * - Casteo implícito (promoción numérica) y explícito
 * - Conversiones entre tipos primitivos
 * - Conversiones entre tipos objeto
 * - Upcasting y downcasting en herencia
 * - Conversión de cadenas a números
 * - Conversión de fechas
 * - Métodos utilitarios
 */
public class CasteoYConversiones {

    public static void main(String[] args) {
        System.out.println("=== CASTEO Y CONVERSIONES EN JAVA ===\n");

        // 1. Casteo implícito (promoción numérica)
        System.out.println("--- Casteo Implícito (Promoción Numérica) ---");
        int intVar = 100;
        long longVar = intVar; // int a long (automático)
        double doubleVar = intVar; // int a double (automático)
        System.out.println("int: " + intVar + " -> long: " + longVar + " -> double: " + doubleVar);

        // 2. Casteo explícito
        System.out.println("\n--- Casteo Explícito ---");
        double pi = 3.14159;
        int piInt = (int) pi; // double a int (pérdida de precisión)
        System.out.println("double: " + pi + " -> int: " + piInt);

        // 3. Conversiones entre tipos primitivos
        System.out.println("\n--- Conversiones entre Tipos Primitivos ---");
        int num = 65;
        char ch = (char) num; // int a char
        System.out.println("int: " + num + " -> char: " + ch);

        // 4. Conversiones entre tipos objeto
        System.out.println("\n--- Conversiones entre Tipos Objeto ---");
        Integer integerObj = 42;
        Long longObj = integerObj.longValue(); // Integer a Long
        System.out.println("Integer: " + integerObj + " -> Long: " + longObj);

        // 5. Upcasting y downcasting en herencia
        System.out.println("\n--- Upcasting y Downcasting ---");
        Animal animal = new Perro(); // Upcasting (implícito)
        if (animal instanceof Perro) {
            Perro perro = (Perro) animal; // Downcasting (explícito con instanceof)
            perro.ladrar();
        }

        // 6. Conversión de cadenas a números
        System.out.println("\n--- Conversión de Cadenas a Números ---");
        String strNum = "123";
        int parsedInt = Integer.parseInt(strNum);
        double parsedDouble = Double.valueOf(strNum);
        System.out.println("String: \"" + strNum + "\" -> int: " + parsedInt + " -> double: " + parsedDouble);

        // 7. Conversión de fechas
        System.out.println("\n--- Conversión de Fechas ---");
        // Antes (Date/Calendar - obsoleto)
        Date oldDate = new Date();
        System.out.println("Date (obsoleto): " + oldDate);

        // Ahora (java.time)
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        String formatted = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("LocalDate: " + localDate);
        System.out.println("LocalDateTime: " + localDateTime);
        System.out.println("Formatted: " + formatted);

        // 8. Métodos utilitarios
        System.out.println("\n--- Métodos Utilitarios ---");
        Object obj = null;
        String strFromObj = String.valueOf(obj); // null -> "null"
        String strFromInt = String.valueOf(42); // int -> "42"
        System.out.println("String.valueOf(null): \"" + strFromObj + "\"");
        System.out.println("String.valueOf(42): \"" + strFromInt + "\"");
    }
}

// Clases para ejemplo de herencia
class Animal {
    public void hacerSonido() {
        System.out.println("Sonido genérico");
    }
}

class Perro extends Animal {
    @Override
    public void hacerSonido() {
        System.out.println("Guau!");
    }

    public void ladrar() {
        System.out.println("El perro ladra: ¡Guau guau!");
    }
}