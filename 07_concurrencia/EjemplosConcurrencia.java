package concurrencia;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ejemplos de Concurrencia en Java
 * - Hilos (Thread, Runnable)
 * - Sincronización
 * - ExecutorService
 * - Estructuras concurrentes
 */

public class EjemplosConcurrencia {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== CONCURRENCIA ===\n");
        
        ejemploHiloBasico();
        ejemploSincronizacion();
        ejemploExecutorService();
        ejemploEstructurasConcurrentes();
    }
    
    // Ejemplo 1: Crear hilos
    static void ejemploHiloBasico() {
        System.out.println("--- Hilos Básicos ---");
        
        // Forma 1: Extender Thread
        class MiHilo extends Thread {
            private String nombre;
            
            public MiHilo(String nombre) {
                this.nombre = nombre;
            }
            
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    System.out.println(nombre + " - Iteración " + i);
                    try {
                        Thread.sleep(500);  // Dormir 500ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        // Forma 2: Implementar Runnable (preferido)
        class TareaRunnable implements Runnable {
            private String nombre;
            
            public TareaRunnable(String nombre) {
                this.nombre = nombre;
            }
            
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    System.out.println(nombre + " ejecutando");
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        // Crear y iniciar hilos
        Thread hilo1 = new MiHilo("Hilo-1");
        Thread hilo2 = new Thread(new TareaRunnable("Tarea-1"));
        Thread hilo3 = new Thread(new TareaRunnable("Tarea-2"));
        
        hilo1.start();  // start() no run()
        hilo2.start();
        hilo3.start();
        
        try {
            hilo1.join();  // Esperar a que termine
            hilo2.join();
            hilo3.join();
            System.out.println("Todos los hilos terminaron\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // Ejemplo 2: Sincronización
    static void ejemploSincronizacion() {
        System.out.println("--- Sincronización ---");
        
        class Contador {
            private int valor = 0;
            
            // Método sincronizado
            public synchronized void incrementar() {
                valor++;
                System.out.println(Thread.currentThread().getName() + ": " + valor);
            }
            
            public synchronized int obtener() {
                return valor;
            }
        }
        
        Contador contador = new Contador();
        
        // Crear múltiples hilos que incrementan simultáneamente
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                contador.incrementar();
            }
        }, "Hilo-1");
        
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                contador.incrementar();
            }
        }, "Hilo-2");
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
            System.out.println("Valor final: " + contador.obtener());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
    
    // Ejemplo 3: ExecutorService
    static void ejemploExecutorService() throws InterruptedException {
        System.out.println("--- ExecutorService ---");
        
        // Crear un pool de 3 hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        // Enviar tareas
        for (int i = 0; i < 5; i++) {
            final int id = i;
            executor.execute(() -> {
                System.out.println("Ejecutando tarea " + id + 
                                 " en " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        
        // Esperar a que todas las tareas terminen
        executor.shutdown();
        if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
        System.out.println("Todas las tareas completadas\n");
    }
    
    // Ejemplo 4: Estructuras concurrentes
    static void ejemploEstructurasConcurrentes() throws InterruptedException {
        System.out.println("--- Estructuras Concurrentes ---");
        
        // AtomicInteger (thread-safe sin sincronización)
        System.out.println("AtomicInteger:");
        AtomicInteger contador = new AtomicInteger(0);
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                int valor = contador.incrementAndGet();
                System.out.println("  Valor: " + valor);
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("Valor final: " + contador.get());
        
        // ConcurrentHashMap
        System.out.println("\nConcurrentHashMap:");
        ConcurrentHashMap<String, Integer> mapa = new ConcurrentHashMap<>();
        mapa.put("Ana", 28);
        mapa.put("Carlos", 35);
        mapa.put("Diana", 25);
        
        System.out.println(mapa);
        System.out.println("Salvo para iteración concurrente sin sincronización externa");
        
        // BlockingQueue
        System.out.println("\nBlockingQueue (Productor-Consumidor):");
        BlockingQueue<String> cola = new LinkedBlockingQueue<>(3);
        
        ExecutorService exec = Executors.newFixedThreadPool(2);
        
        // Productor
        exec.execute(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    cola.put("Elemento-" + i);
                    System.out.println("Producido: Elemento-" + i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        // Consumidor
        exec.execute(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    String elemento = cola.take();
                    System.out.println("Consumido: " + elemento);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        exec.shutdown();
        exec.awaitTermination(10, TimeUnit.SECONDS);
        
        // Lock (alternativa a synchronized)
        System.out.println("\nLock:");
        class RecursoConLock {
            private Lock lock = new ReentrantLock();
            private int valor = 0;
            
            public void incrementar() {
                lock.lock();
                try {
                    valor++;
                    System.out.println("  Incrementado a: " + valor);
                } finally {
                    lock.unlock();
                }
            }
        }
        
        RecursoConLock recurso = new RecursoConLock();
        Thread t1 = new Thread(() -> recurso.incrementar());
        Thread t2 = new Thread(() -> recurso.incrementar());
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
