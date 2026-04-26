package jdklibs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ejemplos de java.util.regex: expresiones regulares
 * - Pattern y Matcher
 * - Validación y extracción de datos
 */
public class EjemplosRegex {

    public static void main(String[] args) {
        System.out.println("=== EJEMPLOS JAVA.UTIL.REGEX ===\n");

        // Validar email
        System.out.println("--- Validación de Email ---");
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        String[] emails = {"usuario@dominio.com", "invalido@", "otro@dominio", "valido@email.org"};
        for (String email : emails) {
            Matcher matcher = emailPattern.matcher(email);
            System.out.println("Email: " + email + " -> Válido: " + matcher.matches());
        }

        // Extraer números de teléfono
        System.out.println("\n--- Extracción de Números de Teléfono ---");
        String texto = "Contactos: Juan 555-1234, María (555) 567-8901, Pedro 555.111.2222";
        String phoneRegex = "\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(texto);

        System.out.println("Texto: " + texto);
        System.out.println("Números encontrados:");
        while (phoneMatcher.find()) {
            System.out.println("  - " + phoneMatcher.group());
        }

        // Reemplazar texto
        System.out.println("\n--- Reemplazo con Regex ---");
        String textoOriginal = "El precio es $19.99 y el descuento es 10%";
        String resultado = textoOriginal.replaceAll("\\$\\d+\\.\\d{2}", "***PRECIO***");
        System.out.println("Original: " + textoOriginal);
        System.out.println("Reemplazado: " + resultado);
    }
}