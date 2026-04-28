import java.util.Scanner;

public class PiramideNumeros {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int altura;
        char opcion;

        System.out.print("Ingrese la altura de la pirámide (1 a 20): ");
        altura = scanner.nextInt();

        if (altura < 1 || altura > 20) {
            System.out.println("Altura inválida. Por favor, ingrese un número entre 1 y 20.");
            scanner.close();
            return;
        }

        System.out.println("\nOpciones:");
        System.out.println("1. Pirámide de asteriscos centrada");
        System.out.println("2. Pirámide de números repetidos");
        System.out.println("3. Pirámide de números multiplicador");
        System.out.print("Seleccione una opción (1-3): ");
        opcion = scanner.next().charAt(0);

        System.out.println();

        switch (opcion) {
            case '1':
                //imprimirPiramideAsteriscos
                for (int i = 1; i <= altura; i++) {
                    for (int j = 1; j <= altura - i; j++) {
                        System.out.print(" ");
                    }
                    for (int j = 1; j <= (2 * i - 1); j++) {
                        System.out.print("*");
                    }
                    System.out.println();
                }
                break;

                
            case '2':
                //imprimirPiramideNumerosRepetidos
                for (int i = 1; i<= altura; i++) {
                    for (int j = 1; j <= altura - i; j++) {
                        System.out.print(" ");
                    }

                    for (int j = 1; j <= i; j++) {
                        System.out.print(i + " ");
                    }
                    System.out.println();
                }
                break;


            case '3':
                //imprimirPiramideNumerosMultiplicador
                for (int i = 1; i <= altura; i++) {
                    for (int j = 1; j <= altura - i; j++) {
                        System.out.print(" ");
                    }
                    for (int j = 1; j <= i; j++) {
                        System.out.print((i * j) + " ");
                    }
                    System.out.println();
                }
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione una opción entre 1 y 3.");
        }
        
        scanner.close();
    }
}