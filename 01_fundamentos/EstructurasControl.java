package fundamentos;

import java.util.Scanner;

/**
 * Ejemplos de estructuras de control en Java
 * - if, else if, else
 * - switch (tradicional y expresión)
 * - Bucles: for, while, do-while, for-each
 */
public class EstructurasControl {
    
    public static void main(String[] args) {
        System.out.println("=== ESTRUCTURAS DE CONTROL ===\n");
        
        ejemplosIf();
        ejemplosSwitch();
        ejemplosSwitchExpresion();
        ejemplosBucles();
    }
    
    // Ejemplo: IF-ELSE IF-ELSE
    static void ejemplosIf() {
        System.out.println("--- IF, ELSE IF, ELSE ---");
        int calificacion = 85;
        
        if (calificacion >= 90) {
            System.out.println("Calificación: A (Excelente)");
        } else if (calificacion >= 80) {
            System.out.println("Calificación: B (Bueno)");
        } else if (calificacion >= 70) {
            System.out.println("Calificación: C (Aceptable)");
        } else {
            System.out.println("Calificación: F (Reprobado)");
        }
        
        // IF anidados
        System.out.println("\n--- IF Anidados ---");
        int edad = 25;
        boolean tieneLicencia = true;
        
        if (edad >= 18) {
            if (tieneLicencia) {
                System.out.println("Puede conducir");
            } else {
                System.out.println("Debe obtener una licencia");
            }
        } else {
            System.out.println("Muy joven para conducir");
        }
    }
    
    // Ejemplo: SWITCH tradicional
    static void ejemplosSwitch() {
        System.out.println("\n--- SWITCH Tradicional ---");
        int diaSemana = 3;
        
        switch (diaSemana) {
            case 1:
                System.out.println("Lunes");
                break;
            case 2:
                System.out.println("Martes");
                break;
            case 3:
                System.out.println("Miércoles");
                break;
            case 4:
                System.out.println("Jueves");
                break;
            case 5:
                System.out.println("Viernes");
                break;
            case 6:
                System.out.println("Sábado");
                break;
            case 7:
                System.out.println("Domingo");
                break;
            default:
                System.out.println("Día inválido");
        }
        
        // Switch con Strings
        System.out.println("\n--- SWITCH con Strings ---");
        String estacion = "verano";
        
        switch (estacion) {
            case "primavera":
                System.out.println("Flores y clima templado");
                break;
            case "verano":
                System.out.println("Calor y días largos");
                break;
            case "otoño":
                System.out.println("Hojas cayendo");
                break;
            case "invierno":
                System.out.println("Frío y nieve");
                break;
            default:
                System.out.println("Estación desconocida");
        }
    }
    
    // Ejemplo: SWITCH como expresión (Java 12+)
    static void ejemplosSwitchExpresion() {
        System.out.println("\n--- SWITCH como Expresión (Java 12+) ---");
        
        int diaSemana = 5;
        String nombreDia = switch (diaSemana) {
            case 1 -> "Lunes";
            case 2 -> "Martes";
            case 3 -> "Miércoles";
            case 4 -> "Jueves";
            case 5 -> "Viernes";
            case 6, 7 -> "Fin de semana";  // múltiples coincidencias
            default -> "Día inválido";
        };
        System.out.println("Día: " + nombreDia);
        
        // Switch con bloques
        System.out.println("\n--- SWITCH con Bloques ---");
        String resultado = switch (diaSemana) {
            case 1, 2, 3, 4, 5 -> {
                System.out.println("Es día de trabajo");
                yield "Día laborable";
            }
            case 6, 7 -> {
                System.out.println("Es fin de semana");
                yield "Descanso";
            }
            default -> "Día desconocido";
        };
        System.out.println("Resultado: " + resultado);
    }
    
    // Ejemplo: BUCLES
    static void ejemplosBucles() {
        System.out.println("\n--- FOR Simple ---");
        for (int i = 0; i < 5; i++) {
            System.out.println("Iteración " + (i + 1));
        }
        
        System.out.println("\n--- FOR con decremento ---");
        for (int i = 5; i > 0; i--) {
            System.out.println("Valor: " + i);
        }
        
        System.out.println("\n--- WHILE ---");
        int contador = 0;
        while (contador < 3) {
            System.out.println("Contador: " + contador);
            contador++;
        }
        
        System.out.println("\n--- DO-WHILE ---");
        contador = 0;
        do {
            System.out.println("DO-WHILE: " + contador);
            contador++;
        } while (contador < 3);
        
        System.out.println("\n--- FOR-EACH (Array) ---");
        int[] numeros = {10, 20, 30, 40, 50};
        for (int num : numeros) {
            System.out.println("Número: " + num);
        }
        
        System.out.println("\n--- FOR-EACH (String) ---");
        String[] palabras = {"Hola", "Mundo", "Java"};
        for (String palabra : palabras) {
            System.out.println(palabra);
        }
        
        System.out.println("\n--- BREAK y CONTINUE ---");
        for (int i = 0; i < 10; i++) {
            if (i == 3) {
                System.out.println("Skip en i=3");
                continue;  // salta el resto de esta iteración
            }
            if (i == 7) {
                System.out.println("Sale del bucle en i=7");
                break;     // sale del bucle
            }
            System.out.println("i = " + i);
        }
        
        System.out.println("\n--- Bucles Anidados ---");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print("(" + i + "," + j + ") ");
            }
            System.out.println();
        }
    }
}
