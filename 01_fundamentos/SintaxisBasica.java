package fundamentos;

import java.util.Scanner;

/**
 * Ejemplos de sintaxis básica en Java
 * - Estructura de un programa
 * - Variables, constantes y tipos primitivos
 * - Operadores
 */
public class SintaxisBasica {
    
    // Constante (modificadores: public static final)
    public static final double PI = 3.14159;
    public static final String EMPRESA = "MiEmpresa";
    
    // Variables de instancia
    private String nombre;
    private int edad;
    
    public static void main(String[] args) {
        System.out.println("=== SINTAXIS BÁSICA EN JAVA ===\n");
        
        // 1. Comentarios
        // Esto es un comentario de línea
        /* Esto es un comentario
           de múltiples líneas */
        
        // 2. Tipos primitivos y variables
        byte byteVar = 127;                    // 8 bits: -128 a 127
        short shortVar = 32000;                // 16 bits
        int intVar = 2147483647;               // 32 bits
        long longVar = 9223372036854775807L;   // 64 bits (sufijo L)
        float floatVar = 3.14f;                // 32 bits (sufijo f)
        double doubleVar = 3.141592653589793; // 64 bits
        boolean boolVar = true;                // true o false
        char charVar = 'A';                    // Un carácter Unicode
        
        System.out.println("--- Tipos Primitivos ---");
        System.out.println("byte: " + byteVar);
        System.out.println("short: " + shortVar);
        System.out.println("int: " + intVar);
        System.out.println("long: " + longVar);
        System.out.println("float: " + floatVar);
        System.out.println("double: " + doubleVar);
        System.out.println("boolean: " + boolVar);
        System.out.println("char: " + charVar);
        
        // 3. Envoltorios (Wrapper classes)
        System.out.println("\n--- Envoltorios (Wrapper Classes) ---");
        Integer intWrapper = 100;           // Auto-boxing
        Double doubleWrapper = 3.14;
        Boolean boolWrapper = false;
        
        // Auto-unboxing
        int primitivo = intWrapper;
        System.out.println("Integer wrapper: " + intWrapper);
        System.out.println("Double wrapper: " + doubleWrapper);
        
        // 4. Operadores aritméticos
        System.out.println("\n--- Operadores Aritméticos ---");
        int a = 10, b = 3;
        System.out.println("a + b = " + (a + b));   // suma
        System.out.println("a - b = " + (a - b));   // resta
        System.out.println("a * b = " + (a * b));   // multiplicación
        System.out.println("a / b = " + (a / b));   // división entera
        System.out.println("a % b = " + (a % b));   // módulo
        System.out.println("++a = " + (++a));       // pre-incremento
        System.out.println("b-- = " + (b--));       // post-decremento
        
        // 5. Operadores relacionales
        System.out.println("\n--- Operadores Relacionales ---");
        System.out.println("10 == 10: " + (10 == 10));    // igual
        System.out.println("10 != 5: " + (10 != 5));      // diferente
        System.out.println("10 > 5: " + (10 > 5));        // mayor
        System.out.println("10 < 5: " + (10 < 5));        // menor
        System.out.println("10 >= 10: " + (10 >= 10));    // mayor o igual
        System.out.println("10 <= 5: " + (10 <= 5));      // menor o igual
        
        // 6. Operadores lógicos
        System.out.println("\n--- Operadores Lógicos ---");
        boolean x = true, y = false;
        System.out.println("true && false: " + (x && y));  // AND
        System.out.println("true || false: " + (x || y));  // OR
        System.out.println("!true: " + (!x));              // NOT
        
        // 7. Operador ternario
        System.out.println("\n--- Operador Ternario ---");
        int edad = 25;
        String resultado = (edad >= 18) ? "Mayor de edad" : "Menor de edad";
        System.out.println(resultado);
        
        // 8. Constantes
        System.out.println("\n--- Constantes ---");
        System.out.println("PI = " + PI);
        System.out.println("EMPRESA = " + EMPRESA);
        
        // 9. Entrada por consola
        System.out.println("\n--- Entrada por Consola (Scanner) ---");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa tu nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingresa tu edad: ");
        int edadInput = scanner.nextInt();
        System.out.println("Hola " + nombre + ", tienes " + edadInput + " años");
        
        scanner.close();
    }
}
