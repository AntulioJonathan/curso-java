/* Ejercicio:
 
Ejercicio: Pirámide de números "escalera" con reglas cambiantes
Escribe un programa en Java que:
 
Solicite al usuario un número entero n (altura de la pirámide, entre 1 y 20).
 
Solicite un carácter opcion que puede ser '1', '2' o '3'.
 
Genere e imprima una pirámide según la opción:

1.	Pirámide de asteriscos centrada ejemplo: *<br> ***<br> *****<br>*******
2.	Pirámide de números donde cada fila i contiene el número i repetido i veces, centrada	ejemplo: 1<br> 2 2<br> 3 3 3<br> 4 4 4 4
3.	Pirámide de números multiplicador: cada fila i contiene números desde i hasta i*i con incrementos de i. Centrada.	ejemplo: Para i=3: 3 6 9
Para n=4: 3<br> 4 8<br> 5 10 15<br>6 12 18 24
*/
import java.util.Scanner;

public class Piramide {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.print("Ingresa la altura n (1 a 20): ");
    int n = sc.nextInt();

    System.out.print("Ingresa la opcion (1, 2 o 3): ");
    int opcion = sc.nextInt();

    for (int i = 1; i <= n; i++) {


      for (int space = 0; space < (n - i); space++) {
        System.out.print(" ");
      }

      switch (opcion) {
        case 1: {
          int cantidad = 2 * i - 1;
          for (int j = 0; j < cantidad; j++) {
            System.out.print("*");
          }
          break;
        }

        case 2: {
          for (int j = 1; j <= i; j++) {
            System.out.print(i);
            if (j < i) System.out.print(" ");
          }
          break;
        }

        case 3: {
          for (int v = i; v <= i * i; v += i) {
            System.out.print(v);
            if (v < i * i) System.out.print(" ");
          }
          break;
        }

        default:
          System.out.print("Opcion invalida");
      }

      System.out.println();
    }

    sc.close();
  }
}