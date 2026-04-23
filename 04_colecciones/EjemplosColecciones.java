package colecciones;

import java.util.*;

/**
 * Ejemplos de Colecciones en Java
 * - List, Set, Map
 * - Implementations
 * - Ordenación y comparación
 */

public class EjemplosColecciones {
    
    public static void main(String[] args) {
        System.out.println("=== COLECCIONES ===\n");
        
        ejemplosList();
        ejemplosSet();
        ejemplosMap();
        ejemplosOrdenacionYComparacion();
    }
    
    // List: ArrayList, LinkedList
    static void ejemplosList() {
        System.out.println("--- LIST (ArrayList, LinkedList) ---");
        
        // ArrayList
        List<String> lista = new ArrayList<>();
        lista.add("Java");
        lista.add("Python");
        lista.add("JavaScript");
        lista.add("Java");  // Permite duplicados
        
        System.out.println("ArrayList: " + lista);
        System.out.println("Tamaño: " + lista.size());
        System.out.println("¿Contiene 'Python'? " + lista.contains("Python"));
        
        // Iterar
        System.out.println("Iterando:");
        for (String linguaje : lista) {
            System.out.println("  - " + linguaje);
        }
        
        // LinkedList
        LinkedList<Integer> enlazada = new LinkedList<>();
        enlazada.add(10);
        enlazada.add(20);
        enlazada.addFirst(5);    // Agregar al inicio
        enlazada.addLast(30);    // Agregar al final
        System.out.println("\nLinkedList: " + enlazada);
        
        // Operaciones de LinkedList
        System.out.println("First: " + enlazada.getFirst());
        System.out.println("Last: " + enlazada.getLast());
        
        enlazada.removeFirst();
        enlazada.removeLast();
        System.out.println("Después de remover First y Last: " + enlazada);
    }
    
    // Set: HashSet, TreeSet, LinkedHashSet
    static void ejemplosSet() {
        System.out.println("\n--- SET (HashSet, TreeSet, LinkedHashSet) ---");
        
        // HashSet (sin orden, rápido)
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Manzana");
        hashSet.add("Plátano");
        hashSet.add("Cereza");
        hashSet.add("Manzana");  // No permite duplicados
        
        System.out.println("HashSet: " + hashSet);  // Orden no garantizado
        
        // TreeSet (ordenado, más lento)
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(50);
        treeSet.add(10);
        treeSet.add(30);
        treeSet.add(20);
        
        System.out.println("TreeSet (ordenado): " + treeSet);
        
        // LinkedHashSet (orden de inserción)
        Set<String> linkedSet = new LinkedHashSet<>();
        linkedSet.add("Primero");
        linkedSet.add("Segundo");
        linkedSet.add("Tercero");
        
        System.out.println("LinkedHashSet (orden de inserción): " + linkedSet);
        
        // Operaciones de Set
        System.out.println("\nOperaciones de Set:");
        System.out.println("¿Contiene 'Plátano'? " + linkedSet.contains("Plátano"));
        linkedSet.remove("Segundo");
        System.out.println("Después de remover: " + linkedSet);
    }
    
    // Map: HashMap, TreeMap, LinkedHashMap
    static void ejemplosMap() {
        System.out.println("\n--- MAP (HashMap, TreeMap) ---");
        
        // HashMap
        Map<String, Integer> edad = new HashMap<>();
        edad.put("Ana", 28);
        edad.put("Carlos", 35);
        edad.put("Diana", 25);
        
        System.out.println("HashMap: " + edad);
        System.out.println("Edad de Ana: " + edad.get("Ana"));
        System.out.println("¿Contiene 'Carlos'? " + edad.containsKey("Carlos"));
        
        // Iterar sobre Map
        System.out.println("Iterando Map:"); // Iterar con entryset
        for (Map.Entry<String, Integer> entrada : edad.entrySet()) {
            System.out.println("  " + entrada.getKey() + " => " + entrada.getValue());
        }
        
        // TreeMap (ordenado por clave)
        Map<String, Double> precios = new TreeMap<>();
        precios.put("Zapatos", 89.99);
        precios.put("Camisa", 29.99);
        precios.put("Pantalón", 49.99);
        
        System.out.println("\nTreeMap (ordenado): " + precios);
        
        // LinkedHashMap (orden de inserción)
        Map<Integer, String> posiciones = new LinkedHashMap<>();
        posiciones.put(1, "Primero");
        posiciones.put(2, "Segundo");
        posiciones.put(3, "Tercero");
        
        System.out.println("LinkedHashMap: " + posiciones);
        
        // Operaciones de Map
        edad.remove("Diana");
        System.out.println("\nTrass de remover 'Diana': " + edad);
        System.out.println("Todas las claves: " + edad.keySet());
        System.out.println("Todos los valores: " + edad.values());
    }
    
    // Ordenación y Comparable/Comparator
    static void ejemplosOrdenacionYComparacion() {
        System.out.println("\n--- ORDENACIÓN Y COMPARACIÓN ---");
        
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("Ana", 28));
        personas.add(new Persona("Carlos", 35));
        personas.add(new Persona("Diana", 25));
        personas.add(new Persona("Elena", 30));
        
        // Ordenar usando Comparable (compareTo)
        System.out.println("Antes de ordenar:");
        for (Persona p : personas) {
            System.out.println("  " + p);
        }
        
        Collections.sort(personas);  // Usa compareTo()
        System.out.println("\nOrdenado por edad (Comparable):");
        for (Persona p : personas) {
            System.out.println("  " + p);
        }
        
        // Ordenar usando Comparator
        System.out.println("\nOrdenado por nombre (Comparator):");
        Collections.sort(personas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return p1.nombre.compareTo(p2.nombre);
            }
        });
        
        for (Persona p : personas) {
            System.out.println("  " + p);
        }
        
        // Ordenar en orden inverso
        System.out.println("\nOrdenado por edad descendente:");
        Collections.sort(personas, Collections.reverseOrder());
        for (Persona p : personas) {
            System.out.println("  " + p);
        }
        
        // Buscar en colección ordenada
        System.out.println("\nBúsqueda binaria:");
        int indice = Collections.binarySearch(personas, nuevaPersona("Diana", 25), Collections.reverseOrder());
        System.out.println("Índice encontrado: " + indice);
    }
    
    static Persona nuevaPersona(String nombre, int edad) {
        return new Persona(nombre, edad);
    }
}

// Clase Persona que implementa Comparable
class Persona implements Comparable<Persona> {
    String nombre;
    int edad;
    
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    
    // Implementar compareTo para ordenar por edad
    @Override
    public int compareTo(Persona otra) {
        return Integer.compare(this.edad, otra.edad);
    }
    
    @Override
    public String toString() {
        return nombre + " (" + edad + " años)";
    }
}
