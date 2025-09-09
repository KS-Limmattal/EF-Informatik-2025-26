public class Loops {
      // Aufgabe c)
      public static void primeFactorisation(int n) {
            System.out.print("Prime factorisation of " + n + " = ");
            int factor = 2;
            while (n != 1) {
                  if (n % factor == 0) {
                        System.out.print(factor);
                        n = n / factor;
                        if (n != 1) {
                              System.out.print(" * ");
                        }
                  } else {
                        factor++;
                  }
            }
            System.out.println();
      }

      /*
       * Aufgabe d)
       * - Genau genommen haben wir nicht die Garantie, dass digits Stellen korrekt sind, sondern dass der Fehler 
       *   kleiner als precision ist.
       * - Die Genauigkeit wird entweder ausgemessen wie in pi() oder ausgerechnet wie in pi2(). Je nachdem ergibt sich 
       *   eine while- oder eine for-Schleife.
       * - pi2() vermeidet ausserdem die Kompliziertheit des Vorzeichenwechsels durch Zusammenfassen jeder Addition mit der 
       *   folgenden Subtraktion. Das macht den Code nochmals einfacher als in pi(), und die Resultate minim genauer,
       *   da ein (negativer) Summand mehr berechnet wird.
       * Zusatzaufgabe:
       * - Wenn digits > 8 ist, wird int i einen integer overflow auslösen, da zu grosse Werte für i benötigt werden.
       *   Deshalb müssen wir für i den Datentyp long wählen, falls wir mehr digits als 8 berechnen wollen.
       * - 15 Dezimalstellen ist das double precision limit. pi(12) läuft auf meinem Computer ca. 3.5h, 
       *   d.h. pi(14) (=15 Stellen mit der Vorkommastelle) bräuchte ca. 14 Tage. Der Datentyp double ist also nicht der
       *   limitierende Faktor.
       */
      public static double pi(int digits) {  
            // calculate precision as a floating point number
            double precision = Math.pow(0.1, digits) / 4;

            // approximating 1/4 Pi
            double sum = 0;
            double summand = 1;
            long i = 1;
            int sign = 1;
            while (Math.abs(summand) > precision) {
                  summand = sign * 1. / i;
                  sum = sum + summand;
                  sign = -sign;
                  i = i + 2;
            }

            return 4 * sum;
      }

      public static double pi2(int digits) {  
            // calculate how many elements of the series need to be calculated
            long n = (long) Math.pow(10, digits) * 4 + 2; // For 1 / i < 1 / n, we need to have an i > n. Hence the "+ 2"

            // approximating 1/4 Pi
            double sum = 0;
            for (long i = 1; i < n; i = i + 4){
                  sum = sum + 1. / i - 1. / (i + 2);
            }

            return 4 * sum;
      }

      public static void main(String[] args) {
            // Test-Code zu Aufgabe c)
            System.out.println("Aufgabe c)");
            primeFactorisation(12);
            System.out.println();

            // Test-Code zu Aufgabe e)
            System.out.println("Aufgabe e)");
            for (int n = 1; n < 12; n++){
                  // Zeit stoppen
                  long start = System.nanoTime();
                  double pi = pi(n);
                  long finish = System.nanoTime();
                  double secondsElapsed = (finish - start) / 1000000000d;
                  System.out.println("Pi approximated to " + n + " digits with pi()  is " + pi  + " in " + secondsElapsed / 60 + " minutes");
                  System.out.println("Pi approximated to " + n + " digits with pi2() is " + pi2(n));
            }
            System.out.println();
      }
}