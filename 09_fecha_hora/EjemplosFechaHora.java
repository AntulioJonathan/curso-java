package fecha_hora;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Ejemplos de java.time (Java 8+)
 * - LocalDate, LocalTime, LocalDateTime
 * - ZonedDateTime, ZoneId
 * - Period, Duration
 * - DateTimeFormatter
 */

public class EjemplosFechaHora {
    
    public static void main(String[] args) {
        System.out.println("=== FECHA Y HORA (java.time) ===\n");
        
        ejemploLocalDate();
        ejemploLocalTime();
        ejemploLocalDateTime();
        ejemploZonedDateTime();
        ejemploPeriodDuration();
        ejemploFormateo();
    }
    
    static void ejemploLocalDate() {
        System.out.println("--- LocalDate ---");
        
        // Crear LocalDate
        LocalDate hoy = LocalDate.now();
        System.out.println("Hoy: " + hoy);
        
        LocalDate fecha = LocalDate.of(2026, 4, 23);
        System.out.println("Fecha específica: " + fecha);
        
        // Componentes
        System.out.println("Año: " + hoy.getYear());
        System.out.println("Mes: " + hoy.getMonthValue());
        System.out.println("Día: " + hoy.getDayOfMonth());
        System.out.println("Día de la semana: " + hoy.getDayOfWeek());
        
        // Operaciones
        LocalDate mañana = hoy.plusDays(1);
        LocalDate proximaSemana = hoy.plusWeeks(1);
        LocalDate proximo Mes = hoy.plusMonths(1);
        LocalDate proximoAño = hoy.plusYears(1);
        
        System.out.println("Mañana: " + mañana);
        System.out.println("Próxima semana: " + proximaSemana);
        System.out.println("Próximo mes: " + proximoMes);
        
        // Comparar fechas
        System.out.println("¿Hoy < Mañana? " + hoy.isBefore(mañana));
        System.out.println("¿Hoy > Mañana? " + hoy.isAfter(mañana));
        System.out.println("¿Hoy = Hoy? " + hoy.isEqual(hoy));
        
        // Verificar si es bisiesto
        System.out.println("¿2024 es bisiesto? " + LocalDate.of(2024, 1, 1).isLeapYear());
    }
    
    static void ejemploLocalTime() {
        System.out.println("\n--- LocalTime ---");
        
        // Crear LocalTime
        LocalTime ahora = LocalTime.now();
        System.out.println("Hora actual: " + ahora);
        
        LocalTime hora = LocalTime.of(14, 30, 45);
        System.out.println("Hora específica: " + hora);
        
        // Componentes
        System.out.println("Hora: " + ahora.getHour());
        System.out.println("Minuto: " + ahora.getMinute());
        System.out.println("Segundo: " + ahora.getSecond());
        System.out.println("Nano: " + ahora.getNano());
        
        // Operaciones
        LocalTime masTarde = hora.plusHours(2);
        LocalTime masEarly = hora.minusMinutes(15);
        
        System.out.println("2 horas después: " + masTarde);
        System.out.println("15 minutos antes: " + masEarly);
        
        // Comparar
        System.out.println("¿14:30:45 es antes de 16:30:45? " + 
                         hora.isBefore(masTarde));
    }
    
    static void ejemploLocalDateTime() {
        System.out.println("\n--- LocalDateTime ---");
        
        // Crear LocalDateTime
        LocalDateTime ahora = LocalDateTime.now();
        System.out.println("Ahora: " + ahora);
        
        LocalDateTime momento = LocalDateTime.of(2026, 4, 23, 14, 30, 0);
        System.out.println("Momento específico: " + momento);
        
        // Componentes
        System.out.println("Fecha: " + ahora.toLocalDate());
        System.out.println("Hora: " + ahora.toLocalTime());
        
        // Operaciones
        LocalDateTime despues = momento.plusDays(10).plusHours(2);
        System.out.println("10 días y 2 horas después: " + despues);
        
        // Entre dos momentos
        long diasEntre = ChronoUnit.DAYS.between(momento, despues);
        long horasEntre = ChronoUnit.HOURS.between(momento, despues);
        System.out.println("Días entre: " + diasEntre);
        System.out.println("Horas entre: " + horasEntre);
    }
    
    static void ejemploZonedDateTime() {
        System.out.println("\n--- ZonedDateTime ---");
        
        // Obtener zona horaria actual
        LocalDateTime ahoraLocal = LocalDateTime.now();
        ZonedDateTime ahoraEnZona = ZonedDateTime.now();
        System.out.println("Ahora en zona local: " + ahoraEnZona);
        
        // Crear ZonedDateTime con zona específica
        ZoneId zonaMadrid = ZoneId.of("Europe/Madrid");
        ZoneId zonaNewYork = ZoneId.of("America/New_York");
        ZoneId zonaTokio = ZoneId.of("Asia/Tokyo");
        
        ZonedDateTime enMadrid = ZonedDateTime.now(zonaMadrid);
        ZonedDateTime enNewYork = ZonedDateTime.now(zonaNewYork);
        ZonedDateTime enTokio = ZonedDateTime.now(zonaTokio);
        
        System.out.println("Madrid: " + enMadrid);
        System.out.println("Nueva York: " + enNewYork);
        System.out.println("Tokio: " + enTokio);
        
        // Convertir entre zonas
        ZonedDateTime mamadridEnNewYork = enMadrid.withZoneSameInstant(zonaNewYork);
        System.out.println("Tiempo de Madrid ajustado a NY: " + madridEnNewYork);
        
        // Listar zonas disponibles
        System.out.println("\nAlgunas zonas disponibles:");
        ZoneId.getAvailableZoneIds().stream()
                                   .filter(z -> z.startsWith("America") || z.startsWith("Europe"))
                                   .limit(5)
                                   .forEach(System.out::println);
    }
    
    static void ejemploPeriodDuration() {
        System.out.println("\n--- Period y Duration ---");
        
        // Period (para fechas)
        LocalDate inicio = LocalDate.of(2020, 1, 1);
        LocalDate fin = LocalDate.of(2026, 4, 23);
        
        Period periodo = Period.between(inicio, fin);
        System.out.println("Período entre fechas: " + periodo);
        System.out.println("Años: " + periodo.getYears());
        System.out.println("Meses: " + periodo.getMonths());
        System.out.println("Días: " + periodo.getDays());
        
        // Crear Period
        Period dosAños = Period.ofYears(2);
        Period tresOchoMeses = Period.of(1, 8, 15);  // 1 año, 8 meses, 15 días
        
        LocalDate fechaConPeriodo = inicio.plus(dosAños);
        System.out.println("Fecha + 2 años: " + fechaConPeriodo);
        
        // Duration (para tiempos)
        LocalTime mañana = LocalTime.of(9, 0);
        LocalTime tarde = LocalTime.of(17, 30);
        
        Duration duracion = Duration.between(mañana, tarde);
        System.out.println("\nDuración entre horas: " + duracion);
        System.out.println("Horas: " + duracion.toHours());
        System.out.println("Minutos: " + duracion.toMinutes());
        System.out.println("Segundos: " + duracion.getSeconds());
        
        // Crear Duration
        Duration cincoHoras = Duration.ofHours(5);
        Duration treintaMinutos = Duration.ofMinutes(30);
        
        System.out.println("Cinco horas: " + cincoHoras);
        System.out.println("Treinta minutos: " + treintaMinutos);
    }
    
    static void ejemploFormateo() {
        System.out.println("\n--- Formateo con DateTimeFormatter ---");
        
        LocalDate fecha = LocalDate.now();
        LocalDateTime momentoCompleto = LocalDateTime.now();
        LocalTime hora = LocalTime.now();
        
        // Formatadores predefinidos
        System.out.println("ISO_DATE: " + fecha.format(DateTimeFormatter.ISO_DATE));
        System.out.println("ISO_LOCAL_DATE: " + fecha.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("ISO_LOCAL_DATE_TIME: " + 
                         momentoCompleto.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        // Formatadores personalizados
        DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter formato3 = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy", 
                                                                 new java.util.Locale("es"));
        
        System.out.println("dd/MM/yyyy: " + fecha.format(formato1));
        System.out.println("HH:mm:ss: " + hora.format(formato2));
        System.out.println("Formato largo en español: " + fecha.format(formato3));
        
        // Parsing (convertir String a fecha)
        String fechaString = "23/04/2026";
        LocalDate fechaParsed = LocalDate.parse(fechaString, formato1);
        System.out.println("Parseado: " + fechaParsed);
        
        String horaString = "14:30:45";
        LocalTime horaParsed = LocalTime.parse(horaString, formato2);
        System.out.println("Hora parseada: " + horaParsed);
    }
}
