package fundamentos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Ejemplos de Arrays y Cadenas en Java
 * - Arrays unidimensionales y multidimensionales
 * - ArrayList y LinkedList
 * - String, StringBuilder, StringBuffer
 */
public class ArraysYCadenas {
    
    public static void main(String[] args) {
        System.out.println("=== ARRAYS Y CADENAS ===\n");
        
        ejemplosArrays();
        ejemplosArraysMultidimensionales();
        ejemplosArrayList();
        ejemplosLinkedList();
        ejemplosCadenas();
    }
    
    static void ejemplosArrays() {
        System.out.println("--- ARRAYS Unidimensionales ---");
        
        // Declaración e inicialización
        int[] numeros = {10, 20, 30, 40, 50};
        System.out.println("Array de números: ");
        for (int num : numeros) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // Array sin inicializar
        String[] nombres = new String[3];
        nombres[0] = "Ana";
        nombres[1] = "Carlos";
        nombres[2] = "Diana";
        
        System.out.println("\nArray de nombres:");
        for (String nombre : nombres) {
            System.out.println("- " + nombre);
        }
        
        // Acceso a elementos
        System.out.println("\nPrimer elemento: " + numeros[0]);
        System.out.println("Último elemento: " + numeros[numeros.length - 1]);
        System.out.println("Longitud del array: " + numeros.length);
        
        // Modificación de elementos
        numeros[2] = 99;
        System.out.println("Después de modificación: " + numeros[2]);
    }
    
    static void ejemplosArraysMultidimensionales() {
        System.out.println("\n--- ARRAYS Multidimensionales ---");
        
        // Matriz 2D
        int[][] matriz = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("Matriz 3x3:");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        
        // Acceso a elemento
        System.out.println("\nElemento [1][2]: " + matriz[1][2]);
        
        // Array de arrays irregulares
        int[][] irregular = {
            {1, 2},
            {3, 4, 5},
            {6}
        };
        
        System.out.println("\nArray irregular:");
        for (int[] fila : irregular) {
            for (int valor : fila) {
                System.out.print(valor + " ");
            }
            System.out.println();
        }
    }
    
    static void ejemplosArrayList() {
        System.out.println("\n--- ARRAYLIST ---");
        
        // Creación de ArrayList
        ArrayList<String> frutas = new ArrayList<>();
        
        // Agregar elementos
        frutas.add("Manzana");
        frutas.add("Plátano");
        frutas.add("Cereza");
        frutas.add("Dátil");
        
        System.out.println("ArrayList: " + frutas);
        System.out.println("Tamaño: " + frutas.size());
        
        // Acceso por índice
        System.out.println("Primer elemento: " + frutas.get(0));
        System.out.println("Último elemento: " + frutas.get(frutas.size() - 1));
        
        // Modificar elemento
        frutas.set(1, "Banana");
        System.out.println("Después de modificación: " + frutas);
        
        // Remover elemento
        frutas.remove(2);  // por índice
        System.out.println("Después de eliminar: " + frutas);
        
        frutas.remove("Dátil");  // por valor
        System.out.println("Después de eliminar 'Dátil': " + frutas);
        
        // Verificar si contiene
        System.out.println("¿Contiene 'Manzana'? " + frutas.contains("Manzana"));
        System.out.println("¿Contiene 'Naranja'? " + frutas.contains("Naranja"));
        
        // Limpiar ArrayList
        ArrayList<Integer> tempList = new ArrayList<>();
        tempList.add(1);
        tempList.add(2);
        tempList.clear();
        System.out.println("ArrayList después de clear: " + tempList);
        
        // ArrayList con tipos primitivos (wrapper)
        ArrayList<Integer> numeros = new ArrayList<>();
        numeros.add(10);
        numeros.add(20);
        numeros.add(30);
        System.out.println("ArrayList de enteros: " + numeros);
    }
    
    static void ejemplosLinkedList() {
        System.out.println("\n--- LINKEDLIST ---");
        
        LinkedList<String> colores = new LinkedList<>();
        
        // Agregar elementos
        colores.add("Rojo");
        colores.add("Verde");
        colores.add("Azul");
        
        System.out.println("LinkedList: " + colores);
        
        // Operaciones específicas de LinkedList
        colores.addFirst("Negro");      // Agregar al inicio
        colores.addLast("Amarillo");    // Agregar al final
        System.out.println("Después de addFirst y addLast: " + colores);
        
        System.out.println("First: " + colores.getFirst());
        System.out.println("Last: " + colores.getLast());
        
        colores.removeFirst();  // Remover del inicio
        colores.removeLast();   // Remover del final
        System.out.println("Después de removeFirst y removeLast: " + colores);
    }
    
    static void ejemplosCadenas() {
        System.out.println("\n--- CADENAS (String) ---");
        
        String texto1 = "Hola";
        String texto2 = "Hola";
        String texto3 = new String("Hola");
        
        // Comparación
        System.out.println("texto1 == texto2: " + (texto1 == texto2));  // true (pool)
        System.out.println("texto1 == texto3: " + (texto1 == texto3));  // false (diferentes objetos)
        System.out.println("texto1.equals(texto3): " + (texto1.equals(texto3)));  // true (contenido)
        System.out.println("texto1.equalsIgnoreCase(\"HOLA\"): " + (texto1.equalsIgnoreCase("HOLA")));
        
        // Métodos comunes
        String mensaje = "  Bienvenido a Java  ";
        System.out.println("\n--- Métodos String ---");
        System.out.println("Longitud: " + mensaje.length());
        System.out.println("Mayúsculas: " + mensaje.toUpperCase());
        System.out.println("Minúsculas: " + mensaje.toLowerCase());
        System.out.println("Sin espacios: '" + mensaje.trim() + "'");
        System.out.println("Substring [2-10]: " + mensaje.substring(2, 10));
        System.out.println("Contiene 'Java': " + mensaje.contains("Java"));
        System.out.println("Comienza con 'Bienvenido': " + mensaje.trim().startsWith("Bienvenido"));
        System.out.println("Reemplazar: " + mensaje.replace("a", "@"));
        System.out.println("Dividir: ");
        String[] palabras = "Uno dos tres".split(" ");
        for (String palabra : palabras) {
            System.out.println("  - " + palabra);
        }
        
        // Concatenación
        System.out.println("\n--- Concatenación ---");
        String concat1 = "Hola" + " " + "Mundo";
        System.out.println(concat1);
        
        // StringBuilder (mutable, eficiente)
        System.out.println("\n--- StringBuilder ---");
        StringBuilder sb = new StringBuilder();
        sb.append("Hola");
        sb.append(" ");
        sb.append("Mundo");
        sb.append("!");
        System.out.println("StringBuilder: " + sb.toString());
        
        // Operaciones con StringBuilder
        sb.insert(5, "Bello ");
        System.out.println("Después de insert: " + sb.toString());
        
        sb.delete(5, 11);
        System.out.println("Después de delete: " + sb.toString());
        
        sb.reverse();
        System.out.println("Reversed: " + sb.toString());
        
        // StringBuffer (sincronizado, más lento)
        System.out.println("\n--- StringBuffer ---");
        StringBuffer sbf = new StringBuffer("Java");
        sbf.append(" es genial");
        System.out.println("StringBuffer: " + sbf.toString());
        
        // Inmutabilidad de String
        System.out.println("\n--- Inmutabilidad de String ---");
        String original = "Java";
        String modificado = original.concat(" es increíble");
        System.out.println("Original: " + original);
        System.out.println("Modificado: " + modificado);
        System.out.println("Son el mismo objeto? " + (original == modificado));  // false
    }
}
