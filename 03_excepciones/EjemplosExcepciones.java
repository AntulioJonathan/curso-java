package excepciones;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Ejemplos de Manejo de Excepciones
 * - try, catch, finally
 * - try-with-resources
 * - Lanzar excepciones
 * - Excepciones personalizadas
 */

// Excepción personalizada (checked)
class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
    
    public SaldoInsuficienteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

// Excepción personalizada (unchecked)
class DepartamentoNoEncontradoException extends RuntimeException {
    public DepartamentoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

public class EjemplosExcepciones {
    
    public static void main(String[] args) {
        System.out.println("=== MANEJO DE EXCEPCIONES ===\n");
        
        ejemplo1_BasicoTryCatch();
        ejemplo2_MultipleCatch();
        ejemplo3_Finally();
        ejemplo4_TryWithResources();
        ejemplo5_LanzarExcepciones();
        ejemplo6_ExcepcionesPersonalizadas();
    }
    
    // Ejemplo 1: Try-Catch básico
    static void ejemplo1_BasicoTryCatch() {
        System.out.println("--- Try-Catch Básico ---");
        try {
            int[] numeros = {1, 2, 3};
            System.out.println("Accediendo a índice 0: " + numeros[0]);
            System.out.println("Accediendo a índice 5: " + numeros[5]);  // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Índice fuera de los límites del array");
            System.out.println("Mensaje: " + e.getMessage());
        }
        
        // Con aritmética
        try {
            int resultado = 10 / 0;  // ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("Error: División por cero");
        }
    }
    
    // Ejemplo 2: Múltiples catch
    static void ejemplo2_MultipleCatch() {
        System.out.println("\n--- Múltiples Catch ---");
        
        String[] numeros = {"1", "2", "abc", "5"};
        
        for (String num : numeros) {
            try {
                int valor = Integer.parseInt(num);
                int resultado = 100 / valor;
                System.out.println("100 / " + valor + " = " + resultado);
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + num + "' no es un número válido");
            } catch (ArithmeticException e) {
                System.out.println("Error: División por cero");
            } catch (Exception e) {
                // Captura cualquier otra excepción
                System.out.println("Error general: " + e.getMessage());
            }
        }
        
        // Multi-catch (Java 7+)
        System.out.println("\n--- Multi-Catch (Java 7+) ---");
        try {
            int[] arr = {1, 2};
            int x = Integer.parseInt("10");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: " + e.getClass().getSimpleName());
        }
    }
    
    // Ejemplo 3: Finally
    static void ejemplo3_Finally() {
        System.out.println("\n--- Finally ---");
        
        try {
            System.out.println("En el bloque try");
            int resultado = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("En el bloque catch");
        } finally {
            System.out.println("En el bloque finally (SIEMPRE se ejecuta)");
        }
        
        System.out.println("Después del bloque try-catch-finally");
    }
    
    // Ejemplo 4: Try-with-resources (Java 7+)
    static void ejemplo4_TryWithResources() {
        System.out.println("\n--- Try-with-Resources ---");
        
        // Simular un recurso (usamos String como ejemplo)
        try (
            java.io.FileReader fr = new java.io.FileReader("archivo.txt")
        ) {
            // El recurso se cierra automáticamente
            System.out.println("Usando recurso");
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
        // No necesitamos finally para cerrar el recurso
    }
    
    // Ejemplo 5: Lanzar excepciones
    static void ejemplo5_LanzarExcepciones() {
        System.out.println("\n--- Lanzar Excepciones ---");
        
        try {
            validarEdad(15);
        } catch (IllegalArgumentException e) {
            System.out.println("Capturado: " + e.getMessage());
        }
        
        try {
            validarEdad(25);
            System.out.println("Edad válida");
        } catch (IllegalArgumentException e) {
            System.out.println("Capturado: " + e.getMessage());
        }
    }
    
    static void validarEdad(int edad) throws IllegalArgumentException {
        if (edad < 0 || edad > 150) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 150");
        }
    }
    
    // Ejemplo 6: Excepciones personalizadas
    static void ejemplo6_ExcepcionesPersonalizadas() {
        System.out.println("\n--- Excepciones Personalizadas ---");
        
        // Exception personalizada (checked)
        try {
            retirarDinero(100, 200);  // Más de lo disponible
        } catch (SaldoInsuficienteException e) {
            System.out.println("Error de transacción: " + e.getMessage());
            e.printStackTrace();
        }
        
        // RuntimeException personalizada (unchecked)
        try {
            String depto = buscarDepartamento("VENTAS");
            System.out.println("Departamento encontrado: " + depto);
        } catch (DepartamentoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    static void retirar(double cantidad, double saldo) throws SaldoInsuficienteException {
        if (cantidad > saldo) {
            throw new SaldoInsuficienteException(
                "Intenta retirar $" + cantidad + " pero solo tiene $" + saldo
            );
        }
        System.out.println("Retiro de $" + cantidad + " exitoso");
    }
    
    static void retirarDinero(double cantidad, double saldo) throws SaldoInsuficienteException {
        retirar(cantidad, saldo);
    }
    
    static String buscarDepartamento(String nombre) {
        if (!nombre.equals("RRHH") && !nombre.equals("IT")) {
            throw new DepartamentoNoEncontradoException(
                "Departamento '" + nombre + "' no existe"
            );
        }
        return nombre;
    }
}
