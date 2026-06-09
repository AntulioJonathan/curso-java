import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * Solución a los 10 desafíos de Streams y Lambdas en Java 21.
 * Archivo autocontenido con records, sealed interfaces y collectors personalizados.
 *
 * @author Sebastian Ramos
 */
public class DesafioStreamsLambdas {

    // ==========================================
    // CONSTANTES
    // ==========================================

    private static final double SALARIO_MINIMO = 30_000;
    private static final int TOP_JOVENES = 3;
    private static final int STREAM_LIMIT = 10_000;
    private static final int VENTANA_SEGUNDOS = 300;
    private static final int UMBRAL_ALERTAS = 100;
    private static final int TOP_FRASES_CORTAS = 5;
    private static final int MIN_PALABRAS_FRASE_LARGA = 10;
    private static final int PRODUCTO_MINIMO = 10;
    private static final int MUESTRA_COLLECTOR = 1_000_000;

    // ==========================================
    // ESTRUCTURAS DE DATOS (JAVA 21 RECORDS)
    // ==========================================

    /** Persona con datos demográficos y lista de hobbies. */
    public record Persona(String nombre, int edad, String ciudad, double salario, List<String> hobbies) {}

    /** Empleado con nombre y salario. Usado en la jerarquía Empresa > Departamento > Empleado. */
    public record Empleado(String nombre, double salario) {}

    /** Departamento con su lista de empleados. */
    public record Departamento(String nombre, List<Empleado> empleados) {}

    /** Empresa con su lista de departamentos. */
    public record Empresa(String nombre, List<Departamento> departamentos) {}

    /** Transacción bancaria parseada. */
    public record Transaction(String id, double monto, String tipo) {}

    /** Registro de venta con cálculo de ingreso derivado. */
    public record Venta(String producto, int cantidad, double precioUnitario, LocalDate fecha) {
        public double getIngreso() {
            return cantidad * precioUnitario;
        }
    }

    /** Resumen consolidado de ventas por producto. */
    public record Resumen(double ingresoTotal, Month mesMayorIngreso, String tendencia) {}

    /** Resultado simultáneo de mediana y moda. */
    public record MedianaModaResult(double mediana, double moda) {}

    /** Alerta de actividad excesiva en ventana de tiempo. */
    public record LogAlert(String user, Instant time, int count) {}

    // ==========================================
    // EXCEPCIÓN PERSONALIZADA Y  TRY
    // ==========================================

    /** Excepción chequeada para transacciones con monto inválido. */
    public static class InvalidTransactionException extends Exception {
        public InvalidTransactionException(String message) {
            super(message);
        }
    }

    /**
     * Try para encapsular operaciones que pueden fallar
     * dentro de un pipeline de streams sin romper el flujo.
     * Implementada con sealed interface y pattern matching (Java 21).
     */
    public sealed interface Try<T> {
        record Success<T>(T value) implements Try<T> {}
        record Failure<T>(Throwable exception) implements Try<T> {}

        @FunctionalInterface
        interface ThrowableCallable<T> {
            T call() throws Throwable;
        }

        static <T> Try<T> of(ThrowableCallable<T> callable) {
            try {
                return new Success<>(callable.call());
            } catch (Throwable e) {
                return new Failure<>(e);
            }
        }

        default Optional<T> toOptional(Consumer<Throwable> errorLogger) {
            if (this instanceof Failure<T> f) {
                errorLogger.accept(f.exception());
                return Optional.empty();
            }
            return Optional.of(((Success<T>) this).value());
        }
    }

    // ==========================================
    // UTILIDADES
    // ==========================================

    /** Verifica si un número es primo usando el algoritmo optimizado 6k ± 1. */
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    /** Clasifica un monto en un rango textual para agrupación. */
    private static String clasificarRango(double monto) {
        if (monto < 100) return "<100";
        if (monto <= 500) return "100-500";
        return ">500";
    }

    /** Pausa el hilo actual, restaurando correctamente la bandera de interrupción. */
    private static void dormir(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==========================================
    // PUNTO DE ENTRADA
    // ==========================================

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("EJECUTANDO DESAFÍOS DE STREAMS Y LAMBDAS (JAVA 21)");
        System.out.println("=================================================\n");

        desafio1();
        System.out.println();
        desafio2();
        System.out.println();
        desafio3();
        System.out.println();
        desafio4();
        System.out.println();
        desafio5();
        System.out.println();
        desafio6();
        System.out.println();
        desafio7();
        System.out.println();
        desafio8();
        System.out.println();
        desafio9();
        System.out.println();
        desafio10();

        System.out.println("\n=================================================");
        System.out.println("DESAFÍOS COMPLETADOS CON ÉXITO");
        System.out.println("=================================================");
    }

    // ==========================================
    // DESAFÍO 1: Transformación y filtrado múltiple
    // ==========================================

    /**
     * Las 3 personas más jóvenes de Madrid o Barcelona con salario > 30000,
     * ordenadas por salario descendente. Extrae hobbies en mayúsculas.
     * Usa: Collectors.toMap, skip, limit, filter, map, sorted.
     */
    public static void desafio1() {
        List<Persona> personas = List.of(
            new Persona("Ana", 30, "Madrid", 35000, List.of("Cine", "Baile")),
            new Persona("Luis", 22, "Barcelona", 28000, List.of("Gaming", "Ajedrez")),
            new Persona("Diego", 25, "Madrid", 50000, List.of("Viajar", "Running")),
            new Persona("Elena", 32, "Madrid", 31000, List.of("Running", "Fotografía")),
            new Persona("Roberto", 50, "Barcelona", 55000, List.of("Vela", "Golf", "Viajar")),
            new Persona("Hugo", 24, "Barcelona", 32000, List.of("Cocina", "Running", "Pádel")),
            new Persona("Sofia", 23, "Madrid", 42000, List.of("Lectura", "Pintura"))
        );

        Map<String, List<String>> resultado = personas.stream()
            .filter(p -> "Madrid".equalsIgnoreCase(p.ciudad()) || "Barcelona".equalsIgnoreCase(p.ciudad()))
            .filter(p -> p.salario() > SALARIO_MINIMO)
            .sorted(Comparator.comparingInt(Persona::edad))
            .skip(0)
            .limit(TOP_JOVENES)
            .sorted(Comparator.comparingDouble(Persona::salario).reversed())
            .collect(Collectors.toMap(
                Persona::nombre,
                p -> p.hobbies().stream().map(String::toUpperCase).collect(Collectors.toList()),
                (existing, replacement) -> existing,
                LinkedHashMap::new
            ));

        System.out.println("=== DESAFÍO 1 ===");
        resultado.forEach((nombre, hobbies) ->
            System.out.printf("Nombre: %-6s | Hobbies: %s%n", nombre, hobbies));
    }

    // ==========================================
    // DESAFÍO 2: Estadísticas sobre stream infinito
    // ==========================================

    /**
     * Acumulador mutable para calcular promedio de pares, máx múltiplo de 7
     * y conteo de primos en una sola pasada sobre un IntStream.
     */
    public static class StreamStats {
        private long sumaPares;
        private long conteoPares;
        private int maxMultiplo7 = Integer.MIN_VALUE;
        private long conteoPrimos;

        public void accept(int value) {
            if (value % 2 == 0) {
                sumaPares += value;
                conteoPares++;
            }
            if (value % 7 == 0 && value > maxMultiplo7) {
                maxMultiplo7 = value;
            }
            if (isPrime(value)) {
                conteoPrimos++;
            }
        }

        public void combine(StreamStats other) {
            this.sumaPares += other.sumaPares;
            this.conteoPares += other.conteoPares;
            this.maxMultiplo7 = Math.max(this.maxMultiplo7, other.maxMultiplo7);
            this.conteoPrimos += other.conteoPrimos;
        }

        public double promedioPares() {
            return conteoPares == 0 ? 0.0 : (double) sumaPares / conteoPares;
        }

        public int maxMultiplo7() {
            return maxMultiplo7 == Integer.MIN_VALUE ? -1 : maxMultiplo7;
        }

        public long conteoPrimos() {
            return conteoPrimos;
        }
    }

    /**
     * Stream infinito Random.ints(), límite 10,000.
     * Calcula 3 métricas en un solo pipeline sin colecciones intermedias.
     * Usa: IntStream, limit, filter, collect con acumulador mutable.
     */
    public static void desafio2() {
        StreamStats stats = new Random().ints(1, 101)
            .limit(STREAM_LIMIT)
            .collect(StreamStats::new, StreamStats::accept, StreamStats::combine);

        System.out.println("=== DESAFÍO 2 ===");
        System.out.printf("Promedio de números pares: %.2f%n", stats.promedioPares());
        System.out.printf("Número más alto múltiplo de 7: %d%n", stats.maxMultiplo7());
        System.out.printf("Cantidad de números primos: %d%n", stats.conteoPrimos());
    }

    // ==========================================
    // DESAFÍO 3: Aplanamiento y agrupación compleja
    // ==========================================

    /**
     * Retorna los empleados de un departamento excluyendo al mejor pagado (outlier).
     * Usa: collectingAndThen, maxBy.
     */
    private static Stream<Empleado> empleadosSinOutlier(Departamento depto) {
        List<Empleado> empleados = depto.empleados();
        if (empleados.isEmpty()) return Stream.empty();

        Empleado outlier = empleados.stream()
            .collect(Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparingDouble(Empleado::salario)),
                opt -> opt.orElseThrow()
            ));

        return empleados.stream().filter(e -> e != outlier);
    }

    /**
     * Salario promedio por empresa excluyendo al empleado mejor pagado de cada departamento.
     * Usa: flatMap, groupingBy, collectingAndThen, maxBy, filter sobre streams internos.
     */
    public static void desafio3() {
        List<Empresa> empresas = List.of(
            new Empresa("TechCorp", List.of(
                new Departamento("Desarrollo", List.of(
                    new Empleado("Ana", 4000),
                    new Empleado("Pedro", 6500),
                    new Empleado("Luis", 3500))),
                new Departamento("Soporte", List.of(
                    new Empleado("Carlos", 4500),
                    new Empleado("Marta", 3000))))),
            new Empresa("BizGroup", List.of(
                new Departamento("Ventas", List.of(
                    new Empleado("Jorge", 2500),
                    new Empleado("Lucia", 5500),
                    new Empleado("Maria", 2800))),
                new Departamento("Marketing", List.of(
                    new Empleado("David", 5000),
                    new Empleado("Elena", 3200)))))
        );

        Map<String, Double> salariosPromedio = empresas.stream()
            .collect(Collectors.toMap(
                Empresa::nombre,
                empresa -> empresa.departamentos().stream()
                    .flatMap(DesafioStreamsLambdas::empleadosSinOutlier)
                    .mapToDouble(Empleado::salario)
                    .average()
                    .orElse(0.0)
            ));

        System.out.println("=== DESAFÍO 3 ===");
        salariosPromedio.forEach((emp, avg) ->
            System.out.printf("Empresa: %-10s | Salario promedio (sin outliers): %.2f%n", emp, avg));
    }

    // ==========================================
    // DESAFÍO 4: Particionamiento y transformación paralela
    // ==========================================

    /** Calcula la frecuencia de cada carácter en una frase, ignorando espacios. */
    public static Map<Character, Long> characterFrequency(String phrase) {
        return phrase.chars()
            .filter(c -> !Character.isWhitespace(c))
            .mapToObj(c -> Character.toLowerCase((char) c))
            .collect(Collectors.toMap(
                Function.identity(),
                c -> 1L,
                Long::sum,
                HashMap::new
            ));
    }

    /**
     * Particiona frases en >10 palabras y el resto. Queda con las 5 más cortas de cada grupo.
     * Calcula frecuencias de letras en paralelo y las fusiona sumando.
     * Usa: partitioningBy, Collector.of personalizado, toMap con fusión, parallelStream.
     */
    public static void desafio4() {
        List<String> frases = List.of(
            "El veloz murcielago hindu comia feliz cardo y lúpulo de la hermosa granja de mi tío",
            "Esta es otra frase bastante larga que tiene el propósito de superar las diez palabras para probar el algoritmo",
            "Java veintiuno introduce características espectaculares como records de patrones y plantillas de cadenas que facilitan el desarrollo de software",
            "Cuando se escribe código funcional con streams en Java es vital entender cómo operan las reducciones paralelas",
            "El desarrollo de sistemas de software empresarial escalables requiere una planificación cuidadosa y un diseño muy robusto",
            "Los programadores que dominan las expresiones lambda y los streams pueden resolver problemas complejos con menos líneas de código",
            "Hola mundo desde Java veintiuno",
            "Aprender streams es muy divertido y útil",
            "El código limpio es poesía para los ojos",
            "Programación reactiva con Java moderno",
            "Optimizar streams paralelos requiere medir con calma",
            "La mediana y la moda son métricas interesantes",
            "Caché con expiración usando LinkedHashMap en Java"
        );

        // 1. Particionar usando parallelStream
        Map<Boolean, List<String>> particiones = frases.parallelStream()
            .collect(Collectors.partitioningBy(
                frase -> frase.trim().split("\\s+").length > MIN_PALABRAS_FRASE_LARGA
            ));

        // 2, 3 y 4. Las 5 más cortas, frecuencia de caracteres, fusionar mapas
        Map<Boolean, Map<Character, Long>> resultadoFrecuencias = particiones.entrySet().parallelStream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().stream()
                    .sorted(Comparator.comparingInt(String::length))
                    .limit(TOP_FRASES_CORTAS)
                    .collect(Collector.of(
                        HashMap::new,
                        (acc, frase) -> {
                            Map<Character, Long> freq = characterFrequency(frase);
                            freq.forEach((k, v) -> acc.merge(k, v, Long::sum));
                        },
                        (m1, m2) -> {
                            m2.forEach((k, v) -> m1.merge(k, v, Long::sum));
                            return m1;
                        }
                    ))
            ));

        System.out.println("=== DESAFÍO 4 ===");
        resultadoFrecuencias.forEach((esLarga, freqs) -> {
            String etiqueta = esLarga
                ? "Frases Largas (> 10 palabras)"
                : "Frases Cortas (<= 10 palabras)";
            System.out.println(etiqueta + " - Frecuencia de letras (5 más cortas):");
            System.out.println(freqs);
        });
    }

    // ==========================================
    // DESAFÍO 5: Pipeline ETL con logging y errores
    // ==========================================

    /**
     * Parsea y valida una línea de transacción bancaria.
     * Lanza InvalidTransactionException si el monto es <= 0 o no numérico.
     *
     * @param raw línea CSV con formato "ID,monto,tipo"
     * @return transacción parseada
     * @throws InvalidTransactionException si la validación falla
     */
    private static Transaction parsearTransaccion(String raw) throws InvalidTransactionException {
        String[] parts = raw.split(",");
        if (parts.length < 3) {
            throw new InvalidTransactionException("Formato inválido: " + raw);
        }

        String id = parts[0].trim();
        String tipo = parts[2].trim();

        double monto;
        try {
            monto = Double.parseDouble(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new InvalidTransactionException("Monto no numérico para ID " + id + ": " + parts[1]);
        }

        if (monto <= 0) {
            throw new InvalidTransactionException("Monto menor o igual a cero para ID " + id + ": " + monto);
        }

        return new Transaction(id, monto, tipo);
    }

    /**
     * Simula ETL bancario. Valida monto, captura excepciones sin romper el stream,
     * filtra solo DEP y agrupa por rango de montos (<100, 100-500, >500).
     * Usa: flatMap para manejo de errores con Try, groupingBy con clasificador personalizado.
     */
    public static void desafio5() {
        List<String> transacciones = List.of(
            "T1001,250.50,DEP",
            "T1002,-10.0,WD",
            "T1003,50.0,DEP",
            "T1004,1200.0,DEP",
            "T1005,0.0,DEP",
            "T1006,450.00,DEP",
            "T1007,85.25,DEP",
            "T1008,600.00,WD",
            "T1009,invalido,DEP"
        );

        Map<String, Double> sumasPorRango = transacciones.stream()
            .map(s -> Try.of(() -> parsearTransaccion(s)))
            .flatMap(tryTx -> tryTx.toOptional(
                err -> System.out.println("[LOG ERR] Capturado en stream: " + err.getMessage())
            ).stream())
            .filter(tx -> "DEP".equalsIgnoreCase(tx.tipo()))
            .collect(Collectors.groupingBy(
                tx -> clasificarRango(tx.monto()),
                Collectors.summingDouble(Transaction::monto)
            ));

        System.out.println("=== DESAFÍO 5 ===");
        sumasPorRango.forEach((rango, suma) ->
            System.out.printf("Rango: %s | Suma de DEP: %.2f%n", rango, suma));
    }

    // ==========================================
    // DESAFÍO 6: Custom Collector para mediana y moda
    // ==========================================

    /**
     * Acumulador de estado mutable para el Collector personalizado.
     * Recolecta todos los valores para calcular mediana y moda al finalizar.
     */
    public static class MedianaModaState {
        private final List<Double> values = new ArrayList<>();

        public void add(Double val) {
            values.add(val);
        }

        public MedianaModaState combine(MedianaModaState other) {
            this.values.addAll(other.values);
            return this;
        }

        public MedianaModaResult calculate() {
            if (values.isEmpty()) {
                return new MedianaModaResult(0.0, 0.0);
            }

            // Mediana
            List<Double> sorted = new ArrayList<>(values);
            Collections.sort(sorted);
            int n = sorted.size();
            double mediana = (n % 2 == 0)
                ? (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2.0
                : sorted.get(n / 2);

            // Moda
            double moda = values.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0.0);

            return new MedianaModaResult(mediana, moda);
        }
    }

    /**
     * Collector<Double, MedianaModaState, MedianaModaResult> manual
     * con Supplier, Accumulator, Combiner y Finisher.
     */
    public static class MedianaModaCollector implements Collector<Double, MedianaModaState, MedianaModaResult> {
        @Override
        public Supplier<MedianaModaState> supplier() {
            return MedianaModaState::new;
        }

        @Override
        public BiConsumer<MedianaModaState, Double> accumulator() {
            return MedianaModaState::add;
        }

        @Override
        public BinaryOperator<MedianaModaState> combiner() {
            return MedianaModaState::combine;
        }

        @Override
        public Function<MedianaModaState, MedianaModaResult> finisher() {
            return MedianaModaState::calculate;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Set.of();
        }
    }

    /**
     * Aplica el Collector personalizado a 1 millón de doubles aleatorios.
     * Usa: Collector manual con Supplier, Accumulator, Combiner, Finisher.
     */
    public static void desafio6() {
        MedianaModaResult result = new Random()
            .doubles(MUESTRA_COLLECTOR, 1.0, 50.0)
            .map(d -> Math.round(d * 10.0) / 10.0)
            .boxed()
            .collect(new MedianaModaCollector());

        System.out.println("=== DESAFÍO 6 ===");
        System.out.printf("Mediana calculada: %.2f%n", result.mediana());
        System.out.printf("Moda calculada: %.2f%n", result.moda());
    }

    // ==========================================
    // DESAFÍO 7: Ventana deslizante temporal sobre logs
    // ==========================================

    /**
     * Procesador de logs con ventana deslizante de 5 minutos por usuario.
     * Usa PriorityQueue como estructura de ventana temporal.
     */
    public static class LogProcessor {
        private final Map<String, PriorityQueue<Instant>> userWindows = new HashMap<>();

        public Optional<LogAlert> processLine(String line) {
            try {
                String[] parts = line.split(",");
                if (parts.length < 3) return Optional.empty();

                Instant timestamp = Instant.parse(parts[0].trim());
                String user = parts[1].trim();

                PriorityQueue<Instant> queue = userWindows.computeIfAbsent(user, k -> new PriorityQueue<>());
                queue.add(timestamp);

                // Remover eventos fuera de la ventana deslizante de 5 minutos
                Instant threshold = timestamp.minusSeconds(VENTANA_SEGUNDOS);
                while (!queue.isEmpty() && queue.peek().isBefore(threshold)) {
                    queue.poll();
                }

                int count = queue.size();
                return Optional.of(new LogAlert(user, timestamp, count));
            } catch (Exception e) {
                return Optional.empty();
            }
        }
    }

    /** Genera un archivo de logs temporal con patrones de actividad normal y anómala. */
    private static void generarLogsDePrueba(Path logPath) throws IOException {
        List<String> logLines = new ArrayList<>();
        Instant baseTime = Instant.parse("2026-05-19T18:00:00Z");

        // Usuario normal: 50 acciones cada 10 segundos (no supera umbral)
        for (int i = 0; i < 50; i++) {
            logLines.add(baseTime.plusSeconds(i * 10L) + ",user_normal,VIEW");
        }

        // Usuario spammer: 120 acciones cada 2 segundos (supera 100 en ventana de 5m)
        for (int i = 0; i < 120; i++) {
            logLines.add(baseTime.plusSeconds(i * 2L) + ",user_spammer,POST");
        }

        logLines.sort(Comparator.comparing(line -> Instant.parse(line.split(",")[0])));
        Files.write(logPath, logLines);
    }

    /**
     * Procesa archivo de logs por streaming con Files.lines().
     * Ventana deslizante de 5 minutos: imprime si supera 100 acciones.
     * Usa: PriorityQueue dentro de map con estado.
     */
    public static void desafio7() {
        Path logPath = Path.of("temp_logs.log");

        try {
            generarLogsDePrueba(logPath);
        } catch (IOException e) {
            System.err.println("No se pudo crear el archivo de log de prueba: " + e.getMessage());
            return;
        }

        System.out.println("=== DESAFÍO 7 ===");
        LogProcessor processor = new LogProcessor();
        try (Stream<String> lines = Files.lines(logPath)) {
            lines.map(processor::processLine)
                .flatMap(Optional::stream)
                .filter(alert -> alert.count() > UMBRAL_ALERTAS)
                .forEach(alert -> System.out.printf(
                    "ALERTA: El usuario '%s' realizó %d acciones en los últimos 5 minutos (a las %s)%n",
                    alert.user(), alert.count(), alert.time()));
        } catch (IOException e) {
            System.err.println("Error procesando los logs: " + e.getMessage());
        } finally {
            try { Files.deleteIfExists(logPath); } catch (IOException ignored) {}
        }
    }

    // ==========================================
    // DESAFÍO 8: Combinaciones con streams
    // ==========================================

    /**
     * Genera combinaciones de tamaño 3 sin repetición de [1,2,3,4,5].
     * Filtra producto > 10 y suma par. Sin bucles for explícitos.
     * Usa: IntStream.range + flatMap anidados.
     */
    public static void desafio8() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);

        List<int[]> resultado = IntStream.range(0, list.size())
            .boxed()
            .flatMap(i -> IntStream.range(i + 1, list.size())
                .boxed()
                .flatMap(j -> IntStream.range(j + 1, list.size())
                    .mapToObj(k -> new int[]{list.get(i), list.get(j), list.get(k)})))
            .filter(comb -> {
                int producto = comb[0] * comb[1] * comb[2];
                int suma = comb[0] + comb[1] + comb[2];
                return producto > PRODUCTO_MINIMO && suma % 2 == 0;
            })
            .toList();

        System.out.println("=== DESAFÍO 8 ===");
        resultado.forEach(comb -> {
            int suma = comb[0] + comb[1] + comb[2];
            int producto = comb[0] * comb[1] * comb[2];
            System.out.printf("Combinación: %s | Suma: %2d (primo: %-5b) | Producto: %2d%n",
                Arrays.toString(comb), suma, isPrime(suma), producto);
        });
    }

    // ==========================================
    // DESAFÍO 9: Caché con expiración
    // ==========================================

    /**
     * Caché simple con expiración por TTL basado en LinkedHashMap.
     * Preserva orden de inserción para determinar el elemento más reciente.
     */
    public static class ExpiringCache {
        private final Map<String, Long> cache = new LinkedHashMap<>();

        public void put(String key, long ttlMillis) {
            cache.put(key, System.currentTimeMillis() + ttlMillis);
        }

        /**
         * Filtra entradas expiradas con removeIf, luego devuelve
         * la clave activa más recientemente insertada con max.
         */
        public Optional<String> getMostRecentActive() {
            long now = System.currentTimeMillis();
            cache.entrySet().removeIf(entry -> entry.getValue() <= now);

            List<String> order = new ArrayList<>(cache.keySet());
            return cache.entrySet().stream()
                .max(Comparator.comparingInt(entry -> order.indexOf(entry.getKey())))
                .map(Map.Entry::getKey);
        }

        public Map<String, Long> getCacheState() {
            return new LinkedHashMap<>(cache);
        }
    }

    /**
     * Caché Map<String, Long> con expiración por TTL.
     * Usa: removeIf antes de operar, max con comparador personalizado.
     */
    public static void desafio9() {
        System.out.println("=== DESAFÍO 9 ===");
        ExpiringCache cache = new ExpiringCache();

        System.out.println("Insertando elementos con diferentes TTL...");
        cache.put("item1", 10000);
        dormir(50);
        cache.put("item2", 150);
        dormir(50);
        cache.put("item3", 8000);
        dormir(50);
        cache.put("item4", 12000);

        System.out.println("Estado inicial del caché: " + cache.getCacheState());

        cache.getMostRecentActive().ifPresentOrElse(
            item -> System.out.println("Elemento activo más reciente: " + item),
            () -> System.out.println("Caché vacío o todo expirado")
        );

        System.out.println("Esperando 200 ms (para expirar item2)...");
        dormir(200);

        cache.getMostRecentActive().ifPresentOrElse(
            item -> System.out.println("Elemento activo más reciente después de esperar: " + item),
            () -> System.out.println("Caché vacío o todo expirado")
        );

        System.out.println("Estado final del caché: " + cache.getCacheState());
    }

    // ==========================================
    // DESAFÍO 10: Reducción compleja con fusiones
    // ==========================================

    /** Calcula el ingreso trimestral sumando los meses indicados del mapa. */
    private static double ingresoTrimestral(Map<Month, Double> mensual, Month... meses) {
        return Arrays.stream(meses)
            .mapToDouble(m -> mensual.getOrDefault(m, 0.0))
            .sum();
    }

    /**
     * Agrupa ventas por producto. Calcula ingreso total, mes con mayor ingreso
     * y tendencia Q1 vs Q4.
     * Usa: Collectors.toMap con merge, groupingBy anidado, maxBy, reducing.
     */
    public static void desafio10() {
        List<Venta> ventas = List.of(
            new Venta("Laptop", 1, 1000.0, LocalDate.of(2026, 1, 15)),
            new Venta("Laptop", 2, 1000.0, LocalDate.of(2026, 3, 20)),
            new Venta("Laptop", 1, 1000.0, LocalDate.of(2026, 11, 5)),
            new Venta("Smartphone", 1, 500.0, LocalDate.of(2026, 2, 10)),
            new Venta("Smartphone", 3, 500.0, LocalDate.of(2026, 10, 15)),
            new Venta("Smartphone", 1, 500.0, LocalDate.of(2026, 12, 12)),
            new Venta("Tablet", 2, 300.0, LocalDate.of(2026, 1, 10)),
            new Venta("Tablet", 2, 300.0, LocalDate.of(2026, 12, 5))
        );

        // Agrupación anidada: Producto -> Mes -> Suma de Ingreso
        Map<String, Map<Month, Double>> productMonthRevenue = ventas.stream()
            .collect(Collectors.groupingBy(
                Venta::producto,
                Collectors.groupingBy(
                    v -> v.fecha().getMonth(),
                    Collectors.summingDouble(Venta::getIngreso)
                )
            ));

        // Mapear cada producto a su resumen final
        Map<String, Resumen> resumenes = productMonthRevenue.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> {
                    Map<Month, Double> mensual = entry.getValue();

                    double totalRevenue = mensual.values().stream()
                        .reduce(0.0, Double::sum);

                    Month bestMonth = mensual.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(Month.JANUARY);

                    double q1 = ingresoTrimestral(mensual, Month.JANUARY, Month.FEBRUARY, Month.MARCH);
                    double q4 = ingresoTrimestral(mensual, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
                    String tendencia = q4 > q1 ? "Creciente" : (q4 < q1 ? "Decreciente" : "Estable");

                    return new Resumen(totalRevenue, bestMonth, tendencia);
                }
            ));

        System.out.println("=== DESAFÍO 10 ===");
        resumenes.forEach((prod, res) ->
            System.out.printf("Producto: %-12s | Total: $%-8.2f | Mes Top: %-10s | Tendencia: %s%n",
                prod, res.ingresoTotal(), res.mesMayorIngreso(), res.tendencia()));
    }
}
