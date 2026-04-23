package poo;

/**
 * Ejemplos de Herencia
 */

// Clase base
public abstract class Animal {
    protected String nombre;
    protected int edad;
    
    public Animal(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    
    // Método abstracto que debe ser implementado por las subclases
    public abstract void hacerSonido();
    
    // Método concreto
    public void info() {
        System.out.println("Nombre: " + nombre + ", Edad: " + edad + " años");
    }
    
    @Override
    public String toString() {
        return "Animal{nombre='" + nombre + "', edad=" + edad + "}";
    }
}

// Subclase: Perro
class Perro extends Animal {
    private String raza;
    
    public Perro(String nombre, int edad, String raza) {
        super(nombre, edad);  // Llamar al constructor de la clase padre
        this.raza = raza;
    }
    
    @Override
    public void hacerSonido() {
        System.out.println(nombre + " ladra: ¡Guau guau!");
    }
    
    public String getRaza() {
        return raza;
    }
    
    @Override
    public String toString() {
        return "Perro{nombre='" + nombre + "', edad=" + edad + ", raza='" + raza + "'}";
    }
}

// Subclase: Gato
class Gato extends Animal {
    private boolean esDelIndoor;
    
    public Gato(String nombre, int edad, boolean esDelIndoor) {
        super(nombre, edad);
        this.esDelIndoor = esDelIndoor;
    }
    
    @Override
    public void hacerSonido() {
        System.out.println(nombre + " maúlla: ¡Miau!");
    }
    
    public boolean isDelIndoor() {
        return esDelIndoor;
    }
    
    @Override
    public String toString() {
        return "Gato{nombre='" + nombre + "', edad=" + edad + ", indoor=" + esDelIndoor + "}";
    }
}

// Programa principal
class TestHerencia {
    public static void main(String[] args) {
        System.out.println("=== HERENCIA ===\n");
        
        // No se puede instanciar directamente una clase abstracta
        // Animal a = new Animal("Test", 5);  // Error
        
        // Crear instancias de las subclases
        Perro perro = new Perro("Rex", 5, "Dálmata");
        Gato gato = new Gato("Whiskers", 3, true);
        
        System.out.println("--- Información ---");
        perro.info();
        perro.hacerSonido();
        System.out.println(perro);
        
        System.out.println();
        
        gato.info();
        gato.hacerSonido();
        System.out.println(gato);
        
        // Polimorfismo: usar referencia de la clase padre
        System.out.println("\n--- Polimorfismo (Upcasting) ---");
        Animal[] animales = {perro, gato};
        
        for (Animal animal : animales) {
            animal.hacerSonido();
            animal.info();
            System.out.println();
        }
        
        // Downcasting
        System.out.println("--- Downcasting ---");
        if (animales[0] instanceof Perro) {
            Perro p = (Perro) animales[0];
            System.out.println("Es un perro de raza: " + p.getRaza());
        }
    }
}
