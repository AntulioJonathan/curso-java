package streams_lambda;

import java.util.*;
import java.util.stream.*;

/**
 * Ejemplos de Lambdas y Stream API (Java 8+)
 */

public class EjemplosStreamsLambda {
    
    public static void main(String[] args) {
        System.out.println("=== LAMBDAS Y STREAM API ===\n");
        
        ejemploExpresionesLambda();
        ejemploInterfacesFuncionales();
        ejemploReferenciasMetodos();
        ejemploStreamAPI();
        ejemploOperacionesTerminales();
        ejemploOptional();
    }
    
    // Ejemplo 1: Expresiones Lambda
    static void ejemploExpresionesLambda() {
        System.out.println("--- Expresiones Lambda ---");
        
        // Lambda sin parámetros
        Runnable r = () -> System.out.println("Ejecutándose");
        r.run();
        
        // Lambda con un parámetro
        java.util.function.Consumer<String> imprimir = 
            (String s) -> System.out.println("Mensaje: " + s);
        imprimir.accept("Hola Lambda");
        
        // Lambda con múltiples parámetros
        java.util.function.BiFunction<Integer, Integer, Integer> suma = 
            (a, b) -> a + b;
        System.out.println("Suma: " + suma.apply(10, 5));
        
        // Lambda con tipo inferido
        java.util.function.BiFunction<Double, Double, Double> multiplicar = 
            (x, y) -> x * y;
        System.out.println("Multiplicación: " + multiplicar.apply(3.0, 4.0));
    }
    
    // Ejemplo 2: Interfaces funcionales
    static void ejemploInterfacesFuncionales() {
        System.out.println("\n--- Interfaces Funcionales ---");
        
        // Predicate (condición)
        java.util.function.Predicate<Integer> esPositivo = x -> x > 0;
        System.out.println("¿10 es positivo? " + esPositivo.test(10));
        System.out.println("¿-5 es positivo? " + esPositivo.test(-5));
        
        // Function (transformación)
        java.util.function.Function<String, Integer> longitud = 
            s -> s.length();
        System.out.println("Longitud de 'Java': " + longitud.apply("Java"));
        
        // Consumer (consumidor, sin retorno)
        java.util.function.Consumer<Integer> mostrarAlCuadrado = 
            n -> System.out.println(n + "² = " + (n * n));
        mostrarAlCuadrado.accept(5);
        
        // Supplier (proveedor, sin parámetros)
        java.util.function.Supplier<String> obtenerSaludo = 
            () -> "¡Hola desde Supplier!";
        System.out.println(obtenerSaludo.get());
    }
    
    // Ejemplo 3: Referencias a métodos (::)
    static void ejemploReferenciasMetodos() {
        System.out.println("\n--- Referencias a Métodos (::) ---");
        
        List<String> palabras = Arrays.asList("Java", "Streams", "Lambda");
        
        // Referencia a método estático
        System.out.println("Convertir a mayúsculas (método estático):");
        palabras.forEach(System.out::println);
        
        // Referencia a método de instancia
        System.out.println("\nLongitud de cada palabra:");
        java.util.function.Function<String, Integer> obtenerLongitud = 
            String::length;
        palabras.stream()
               .map(obtenerLongitud)
               .forEach(System.out::println);
        
        // Referencia a constructor
        System.out.println("\nCrear Strings desde enteros:");
        java.util.function.Function<Integer, String> crearString = 
            String::valueOf;
        Arrays.asList(1, 2, 3).stream()
                             .map(crearString)
                             .forEach(System.out::println);
    }
    
    // Ejemplo 4: Stream API
    static void ejemploStreamAPI() {
        System.out.println("\n--- Stream API ---");
        
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Crear streams
        System.out.println("Números: " + numeros);
        
        // filter: mantener solo números pares
        System.out.println("\nNúmeros pares (filter):");
        numeros.stream()
               .filter(n -> n % 2 == 0)
               .forEach(System.out::print);
        System.out.println();
        
        // map: transformar (multiplicar por 2)
        System.out.println("Números multiplicados por 2 (map):");
        numeros.stream()
               .map(n -> n * 2)
               .forEach(n -> System.out.print(n + " "));
        System.out.println();
        
        // flatMap: aplanar streams anidados
        System.out.println("FlatMap (aplanar listas):");
        List<List<Integer>> listasAnidadas = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4),
            Arrays.asList(5, 6)
        );
        listasAnidadas.stream()
                     .flatMap(List::stream)
                     .forEach(n -> System.out.print(n + " "));
        System.out.println();
        
        // distinct: remover duplicados
        System.out.println("Sin duplicados (distinct):");
        Arrays.asList(1, 1, 2, 2, 3, 3).stream()
                                       .distinct()
                                       .forEach(System.out::print);
        System.out.println();
        
        // sorted: ordenar
        System.out.println("Ordenado (sorted):");
        Arrays.asList(5, 2, 8, 1, 9).stream()
                                    .sorted()
                                    .forEach(System.out::print);
        System.out.println();
        
        // limit: limitar cantidad
        System.out.println("Primeros 3 elementos (limit):");
        numeros.stream()
               .limit(3)
               .forEach(System.out::print);
        System.out.println();
        
        // skip: saltar elementos
        System.out.println("Saltando los primeros 3 (skip):");
        numeros.stream()
               .skip(3)
               .limit(3)
               .forEach(System.out::print);
        System.out.println();
    }
    
    // Ejemplo 5: Operaciones terminales
    static void ejemploOperacionesTerminales() {
        System.out.println("\n--- Operaciones Terminales ---");
        
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);
        
        // forEach: iterar
        System.out.print("forEach: ");
        numeros.stream().forEach(System.out::print);
        System.out.println();
        
        // count: contar elementos
        long cantidad = numeros.stream()
                              .filter(n -> n > 2)
                              .count();
        System.out.println("Cantidad de números > 2: " + cantidad);
        
        // collect: recopilar en colección
        System.out.println("Números pares (collect):");
        List<Integer> pares = numeros.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(Collectors.toList());
        System.out.println(pares);
        
        // reduce: reducir a un valor
        System.out.println("Suma (reduce):");
        int suma = numeros.stream()
                         .reduce(0, (a, b) -> a + b);
        System.out.println("Suma total: " + suma);
        
        // anyMatch, allMatch, noneMatch: predicados
        boolean hayPar = numeros.stream().anyMatch(n -> n % 2 == 0);
        boolean todosMayoresQueUno = numeros.stream().allMatch(n -> n > 1);
        boolean ningunoCero = numeros.stream().noneMatch(n -> n == 0);
        
        System.out.println("¿Hay algún par? " + hayPar);
        System.out.println("¿Todos > 1? " + todosMayoresQueUno);
        System.out.println("¿Ninguno es 0? " + ningunoCero);
        
        // min, max, findFirst
        System.out.println("Mínimo: " + numeros.stream().min(Integer::compare).get());
        System.out.println("Máximo: " + numeros.stream().max(Integer::compare).get());
        System.out.println("Primer elemento: " + numeros.stream().findFirst().get());
    }
    
    // Ejemplo 6: Optional
    static void ejemploOptional() {
        System.out.println("\n--- Optional ---");
        
        List<String> nombres = Arrays.asList("Ana", "Carlos", "Diana");
        
        // Optional.of y isPresent
        Optional<String> primero = nombres.stream().findFirst();
        if (primero.isPresent()) {
            System.out.println("Primer nombre: " + primero.get());
        }
        
        // Optional.orElse
        String nombre = nombres.stream()
                              .filter(n -> n.startsWith("Z"))
                              .findFirst()
                              .orElse("No encontrado");
        System.out.println("Nombre que comienza con Z: " + nombre);
        
        // Optional.ifPresent
        System.out.println("Procesando con ifPresent:");
        nombres.stream()
               .filter(n -> n.length() > 4)
               .findFirst()
               .ifPresent(n -> System.out.println("  Nombre largo: " + n));
        
        // Optional.map
        Optional<Integer> longitud = nombres.stream()
                                           .findFirst()
                                           .map(String::length);
        System.out.println("Longitud del primer nombre: " + longitud.get());
    }
}
