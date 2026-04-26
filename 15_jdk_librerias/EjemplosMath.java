package jdklibs;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Ejemplos de java.math: BigDecimal y BigInteger
 * - Cálculos de alta precisión
 * - Operaciones aritméticas precisas
 */
public class EjemplosMath {

    public static void main(String[] args) {
        System.out.println("=== EJEMPLOS JAVA.MATH ===\n");

        // BigDecimal para cálculos financieros
        System.out.println("--- BigDecimal ---");
        BigDecimal precio = new BigDecimal("19.99");
        BigDecimal cantidad = new BigDecimal("3");
        BigDecimal total = precio.multiply(cantidad);
        System.out.println("Precio: " + precio + " x Cantidad: " + cantidad + " = Total: " + total);

        // Redondeo
        BigDecimal pi = new BigDecimal("3.141592653589793");
        BigDecimal piRounded = pi.setScale(2, RoundingMode.HALF_UP);
        System.out.println("Pi redondeado a 2 decimales: " + piRounded);

        // BigInteger para números muy grandes
        System.out.println("\n--- BigInteger ---");
        BigInteger factorial = BigInteger.ONE;
        for (int i = 1; i <= 50; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        System.out.println("50! tiene " + factorial.toString().length() + " dígitos");
        System.out.println("Primeros 50 dígitos: " + factorial.toString().substring(0, 50) + "...");
    }
}