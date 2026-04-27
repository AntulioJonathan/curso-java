package fundamentos;

import java.util.concurrent.CountDownLatch;

public class StringBufferVsStringBuilder {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== DIFERENCIAS ENTRE StringBuffer Y StringBuilder ===\n");

        // 1. Uso básico (similitudes)
        System.out.println("1. USO BÁSICO (ambos funcionan igual):");
        ejemploUsoBasico();

        // 2. Diferencia de seguridad en hilos (Thread-safety)
        System.out.println("\n2. COMPORTAMIENTO EN ENTORNOS MULTIHILO:");
        ejemploThreadSafety();

        // 3. Comparación de rendimiento (un solo hilo)
        System.out.println("\n3. RENDIMIENTO (UN SOLO HILO):");
        ejemploRendimiento();
    }

    // EJEMPLO 1: Mismos métodos, misma sintaxis
    private static void ejemploUsoBasico() {
        // StringBuffer
        StringBuffer buffer = new StringBuffer("Hola");
        buffer.append(" Mundo");
        buffer.insert(5, "querido ");
        System.out.println("StringBuffer: " + buffer); // Hola querido Mundo

        // StringBuilder
        StringBuilder builder = new StringBuilder("Hola");
        builder.append(" Mundo");
        builder.insert(5, "querido ");
        System.out.println("StringBuilder: " + builder); // Hola querido Mundo
    }

    // EJEMPLO 2: Múltiples hilos modificando el mismo objeto
    private static void ejemploThreadSafety() throws InterruptedException {
        final int ITERACIONES = 1000;
        final int HILOS = 4;

        // CASO 1: StringBuilder (NO thread-safe)
        StringBuilder sbBuilder = new StringBuilder();
        CountDownLatch latchBuilder = new CountDownLatch(HILOS);

        for (int i = 0; i < HILOS; i++) {
            new Thread(() -> {
                for (int j = 0; j < ITERACIONES; j++) {
                    sbBuilder.append("a");
                }
                latchBuilder.countDown();
            }).start();
        }
        latchBuilder.await();
        int lengthBuilder = sbBuilder.length();
        int esperado = HILOS * ITERACIONES;
        System.out.printf("StringBuilder -> Longitud real: %d | Longitud esperada: %d | Diferencia: %d%n",
                lengthBuilder, esperado, esperado - lengthBuilder);

        // CASO 2: StringBuffer (thread-safe)
        StringBuffer sbBuffer = new StringBuffer();
        CountDownLatch latchBuffer = new CountDownLatch(HILOS);

        for (int i = 0; i < HILOS; i++) {
            new Thread(() -> {
                for (int j = 0; j < ITERACIONES; j++) {
                    sbBuffer.append("a");
                }
                latchBuffer.countDown();
            }).start();
        }
        latchBuffer.await();
        int lengthBuffer = sbBuffer.length();
        System.out.printf("StringBuffer  -> Longitud real: %d | Longitud esperada: %d | Diferencia: %d%n",
                lengthBuffer, esperado, esperado - lengthBuffer);
    }

    // EJEMPLO 3: Comparación de velocidad (un solo hilo)
    private static void ejemploRendimiento() {
        final int ITERACIONES = 100_000;

        // StringBuilder
        long inicioBuilder = System.nanoTime();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ITERACIONES; i++) {
            builder.append(i);
        }
        long finBuilder = System.nanoTime();
        long tiempoBuilder = finBuilder - inicioBuilder;

        // StringBuffer
        long inicioBuffer = System.nanoTime();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < ITERACIONES; i++) {
            buffer.append(i);
        }
        long finBuffer = System.nanoTime();
        long tiempoBuffer = finBuffer - inicioBuffer;

        System.out.printf("StringBuilder: %d ns (%d ms)%n", tiempoBuilder, tiempoBuilder / 1_000_000);
        System.out.printf("StringBuffer:  %d ns (%d ms)%n", tiempoBuffer, tiempoBuffer / 1_000_000);

        if (tiempoBuilder < tiempoBuffer) {
            System.out.printf("StringBuilder fue ~%.2f veces más rápido%n",
                    (double) tiempoBuffer / tiempoBuilder);
        } else {
            System.out.printf("StringBuffer fue ~%.2f veces más rápido%n",
                    (double) tiempoBuilder / tiempoBuffer);
        }
    }
}