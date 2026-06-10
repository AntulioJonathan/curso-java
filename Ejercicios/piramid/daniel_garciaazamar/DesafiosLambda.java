package streams_lambda;

import java.io.IOException;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class DesafiosLambda {

    public static void main(String[] args) throws IOException {
        System.out.println("=== DESAFIOS STREAMS Y LAMBDAS ===");

        desafio1();
        desafio2();
        desafio3();
        desafio4();
        desafio5();
        desafio6();
        desafio7();
        desafio8();
        desafio9();
        desafio10();
    }

    static void desafio1() {
        System.out.println("--- Desafío 1 ---");

        List<Persona> personas = Arrays.asList(
            new Persona("Ana", 22, "Madrid", 35000, Arrays.asList("leer", "correr")),
            new Persona("Luis", 19, "Barcelona", 42000, Arrays.asList("futbol", "musica")),
            new Persona("Carlos", 25, "Madrid", 31000, Arrays.asList("cine", "viajar")),
            new Persona("Marta", 21, "Valencia", 50000, Arrays.asList("pintar", "leer")),
            new Persona("Sofia", 20, "Barcelona", 28000, Arrays.asList("bailar", "series")),
            new Persona("Pedro", 23, "Madrid", 39000, Arrays.asList("gym", "juegos")),
            new Persona("Laura", 18, "Barcelona", 33000, Arrays.asList("natacion", "arte"))
        );

        Map<String, List<String>> resultado = personas.stream()
            .filter(p -> p.getCiudad().equals("Madrid") || p.getCiudad().equals("Barcelona"))
            .filter(p -> p.getSalario() > 30000)
            .sorted(Comparator.comparingInt(Persona::getEdad))
            .skip(0)
            .limit(3)
            .sorted(Comparator.comparingDouble(Persona::getSalario).reversed())
            .map(p -> new Persona(
                p.getNombre(),
                p.getEdad(),
                p.getCiudad(),
                p.getSalario(),
                p.getHobbies().stream()
                    .map(String::toUpperCase)
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toMap(
                Persona::getNombre,
                Persona::getHobbies,
                (a, b) -> a,
                LinkedHashMap::new
            ));

        System.out.println(resultado);
        System.out.println();
    }

    static void desafio2() {
        System.out.println("--- Desafío 2 ---");

        Random random = new Random();

        int[] datos = random.ints(1, 101)
            .limit(10000)
            .filter(n -> n >= 1 && n <= 100)
            .collect(
                () -> new int[4],
                (a, n) -> {
                    if (n % 2 == 0) {
                        a[0] += n;
                        a[1]++;
                    }

                    if (n % 7 == 0 && n > a[2]) {
                        a[2] = n;
                    }

                    if (esPrimo(n)) {
                        a[3]++;
                    }
                },
                (a, b) -> {
                    a[0] += b[0];
                    a[1] += b[1];
                    a[2] = Math.max(a[2], b[2]);
                    a[3] += b[3];
                }
            );

        double promedioPares = datos[1] == 0 ? 0 : (double) datos[0] / datos[1];

        System.out.println("Promedio de números pares: " + promedioPares);
        System.out.println("Número más alto múltiplo de 7: " + datos[2]);
        System.out.println("Cantidad de números primos: " + datos[3]);
        System.out.println();
    }

    static void desafio3() {
        System.out.println("--- Desafío 3 ---");

        List<Empresa> empresas = Arrays.asList(
            new Empresa("TechSoft", Arrays.asList(
                new Departamento("Sistemas", Arrays.asList(
                    new Empleado("Ana", 20000),
                    new Empleado("Luis", 30000),
                    new Empleado("Carlos", 50000)
                )),
                new Departamento("Ventas", Arrays.asList(
                    new Empleado("Marta", 18000),
                    new Empleado("Pedro", 25000),
                    new Empleado("Sofia", 40000)
                ))
            )),

            new Empresa("DataCorp", Arrays.asList(
                new Departamento("Soporte", Arrays.asList(
                    new Empleado("Raul", 15000),
                    new Empleado("Laura", 22000),
                    new Empleado("Diana", 35000)
                )),
                new Departamento("Marketing", Arrays.asList(
                    new Empleado("Mario", 17000),
                    new Empleado("Elena", 21000),
                    new Empleado("Pablo", 28000)
                ))
            ))
        );

        Map<String, Double> promedioPorEmpresa = empresas.stream()
            .flatMap(empresa -> empresa.getDepartamentos().stream()
                .flatMap(departamento -> {
                    Empleado mejorPagado = departamento.getEmpleados().stream()
                        .collect(Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparingDouble(Empleado::getSalario)),
                            empleado -> empleado.orElse(null)
                        ));

                    return departamento.getEmpleados().stream()
                        .filter(empleado -> empleado != mejorPagado)
                        .map(empleado -> new Registro(
                            empresa.getNombre(),
                            empleado.getSalario()
                        ));
                })
            )
            .collect(Collectors.groupingBy(
                Registro::getEmpresa,
                Collectors.averagingDouble(Registro::getSalario)
            ));

        System.out.println(promedioPorEmpresa);
        System.out.println();
    }

    static void desafio4() {
        System.out.println("--- Desafío 4 ---");

        List<String> frases = Arrays.asList(
            "El delantero definio con mucha calma frente al portero",
            "El equipo presiono alto durante todo el primer tiempo",
            "El gimnasio ayuda a mejorar fuerza resistencia y disciplina diaria",
            "La rutina de pierna fue pesada pero muy efectiva",
            "El portero salvo un penal en los ultimos minutos",
            "Entrenar pecho espalda y brazo requiere buena tecnica",
            "El mediocampista dio un pase perfecto para iniciar el contraataque",
            "Hacer cardio despues de pesas ayuda a mejorar la condicion fisica",
            "El entrenador cambio la estrategia para dominar mejor el partido",
            "La sentadilla es uno de los ejercicios mas completos del gimnasio",
            "El equipo gano la final con un golazo desde fuera del area",
            "Dormir bien y comer suficiente proteina mejora el crecimiento muscular"
        );

        Map<Boolean, Map<Character, Long>> resultado = frases.parallelStream()
            .collect(Collectors.partitioningBy(
                frase -> contarPalabras(frase) > 10,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    lista -> lista.stream()
                        .sorted(Comparator.comparingInt(String::length))
                        .limit(5)
                        .map(DesafiosLambda::frecuenciaLetras)
                        .flatMap(mapa -> mapa.entrySet().stream())
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            Long::sum,
                            TreeMap::new
                        ))
                )
            ));

        System.out.println(resultado);
        System.out.println();
    }

    static void desafio5() {
        System.out.println("--- Desafío 5 ---");

        List<String> datos = Arrays.asList(
            "T1001,250.50,DEP",
            "T1002,0,WD",
            "T1003,700.00,DEP",
            "T1004,50.00,DEP",
            "T1005,-20.00,DEP",
            "T1006,120.00,WD",
            "T1007,90.00,DEP",
            "T1008,800.00,DEP"
        );

        Map<String, Double> resultado = datos.stream()
            .flatMap(linea -> convertirSeguro(linea)
                .map(Stream::of)
                .orElseGet(Stream::empty)
            )
            .filter(t -> t.getTipo().equals("DEP"))
            .collect(Collectors.groupingBy(
                t -> obtenerRango(t.getMonto()),
                Collectors.summingDouble(Transaction::getMonto)
            ));

        System.out.println(resultado);
        System.out.println();
    }

    static void desafio6() {
        System.out.println("--- Desafío 6 ---");

        Random random = new Random();

        ResultadoEstadistico resultado = random.doubles(1_000_000, 1, 101)
            .boxed()
            .collect(collectorMedianaModa());

        System.out.println("Mediana: " + resultado.getMediana());
        System.out.println("Moda: " + resultado.getModa());
        System.out.println();
    }

    static void desafio7() throws IOException {
        System.out.println("--- Desafío 7 ---");

        Path ruta = Paths.get("logs.txt");

        if (!Files.exists(ruta)) {
            crearArchivoEjemplo(ruta);
        }

        try (Stream<String> lineas = Files.lines(ruta)) {
            lineas
                .map(EventoLog::convertir)
                .collect(Collector.of(
                    VentanaUsuarios::new,
                    VentanaUsuarios::procesar,
                    VentanaUsuarios::combinar
                ));
        }

        System.out.println();
    }

    static void desafio8() {
        System.out.println("--- Desafío 8 ---");

        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5);

        List<int[]> resultado = IntStream.range(0, numeros.size())
            .boxed()
            .flatMap(i -> IntStream.range(i + 1, numeros.size())
                .boxed()
                .flatMap(j -> IntStream.range(j + 1, numeros.size())
                    .mapToObj(k -> new int[] {
                        numeros.get(i),
                        numeros.get(j),
                        numeros.get(k)
                    })
                )
            )
            .filter(combinacion -> calcularProducto(combinacion) > 10)
            .filter(combinacion -> calcularSuma(combinacion) % 2 == 0)
            .collect(Collectors.toList());

        resultado.forEach(combinacion ->
            System.out.println(
                Arrays.toString(combinacion) +
                " | Suma: " + calcularSuma(combinacion) +
                " | Producto: " + calcularProducto(combinacion) +
                " | Suma es primo: " + esPrimo(calcularSuma(combinacion))
            )
        );

        System.out.println();
    }

    static void desafio9() {
        System.out.println("--- Desafío 9 ---");

        CacheSimple cache = new CacheSimple();

        cache.insertar("usuario1", 3000);
        cache.insertar("usuario2", 5000);
        cache.insertar("usuario3", 7000);

        Optional<Map.Entry<String, Long>> resultado = cache.obtenerMasRecienteNoExpirado();

        resultado.ifPresentOrElse(
            entrada -> System.out.println("Clave más reciente no expirada: " + entrada.getKey()),
            () -> System.out.println("No hay entradas válidas en el cache")
        );

        System.out.println();
    }

    static void desafio10() {
        System.out.println("--- Desafío 10 ---");

        List<Venta> ventas = Arrays.asList(
            new Venta("Laptop", 2, 15000, LocalDate.of(2026, 1, 10)),
            new Venta("Laptop", 1, 16000, LocalDate.of(2026, 11, 5)),
            new Venta("Laptop", 3, 14000, LocalDate.of(2026, 12, 15)),
            new Venta("Mouse", 10, 250, LocalDate.of(2026, 2, 20)),
            new Venta("Mouse", 20, 300, LocalDate.of(2026, 10, 8)),
            new Venta("Teclado", 5, 800, LocalDate.of(2026, 3, 12)),
            new Venta("Teclado", 8, 900, LocalDate.of(2026, 11, 22))
        );

        Map<String, Resumen> resultado = ventas.stream()
            .collect(Collectors.toMap(
                Venta::getProducto,
                Resumen::desdeVenta,
                Resumen::combinar,
                LinkedHashMap::new
            ));

        resultado.forEach((producto, resumen) -> {
            System.out.println("Producto: " + producto);
            System.out.println("Ingreso total: " + resumen.getIngresoTotal());
            System.out.println("Mes con mayor ingreso: " + resumen.getMesMayorIngreso());
            System.out.println("Tendencia: " + resumen.getTendencia());
            System.out.println();
        });
    }
    // Método auxiliar del Desafío 2 y 8
    static boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }

        return IntStream.rangeClosed(2, (int) Math.sqrt(n))
            .noneMatch(i -> n % i == 0);
    }
    // Método auxiliar del Desafío 4
    static long contarPalabras(String frase) {
        return Arrays.stream(frase.split(" "))
            .filter(palabra -> !palabra.isEmpty())
            .count();
    }
    // Método auxiliar del Desafío 4
    static Map<Character, Long> frecuenciaLetras(String frase) {
        return frase.toLowerCase()
            .chars()
            .filter(c -> c != ' ')
            .mapToObj(c -> (char) c)
            .collect(
                HashMap::new,
                (mapa, letra) -> mapa.put(letra, mapa.getOrDefault(letra, 0L) + 1),
                (mapa1, mapa2) -> mapa2.forEach(
                    (letra, cantidad) -> mapa1.merge(letra, cantidad, Long::sum)
                )
            );
    }
    // Método auxiliar del Desafío 5
    static Optional<Transaction> convertirSeguro(String linea) {
        try {
            return Optional.of(convertir(linea));
        } catch (MontoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }
    // Método auxiliar del Desafío 5
    static Transaction convertir(String linea) throws MontoInvalidoException {
        String[] partes = linea.split(",");

        String id = partes[0];
        double monto = Double.parseDouble(partes[1]);
        String tipo = partes[2];

        if (monto <= 0) {
            throw new MontoInvalidoException("Monto invalido en " + id + ": " + monto);
        }

        return new Transaction(id, monto, tipo);
    }
    // Método auxiliar del Desafío 5
    static String obtenerRango(double monto) {
        if (monto < 100) {
            return "Menor a 100";
        } else if (monto <= 500) {
            return "Entre 100 y 500";
        } else {
            return "Mayor a 500";
        }
    }
    // Método auxiliar del Desafío 6
    static Collector<Double, AcumuladorEstadistico, ResultadoEstadistico> collectorMedianaModa() {
        return Collector.of(
            AcumuladorEstadistico::new,
            AcumuladorEstadistico::agregar,
            AcumuladorEstadistico::combinar,
            AcumuladorEstadistico::calcularResultado
        );
    }
    // Método Auxiliar del Desafío 8

    static int calcularSuma(int[] combinacion) {
        return Arrays.stream(combinacion)
            .sum();
    }
    // Método Auxiliar del Desafío 8

    static int calcularProducto(int[] combinacion) {
        return Arrays.stream(combinacion)
            .reduce(1, (a, b) -> a * b);
    }

    // Método auxiliar del Desafío 7

    static void crearArchivoEjemplo(Path ruta) throws IOException {
        List<String> logs = Arrays.asList(
            "2026-04-23T10:00:00,ana,LOGIN",
            "2026-04-23T10:01:00,ana,CLICK",
            "2026-04-23T10:02:00,ana,CLICK",
            "2026-04-23T10:03:00,carlos,LOGIN",
            "2026-04-23T10:04:00,ana,BUY"
        );

        Files.write(ruta, logs);
    }
}

// Clases del Desafío 1
class Persona {

    private String nombre;
    private int edad;
    private String ciudad;
    private double salario;
    private List<String> hobbies;

    public Persona(String nombre, int edad, String ciudad, double salario, List<String> hobbies) {
        this.nombre = nombre;
        this.edad = edad;
        this.ciudad = ciudad;
        this.salario = salario;
        this.hobbies = hobbies;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public double getSalario() {
        return salario;
    }

    public List<String> getHobbies() {
        return hobbies;
    }
}

// Clases del Desafío 3
class Empresa {

    private String nombre;
    private List<Departamento> departamentos;

    public Empresa(String nombre, List<Departamento> departamentos) {
        this.nombre = nombre;
        this.departamentos = departamentos;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }
}

// Clases del Desafío 3
class Departamento {

    private String nombre;
    private List<Empleado> empleados;

    public Departamento(String nombre, List<Empleado> empleados) {
        this.nombre = nombre;
        this.empleados = empleados;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }
}

// Clases del Desafío 3
class Empleado {

    private String nombre;
    private double salario;

    public Empleado(String nombre, double salario) {
        this.nombre = nombre;
        this.salario = salario;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalario() {
        return salario;
    }
}

// Clases del Desafío 3
class Registro {

    private String empresa;
    private double salario;

    public Registro(String empresa, double salario) {
        this.empresa = empresa;
        this.salario = salario;
    }

    public String getEmpresa() {
        return empresa;
    }

    public double getSalario() {
        return salario;
    }
}

// Clases del Desafío 5
class Transaction {

    private String id;
    private double monto;
    private String tipo;

    public Transaction(String id, double monto, String tipo) {
        this.id = id;
        this.monto = monto;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipo() {
        return tipo;
    }
}

// Clases del Desafío 5
class MontoInvalidoException extends Exception {

    public MontoInvalidoException(String mensaje) {
        super(mensaje);
    }
}

// Clases del Desafío 6
class ResultadoEstadistico {

    private double mediana;
    private double moda;

    public ResultadoEstadistico(double mediana, double moda) {
        this.mediana = mediana;
        this.moda = moda;
    }

    public double getMediana() {
        return mediana;
    }

    public double getModa() {
        return moda;
    }
}

// Clases del Desafío 6
class AcumuladorEstadistico {

    private List<Double> valores;
    private Map<Double, Long> frecuencias;

    public AcumuladorEstadistico() {
        this.valores = new ArrayList<>();
        this.frecuencias = new HashMap<>();
    }

    public void agregar(Double valor) {
        double valorRedondeado = Math.round(valor);

        valores.add(valorRedondeado);
        frecuencias.merge(valorRedondeado, 1L, Long::sum);
    }

    public AcumuladorEstadistico combinar(AcumuladorEstadistico otro) {
        valores.addAll(otro.valores);

        otro.frecuencias.forEach(
            (valor, cantidad) -> frecuencias.merge(valor, cantidad, Long::sum)
        );

        return this;
    }

    public ResultadoEstadistico calcularResultado() {
        List<Double> valoresOrdenados = valores.stream()
            .sorted()
            .collect(Collectors.toList());

        double mediana;
        int cantidad = valoresOrdenados.size();

        if (cantidad % 2 == 0) {
            mediana = (
                valoresOrdenados.get(cantidad / 2 - 1) +
                valoresOrdenados.get(cantidad / 2)
            ) / 2;
        } else {
            mediana = valoresOrdenados.get(cantidad / 2);
        }

        double moda = frecuencias.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(0.0);

        return new ResultadoEstadistico(mediana, moda);
    }
}

// Clases del Desafío 7
class EventoLog {

    private LocalDateTime timestamp;
    private String usuario;
    private String accion;

    public EventoLog(LocalDateTime timestamp, String usuario, String accion) {
        this.timestamp = timestamp;
        this.usuario = usuario;
        this.accion = accion;
    }

    public static EventoLog convertir(String linea) {
        String[] partes = linea.split(",");

        return new EventoLog(
            LocalDateTime.parse(partes[0]),
            partes[1],
            partes[2]
        );
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAccion() {
        return accion;
    }
}

// Clases del Desafío 7
class VentanaUsuarios {

    private Map<String, PriorityQueue<EventoLog>> eventosPorUsuario;

    public VentanaUsuarios() {
        this.eventosPorUsuario = new HashMap<>();
    }

    public void procesar(EventoLog evento) {
        PriorityQueue<EventoLog> cola = eventosPorUsuario.computeIfAbsent(
            evento.getUsuario(),
            usuario -> new PriorityQueue<>(
                Comparator.comparing(EventoLog::getTimestamp)
            )
        );

        cola.add(evento);

        LocalDateTime limite = evento.getTimestamp().minusMinutes(5);

        while (!cola.isEmpty() && cola.peek().getTimestamp().isBefore(limite)) {
            cola.poll();
        }

        if (cola.size() > 100) {
            System.out.println(
                "Usuario: " + evento.getUsuario() +
                " supero 100 acciones en 5 minutos. Total: " + cola.size()
            );
        }
    }

    public VentanaUsuarios combinar(VentanaUsuarios otro) {
        otro.eventosPorUsuario.forEach((usuario, cola) ->
            eventosPorUsuario.merge(usuario, cola, (cola1, cola2) -> {
                cola1.addAll(cola2);
                return cola1;
            })
        );

        return this;
    }
}

// Clases del Desafío 9
class CacheSimple {

    private Map<String, Long> cache;

    public CacheSimple() {
        this.cache = new LinkedHashMap<>();
    }

    public void insertar(String clave, long ttlMilisegundos) {
        long expiracion = System.currentTimeMillis() + ttlMilisegundos;
        cache.put(clave, expiracion);
    }

    public Optional<Map.Entry<String, Long>> obtenerMasRecienteNoExpirado() {
        long ahora = System.currentTimeMillis();

        cache.entrySet().removeIf(entrada -> entrada.getValue() <= ahora);

        return cache.entrySet().stream()
            .max(Comparator.comparingInt(
                entrada -> obtenerPosicion(entrada.getKey())
            ));
    }

    private int obtenerPosicion(String clave) {
        List<String> claves = new ArrayList<>(cache.keySet());
        return claves.indexOf(clave);
    }
}

// Clases del Desafío 10
class Venta {

    private String producto;
    private int cantidad;
    private double precioUnitario;
    private LocalDate fecha;

    public Venta(String producto, int cantidad, double precioUnitario, LocalDate fecha) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double calcularIngreso() {
        return cantidad * precioUnitario;
    }

    public int obtenerTrimestre() {
        return (fecha.getMonthValue() - 1) / 3 + 1;
    }
}

// Clases del Desafío 10
class Resumen {

    private double ingresoTotal;
    private Map<Month, Double> ingresoPorMes;
    private double ingresoPrimerTrimestre;
    private double ingresoUltimoTrimestre;
    private Month mesMayorIngreso;
    private String tendencia;

    public Resumen() {
        this.ingresoPorMes = new HashMap<>();
    }

    public static Resumen desdeVenta(Venta venta) {
        Resumen resumen = new Resumen();

        resumen.ingresoTotal = venta.calcularIngreso();

        resumen.ingresoPorMes.put(
            venta.getFecha().getMonth(),
            venta.calcularIngreso()
        );

        if (venta.obtenerTrimestre() == 1) {
            resumen.ingresoPrimerTrimestre = venta.calcularIngreso();
        }

        if (venta.obtenerTrimestre() == 4) {
            resumen.ingresoUltimoTrimestre = venta.calcularIngreso();
        }

        resumen.actualizarDatosCalculados();

        return resumen;
    }

    public Resumen combinar(Resumen otro) {
        this.ingresoTotal += otro.ingresoTotal;

        otro.ingresoPorMes.forEach(
            (mes, ingreso) -> this.ingresoPorMes.merge(mes, ingreso, Double::sum)
        );

        this.ingresoPrimerTrimestre += otro.ingresoPrimerTrimestre;
        this.ingresoUltimoTrimestre += otro.ingresoUltimoTrimestre;

        this.actualizarDatosCalculados();

        return this;
    }

    private void actualizarDatosCalculados() {
        this.mesMayorIngreso = ingresoPorMes.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);

        if (ingresoUltimoTrimestre > ingresoPrimerTrimestre) {
            tendencia = "CRECIENTE";
        } else if (ingresoUltimoTrimestre < ingresoPrimerTrimestre) {
            tendencia = "DECRECIENTE";
        } else {
            tendencia = "ESTABLE";
        }
    }

    public double getIngresoTotal() {
        return ingresoTotal;
    }

    public Month getMesMayorIngreso() {
        return mesMayorIngreso;
    }

    public String getTendencia() {
        return tendencia;
    }
}