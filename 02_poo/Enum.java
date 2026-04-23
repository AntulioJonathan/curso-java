package poo;

/**
 * Ejemplos de Enumerados (Enums)
 */

// Enum simple
enum DiaSemana {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;
    
    // Método en enum
    public boolean esFinDeSemana() {
        return this == SABADO || this == DOMINGO;
    }
}

// Enum con atributos y constructor
enum Estado {
    ACTIVO("A", "Estado activo"),
    INACTIVO("I", "Estado inactivo"),
    SUSPENDIDO("S", "Estado suspendido"),
    ELIMINADO("E", "Estado eliminado");
    
    private final String codigo;
    private final String descripcion;
    
    // Constructor (implícitamente private)
    Estado(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}

// Enum con métodos abstractos
enum Operacion {
    SUMA("+") {
        @Override
        public double ejecutar(double a, double b) {
            return a + b;
        }
    },
    RESTA("-") {
        @Override
        public double ejecutar(double a, double b) {
            return a - b;
        }
    },
    MULTIPLICACION("*") {
        @Override
        public double ejecutar(double a, double b) {
            return a * b;
        }
    },
    DIVISION("/") {
        @Override
        public double ejecutar(double a, double b) {
            if (b == 0) {
                throw new ArithmeticException("División por cero");
            }
            return a / b;
        }
    };
    
    private final String simbolo;
    
    Operacion(String simbolo) {
        this.simbolo = simbolo;
    }
    
    public String getSimbolo() {
        return simbolo;
    }
    
    // Método abstracto que deben implementar todas las constantes
    public abstract double ejecutar(double a, double b);
}

// Nivel de acceso en una aplicación
enum NivelAcceso {
    USUARIO(1),
    MODERADOR(2),
    ADMINISTRADOR(3);
    
    private final int nivel;
    
    NivelAcceso(int nivel) {
        this.nivel = nivel;
    }
    
    public int getNivel() {
        return nivel;
    }
    
    public boolean tieneAcceso(NivelAcceso requerido) {
        return this.nivel >= requerido.nivel;
    }
}

class TestEnums {
    public static void main(String[] args) {
        System.out.println("=== ENUMERADOS (ENUMS) ===\n");
        
        // Enum simple
        System.out.println("--- Enum Simple ---");
        DiaSemana hoy = DiaSemana.VIERNES;
        System.out.println("Hoy es: " + hoy);
        System.out.println("¿Es fin de semana? " + hoy.esFinDeSemana());
        
        System.out.println("Sábado es fin de semana? " + DiaSemana.SABADO.esFinDeSemana());
        
        // Iterar sobre enum
        System.out.println("\n--- Iterar Enum ---");
        for (DiaSemana dia : DiaSemana.values()) {
            System.out.println(dia + ": " + (dia.esFinDeSemana() ? "Fin de semana" : "Día laboral"));
        }
        
        // Enum con atributos
        System.out.println("\n--- Enum con Atributos ---");
        Estado miEstado = Estado.ACTIVO;
        System.out.println("Estado: " + miEstado);
        System.out.println("Código: " + miEstado.getCodigo());
        System.out.println("Descripción: " + miEstado.getDescripcion());
        
        System.out.println("\nTodos los estados:");
        for (Estado est : Estado.values()) {
            System.out.println("  " + est + " (" + est.getCodigo() + ") - " + est.getDescripcion());
        }
        
        // Obtener enum por nombre
        System.out.println("\n--- Obtener Enum por Nombre ---");
        Estado estado = Estado.valueOf("SUSPENDIDO");
        System.out.println("Estado encontrado: " + estado);
        
        // Enum con métodos abstractos
        System.out.println("\n--- Enum con Métodos Abstractos ---");
        Operacion op = Operacion.SUMA;
        double resultado = op.ejecutar(10, 5);
        System.out.println("10 " + op.getSimbolo() + " 5 = " + resultado);
        
        System.out.println("10 " + Operacion.MULTIPLICACION.getSimbolo() + 
                         " 5 = " + Operacion.MULTIPLICACION.ejecutar(10, 5));
        
        // Nivel de acceso
        System.out.println("\n--- Control de Acceso ---");
        NivelAcceso miNivel = NivelAcceso.MODERADOR;
        NivelAcceso requerido = NivelAcceso.ADMINISTRADOR;
        
        System.out.println("Mi nivel: " + miNivel);
        System.out.println("Puede acceder a ADMINISTRADOR? " + miNivel.tieneAcceso(requerido));
        System.out.println("Puede acceder a USUARIO? " + miNivel.tieneAcceso(NivelAcceso.USUARIO));
        
        // Comparación de enums
        System.out.println("\n--- Comparación de Enums ---");
        System.out.println("ACTIVO == ACTIVO? " + (Estado.ACTIVO == Estado.ACTIVO));
        System.out.println("ACTIVO == INACTIVO? " + (Estado.ACTIVO == Estado.INACTIVO));
    }
}
