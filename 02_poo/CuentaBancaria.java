package poo;

/**
 * Ejemplos de Encapsulación y Modificadores de Acceso
 */
public class CuentaBancaria {
    // Modificadores: private, package-private, protected, public
    
    private String numeroCuenta;
    private double saldo;
    private static final double SALDO_MINIMO = 0.0;
    
    public CuentaBancaria(String numeroCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        if (saldoInicial >= SALDO_MINIMO) {
            this.saldo = saldoInicial;
        } else {
            this.saldo = 0.0;
        }
    }
    
    // Getter para saldo (solo lectura)
    public double getSaldo() {
        return saldo;
    }
    
    // Getter para numeroCuenta
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    // Métodos públicos para controlar las operaciones
    public void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.println("Depósito exitoso: $" + cantidad);
        } else {
            System.out.println("La cantidad debe ser positiva");
        }
    }
    
    public boolean retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            System.out.println("Retiro exitoso: $" + cantidad);
            return true;
        } else {
            System.out.println("Fondos insuficientes o cantidad inválida");
            return false;
        }
    }
    
    public void transferir(CuentaBancaria destino, double cantidad) {
        if (this.retirar(cantidad)) {
            destino.depositar(cantidad);
            System.out.println("Transferencia realizada: $" + cantidad);
        }
    }
    
    @Override
    public String toString() {
        return "Cuenta: " + numeroCuenta + ", Saldo: $" + saldo;
    }
    
    public static void main(String[] args) {
        System.out.println("=== ENCAPSULACIÓN ===\n");
        
        CuentaBancaria cuenta1 = new CuentaBancaria("001-001", 1000.0);
        CuentaBancaria cuenta2 = new CuentaBancaria("002-002", 500.0);
        
        System.out.println(cuenta1);
        System.out.println(cuenta2);
        
        cuenta1.depositar(200);
        cuenta1.retirar(150);
        System.out.println(cuenta1);
        
        System.out.println("\nTransferencia:");
        cuenta1.transferir(cuenta2, 300);
        System.out.println(cuenta1);
        System.out.println(cuenta2);
    }
}
