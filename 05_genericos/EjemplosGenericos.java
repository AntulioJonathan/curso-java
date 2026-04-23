package genericos;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplos de Genéricos en Java
 * - Clases genéricas
 * - Métodos genéricos
 * - Comodines (wildcards)
 */

// Clase genérica
class Contenedor<T> {
    private T valor;
    
    public Contenedor(T valor) {
        this.valor = valor;
    }
    
    public T obtener() {
        return valor;
    }
    
    public void establecer(T valor) {
        this.valor = valor;
    }
    
    @Override
    public String toString() {
        return "Contenedor{" + valor + "}";
    }
}

// Clase genérica con dos parámetros de tipo
class Par<K, V> {
    private K clave;
    private V valor;
    
    public Par(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }
    
    public K getClave() {
        return clave;
    }
    
    public V getValor() {
        return valor;
    }
    
    @Override
    public String toString() {
        return clave + " => " + valor;
    }
}

// Interfaz genérica
interface Repositorio<T> {
    void agregar(T item);
    T obtener(int indice);
    List<T> obtenerTodos();
}

// Implementación de interfaz genérica
class RepositorioLista<T> implements Repositorio<T> {
    private List<T> elementos = new ArrayList<>();
    
    @Override
    public void agregar(T item) {
        elementos.add(item);
    }
    
    @Override
    public T obtener(int indice) {
        return elementos.get(indice);
    }
    
    @Override
    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }
}

public class EjemplosGenericos {
    
    public static void main(String[] args) {
        System.out.println("=== GENÉRICOS ===\n");
        
        ejemploClaseGenerica();
        ejemploClaseMultipleParametros();
        ejemploMetodoGenerico();
        ejemploWildcards();
        ejemploRepositorio();
    }
    
    // Ejemplo 1: Clase genérica
    static void ejemploClaseGenerica() {
        System.out.println("--- Clase Genérica Básica ---");
        
        // Con String
        Contenedor<String> contenedorString = new Contenedor<>("Hola Java");
        System.out.println(contenedorString);
        System.out.println("Valor: " + contenedorString.obtener());
        
        // Con Integer
        Contenedor<Integer> contenedorInt = new Contenedor<>(42);
        System.out.println(contenedorInt);
        System.out.println("Valor: " + contenedorInt.obtener());
        
        // Con Double
        Contenedor<Double> contenedorDouble = new Contenedor<>(3.14159);
        System.out.println(contenedorDouble);
        
        // Con ArrayList
        Contenedor<ArrayList<String>> contenedorLista = 
            new Contenedor<>(new ArrayList<>());
        contenedorLista.obtener().add("Elemento 1");
        System.out.println("Lista: " + contenedorLista.obtener());
    }
    
    // Ejemplo 2: Múltiples parámetros de tipo
    static void ejemploClaseMultipleParametros() {
        System.out.println("\n--- Múltiples Parámetros de Tipo ---");
        
        Par<String, Integer> usuarioEdad = 
            new Par<>("Ana", 28);
        System.out.println("Usuario-Edad: " + usuarioEdad);
        
        Par<String, Double> productoPrecio = 
            new Par<>("Laptop", 899.99);
        System.out.println("Producto-Precio: " + productoPrecio);
        
        Par<Integer, String> idNombre = 
            new Par<>(101, "Carlos");
        System.out.println("ID-Nombre: " + idNombre);
    }
    
    // Ejemplo 3: Métodos genéricos
    static void ejemploMetodoGenerico() {
        System.out.println("\n--- Métodos Genéricos ---");
        
        // Método que devuelve el elemento más frecuente
        Integer[] numeros = {1, 2, 2, 3, 3, 3, 4};
        System.out.println("Array de números: " + java.util.Arrays.toString(numeros));
        imprimirArray(numeros);
        
        String[] palabras = {"Java", "Python", "Java", "C++"};
        System.out.println("\nArray de palabras: " + java.util.Arrays.toString(palabras));
        imprimirArray(palabras);
        
        // Método de intercambio
        System.out.println("\n--- Intercambio Genérico ---");
        Integer[] nums = {1, 2, 3};
        intercambiar(nums, 0, 2);
        System.out.println("Después de intercambiar índices 0 y 2: " + 
                         java.util.Arrays.toString(nums));
    }
    
    // Método genérico
    static <T> void imprimirArray(T[] array) {
        System.out.println("Array de " + array[0].getClass().getSimpleName() + ":");
        for (T elemento : array) {
            System.out.println("  - " + elemento);
        }
    }
    
    // Método genérico para intercambiar
    static <T> void intercambiar(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    // Ejemplo 4: Wildcard (?) y restricciones
    static void ejemploWildcards() {
        System.out.println("\n--- Wildcards (?) ---");
        
        List<Integer> enteros = new ArrayList<>();
        enteros.add(1);
        enteros.add(2);
        enteros.add(3);
        
        // Wildcard sin restricción: ? (puede ser cualquier tipo)
        System.out.println("Lista: " + enteros);
        imprimirLista(enteros);
        
        List<String> strings = new ArrayList<>();
        strings.add("A");
        strings.add("B");
        imprimirLista(strings);
        
        // Wildcard con límite superior: ? extends T
        System.out.println("\n--- Wildcard con 'extends' (límite superior) ---");
        sumarNumeros(enteros);  // Funciona con Integer, Double, Number
        
        // Wildcard con límite inferior: ? super T
        System.out.println("\n--- Wildcard con 'super' (límite inferior) ---");
        List<Number> numeros = new ArrayList<>();
        numeros.add(1);
        numeros.add(2.5);
        numeros.add(3L);
        agregarNumeros(numeros, enteros);
    }
    
    // Método que acepta cualquier tipo de lista
    static void imprimirLista(List<?> lista) {
        System.out.println("  Contenido: ");
        for (Object item : lista) {
            System.out.println("    - " + item);
        }
    }
    
    // Método que acepta Number y sus subclases
    static void sumarNumeros(List<? extends Number> numeros) {
        double suma = 0;
        for (Number n : numeros) {
            suma += n.doubleValue();
        }
        System.out.println("Suma: " + suma);
    }
    
    // Método que acepta Number y sus superclases
    static void agregarNumeros(List<? super Integer> lista, List<Integer> origen) {
        for (Integer num : origen) {
            lista.add(num);
        }
        System.out.println("Números agregados: " + lista);
    }
    
    // Ejemplo 5: Repositorio genérico
    static void ejemploRepositorio() {
        System.out.println("\n--- Repositorio Genérico ---");
        
        // Repositorio de Strings
        Repositorio<String> repoCiudades = new RepositorioLista<>();
        repoCiudades.agregar("Madrid");
        repoCiudades.agregar("Barcelona");
        repoCiudades.agregar("Valencia");
        System.out.println("Ciudades: " + repoCiudades.obtenerTodos());
        
        // Repositorio de Integers
        Repositorio<Integer> repoNumeros = new RepositorioLista<>();
        repoNumeros.agregar(10);
        repoNumeros.agregar(20);
        repoNumeros.agregar(30);
        System.out.println("Números: " + repoNumeros.obtenerTodos());
    }
}
