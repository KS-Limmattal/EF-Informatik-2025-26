import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class Mahatva {
      /*
       * - Genau genommen haben wir nicht die Garantie, dass digits Stellen korrekt
       * sind, sondern dass der Fehler
       * kleiner als precision ist.
       * - Die Genauigkeit wird ausgerechnet.
       * - pi() vermeidet die Kompliziertheit des Vorzeichenwechsels durch
       * Zusammenfassen jeder Addition mit der
       * folgenden Subtraktion.
       * Zusatzaufgabe:
       * - Wenn digits > 8 ist, wird int i einen integer overflow auslösen, da zu
       * grosse Werte für i benötigt werden.
       * Deshalb müssen wir für i den Datentyp long wählen, falls wir mehr digits als
       * 8 berechnen wollen.
       * - 15 Dezimalstellen ist das double precision limit. pi(12) läuft auf meinem
       * Computer ca. 3.5h,
       * d.h. pi(14) (=15 Stellen mit der Vorkommastelle) bräuchte ca. 14 Tage. Der
       * Datentyp double ist also nicht der
       * limitierende Faktor.
       */

      static class mahatvaTask extends RecursiveTask<Double> {
            private long start, end;
            int splitDepth;

            mahatvaTask(long start, long end, int splitDepth) {
                  this.start = start;
                  this.end = end;
                  this.splitDepth = splitDepth;
            }

            protected Double compute() {
                  if (splitDepth == 0) {
                        double sum = 0;
                        for (long i = start; i < end; i = i + 4) {
                              sum = sum + 1. / i - 1. / (i + 2);
                        }

                        return sum;
                  } else {
                        long mid = (start + end) / 2;
                        mid = (mid / 4) * 4 + 1;
                        mahatvaTask leftTask = new mahatvaTask(start, mid, splitDepth - 1);
                        mahatvaTask rightTask = new mahatvaTask(mid, end, splitDepth - 1);

                        leftTask.fork();
                        double rightResult = rightTask.compute();
                        double leftResult = leftTask.join();

                        return leftResult + rightResult;
                  }
            }
      }

      public static double pi(int digits, int splitDepth) {
            // calculate how many elements of the series need to be calculated
            long n = (long) Math.pow(10, digits) * 4 + 2; // For 1 / i < 1 / n, we need to have an i > n. Hence the "+2"

            ForkJoinPool pool = new ForkJoinPool();
            mahatvaTask task = new mahatvaTask(1, n, splitDepth);
            double pi = pool.invoke(task) * 4;
            pool.close();

            return pi;
      }

      public static void main(String[] args) {
            int digits = 10;
            for (int n = 0; n < 100; n++) {
                  // Zeit stoppen
                  long start = System.nanoTime();
                  double pi = pi(digits, n);
                  long finish = System.nanoTime();
                  double secondsElapsed = (finish - start) / 1000000000d;

                  System.out.println("Pi approximated to " + digits + " digits in 2^" + n + " = " + (int) Math.pow(2, n) + " task(s) is " + pi + " in " + secondsElapsed
                              + " seconds");
            }
            System.out.println();
      }
}