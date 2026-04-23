package poo;

/**
 * Ejemplos de Clases y Objetos
 * - Atributos, métodos, constructores
 * - Palabra this
 */
public class Persona {
    // Atributos
    private String nombre;
    private int edad;
    private double altura;
    
    // Constructor sin parámetros
    public Persona() {
        this.nombre = "Sin nombre";
        this.edad = 0;
        this.altura = 0.0;
    }
    
    // Constructor con parámetros
    public Persona(String nombre, int edad, double altura) {
        this.nombre = nombre;
        this.edad = edad;
        this.altura = altura;
    }
    
    // Métodos (getters y setters)
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        if (edad >= 0 && edad <= 150) {
            this.edad = edad;
        }
    }
    
    public double getAltura() {
        return altura;
    }
    
    public void setAltura(double altura) {
        this.altura = altura;
    }
    
    // Método que usa this para acceder a atributos
    public void apresentarse() {
        System.out.println("Hola, me llamo " + this.nombre + 
                         " y tengo " + this.edad + " años");
    }
    
    // Método que retorna información
    public String obtenerInfo() {
        return "Nombre: " + nombre + ", Edad: " + edad + ", Altura: " + altura + "m";
    }
    
    // Método sobrescrito de Object
    @Override
    public String toString() {
        return "Persona{" +
               "nombre='" + nombre + '\'' +
               ", edad=" + edad +
               ", altura=" + altura +
               '}';
    }
    
    public static void main(String[] args) {
        System.out.println("=== CLASES Y OBJETOS ===\n");
        
        // Crear objetos
        Persona p1 = new Persona();
        System.out.println("Persona 1: " + p1);
        
        Persona p2 = new Persona("Ana García", 28, 1.70);
        System.out.println("Persona 2: " + p2);
        
        // Usar métodos
        p2.apresentarse();
        System.out.println(p2.obtenerInfo());
        
        // Usar getters y setters
        p1.setNombre("Juan");
        p1.setEdad(35);
        p1.setAltura(1.80);
        System.out.println("\nPersona 1 después de cambios: " + p1);
    }
}
