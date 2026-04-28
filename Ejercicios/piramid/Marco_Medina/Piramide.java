import java.util.Scanner;
 
public class Piramide {
    public static void main(String[] args) {
 
        Scanner sc = new Scanner(System.in);
 
        System.out.print("Ingresa la altura (1-20): ");
        int n = sc.nextInt();
 
        System.out.print("Elige opción (1, 2 o 3): ");
        int opcion = sc.nextInt();
 
        for (int i = 1; i <= n; i++) {
 
            // Espacios para centrar
            for (int j = 1; j <= n - i; j++) {
                System.out.print(" ");
            }
 
            // OPCIÓN 1: Asteriscos
            if (opcion == 1) {
                for (int j = 1; j <= (2 * i - 1); j++) {
                    System.out.print("*");
                }
            }
 
            // OPCIÓN 2: Números repetidos
            else if (opcion == 2) {
                for (int j = 1; j <= i; j++) {
                    System.out.print(i + " ");
                }
            }
 
            // OPCIÓN 3: Multiplicador
            else if (opcion == 3) {
                for (int j = 1; j <= i; j++) {
                    System.out.print((i * j) + " ");
                }
            }
 
            System.out.println();
        }
 
        sc.close();
    }
}
 