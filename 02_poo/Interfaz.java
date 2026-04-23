package poo;

/**
 * Ejemplos de Interfaces y Polimorfismo
 */

// Interfaz 1: Volador
interface Volador {
    void volar();
    int getMétrosAltura();
}

// Interfaz 2: Nadador
interface Nadador {
    void nadar();
    int getProfundidad();
}

// Interfaz 3: Corredor
interface Corredor {
    void correr(int velocidad);
}

// Clase que implementa una interfaz
class Pajaro implements Volador {
    private String especie;
    private int metrosAltura;
    
    public Pajaro(String especie) {
        this.especie = especie;
        this.metrosAltura = 0;
    }
    
    @Override
    public void volar() {
        System.out.println(especie + " está volando a " + metrosAltura + " metros");
    }
    
    @Override
    public int getMétrosAltura() {
        return metrosAltura;
    }
    
    public void subirAltura(int metros) {
        this.metrosAltura += metros;
    }
}

// Clase que implementa múltiples interfaces
class Pato implements Volador, Nadador, Corredor {
    private String nombre;
    private int alturaVuelo;
    private int profundidadAgua;
    
    public Pato(String nombre) {
        this.nombre = nombre;
        this.alturaVuelo = 0;
        this.profundidadAgua = 0;
    }
    
    @Override
    public void volar() {
        System.out.println(nombre + " está volando");
    }
    
    @Override
    public int getMétrosAltura() {
        return alturaVuelo;
    }
    
    @Override
    public void nadar() {
        System.out.println(nombre + " está nadando");
    }
    
    @Override
    public int getProfundidad() {
        return profundidadAgua;
    }
    
    @Override
    public void correr(int velocidad) {
        System.out.println(nombre + " está corriendo a " + velocidad + " km/h");
    }
}

// Interfaz con métodos default (Java 8+)
interface Vehículo {
    void acelerar();
    void frenar();
    
    // Método default (tiene implementación en la interfaz)
    default void encender() {
        System.out.println("Vehículo encendido");
    }
    
    // Método static en interfaz
    static String getInfo() {
        return "Esto es un vehículo";
    }
}

class Auto implements Vehículo {
    @Override
    public void acelerar() {
        System.out.println("Auto está acelerando");
    }
    
    @Override
    public void frenar() {
        System.out.println("Auto está frenando");
    }
}

class TestInterfaces {
    public static void main(String[] args) {
        System.out.println("=== INTERFACES Y POLIMORFISMO ===\n");
        
        // Usando interfaz Volador
        System.out.println("--- Interfaz Volador ---");
        Pajaro aguila = new Pajaro("Águila");
        aguila.subirAltura(100);
        aguila.volar();
        
        // Usando múltiples interfaces
        System.out.println("\n--- Múltiples Interfaces ---");
        Pato pato = new Pato("Donald");
        pato.volar();
        pato.nadar();
        pato.correr(15);
        
        // Polimorfismo con interfaces
        System.out.println("\n--- Polimorfismo ---");
        Volador[] voladores = {aguila, pato};
        for (Volador v : voladores) {
            v.volar();
        }
        
        // Métodos default en interfaces (Java 8+)
        System.out.println("\n--- Métodos Default en Interfaces ---");
        Auto miAuto = new Auto();
        miAuto.encender();  // Método heredado de la interfaz Vehículo
        miAuto.acelerar();
        miAuto.frenar();
        
        // Método static en interfaz
        System.out.println(Vehículo.getInfo());
    }
}
