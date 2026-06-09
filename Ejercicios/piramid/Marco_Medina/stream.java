/*Desafíos de stream y lambdas en Java Everyone:
 
---
 
Desafío 1: Transformación y filtrado múltiple con objetos complejos
 
Enunciado:  
Tienes una lista de Persona (nombre, edad, ciudad, salario, lista de hobbies).  
Debes obtener:  
- Las 3 personas más jóvenes que vivan en "Madrid" o "Barcelona", con salario > 30000, ordenadas por salario descendente.  
- De ellas, extraer un Map<String, List<String>> donde la clave es el nombre y el valor es la lista de hobbies en mayúsculas.
 
Requisito técnico:  
Usar Collectors.toMap, skip, limit, filter, map, sorted.
 
---
 
Desafío 2: Estadísticas en tiempo real sobre streams infinitos
 
Enunciado:  
Genera un stream infinito de números enteros aleatorios entre 1 y 100 (usa Random.ints()).  
Procesa los primeros 10,000 números y calcula:  
- Promedio de los números pares.  
- Número más alto entre los múltiplos de 7.  
- Cantidad de números que son primos.  
Todo en un solo pipeline sin crear colecciones intermedias.
 
Requisito técnico:  
Usar IntStream, limit, filter, summaryStatistics o reduce / collect.
 
---
 
Desafío 3: Aplanamiento y agrupación compleja
 
Enunciado:  
Dada una lista de Empresa (nombre, lista de Departamento, cada departamento tiene lista de Empleado con nombre y salario).  
Calcula el salario promedio por empresa, pero excluyendo al empleado mejor pagado de cada departamento (outlier).
 
Requisito técnico:  
Usar flatMap, groupingBy, collectingAndThen, maxBy, filter sobre streams internos.
 
---
 
Desafío 4: Particionamiento y transformación paralela
 
Enunciado:  
Tienes un List<String> de frases largas.  
Debes:  
1. Dividir en dos grupos (partición): frases con más de 10 palabras y el resto.  
2. De cada grupo, quedarte con las 5 frases más cortas (por longitud de caracteres).  
3. Transformar cada frase a Map<Character, Long> (frecuencia de cada letra ignorando espacios).  
4. Combinar los mapas de cada partición sumando frecuencias de letras.
 
Requisito técnico:  
Usar partitioningBy, collect personalizado, toMap con fusión, parallelStream.
 
---
 
Desafío 5: Simulación de pipeline ETL con logging y errores
 
Enunciado:  
Simula un stream de String que representan transacciones bancarias: "ID, monto, tipo(DEP/WD)"  
Ejemplo: "T1001,250.50,DEP", "T1002,0,WD" (monto 0 inválido).  
Procesa validando:  
- Si monto <= 0 → lanza una excepción chequeada personalizada, pero captúrala dentro del stream y loguea el error sin detener el flujo.  
- Convierte a Transaction objeto.  
- Filtra solo DEP y suma montos agrupando por ID (en realidad agrupa por rango de montos: <100, 100-500, >500).
 
Requisito técnico:  
Usar flatMap para manejo de errores con Try (de Vavr o simular con Optional), groupingBy con clasificador personalizado.
 
---
 
Desafío 6: Custom Collector para mediana y moda
 
Enunciado:  
Implementa un collector personalizado que calcule simultáneamente:  
- Mediana de una lista de Double.  
- Moda (valor más frecuente).  
 
Luego aplícalo a un stream de 1 millón de números aleatorios.
 
Requisito técnico:  
Crear Collector<T, A, R> manual con Supplier, Accumulator, Combiner, Finisher.
 
---
 
Desafío 7: Ventana deslizante temporal sobre logs
 
Enunciado:  
Tienes un archivo enorme de logs (no cabe en memoria). Cada línea: timestamp, usuario, acción.  
Usa Files.lines() para procesar en streaming.  
Define una ventana deslizante de 5 minutos: por cada nuevo evento, calcula cuántas acciones realizó el mismo usuario en los últimos 5 minutos.  
Imprime solo si supera 100 acciones en la ventana.
 
Requisito técnico:  
Usar PriorityQueue dentro de un collect personalizado o map con estado.
 
---
 
Desafío 8: Stream de combinaciones y permutaciones
 
Enunciado:  
Dada una lista de enteros [1,2,3,4,5], genera todas las combinaciones de tamaño 3 (no repetición).  
Para cada combinación, calcula:  
- Suma de elementos.  
- Producto.  
- ¿Es la suma un número primo?  
 
Filtra solo combinaciones donde el producto sea mayor que 10 y la suma sea par.  
Devuelve el resultado como List<int[]>.
 
Requisito técnico:  
Usar IntStream.range + flatMap anidados para generar combinaciones.  
Nada de bucles for explícitos.
 
---
 
Desafío 9: Cache con expiración usando Streams
 
Enunciado:  
Implementa un caché simple Map<String, Long> donde el valor es el timestamp de expiración (now + TTL).  
Cada vez que accedes, debes:  
- Filtrar entradas no expiradas.  
- Devolver el valor más recientemente insertado (por clave ordenada por inserción).  
 
Hazlo usando un stream que opere en el entrySet y maneje la expiración.
 
Requisito técnico:  
Usar removeIf antes de cualquier operación, luego max con comparador personalizado.
 
---
 
Desafío 10: Reducción compleja con fusiones
 
Enunciado:  
Tienes un stream de Venta (producto, cantidad, precioUnitario, fecha).  
Debes agrupar por producto y calcular:  
- Ingreso total (cantidad * precio).  
- Mes con mayor ingreso para ese producto.  
- Tendencia: comparar ingreso primer trimestre vs último trimestre.
 
Devuelve Map<Producto, Resumen> donde Resumen es una clase con esos tres campos.
 
Requisito técnico:  
Usar Collectors.toMap con funciones de merge, Collectors.groupingBy anidado, maxBy con fecha, reducing.
*/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class stream {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Desafío 1 ---");
        desafio1();
        System.out.println("\n--- Desafío 2 ---");
        desafio2();
        System.out.println("\n--- Desafío 3 ---");
        desafio3();
        System.out.println("\n--- Desafío 4 ---");
        desafio4();
        System.out.println("\n--- Desafío 5 ---");
        desafio5();
        System.out.println("\n--- Desafío 6 ---");
        desafio6();
        System.out.println("\n--- Desafío 7 ---");
        desafio7();
        System.out.println("\n--- Desafío 8 ---");
        desafio8();
        System.out.println("\n--- Desafío 9 ---");
        desafio9();
        System.out.println("\n--- Desafío 10 ---");
        desafio10();
    }

    static void desafio1() {
        List<Persona> personas = Arrays.asList(
            new Persona("Maria", 25, "Madrid", 35000, Arrays.asList("futbol", "cine")),
            new Persona("Luis", 30, "Barcelona", 40000, Arrays.asList("lectura", "viajar")),
            new Persona("Carmen", 22, "Madrid", 32000, Arrays.asList("musica", "deporte")),
            new Persona("David", 28, "Barcelona", 29000, Arrays.asList("cocina", "arte")),
            new Persona("Eve", 24, "Madrid", 31000, Arrays.asList("tecnologia", "juegos"))
        );

        Map<String, List<String>> resultado = personas.stream()
            .filter(p -> ("Madrid".equals(p.getCiudad()) || "Barcelona".equals(p.getCiudad())) && p.getSalario() > 30000)
            .sorted(Comparator.comparingInt(Persona::getEdad))
            .limit(3)
            .sorted(Comparator.comparingDouble(Persona::getSalario).reversed())
            .collect(Collectors.toMap(
                Persona::getNombre,
                p -> p.getHobbies().stream().map(String::toUpperCase).collect(Collectors.toList()),
                (a, b) -> a,
                LinkedHashMap::new
            ));

        System.out.println(resultado);
    }

    static class Persona {
        private final String nombre;
        private final int edad;
        private final String ciudad;
        private final double salario;
        private final List<String> hobbies;

        public Persona(String nombre, int edad, String ciudad, double salario, List<String> hobbies) {
            this.nombre = nombre;
            this.edad = edad;
            this.ciudad = ciudad;
            this.salario = salario;
            this.hobbies = hobbies;
        }

        public String getNombre() { return nombre; }
        public int getEdad() { return edad; }
        public String getCiudad() { return ciudad; }
        public double getSalario() { return salario; }
        public List<String> getHobbies() { return hobbies; }
    }

    static void desafio2() {
        Random random = new Random(123);
        StatsAccumulator stats = random.ints(1, 101)
            .limit(10000)
            .collect(StatsAccumulator::new,
                StatsAccumulator::accept,
                StatsAccumulator::combine
            );

        double promedioPares = stats.countPares > 0 ? stats.sumPares / (double) stats.countPares : 0.0;
        System.out.println("Promedio pares: " + promedioPares);
        System.out.println("Máximo múltiplo de 7: " + stats.maxMultiplo7);
        System.out.println("Cantidad de primos: " + stats.cantidadPrimos);
    }

    static class StatsAccumulator {
        double sumPares;
        long countPares;
        int maxMultiplo7;
        long cantidadPrimos;

        void accept(int n) {
            if (n % 2 == 0) {
                sumPares += n;
                countPares++;
            }
            if (n % 7 == 0) {
                maxMultiplo7 = Math.max(maxMultiplo7, n);
            }
            if (esPrimo(n)) {
                cantidadPrimos++;
            }
        }

        void combine(StatsAccumulator other) {
            sumPares += other.sumPares;
            countPares += other.countPares;
            maxMultiplo7 = Math.max(maxMultiplo7, other.maxMultiplo7);
            cantidadPrimos += other.cantidadPrimos;
        }
    }

    private static boolean esPrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    static void desafio3() {
        List<Empresa> empresas = Arrays.asList(
            new Empresa("TecnoSoft", Arrays.asList(
                new Departamento("Desarrollo", Arrays.asList(
                    new Empleado("Ana", 52000),
                    new Empleado("Javier", 75000),
                    new Empleado("Silvia", 46000)
                )),
                new Departamento("Marketing", Arrays.asList(
                    new Empleado("Lola", 39000),
                    new Empleado("Miguel", 61000)
                ))
            )),
            new Empresa("EcoFoods", Arrays.asList(
                new Departamento("Operaciones", Arrays.asList(
                    new Empleado("Carlos", 32000),
                    new Empleado("Sofia", 28000),
                    new Empleado("Pedro", 26000)
                )),
                new Departamento("Ventas", Arrays.asList(
                    new Empleado("Raul", 48000),
                    new Empleado("Marta", 53000)
                ))
            ))
        );

        Map<String, Double> promedioPorEmpresa = empresas.stream()
            .collect(Collectors.toMap(
                Empresa::getNombre,
                empresa -> empresa.getDepartamentos().stream()
                    .mapToDouble(departamento -> departamento.getEmpleados().stream()
                        .collect(Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparingDouble(Empleado::getSalario)),
                            maxEmpleado -> departamento.getEmpleados().stream()
                                .filter(e -> !e.equals(maxEmpleado.orElse(null)))
                                .mapToDouble(Empleado::getSalario)
                                .average()
                                .orElse(0)
                        ))
                    )
                    .average()
                    .orElse(0)
            ));

        System.out.println(promedioPorEmpresa);
    }

    static class Empresa {
        private final String nombre;
        private final List<Departamento> departamentos;

        public Empresa(String nombre, List<Departamento> departamentos) {
            this.nombre = nombre;
            this.departamentos = departamentos;
        }

        public String getNombre() { return nombre; }
        public List<Departamento> getDepartamentos() { return departamentos; }
    }

    static class Departamento {
        private final String nombre;
        private final List<Empleado> empleados;

        public Departamento(String nombre, List<Empleado> empleados) {
            this.nombre = nombre;
            this.empleados = empleados;
        }

        public String getNombre() { return nombre; }
        public List<Empleado> getEmpleados() { return empleados; }
    }

    static class Empleado {
        private final String nombre;
        private final double salario;

        public Empleado(String nombre, double salario) {
            this.nombre = nombre;
            this.salario = salario;
        }

        public String getNombre() { return nombre; }
        public double getSalario() { return salario; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Empleado)) return false;
            Empleado empleado = (Empleado) o;
            return Double.compare(empleado.salario, salario) == 0 && Objects.equals(nombre, empleado.nombre);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nombre, salario);
        }
    }

    static void desafio4() {
        List<String> frases = Arrays.asList(
            "Esta es una frase con pocas palabras",
            "En paralelo procesamos las frases más cortas y sumamos frecuencias de caracteres",
            "Un ejemplo interesante de stream paralelo con particionamiento",
            "Java ofrece colecciones y streams para transformaciones complejas",
            "El reto pide particionar en frases con más de diez palabras y el resto",
            "Otro texto de ejemplo con suficientes palabras para pertenecer a la partición larga",
            "Pequeña frase corta",
            "Frase con siete palabras exactas aquí mismo",
            "Un mensaje realmente corto",
            "Esta es otra frase que probablemente pertenezca al grupo largo"
        );

        Map<Boolean, Map<Character, Long>> frecuenciasPorParticion = frases.parallelStream()
            .collect(Collectors.partitioningBy(
                frase -> frase.split("\\s+").length > 10,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    lista -> lista.stream()
                        .sorted(Comparator.comparingInt(String::length))
                        .limit(5)
                        .flatMap(frase -> frecuenciaLetras(frase).entrySet().stream())
                        .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            Long::sum,
                            LinkedHashMap::new
                        ))
                )
            ));

        System.out.println(frecuenciasPorParticion);
    }

    static Map<Character, Long> frecuenciaLetras(String frase) {
        return frase.replace(" ", "").chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(c -> c, LinkedHashMap::new, Collectors.counting()));
    }

    static void desafio5() {
        List<String> transacciones = Arrays.asList(
            "T1001,250.50,DEP",
            "T1002,0,WD",
            "T1003,80,DEP",
            "T1004,600.75,DEP",
            "T1005,-50,DEP",
            "T1006,450,DEP",
            "T1007,50,WD"
        );

        Map<String, Double> sumaPorRango = transacciones.stream()
            .flatMap(linea -> {
                try {
                    return Stream.of(parseTransaction(linea));
                } catch (InvalidTransactionException e) {
                    System.err.println("Error: " + e.getMessage());
                    return Stream.empty();
                }
            })
            .filter(t -> "DEP".equals(t.getTipo()))
            .collect(Collectors.groupingBy(
                t -> rangoMonto(t.getMonto()),
                Collectors.summingDouble(Transaction::getMonto)
            ));

        System.out.println(sumaPorRango);
    }

    static String rangoMonto(double monto) {
        if (monto < 100) return "<100";
        if (monto <= 500) return "100-500";
        return ">500";
    }

    static Transaction parseTransaction(String linea) throws InvalidTransactionException {
        String[] partes = linea.split(",");
        if (partes.length != 3) {
            throw new InvalidTransactionException("Formato inválido: " + linea);
        }
        String id = partes[0].trim();
        double monto;
        try {
            monto = Double.parseDouble(partes[1].trim());
        } catch (NumberFormatException e) {
            throw new InvalidTransactionException("Monto inválido en transacción " + id);
        }
        String tipo = partes[2].trim();
        if (monto <= 0) {
            throw new InvalidTransactionException("Monto inválido <= 0 en " + id);
        }
        if (!"DEP".equals(tipo) && !"WD".equals(tipo)) {
            throw new InvalidTransactionException("Tipo inválido en " + id);
        }
        return new Transaction(id, monto, tipo);
    }

    static class InvalidTransactionException extends Exception {
        public InvalidTransactionException(String message) {
            super(message);
        }
    }

    static class Transaction {
        private final String id;
        private final double monto;
        private final String tipo;

        public Transaction(String id, double monto, String tipo) {
            this.id = id;
            this.monto = monto;
            this.tipo = tipo;
        }

        public String getId() { return id; }
        public double getMonto() { return monto; }
        public String getTipo() { return tipo; }
    }

    static void desafio6() {
        DoubleStats resultado = ThreadLocalRandom.current().doubles(1_000_000, 0, 1000)
            .boxed()
            .collect(DoubleStatsCollector.collector());

        System.out.println("Mediana: " + resultado.getMediana());
        System.out.println("Moda: " + resultado.getModa());
    }

    static class DoubleStats {
        private final double mediana;
        private final double moda;

        public DoubleStats(double mediana, double moda) {
            this.mediana = mediana;
            this.moda = moda;
        }

        public double getMediana() { return mediana; }
        public double getModa() { return moda; }
    }

    static class DoubleStatsCollector implements Collector<Double, DoubleStatsCollector.Accumulator, DoubleStats> {
        static class Accumulator {
            final List<Double> values = new ArrayList<>();
            final Map<Double, Long> frequency = new HashMap<>();

            void add(Double value) {
                values.add(value);
                frequency.merge(value, 1L, Long::sum);
            }

            void combine(Accumulator other) {
                values.addAll(other.values);
                other.frequency.forEach((key, count) -> frequency.merge(key, count, Long::sum));
            }
        }

        static Collector<Double, Accumulator, DoubleStats> collector() {
            return Collector.of(
                Accumulator::new,
                Accumulator::add,
                (left, right) -> { left.combine(right); return left; },
                acc -> {
                    Collections.sort(acc.values);
                    int size = acc.values.size();
                    double median;
                    if (size == 0) {
                        median = 0;
                    } else if (size % 2 == 0) {
                        median = (acc.values.get(size / 2 - 1) + acc.values.get(size / 2)) / 2.0;
                    } else {
                        median = acc.values.get(size / 2);
                    }
                    double mode = acc.frequency.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(0.0);
                    return new DoubleStats(median, mode);
                }
            );
        }

        @Override public java.util.function.Supplier<Accumulator> supplier() { return Accumulator::new; }
        @Override public java.util.function.BiConsumer<Accumulator, Double> accumulator() { return Accumulator::add; }
        @Override public java.util.function.BinaryOperator<Accumulator> combiner() { return (left, right) -> { left.combine(right); return left; }; }
        @Override public java.util.function.Function<Accumulator, DoubleStats> finisher() { return acc -> {
            Collections.sort(acc.values);
            int size = acc.values.size();
            double median;
            if (size == 0) {
                median = 0;
            } else if (size % 2 == 0) {
                median = (acc.values.get(size / 2 - 1) + acc.values.get(size / 2)) / 2.0;
            } else {
                median = acc.values.get(size / 2);
            }
            double mode = acc.frequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0.0);
            return new DoubleStats(median, mode);
        }; }
        @Override public Set<Characteristics> characteristics() { return Collections.emptySet(); }
    }

    static void desafio7() throws IOException {
        List<String> registros = new ArrayList<>();
        Instant base = Instant.now();
        String usuario = "usuario1";
        for (int i = 0; i < 110; i++) {
            Instant timestamp = base.plusSeconds(i * 2);
            registros.add(timestamp.toString() + "," + usuario + ",ACTION");
        }
        for (int i = 0; i < 50; i++) {
            Instant timestamp = base.plusSeconds(600 + i * 10);
            registros.add(timestamp.toString() + ",usuario2,OTHER");
        }

        Path archivo = Files.createTempFile("logs", ".txt");
        Files.write(archivo, registros);

        Files.lines(archivo)
            .map(LogEvent::parse)
            .collect(Collector.of(
                HashMap<String, Deque<Instant>>::new,
                (map, evento) -> {
                    Deque<Instant> ventana = map.computeIfAbsent(evento.usuario, k -> new ArrayDeque<>());
                    ventana.addLast(evento.timestamp);
                    Instant limite = evento.timestamp.minus(Duration.ofMinutes(5));
                    while (!ventana.isEmpty() && ventana.peekFirst().isBefore(limite)) {
                        ventana.removeFirst();
                    }
                    if (ventana.size() > 100) {
                        System.out.println("Usuario " + evento.usuario + " tiene " + ventana.size() + " acciones en los últimos 5 minutos.");
                    }
                },
                (left, right) -> {
                    right.forEach((usuarioKey, deque) -> {
                        Deque<Instant> ventana = left.computeIfAbsent(usuarioKey, k -> new ArrayDeque<>());
                        ventana.addAll(deque);
                    });
                    return left;
                }
            ));
    }

    static class LogEvent {
        final Instant timestamp;
        final String usuario;
        final String accion;

        public LogEvent(Instant timestamp, String usuario, String accion) {
            this.timestamp = timestamp;
            this.usuario = usuario;
            this.accion = accion;
        }

        static LogEvent parse(String linea) {
            String[] partes = linea.split(",");
            return new LogEvent(Instant.parse(partes[0]), partes[1], partes[2]);
        }
    }

    static void desafio8() {
        int[] base = {1, 2, 3, 4, 5};

        List<int[]> combinaciones = IntStream.range(0, base.length)
            .boxed()
            .flatMap(i -> IntStream.range(i + 1, base.length)
                .boxed()
                .flatMap(j -> IntStream.range(j + 1, base.length)
                    .mapToObj(k -> new int[]{base[i], base[j], base[k]})))
            .filter(arr -> {
                int producto = Arrays.stream(arr).reduce(1, (a, b) -> a * b);
                int suma = Arrays.stream(arr).sum();
                return producto > 10 && suma % 2 == 0;
            })
            .collect(Collectors.toList());

        combinaciones.forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    static void desafio9() {
        ExpiringCache cache = new ExpiringCache();
        cache.put("a", 1_000);
        cache.put("b", 5_000);
        cache.put("c", 10_000);

        try {
            Thread.sleep(2);
        } catch (InterruptedException ignored) {
        }

        cache.put("d", 5_000);
        System.out.println("Última entrada válida: " + cache.getLatestValid().orElse("ninguna"));
    }

    static class ExpiringCache {
        private final LinkedHashMap<String, Long> datos = new LinkedHashMap<>();

        public void put(String clave, long ttlMillis) {
            datos.put(clave, System.currentTimeMillis() + ttlMillis);
        }

        public Optional<String> getLatestValid() {
            long ahora = System.currentTimeMillis();
            datos.entrySet().removeIf(entry -> entry.getValue() <= ahora);
            return datos.entrySet().stream()
                .reduce((first, second) -> second)
                .map(entry -> entry.getKey() + "=" + entry.getValue());
        }
    }

    static void desafio10() {
        List<Venta> ventas = Arrays.asList(
            new Venta("ProductoA", 10, 12.5, LocalDate.of(2025, 1, 15)),
            new Venta("ProductoA", 5, 15.0, LocalDate.of(2025, 11, 3)),
            new Venta("ProductoA", 8, 13.0, LocalDate.of(2025, 3, 20)),
            new Venta("ProductoB", 20, 7.5, LocalDate.of(2025, 2, 5)),
            new Venta("ProductoB", 10, 9.0, LocalDate.of(2025, 12, 12)),
            new Venta("ProductoB", 15, 8.0, LocalDate.of(2025, 10, 22)),
            new Venta("ProductoC", 6, 25.0, LocalDate.of(2025, 4, 10))
        );

        Map<String, Resumen> resumenPorProducto = ventas.stream()
            .collect(Collectors.groupingBy(
                Venta::getProducto,
                Collectors.collectingAndThen(Collectors.toList(), lista -> crearResumen(lista))
            ));

        resumenPorProducto.forEach((producto, resumen) -> System.out.println(producto + " => " + resumen));
    }

    static Resumen crearResumen(List<Venta> ventas) {
        double totalIngreso = ventas.stream().mapToDouble(v -> v.getCantidad() * v.getPrecioUnitario()).sum();
        Map<Month, Double> ingresoPorMes = ventas.stream()
            .collect(Collectors.groupingBy(v -> v.getFecha().getMonth(), Collectors.summingDouble(v -> v.getCantidad() * v.getPrecioUnitario())));
        Month mesMayorIngreso = ingresoPorMes.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
        double primerTrimestre = ventas.stream()
            .filter(v -> v.getFecha().getMonthValue() <= 3)
            .mapToDouble(v -> v.getCantidad() * v.getPrecioUnitario())
            .sum();
        double ultimoTrimestre = ventas.stream()
            .filter(v -> v.getFecha().getMonthValue() >= 10)
            .mapToDouble(v -> v.getCantidad() * v.getPrecioUnitario())
            .sum();
        String tendencia = ultimoTrimestre > primerTrimestre ? "CRECIENTE" : ultimoTrimestre < primerTrimestre ? "DESCENDENTE" : "ESTABLE";
        return new Resumen(totalIngreso, mesMayorIngreso, tendencia);
    }

    static class Venta {
        private final String producto;
        private final int cantidad;
        private final double precioUnitario;
        private final LocalDate fecha;

        public Venta(String producto, int cantidad, double precioUnitario, LocalDate fecha) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.fecha = fecha;
        }

        public String getProducto() { return producto; }
        public int getCantidad() { return cantidad; }
        public double getPrecioUnitario() { return precioUnitario; }
        public LocalDate getFecha() { return fecha; }
    }

    static class Resumen {
        private final double ingresoTotal;
        private final Month mesMayorIngreso;
        private final String tendencia;

        public Resumen(double ingresoTotal, Month mesMayorIngreso, String tendencia) {
            this.ingresoTotal = ingresoTotal;
            this.mesMayorIngreso = mesMayorIngreso;
            this.tendencia = tendencia;
        }

        @Override
        public String toString() {
            return "Resumen{ingresoTotal=" + ingresoTotal + ", mesMayorIngreso=" + mesMayorIngreso + ", tendencia='" + tendencia + "'}";
        }
    }
}
